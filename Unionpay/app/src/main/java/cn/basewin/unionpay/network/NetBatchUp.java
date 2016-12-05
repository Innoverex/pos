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
 * Created by kxf on 2016/8/9.
 * 批上送(批上送金融交易/批上送结束)
 */
@AnnotationNet(action = ActionConstant.ACTION_BATCH_UP_FINANCIAL_TRANS)
public class NetBatchUp extends OrdinaryMessage {

    private static final String TAG = "NetSettlement";

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        Log.e(TAG, "组报文...");
        packManager.setBit("msgid", "0320");
        packManager.setBit(48, (String) map.get("field48"));
        packManager.setBit(49, "159");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        String field60_3 = (String) map.get("field60_3");
        packManager.setBit(60, "00" + batchAuto + field60_3);
        return packManager;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        return true;
    }
}