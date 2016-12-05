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
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/3 15:58<br>
 * 描述: 撤销报文 <br>
 */
@AnnotationNet(action = ActionConstant.ACTION_VOID)
public class NetVoid extends ReverseTradeMessage {
    private static final String TAG = NetVoid.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        String trace = FlowControl.MapHelper.getTrace();
        TransactionData tranByTraceNO = TransactionDataDao.getTranByTraceNO(trace);
        String field22 = null;
        Card card = FlowControl.MapHelper.getCard();
        boolean hasCard = true;
        boolean isIC = false;
        String track2 = null;
        String track3 = null;
        String field2 = null;
        String field23 = null;
        String field14 = null;
        if (card.type == 0) {
            hasCard = false;
        }
        iso.setBit("msgid", "0200");
        iso.setBit(3, "200000");
        iso.setBit(4, tranByTraceNO.getAmount());
        if (hasCard) {
            field14 = card.getExpDate();
            isIC = card.isIC();
            field22 = PosUtil._22(card);
            track2 = card.getTrack2ToD();
            track3 = card.getTrack3ToD();
            field2 = card.getPan();
            field23 = card.get23();
        } else {
            field14 = tranByTraceNO.getExpDate();
            field22 = tranByTraceNO.getServiceCode();
            isIC = PosUtil.isICBy22(field22);
            track2 = tranByTraceNO.getTrack2().replace('=', 'D');
            track3 = tranByTraceNO.getTrack3().replace('=', 'D');
            field2 = tranByTraceNO.getPan();
            field23 = tranByTraceNO.getCardSn();
        }
        iso.setBit(14, field14);
        iso.setBit(22, field22);
        if (!isIC) {
            isoSetTrack3(iso, track3);
        } else {
            iso.setBit(2, field2);
            iso.setBit(23, field23);
        }
        iso.setBit(25, "00");
        iso.setBit(26, "12");

        isoSetTrack2(iso, track2);
        iso.setBit(37, tranByTraceNO.getReferenceNo());
        iso.setBit(49, "156");

        byte[] pin = FlowControl.MapHelper.getPWD();
        if (pin != null) {
            iso.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
        }
        iso.setBit(53, SettingConstant.getSecureSession());
        iso.setBit(60, "23" + SettingConstant.getBatch() + "000" + "6" + "0" + "1");
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

    @Override
    protected void beforeRequest(Iso8583Manager iso) {
        super.beforeRequest(iso);
    }
}
