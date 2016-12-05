package cn.basewin.unionpay.setting;

import android.text.TextUtils;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.HashMap;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 18:04<br>
 * 描述：IC卡公钥下载
 */
public class SysDownloadICKey extends BaseProgressAty {
    private final int TLV_LENGTH = 24;
    private final int LENGTH = TLV_LENGTH + 22;
    private StringBuilder icTLV = new StringBuilder();
    private int paramsLength;
    private int index = 0;

    @Override
    protected String getTitleString() {
        return "IC卡公钥下载";
    }

    @Override
    protected void afterSetContentView() {
        setHint("正在查询");
        NetHelper.distribution(ActionConstant.ACTION_QUERY_ICKEY, null, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                byte[] bytes = data.getBitBytes(62);
                String result = BytesUtil.bytesToHexString(bytes);
                paramsLength += (result.length() - 2) / LENGTH;
                if (!TextUtils.isEmpty(result)) {
                    int code = Integer.parseInt(result.substring(1, 2));
                    if (code != 0) {
                        icTLV.append(result.substring(2));
                    }
                    next(code);
                }
            }

            @Override
            public void onFailure(int code, String s) {
                onShowDialog(s);
            }
        });
    }

    private void next(int code) {
        switch (code) {
            case 0://POS中心没有秘钥
                onShowDialog("POS中心没有参数");
                break;
            case 1://有后续秘钥且一个报文能存下所有秘钥
                index = 0;
                downloadICKey(icTLV.toString());
                break;
            case 2://有后续秘钥且一个报文存不下所有秘钥
                queryNextICKey();
                break;
            case 3://没有后续秘钥
                index = 0;
                downloadICKey(icTLV.toString());
                break;
        }
    }

    /**
     * 继续查询IC key
     */
    private void queryNextICKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("62", "1" + paramsLength);
        NetHelper.distribution(ActionConstant.ACTION_QUERY_NEXT_ICKEY, map, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                byte[] bytes = data.getBitBytes(62);
                String result = BytesUtil.bytesToHexString(bytes);
                paramsLength += (result.length() - 2) / LENGTH;
                if (!TextUtils.isEmpty(result)) {
                    int code = Integer.parseInt(result.substring(1, 2));
                    if (code != 0) {
                        icTLV.append(result.substring(2));
                    }
                    next(code);
                }
            }

            @Override
            public void onFailure(int code, String s) {
                onShowDialog(s);
            }
        });
    }

    /**
     * 下载IC key
     */
    private void downloadICKey(String result) {
        Map<String, Object> map = new HashMap<>();
        String bit62 = result.substring(index * LENGTH, (index + 1) * LENGTH);
        bit62 = bit62.substring(0, TLV_LENGTH);
        map.put("62", bit62);
        setHint("正在下载" + "[" + (index + 1) + "/" + paramsLength + "]");
        NetHelper.distribution(ActionConstant.ACTION_DOWNLOAD_ICKEY, map, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                index++;
                if (index < paramsLength) {
                    downloadICKey(icTLV.toString());
                } else {
                    downloadFinish();
                }
            }

            @Override
            public void onFailure(int code, String s) {
                onShowDialog(s);
            }
        });
    }

    /**
     * 下载结束
     */
    private void downloadFinish() {
        NetHelper.distribution(ActionConstant.ACTION_DOWNLOAD_FINISH_ICKEY, null, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                onShowDialog("处理成功");
            }

            @Override
            public void onFailure(int code, String s) {
                onShowDialog(s);
            }
        });
    }
}