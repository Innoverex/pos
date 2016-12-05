package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/4 14:05<br>
 * 描述：继续查询IC卡参数
 */
@AnnotationNet(action = ActionConstant.ACTION_QUERY_NEXT_PARAMS)
public class NetQueryNextICParams extends OrdinaryMessage {
    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0820");
        iso.setBit(11, SettingConstant.getTraceAuto());
        StringBuilder sb = new StringBuilder();
        sb.append("00")//交易类型码
                .append(SettingConstant.getBatch())//批次号6位
                .append("382");//网络管理信息码
        iso.setBit(60, sb.toString());
        String bit62 = (String) map.get("62");
        iso.setBinaryBit(62, bit62.getBytes());//终端状态
        return iso;
    }


}
