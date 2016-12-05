package cn.basewin.unionpay.network;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BCDHelper;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.trade.NetSettleWaitAty;
import cn.basewin.unionpay.trade.NetUploadTCWaitAty;

/**
 * Created by kxf on 2016/8/23.
 * 9.4.7 基于 PBOC 借/贷记 IC 卡批上送通知交易
 */
@AnnotationNet(action = ActionConstant.ACTION_BATCH_UP_IC_SETTLEMENT)
public class NetICSettlement extends OrdinaryMessage {
    private static final String TAG = "NetSettlement";

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Log.e(TAG, "基于 PBOC 借/贷记 IC 卡批上送通知交易   组报文...");
        TransactionData td = (TransactionData) map.get(NetUploadTCWaitAty.currentTransactionData);
        iso.setBit("msgid", "0320");
        iso.setBit(2, td.getPan());
        iso.setBit(4, td.getAmount());
        iso.setBit(11, td.getTrace());
        iso.setBit(22, td.getServiceCode());
        if (!TextUtils.isEmpty(td.getCardSn())) {
            iso.setBit(23, td.getCardSn());
        }
        if (!TextUtils.isEmpty(td.getField55())) {
            iso.setBinaryBit(55, BCDHelper.StrToBCD(td.getField55()));
        }
        if ((boolean) map.get(NetSettleWaitAty.SETTLEMENT_RESULT)) {
            iso.setBit(60, "00" + td.getBatch() + "203" + "6" + "0");
        } else {
            iso.setBit(60, "00" + td.getBatch() + "205" + "6" + "0");
        }
        String field62 = "61" + "0" + td.getCardType() + "00" + td.getAmount() + "159";
        iso.setBinaryBit(62, BytesUtil.ascii2Bcd(field62));
        return iso;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        return true;
    }
}
