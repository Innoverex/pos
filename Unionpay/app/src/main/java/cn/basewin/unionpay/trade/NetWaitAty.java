package cn.basewin.unionpay.trade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.basewin.define.PBOCErrorCode;
import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;
import cn.basewin.unionpay.network.remote.Reverse;
import cn.basewin.unionpay.setting.SystemParSettingAty;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/20 10:43<br>
 * 描述: 网络请求 的等待 <br>
 */
public class NetWaitAty extends BaseWaitAty {
    private static final int MSG_REVERSE_SUCCESS = 1276;
    private static final int MSG_REVERSE_FAIL = MSG_REVERSE_SUCCESS + 1;
    private static final int MSG_REVERSE_FAIL_PRINT = MSG_REVERSE_SUCCESS + 2;//打印冲正单
    private static final int MSG_REVERSE_COMPLETE = MSG_REVERSE_SUCCESS + 3;//complete
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REVERSE_SUCCESS:
                    TLog.showToast("冲正成功！");
                    handler.sendEmptyMessage(MSG_REVERSE_COMPLETE);
                    break;
                case MSG_REVERSE_FAIL:
                    if (Reverse.getCursor() <= Reverse.getNumber()) {
                        setHint(String.format(getString(R.string.comm_reversing), Reverse.getCursor(), Reverse.getNumber()));
                    } else {
                        TLog.showToast("冲正失败！");
                        handler.sendEmptyMessage(MSG_REVERSE_FAIL_PRINT);
                    }
                    break;
                case MSG_REVERSE_FAIL_PRINT:
                    startActivityForResult(new Intent(NetWaitAty.this, PrintReverseErrorAty.class), 0);
                    break;
                case MSG_REVERSE_COMPLETE:
                    setHint(getString(R.string.comm_wait));
                    net();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideLeftKeepMargin();
        setHint(getString(R.string.comm_wait));
        LedUtil.starNet();
        start();
        if (!needReverse()) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    net();
                }
            }, 2000);
        }
    }

    /**
     * 判断是否需要冲正
     */
    private boolean needReverse() {
        TLog.l("冲正判断");
        if (!Reverse.getReverseFlag()) {
            TLog.l("没有冲正记录");
            return false;
        }
        TLog.l("冲正请求");
        setHint(String.format(getString(R.string.comm_reversing), 1, Integer.parseInt(SystemParSettingAty.getReverseTimes())));
        Reverse.reverseExcute(new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                handler.sendEmptyMessage(MSG_REVERSE_SUCCESS);
            }

            @Override
            public void onFailure(int code, String s) {
                Log.e(TAG, "冲正失败！  code=" + code + "   " + s);
                handler.sendEmptyMessage(MSG_REVERSE_FAIL);
            }
        });
        return true;
    }

    private boolean isError = false;
    private int errorcoder = 0;

    private void showError(int code) {
        String errorMsgByCode = PBOCErrorCode.getErrorMsgByCode(code);
        TLog.showToast(errorMsgByCode);
    }

    @Override
    public void onError(Intent intent) throws RemoteException {
        errorcoder = intent.getIntExtra("code", 65282);
        isError = true;
    }

    /**
     * 网络请求
     */
    protected void net() {
        TLog.l("联机网络请求");
        if (isError) {
            showError(errorcoder);
            finish();
            return;
        }
        int action = FlowControl.MapHelper.getAction();
        //根据下个界面是否是签名判断是否需要保存打印标志位
        Log.i(TAG, "SignatureAty.class.getName()=" + SignatureAty.class.getName() + "   getNextFlowName()=" + getNextFlowName());
        if (SignatureAty.class.getName().equals(getNextFlowName())) {
            FlowControl.MapHelper.getMap().put(PrintWaitAty.THIS_TRACE_IS_NEED_PRINT, true);
        } else {
            FlowControl.MapHelper.getMap().put(PrintWaitAty.THIS_TRACE_IS_NEED_PRINT, false);
        }
        NetHelper.distribution(action, FlowControl.MapHelper.getMap(), new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                LedUtil.stopNet();
                startNextFlow();
            }

            @Override
            public void onFailure(int code, final String s) {
                NetWaitAty.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TLog.l(s);
                        LedUtil.stopNet();
                        LedUtil.starFail();
                        DialogHelper.showAndClose(NetWaitAty.this, s);
                        TDevice.power_open();
                        TDevice.closeScreen(NetWaitAty.this);
                        LedUtil.stopFail();
                    }
                });
            }
        });
    }

    protected void clickLeft() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handler.sendEmptyMessage(MSG_REVERSE_COMPLETE);
    }
}