package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/15 18:17<br>
 * 描述：分期付款消费撤销
 */
@AnnotationNet(action = ActionConstant.ACTION_INSTALLMENT_VOID)
public class NetInstallmentVoid extends ReverseTradeMessage {
    private String TAG = NetInstallmentVoid.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        String certificate = FlowControl.MapHelper.getTrace();
        TransactionData tranByTraceNO = TransactionDataDao.getTranByTraceNO(certificate);

        iso.setBit("msgid", "0200");
        Card card = FlowControl.MapHelper.getCard();
        iso.setBit(2, card.getPan());
        iso.setBit(3, "200000");
        iso.setBit(4, tranByTraceNO.getAmount());
        iso.setBit(14, card.getExpDate());
        String _22 = PosUtil._22(card);
        iso.setBit(22, _22);
        if (!card.isIC()) {
            isoSetTrack3(iso, card.getTrack3ToD());
        } else {
            iso.setBit(23, card.get23());
            iso.setBit(55, card.get55());
        }
        iso.setBit(25, "64");
        iso.setBit(26, "12");

        isoSetTrack2(iso, card.getTrack2ToD());
        iso.setBit(37, tranByTraceNO.getReferenceNo());
        iso.setBit(49, "156");

        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            iso.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
        }
        iso.setBit(53, SettingConstant.getSecureSession());
        iso.setBit(60, "23" + SettingConstant.getBatch() + "000" + "5" + "0" + "0" + "");
        iso.setBit(61, SettingConstant.getBatch() + tranByTraceNO.getTrace());
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
