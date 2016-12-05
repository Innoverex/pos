package cn.basewin.unionpay.network.remote;

import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

import com.basewin.aidl.OnPBOCListener;
import com.basewin.define.InputPBOCOnlineData;
import com.basewin.define.PBOCTransactionResult;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.setting.CommuParmOtherAyt;
import cn.basewin.unionpay.setting.MerchantSetting;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/25 09:44<br>
 * 描述: 交易的message ，所有的交易报文都继承这个类<br>
 * 推荐 每个项目自己去修改这个类的功能
 */
public abstract class OfflineTradeMessage extends NetMessage implements OnPBOCListener {
    private static final String TAG = OfflineTradeMessage.class.getName();
    private NetResponseListener pboc;
    private Iso8583Manager returnISO; //返回的数据

    @Override
    public boolean checkMAC(Iso8583Manager isoMessage) {
        byte[] macInput = new byte[0];
        try {
            macInput = isoMessage.getMacData("msgid", "63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String calcMAC = null;
        try {
            calcMAC = TradeEncUtil.getMacECB(macInput);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String oldMAC = "";
        try {
            oldMAC = BCDHelper.bcdToString(isoMessage.getBitBytes(64));
        } catch (Exception e) {
            e.printStackTrace();
            TLog.l("checkMAC 没有64域！！！");
        }

        return (oldMAC.equals(calcMAC));
    }

    @Override
    protected Iso8583Manager getBeforEncryption(Map<String, Object> map) throws Exception {
        Iso8583Manager iso8583Manager = new Iso8583Manager(context());
        iso8583Manager.setBit("tpdu", CommuParmOtherAyt.getTPDU());
        iso8583Manager.setBit("header", SettingConstant.getHEADER());
        String serialAuto = SettingConstant.getTraceAuto();
        iso8583Manager.setBit(11, serialAuto);
        iso8583Manager.setBit(41, MerchantSetting.getTerminalNo());
        iso8583Manager.setBit(42, MerchantSetting.getMerchantNo());
        FlowControl.MapHelper.setSerial(serialAuto);
        return iso8583Manager;
    }

    @Override
    protected void beforeRequest(Iso8583Manager iso) {
        super.beforeRequest(iso);
        TransactionDataDao.save(iso);
    }

    @Override
    protected boolean IsMAC() {
        return true;
    }

    @Override
    public void errorNet(int code, String msg) {
    }


    /**
     * 服务器返回数据之后
     * 1.校验数据的正确性
     * 2.保存到数据库
     * 3.跟新冲正数据
     * 出现了错误就返回 false 代表终止 lis 的成功回调
     * 过程没问题就就返回ture 就会执行lis的成功回调
     * 4.ic卡插卡交易 交给pboc流程校验 通过了就返回true 没通过就返回false 并且
     *
     * @param iso 8583解密后的数据包
     * @param lis 网络自定义回调
     * @return
     */
    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {

        TransactionDataDao.save(iso);

        FlowControl.MapHelper.setSerial(iso.getBit(11));//设置流水号给后面的界面使用

        if (checkIC()) {
            try {
                pboc = lis;
                returnISO = iso;
                ServiceManager.getInstence().getPboc().refreshListener(this);
                InputPBOCOnlineData data = new InputPBOCOnlineData(new Intent());
                data.setAuthCode(PosUtil.parseF55(returnISO.getBitBytes(55), false, "91"));
                data.setResponseCode(iso.getBit(39));
                data.setICData(PosUtil.parseF55(returnISO.getBitBytes(55), true, "71", "72"));
                data.setOnlineFlag(true);
                Log.e(TAG, "交易返回数据替换pboc监听 终止 监听 把监听交给了pboc！");
                ServiceManager.getInstence().getPboc().inputOnlineProcessResult(data.getIntent());
            } catch (Exception e) {
                Log.e(TAG, "交易返回数据替换pboc监听出现错误！");
                e.printStackTrace();
            }
            //返回false 终止 成功 回调 。在替换pboc回调的时候把这个请求回调交给pboc回调执行
            return false;
        }
        return true;
    }

    /**
     * 通过原始报文判断这比交易是否是插卡交易
     *
     * @return
     */
    private boolean checkIC() {
        String bit = msg8583Data.getBit(22);
        String _22 = bit.substring(0, 2);
        if (_22.equals("05")) {
            return true;
        }
        return false;
    }

    @Override
    public void onError(Intent intent) throws RemoteException {
        TLog.l("onError");
        //TODO 错误处理
        pboc.onFailure(0, "IC卡交易拒绝");
    }

    @Override
    public void onFindingCard(int i, Intent intent) throws RemoteException {

    }

    @Override
    public void onStartPBOC() throws RemoteException {

    }

    @Override
    public void onSelectApplication(List<String> list) throws RemoteException {

    }

    @Override
    public void onConfirmCertInfo(String s, String s1) throws RemoteException {

    }

    @Override
    public void onConfirmCardInfo(Intent intent) throws RemoteException {

    }

    @Override
    public void onRequestInputPIN(boolean b, int i) throws RemoteException {

    }

    @Override
    public void onAARequestOnlineProcess(Intent intent) throws RemoteException {

    }

    @Override
    public void onTransactionResult(int result, Intent intent) throws RemoteException {
        if (result == PBOCTransactionResult.QPBOC_ARQC) {
            // 非接卡的话在此处获取数据
        } else if (result == PBOCTransactionResult.APPROVED) {
            Log.d(TAG, "IC卡交易成功");
            pboc.onSuccess(returnISO);
            // IC卡交易成功
        } else if (result == PBOCTransactionResult.TERMINATED) {
            // IC卡交易拒绝，如果联机成功了需要冲正
            Log.d(TAG, "IC卡交易拒绝,如果联机成功了需要冲正");
            pboc.onFailure(0, "IC卡交易拒绝");
            //todo 冲正处理？
        }
    }

    @Override
    public void onRequestAmount() throws RemoteException {

    }

    @Override
    public void onReadECBalance(Intent intent) throws RemoteException {

    }

    @Override
    public void onReadCardOfflineRecord(Intent intent) throws RemoteException {

    }
}
