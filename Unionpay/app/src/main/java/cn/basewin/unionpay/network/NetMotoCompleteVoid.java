package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * Created by kxf on 2016/8/23.
 * 订购预授权完成撤销
 */
@AnnotationNet(action = ActionConstant.ACTION_MOTO_COMPLETE_VOID)
public class NetMotoCompleteVoid extends ReverseTradeMessage {
    private static final String TAG = NetMotoCompleteVoid.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        String certificate = FlowControl.MapHelper.getTrace();
        TransactionData tranByTraceNO = TransactionDataDao.getTranByTraceNO(certificate);

        iso.setBit("msgid", "0200");
        iso.setBit(2, (String) FlowControl.MapHelper.getMap().get(InputCardNumberAty.KEY_DATA));
        iso.setBit(3, "200000");
        iso.setBit(4, tranByTraceNO.getAmount());
        iso.setBit(22, "012");
        iso.setBit(25, "18");

        iso.setBit(37, tranByTraceNO.getReferenceNo());
        iso.setBit(49, "156");

        iso.setBit(60, "21" + SettingConstant.getBatch() + "000" + "6" + "0");
        iso.setBit(61, SettingConstant.getBatch() + tranByTraceNO.getTrace() + tranByTraceNO.getDate());
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