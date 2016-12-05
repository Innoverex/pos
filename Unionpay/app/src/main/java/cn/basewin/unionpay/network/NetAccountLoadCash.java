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
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/22 14:39<br>
 * 描述：磁条卡现金充值
 */
@AnnotationNet(action = ActionConstant.ACTION_ACCOUNT_LOAD_CASH)
public class NetAccountLoadCash extends ReverseTradeMessage {
    private static final String TAG = NetAccountLoadCash.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Card card = FlowControl.MapHelper.getCard();
        packManager.setBit("msgid", "0220");
        packManager.setBit(3, "630000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        packManager.setBit(4, s);

        String _22 = PosUtil._22(card);
        packManager.setBit(22, _22);

        packManager.setBit(25, "00");

        if (card != null) {
            packManager.setBit(14, card.getExpDate());
        }
        if (!card.isIC()) {
            isoSetTrack3(packManager, card.getTrack3ToD());
        } else {
            packManager.setBit(2, card.getPan());
        }

        isoSetTrack2(packManager, card.getTrack2ToD());

        packManager.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        packManager.setBit(60, "48" + batchAuto + "000" + "01");

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
