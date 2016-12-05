package cn.basewin.unionpay.network;

import android.text.TextUtils;
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
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/3 15:58<br>
 * 描述: 电子现金圈存现金充值撤销报文 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_ECLOAD_CASH_VOID)
public class NetECLoadCashVoid extends ReverseTradeMessage {
    private static final String TAG = NetVoid.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        String certificate = FlowControl.MapHelper.getTrace();
        TransactionData tranByTraceNO = TransactionDataDao.getTranByTraceNO(certificate);

        iso.setBit("msgid", "0200");
        Card card = FlowControl.MapHelper.getCard();
        if (card != null) {
            iso.setBit(2, card.getPan());
        }
        iso.setBit(3, "170000");
        iso.setBit(4, tranByTraceNO.getAmount());
        if (card != null) {
            iso.setBit(14, card.getExpDate());
        }
        String _22 = PosUtil._22(card);
        if ("07".equals(_22.substring(0, 2))) {
            _22 = "98" + _22.substring(2, 3);
        }
        iso.setBit(22, _22);

        if (!card.isIC()) {
            if (!TextUtils.isEmpty(card.getTrack3ToD())) {
                iso.setBit(36, card.getTrack3ToD());
            }
        } else {
            iso.setBit(23, card.get23());
            iso.setBit(55, card.get55());
        }
        iso.setBit(25, "91");

        String track2 = card.getTrack2ToD();
        iso.setBit(35, track2);
        iso.setBit(37, tranByTraceNO.getReferenceNo());
        String referNO = tranByTraceNO.getAuthCode();
        if (referNO != null) {
            iso.setBit(38, referNO);
        }
        iso.setBit(49, "156");

        iso.setBit(60, "51" + SettingConstant.getBatch() + "000" + "6" + "0");
        iso.setBit(61, tranByTraceNO.getBatch() + tranByTraceNO.getTrace());
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
    protected void beforeRequest(Iso8583Manager iso) {
        super.beforeRequest(iso);
    }
}
