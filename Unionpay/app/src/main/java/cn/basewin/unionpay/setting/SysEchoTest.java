package cn.basewin.unionpay.setting;

import com.basewin.packet8583.factory.Iso8583Manager;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 15:15<br>
 * 描述：回响测试
 */
public class SysEchoTest extends BaseProgressAty {

    @Override
    protected String getTitleString() {
        return "回响测试";
    }

    @Override
    protected void afterSetContentView() {
        NetHelper.distribution(ActionConstant.ACTION_ECHO_TEST, null, new NetResponseListener() {
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
