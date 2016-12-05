package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/24 17:37<br>
 * 描述: 订购持卡人身份验证 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_MOTO_VERIFY)
public class NetVerify extends OrdinaryMessage {
    private static final String TAG = NetVerify.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        //2、3、11、14、22、25、41、42、48、49、60、62、64
        iso.setBit("msgid", "0100");
        iso.setBit(2, FlowControl.MapHelper.getCardNO());
        iso.setBit(3, "330000");
        iso.setBit(22, "012");
        iso.setBit(25, "00");
        iso.setBit(48, "04");
        iso.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        iso.setBit(60, "01" + batchAuto + "000" + "5" + "0");
        String cv = FlowControl.MapHelper.getCVNCode();
        String sf = FlowControl.MapHelper.getID6();
        String tx = FlowControl.MapHelper.getPhoneNO();
        String nm = FlowControl.MapHelper.getName();
        String s1 = PosUtil.moto62(cv, sf, tx, nm);
        TLog.l("s1:" + s1);
        iso.setBinaryBit(62, s1.getBytes());
        iso.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        Log.d(TAG, "macInput=" + BytesUtil.bytes2HexString(macInput));
        Log.d(TAG, "macInput.length=" + macInput.length);
        Log.d(TAG, "mac=" + mac);
        iso.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return iso;
    }
}
