package cn.basewin.unionpay.setting;

import com.basewin.packet8583.factory.Iso8583Manager;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 18:04<br>
 * 描述：IC卡黑名单下载
 */
public class SysDownloadCardBin extends BaseProgressAty {
    @Override
    protected String getTitleString() {
        return "IC卡黑名单下载";
    }

    @Override
    protected void afterSetContentView() {
        NetHelper.distribution(ActionConstant.ACTION_DOWNLOAD_CARDBIN, null, new NetResponseListener() {
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
