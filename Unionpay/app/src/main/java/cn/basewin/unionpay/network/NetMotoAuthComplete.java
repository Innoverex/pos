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
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * Created by kxf on 2016/8/23.
 * 订购预授权完成请求
 */
@AnnotationNet(action = ActionConstant.ACTION_MOTO_AUTH_COMPLETE)
public class NetMotoAuthComplete extends ReverseTradeMessage {
    private static final String TAG = NetMotoAuthComplete.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        TLog.pos(TAG, "Enter 8583 packing of " + TAG);
        iso.setBit("msgid", "0200");
        iso.setBit(2, (String) FlowControl.MapHelper.getMap().get(InputCardNumberAty.KEY_DATA));
        iso.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        iso.setBit(4, s);
        iso.setBit(22, "012");
        iso.setBit(25, "18");

        iso.setBit(38, FlowControl.MapHelper.getAuthCode());

        iso.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        iso.setBit(60, "20" + batchAuto);
        iso.setBit(61, "000000" + "000000" + FlowControl.MapHelper.getDate());
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