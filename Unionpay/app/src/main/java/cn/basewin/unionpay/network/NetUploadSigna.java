package cn.basewin.unionpay.network;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * Created by kxf on 2016/8/30.
 */
@AnnotationNet(action = ActionConstant.ACTION_UPLOAD_SIGNA)
public class NetUploadSigna extends OrdinaryMessage {
    private static final String TAG = NetSale.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        TransactionData t = TransactionDataDao.getTranByTraceNO((String) map.get(NetUploadSignaWaitAty.KEY_SIGNA_TRACE));
        iso.setBit("msgid", "0820");
        if (!TextUtils.isEmpty(t.getPan())) {
            iso.setBit(2, t.getPan());
        }
        iso.setBit(4, t.getAmount());

        iso.setBit(11, t.getTrace());

        String field15 = t.getDate();
        if (!TextUtils.isEmpty(field15)) {
            Log.e(TAG, "data field15=" + field15);
            iso.setBit(15, field15);
        }

        String field37 = t.getReferenceNo();
        if (!TextUtils.isEmpty(field37)) {
            Log.e(TAG, "签购单要素 field37=" + field37);
            iso.setBit(37, field37);
        }

        String field55 = (String) map.get(NetUploadSignaWaitAty.KEY_DATA_FIELD55);
        Log.e(TAG, "data field55=" + field55);
        iso.setBinaryBit(55, BCDHelper.StrToBCD(field55));
        iso.setBit(60, "07" + t.getBatch() + "800");
        String field62 = BCDHelper.bcdToString((byte[]) map.get(NetUploadSignaWaitAty.KEY_DATA_SIGNA));
        Log.e(TAG, "data field62=" + field62);
        iso.setBinaryBit(62, BytesUtil.ascii2Bcd(field62));
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