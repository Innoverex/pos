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
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作   者：hanlei
 * 创建时间：2016/8/9 10:19
 * 描   述：余额查询报文
 */
@AnnotationNet(action = ActionConstant.ACTION_QUERY_BALANCE)
public class NetQueryBalance extends TradeMessage {
    private static final String TAG = NetQueryBalance.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Card card = FlowControl.MapHelper.getCard();
        packManager.setBit("msgid", "0200");
        packManager.setBit(3, "310000");

        if (card.getExpDate().length() > 0) {
            packManager.setBit(14, card.getExpDate());
        }

        String _22 = PosUtil._22(card);
        packManager.setBit(22, _22);

        packManager.setBit(25, "00");
        packManager.setBit(26, "12");

        if (!card.isIC()) {
            isoSetTrack3(packManager, card.getTrack3ToD());
        } else {
            packManager.setBit(2, card.getPan());
            packManager.setBit(23, card.get23());
            Log.e(TAG, "card.get55()=" + card.get55());
            packManager.setBinaryBit(55, BCDHelper.StrToBCD(card.get55()));
        }

        isoSetTrack2(packManager, card.getTrack2ToD());
        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            packManager.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
        }
        packManager.setBit(49, "156");
        packManager.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        packManager.setBit(60, "01" + batchAuto + "000" + "6" + "0");
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
        if (!super.afterRequest(iso, lis)) {
            return false;
        }
        String balance = iso.getBit(54);
        Log.e(TAG, "balance=" + balance);
        if ('C' == balance.charAt(7)) {
            FlowControl.MapHelper.setBalance(PosUtil.centToYuan(balance.substring(8, 20)));
        }
        return true;
    }

    @Override
    public boolean afterRequestByIC(Iso8583Manager iso, NetResponseListener lis) {
        String balance = iso.getBit(54);
        Log.e(TAG, "balance=" + balance);
        if ('C' == balance.charAt(7)) {
            FlowControl.MapHelper.setBalance(PosUtil.centToYuan(balance.substring(8, 20)));
        }
        return super.afterRequestByIC(iso, lis);
    }
}
