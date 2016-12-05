package cn.basewin.unionpay.network;

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
import cn.basewin.unionpay.utils.FormatUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/15 17:55<br>
 * 描述: 联盟积分消费 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_CUPBONUS)
public class NetCUPBonus extends ReverseTradeMessage {
    private static final String TAG = NetCUPBonus.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Card card = FlowControl.MapHelper.getCard();
        iso.setBit("msgid", "0200");
        iso.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        iso.setBit(4, s);

        String _22 = PosUtil._22(card);
        iso.setBit(22, _22);

        iso.setBit(25, "65");
        iso.setBit(26, "12");

        if (card != null) {
            iso.setBit(14, card.getExpDate());
        }
        if (!card.isIC()) {
            isoSetTrack3(iso, card.getTrack3ToD());
        } else {
            iso.setBit(23, card.get23());
            iso.setBit(55, card.get55());
        }

        isoSetTrack2(iso, card.getTrack2ToD());
        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            iso.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            Log.i(TAG, "PIN from pinpad：" + new String(pin));
        }
        iso.setBit(49, "156");
        iso.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        iso.setBit(60, "22" + batchAuto + "000" + "5" + "0" + "1" + "" + "065");
        String s1 = FormatUtil.alignLeftFillSpace(FlowControl.MapHelper.getGoodsCode(), 30);
//        String s1 = "123456789012345678901234567890";
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
