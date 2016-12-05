package cn.basewin.unionpay.network;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.PBOCBinder;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.ErrorConstant;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.OrdinaryMessage;
import cn.basewin.unionpay.setting.SettingConstant;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 18:00<br>
 * 描述：IC卡参数下载
 */
@AnnotationNet(action = ActionConstant.ACTION_DOWNLOAD_ICPARAMETER)
public class NetDownloadICParams extends OrdinaryMessage {
    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        iso.setBit("msgid", "0800");
        iso.setBit(11, SettingConstant.getTraceAuto());
        StringBuilder sb = new StringBuilder();
        sb.append("00")//交易类型码
                .append(SettingConstant.getBatch())//批次号6位
                .append("380");//网络管理信息码
        iso.setBit(60, sb.toString());
        String bit62 = (String) map.get("62");
        iso.setBinaryBit(62, BytesUtil.ascii2Bcd(bit62));//终端状态
        return iso;
    }


    @Override
    public boolean afterRequest(Iso8583Manager iso, NetResponseListener lis) {
        try {
            PBOCBinder mPbocService = ServiceManager.getInstence().getPboc();
            byte[] bytes = iso.getBitBytes(62);
            String result = BytesUtil.bytesToHexString(bytes);
            int code = Integer.parseInt(result.substring(1, 2));
            switch (code) {
                case 0:
                    if (lis != null) {
                        lis.onFailure(ErrorConstant.ERR_PARA, "获取IC卡参数失败");
                    }
                    return false;
                case 1:
                    String keyValue = result.substring(2);
                    mPbocService.writeAid(0, keyValue);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (lis != null) {
                lis.onFailure(ErrorConstant.ERR_PARA, "存储参数失败");
            }
            return false;
        }
        return true;
    }
}
