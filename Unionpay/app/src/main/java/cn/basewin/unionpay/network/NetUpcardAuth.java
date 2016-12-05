package cn.basewin.unionpay.network;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: hanlei <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/3 14:22<br>
 * 描述: 手机芯片预授权报文 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_UPCARD_AUTH)
public class NetUpcardAuth extends ReverseTradeMessage {
    private static final String TAG = NetUpcardAuth.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Card card = FlowControl.MapHelper.getCard();
        packManager.setBit("msgid", "0100");
        if (card != null) {
            packManager.setBit(2, card.getPan());
        }
        packManager.setBit(3, "030000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        packManager.setBit(4, s);
        if (card.getExpDate().length() > 0) {
            packManager.setBit(14, card.getExpDate());
        }

        String _22 = "96" + PosUtil._22(card).substring(2, 3);
        iso.setBit(22, _22);

        packManager.setBit(25, "06");
        packManager.setBit(26, "12");

        if (!card.isIC()) {
            isoSetTrack3(packManager, card.getTrack3ToD());
            if (!TextUtils.isEmpty(card.getTrack3ToD())) {
                Log.e(TAG, "T3=" + card.getTrack3ToD());
                String encT3 = TradeEncUtil.getEncTrack3Standard(card.getTrack3ToD());
                Log.e(TAG, "encT3=" + encT3);
                packManager.setBit(36, encT3);
            }
        } else {
            packManager.setBit(2, card.getPan());
            packManager.setBit(23, card.get23());
            packManager.setBinaryBit(55, BCDHelper.StrToBCD(card.get55()));
        }

        isoSetTrack2(packManager, card.getTrack2ToD());
        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            packManager.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            Log.i(TAG, "PIN from pinpad：" + new String(pin));
        }
        packManager.setBit(49, "156");
        packManager.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        packManager.setBit(60, "10" + batchAuto + "000" + "6" + "0");
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
