package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.TradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputIdCardNumAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.FormatUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/22 14:39<br>
 * 描述：磁条卡账户验证
 */
@AnnotationNet(action = ActionConstant.ACTION_ACCOUNT_LOAD_CONFIRM)
public class NetAccountLoadConfirm extends TradeMessage {
    private static final String TAG = NetAccountLoadConfirm.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Card card = FlowControl.MapHelper.getCard();
        packManager.setBit("msgid", "0100");
        packManager.setBit(3, "330000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));

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
            packManager.setBit(23, card.get23());
            packManager.setBinaryBit(55, BCDHelper.StrToBCD(card.get55()));
            Log.d(TAG, "original field 55:" + card.get55());
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
        packManager.setBit(60, "01" + batchAuto + "000" + "6" + "01");
        String cardType = (String) FlowControl.MapHelper.getMap().get(InputIdCardNumAty.KEY_ID_TYPE);
        String idCard = (String) FlowControl.MapHelper.getMap().get(InputIdCardNumAty.KEY_ID_CARD);
        String bit62 = cardType + idCard;
        String bit62Length = FormatUtil.alignRigthFillZero(bit62.length() + "", 3);
        packManager.setBinaryBit(62, BytesUtil.ascii2Bcd(bit62Length + bit62));

        packManager.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        Log.d(TAG, "macInput=" + BytesUtil.bytes2HexString(macInput));
        Log.d(TAG, "macInput.length=" + macInput.length);
        Log.d(TAG, "mac=" + mac);
        packManager.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return packManager;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        if (super.afterRequest(iso, lis)) {
            double money = Double.parseDouble(new String(iso.getBitBytes(62))) / 100;
            FlowControl.MapHelper.setCanRechargeMoney(money);
            return true;
        } else {
            return false;
        }
    }
}
