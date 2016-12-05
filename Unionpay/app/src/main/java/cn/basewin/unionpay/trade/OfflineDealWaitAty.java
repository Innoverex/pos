package cn.basewin.unionpay.trade;

import android.os.Bundle;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作   者：hanlei
 * 创建时间：2016/8/16 18:59
 * 描   述：脱机交易“网络请求”（实际是组包、保存数据操作）
 */
public class OfflineDealWaitAty extends BaseWaitAty {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                offlineNet();
            }
        }, 2000);
    }

    public void offlineNet() {
        TLog.l("脱机网络请求");
        int action = FlowControl.MapHelper.getAction();
        NetHelper.offlineDistribution(action, FlowControl.MapHelper.getMap(), new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                startNextFlow();
                finish();
            }

            @Override
            public void onFailure(int code, final String s) {
                OfflineDealWaitAty.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TLog.l(s);
                        DialogHelper.showAndClose(OfflineDealWaitAty.this, s);
                    }
                });
            }
        });
    }
}
