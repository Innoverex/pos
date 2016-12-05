package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;

/**
 * Created by kxf on 2016/8/2.
 * 积分签到
 */
@AnnotationNet(action = ActionConstant.ACTION_SIGN_BONUS)
public class NetBounsSign extends OrdinaryMessage {
    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        Iso8583Manager packManager = iso;
        packManager.setBit("msgid", "0820");
        packManager.setBit(22, "021");
        packManager.setBit(60, "00" + SettingConstant.getBatch() + "401");

        return packManager;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        return true;
    }
}
