package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.OfflineTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.ChooseAuthTypeAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputAuthOrganizationCodeAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;

/**
 * 作   者：hanlei
 * 创建时间：2016/8/17 12:21
 * 描   述：离线结算报文
 */
@AnnotationNet(action = ActionConstant.ACTION_OFFLINE)
public class NetOfflineSettlement extends OfflineTradeMessage {
    private static final String TAG = NetOfflineSettlement.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0220");

        iso.setBit(2, FlowControl.MapHelper.getCardNO());

        iso.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        iso.setBit(4, s);

        iso.setBit(12, TDevice.getSysTime());
        iso.setBit(13, TDevice.getSysDate());

        String expDate = FlowControl.MapHelper.getCardExpDate();
        iso.setBit(14, expDate);

        iso.setBit(22, "012");

        iso.setBit(25, "00");

        String authCode = FlowControl.MapHelper.getAuthCode();
        if (authCode != null) {
            iso.setBit(38, authCode);
        }

        iso.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        iso.setBit(60, "30" + batchAuto + "000" + "6" + "0");
        StringBuilder field61 = new StringBuilder("000000" + "000000" + "0000");//原批次号+原流水号+原交易日期
        field61.append((String) FlowControl.MapHelper.getMap().get(ChooseAuthTypeAty.KEY_CHOOSE_PAY_TYPE));
        String authOrganization = (String) FlowControl.MapHelper.getMap().get(InputAuthOrganizationCodeAty.KEY_AUTH_ORGANIZATION_CODE);
        if (authOrganization != null) {
            field61.append(authOrganization);
        }
        iso.setBit(61, field61.toString());

        String field63 = "000";
        field63 = FlowControl.MapHelper.getCardOrganizationCode();
        iso.setBit(63, field63);

        return iso;
    }
}
