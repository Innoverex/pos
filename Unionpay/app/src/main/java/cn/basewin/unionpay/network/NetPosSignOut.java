package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.menu.action.MenuPosSignOut;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * Created by kxf on 2016/8/2.
 */
@AnnotationNet(action = ActionConstant.ACTION_SIGN_OUT)
public class NetPosSignOut extends OrdinaryMessage {
    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0820");
        iso.setBit(11, SettingConstant.getTraceAuto());
        iso.setBit(60, "00" + SettingConstant.getBatch() + "002");

        return iso;
    }

    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        boolean b = super.afterRequest(iso, lis);
        if (b) {
            MenuPosSignOut.signOutPos();
            UIHelper.SignInAty(context());
        }
        return b;
    }
}
