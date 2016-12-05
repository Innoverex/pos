package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputCVN2Aty;
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.trade.InputID6Aty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputNameAty;
import cn.basewin.unionpay.trade.InputPhoneNoAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * Created by kxf on 2016/8/23.
 * 订购预授权
 */
@AnnotationNet(action = ActionConstant.ACTION_MOTO_AUTH)
public class NetMotoAuth extends ReverseTradeMessage {
    private static final String TAG = NetMotoAuth.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0100");
        iso.setBit(2, (String) FlowControl.MapHelper.getMap().get(InputCardNumberAty.KEY_DATA));
        iso.setBit(3, "030000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        iso.setBit(4, s);
        iso.setBit(22, "012");
        iso.setBit(25, "18");

        iso.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        iso.setBit(60, "10" + batchAuto);
//        String field62 = "01" + "CV003" + map.get(InputCVN2Aty.KEY_DATA)
//                + "SF006" + map.get(InputID6Aty.KEY_DATA)
//                + "TX011" + map.get(InputPhoneNoAty.KEY_DATA)
//                + "NM" + String.format("%d03", ((String) map.get(InputNameAty.KEY_DATA)).length()) + map.get(InputNameAty.KEY_DATA);
        String field62 = PosUtil.moto62((String) map.get(InputCVN2Aty.KEY_DATA),
                (String) map.get(InputID6Aty.KEY_DATA), (String) map.get(InputPhoneNoAty.KEY_DATA),
                (String) map.get(InputNameAty.KEY_DATA));
        iso.setBinaryBit(62, BytesUtil.ascii2Bcd(field62));
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