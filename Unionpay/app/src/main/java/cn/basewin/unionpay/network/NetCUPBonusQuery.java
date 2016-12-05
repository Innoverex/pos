package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/15 16:01<br>
 * 描述: 积分余额查询 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_CUPBONUS_QUERY)
public class NetCUPBonusQuery extends ReverseTradeMessage {
    private static final String TAG = NetCUPBonusQuery.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Card card = FlowControl.MapHelper.getCard();
        iso.setBit("msgid", "0200");
        iso.setBit(3, "310000");

        if (card.getExpDate().length() > 0) {
            iso.setBit(14, card.getExpDate());
        }

        String _22 = PosUtil._22(card);
        iso.setBit(22, _22);

        iso.setBit(25, "65");
        iso.setBit(26, "12");

        if (!card.isIC()) {
            isoSetTrack3(iso, card.getTrack3ToD());
        } else {
            iso.setBit(14, card.getICData());
            iso.setBit(23, card.get23());
            iso.setBit(55, card.get55());
        }

        isoSetTrack2(iso, card.getTrack2ToD());
        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            iso.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
        }
        iso.setBit(49, "156");
        iso.setBit(53, SettingConstant.getSecureSession());
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        iso.setBit(60, "03" + batchAuto + "000" + "5" + "0" + "1" + "" + "065");
        iso.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        Log.d(TAG, "macInput=" + BytesUtil.bytes2HexString(macInput));
        Log.d(TAG, "macInput.length=" + macInput.length);
        Log.d(TAG, "mac=" + mac);
        iso.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return iso;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        boolean checkResult = super.afterRequest(iso, lis);
        if (!checkResult) {
            return false;
        }
        String balance = iso.getBit(54);
        if ('C' == balance.charAt(7)) {
            FlowControl.MapHelper.setBalance(PosUtil.centToYuan(balance.substring(8, 20)));
        }
        TLog.l("积分联盟查询");
        TLog.l("积分联盟查询：" + PosUtil.centToYuan(balance.substring(8, 20)));
        return true;
    }
}
