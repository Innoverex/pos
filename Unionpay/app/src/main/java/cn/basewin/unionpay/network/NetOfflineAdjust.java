package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.OfflineTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;

/**
 * 作   者：hanlei
 * 创建时间：2016/8/17 12:21
 * 描   述：离线调整报文
 */
@AnnotationNet(action = ActionConstant.ACTION_ADJUST)
public class NetOfflineAdjust extends OfflineTradeMessage {
    private static final String TAG = NetOfflineAdjust.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        String trace = FlowControl.MapHelper.getTrace();
        TransactionData trans = TransactionDataDao.getTranByTraceNO(trace);

        iso.setBit("msgid", "0220");

        iso.setBit(2, trans.getPan());

        iso.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        iso.setBit(4, s);

        iso.setBit(12, TDevice.getSysTime());
        iso.setBit(13, TDevice.getSysDate());

        iso.setBit(14, trans.getExpDate());

        iso.setBit(22, "012");

        iso.setBit(25, "00");

        String referNO = trans.getReferenceNo();
        if (referNO != null) {
            iso.setBit(37, referNO);
        }

        String authCode = trans.getAuthCode();
        if (authCode != null) {
            iso.setBit(38, authCode);
        }

        String fee = FlowControl.MapHelper.getFee();
        if (fee != null) {
            iso.setBit(48, fee);
        }

        iso.setBit(49, "156");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        StringBuilder field60 = new StringBuilder();
        if (fee != null) {
            field60.append("34");
        } else {
            field60.append("32");
        }
        field60.append(batchAuto + "000" + "6" + "0");
        iso.setBit(60, field60.toString());

        String field61 = trans.getField61();

        iso.setBit(61, field61.substring(0, 6) + trans.getOldTrace() + trans.getOldDate() + field61.substring(16));

        iso.setBit(63, trans.getField63());

        return iso;
    }
}
