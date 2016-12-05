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
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputPhoneNoAty;
import cn.basewin.unionpay.trade.InputReservaNoAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * Created by kxf on 2016/8/18.
 * 预约消费
 */
@AnnotationNet(action = ActionConstant.ACTION_RESERVATION_SALE)
public class NetReservationSale extends ReverseTradeMessage {
    private static final String TAG = "NetReservationSale";

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        packManager.setBit("msgid", "0200");
        packManager.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        packManager.setBit(4, s);
        packManager.setBit(22, "921");
        packManager.setBit(25, "67");
        packManager.setBit(26, "12");

        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            packManager.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            Log.i(TAG, "PIN from pinpad：" + new String(pin));
        }
        packManager.setBit(49, "156");
        packManager.setBit(53, "1600000000000000");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        packManager.setBit(60, "54" + batchAuto + "000" + "6" + "0");
        String field62 = "90" + FlowControl.MapHelper.getMap().get(InputPhoneNoAty.KEY_DATA) + FlowControl.MapHelper.getMap().get(InputReservaNoAty.KEY_RESERVA_NO);
        TLog.e(TAG, field62);
        packManager.setBinaryBit(62, BytesUtil.ascii2Bcd(field62));
        packManager.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        Log.d(TAG, "macInput=" + BytesUtil.bytes2HexString(macInput));
        Log.d(TAG, "macInput.length=" + macInput.length);
        Log.d(TAG, "mac=" + mac);
        packManager.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return packManager;
    }
}
