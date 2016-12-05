package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;

/**
 * Created by kxf on 2016/8/4.
 * 结算
 */
@AnnotationNet(action = ActionConstant.ACTION_SETTLEMENT)
public class NetSettlement extends OrdinaryMessage {

    private static final String TAG = "NetSettlement";

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Log.e(TAG, "组报文...");
        iso.setBit("msgid", "0500");
        iso.setBit(11, SettingConstant.getTraceAuto());
        iso.setBit(48, (String) map.get("field48"));
        iso.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        iso.setBit(60, "00" + batchAuto + "201");
        iso.setBit(63, SettingConstant.getOPERATOR_NO() + " ");

        return iso;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        return true;
    }
}