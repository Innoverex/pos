package cn.basewin.unionpay.network.remote;

import android.content.Intent;
import android.util.Log;

import com.basewin.aidl.OnPBOCListener;
import com.basewin.define.InputPBOCOnlineData;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;

import cn.basewin.unionpay.ErrorConstant;
import cn.basewin.unionpay.db.ResponseCodeDao;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/25 09:44<br>
 * 描述: 交易的message ，所有《需要冲正》的交易报文都继承这个类<br>
 * 推荐 每个项目自己去修改这个类的功能
 */
public abstract class ReverseTradeMessage extends TradeMessage implements OnPBOCListener {
    private static final String TAG = ReverseTradeMessage.class.getName();

    @Override
    protected void beforeRequest(Iso8583Manager iso) {
        super.beforeRequest(iso);
    }

    @Override
    public void errorNet(int code, String msg) {
        Reverse.updataBy39("06");
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
        if (!Reverse.checkMsg(msg8583Data, iso)) {
            TLog.l("校验数据失败");
            lis.onFailure(ErrorConstant.NET_CHECK_MAC, ResponseCodeDao.show("A0"));
            return false;
        }
        Reverse.setReverseFlag(false);
        if (!iso.getBit(39).equals("00")) {
            TLog.l("39域不为00");
            lis.onFailure(ErrorConstant.NET_CHECK_MAC, ResponseCodeDao.show(iso.getBit(39)));
            return false;
        }
        if (IsMAC()) {
            if (!checkMAC(iso)) {
                //校验没成功
                TLog.l("mac校验失败");
                Reverse.updataBy39("A0");
                lis.onFailure(ErrorConstant.NET_CHECK_MAC, ResponseCodeDao.show("A0"));
                return false;
            }
        }
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

    @Override
    public void isNetSuccess(boolean b) {
        super.isNetSuccess(b);
        if (b) {
            Reverse.save(msg8583Data);
        }
    }
}
