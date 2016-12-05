package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/8 17:49<br>
 * 描述: pos签到 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_SIGN_POS)
public class NetPosSignIn extends OrdinaryMessage {

    private static final String TAG = NetPosSignIn.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Log.e(TAG, "组报文...");
        iso.setBit("msgid", "0800");
        iso.setBit(11, SettingConstant.getTraceAuto());
        iso.setBit(60, "00" + SettingConstant.getBatch() + "003");
        String sn = ServiceManager.getInstence().getDeviceinfo().getSN();
        String field62 = "Sequence No" + sn;
        Log.i(TAG, "设备序列号 field62=" + field62);
        iso.setBinaryBit(62, BytesUtil.ascii2Bcd(field62));
//        iso.setBinaryBit(62, BytesUtil.ascii2Bcd("Sequence No12307203567448"));
        iso.setBit(63, SettingConstant.getOPERATOR_NO() + " ");

        return iso;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        boolean b = super.afterRequest(iso, lis);
        if (b) {
            String time = iso.getBit(12);
            String date = iso.getBit(13);
            String dateTime = TDevice.formatDate(date + time);
            Log.e(TAG, "dateTime=" + dateTime);
            byte[] field62 = iso.getBitBytes(62);
            Log.i(TAG, "field62 length = " + field62.length);

            String tmpField62 = BytesUtil.bytes2HexString(field62);

            Log.i(TAG, "field62:" + tmpField62);

            if (TradeEncUtil.updateWorkKey(tmpField62)) {
                Log.e(TAG, context().getString(R.string.jiazaigongzuomiyue) + context().getString(R.string.caozuochenggong));
                TDevice.setSystemTime(context(), null, date, time);
                SettingConstant.setSignTime(TDevice.getSysDate());
                String batchNo = iso.getBit(60).substring(2, 8);
                Log.e(TAG, "batchNo=" + batchNo);
                SettingConstant.setBatch(batchNo);
            } else {
                lis.onFailure(0, context().getString(R.string.jiazaigongzuomiyue) + context().getString(R.string.caozuoshibai));
                return false;
            }
        }
        return b;
    }
}
