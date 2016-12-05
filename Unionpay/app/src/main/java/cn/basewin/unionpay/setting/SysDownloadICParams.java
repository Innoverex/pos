package cn.basewin.unionpay.setting;

import android.text.TextUtils;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 18:04<br>
 * 描述：IC卡参数下载
 */
public class SysDownloadICParams extends BaseProgressAty {
    private int paramsLength = 0;
    private int index = 0;
    private List<String> mResult = new ArrayList<>();

    @Override
    protected String getTitleString() {
        return "IC卡参数下载";
    }

    @Override
    protected void afterSetContentView() {
        setHint("正在查询");
        NetHelper.distribution(ActionConstant.ACTION_QUERY_ICPARAMS, null, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                byte[] bytes = data.getBitBytes(62);
                String result = BytesUtil.bytesToHexString(bytes);
                if (!TextUtils.isEmpty(result)) {
                    int code = Integer.parseInt(result.substring(1, 2));
                    if (code != 0) {
                        result = result.substring(4);
                        String[] strings = result.split("9f");
                        mResult.addAll(Arrays.asList(strings));
                        paramsLength = mResult.size();
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
            case 0://POS中心没有参数
                onShowDialog("POS中心没有参数");
                break;
            case 1://有后续参数且一个报文能存下所有参数
            case 3://没有后续参数
                index = 0;
                downloadICParams();
                break;
            case 2://有后续参数且一个报文存不下所有参数
                queryNextICKey();
                break;
        }
    }

    /**
     * 继续查询IC参数
     */
    private void queryNextICKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("62", "1" + paramsLength);
        NetHelper.distribution(ActionConstant.ACTION_QUERY_NEXT_PARAMS, map, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                byte[] bytes = data.getBitBytes(62);
                String result = BytesUtil.bytesToHexString(bytes);
                if (!TextUtils.isEmpty(result)) {
                    int code = Integer.parseInt(result.substring(1, 2));
                    if (code != 0) {
                        result = result.substring(2);
                        String[] strings = result.split("9f");
                        mResult.addAll(Arrays.asList(strings));
                        paramsLength = mResult.size();
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
     * 下载IC卡参数
     */
    private void downloadICParams() {
        Map<String, Object> map = new HashMap<>();
        String bit62 = "9f" + mResult.get(index);
        map.put("62", bit62);
        setHint("正在下载" + "[" + (index + 1) + "/" + paramsLength + "]");
        NetHelper.distribution(ActionConstant.ACTION_DOWNLOAD_ICPARAMETER, map, new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                index++;
                if (index < paramsLength) {
                    downloadICParams();
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
     * IC卡参数下载结束
     */
    private void downloadFinish() {
        NetHelper.distribution(ActionConstant.ACTION_DOWNLOAD_FINISH_PAMRAMS, null, new NetResponseListener() {
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