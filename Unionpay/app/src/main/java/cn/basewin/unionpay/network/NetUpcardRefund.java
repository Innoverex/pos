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
import cn.basewin.unionpay.trade.InputDateAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputReferNo;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/4 16:27<br>
 * 描述:  手机芯片退货交易<br>
 */
@AnnotationNet(action = ActionConstant.ACTION_UPCARD_REFUND)
public class NetUpcardRefund extends ReverseTradeMessage {
    private static final String TAG = NetUpcardRefund.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0220");
        iso.setBit(3, "200000");
        String s = (String) map.get(InputMoneyAty.KEY_MONEY);
        iso.setBit(4, PosUtil.yuanTo12(s));
        Card card = (Card) map.get(SwipingCardAty.KEY_CARD);
        iso.setBit(2, card.getPan());
        iso.setBit(14, card.getExpDate());
        String _22 = "96" + PosUtil._22(card).substring(2, 3);
        iso.setBit(22, _22);
        if (card.isIC()) {
            iso.setBit(2, card.getPan());
            iso.setBit(23, card.get23());
            iso.setBinaryBit(55, BCDHelper.StrToBCD(card.get55()));
        }
        iso.setBit(25, "00");
        isoSetTrack2(iso, card.getTrack2ToD());
        if (!card.isIC()) {
            isoSetTrack3(iso, card.getTrack3ToD());
        }
        iso.setBit(37, (String) map.get(InputReferNo.KEY_DATA));
        iso.setBit(49, "156");
        iso.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        iso.setBit(60, "25" + batchAuto + "000" + "6" + "0" + "1");
        iso.setBit(61, "000000" + "000000" + (String) map.get(InputDateAty.KEY_DATA));
        iso.setBit(63, "000");
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
