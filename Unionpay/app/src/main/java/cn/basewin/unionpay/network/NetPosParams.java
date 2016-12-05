package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 14:11<br>
 * 描述：POS参数传递报文
 */
@AnnotationNet(action = ActionConstant.ACTION_EXCHANGE_PARAMETER)
public class NetPosParams extends OrdinaryMessage {
    private static final String TAG = NetPosParams.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0800");
        StringBuilder sb = new StringBuilder();
        sb.append("00")//交易类型码
                .append(SettingConstant.getBatch())//批次号6位
                .append("364");//网络管理信息码
        iso.setBit(60, sb.toString());
        return iso;
    }
}
