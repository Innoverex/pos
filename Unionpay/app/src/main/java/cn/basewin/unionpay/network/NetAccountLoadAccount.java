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
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/23 17:02<br>
 * 描述：磁条卡账户充值
 */
@AnnotationNet(action = ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT)
public class NetAccountLoadAccount extends ReverseTradeMessage {
    private static final String TAG = NetAccountLoadAccount.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Card turnOutCard = FlowControl.MapHelper.getSecondCard();//转出卡
        Card turnInCard = FlowControl.MapHelper.getCard();//转入卡
        packManager.setBit("msgid", "0200");
        packManager.setBit(3, "400000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        packManager.setBit(4, s);

        String _22 = PosUtil._22(turnOutCard);
        packManager.setBit(22, "021");

        packManager.setBit(25, "66");
        packManager.setBit(26, "12");
        if (turnOutCard != null) {
//            packManager.setBit(2, turnOutCard.getPan());
            packManager.setBit(14, turnOutCard.getExpDate());
            isoSetTrack2(packManager, turnOutCard.getTrack2ToD());
            isoSetTrack3(packManager, turnOutCard.getTrack3ToD());
        }

        packManager.setBit(49, "156");
        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            packManager.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            Log.i(TAG, "PIN from pinpad：" + new String(pin));
        }
        packManager.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        packManager.setBit(60, "49" + batchAuto + "000" + "01");
        String bit62 = turnInCard.getPan();
        String bit62Length = FormatUtil.alignRigthFillZero(bit62.length() + "", 3);
        packManager.setBinaryBit(62, (bit62Length + bit62).getBytes());
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
