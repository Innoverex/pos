package cn.basewin.unionpay.trade;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BasePrintImp;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.view.HintDialog;

/**
 * kxf
 * 打印冲正故障单
 */
public class PrintReverseErrorAty extends BaseWaitAty {
    private HintDialog hintDialog;
    private BasePrintImp cpu = new BasePrintImp(this) {
        @Override
        public void onPrintError(int code, String msg) {
            hintDialog = getDialog(msg + ",是否重新打印？");
            hintDialog.setHasBtn();
            hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                @Override
                public void ok() {
                    try {
                        demo.printNext();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void calcel() {
                    finish();
                }
            });
            hintDialog.timerTask(AppConfig.PRINT_TIMEOUT);
            hintDialog.show();
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            finish();
        }
    };
    PrintClient demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTimerFlag(false);//关闭倒计时
        hideLeftKeepMargin();
        setHint(getString(R.string.dayinguzhangdan));
        start();
        new AsyncTask<Void, Void, PrintClient>() {

            @Override
            protected PrintClient doInBackground(Void... params) {
                try {
                    demo = PrintHelper.PrintReverseError();
                    demo.setPrintListen(cpu);
                    demo.print();
                    Log.i(TAG, "开始打印。。。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return demo;
            }
        }.execute();
    }

    private HintDialog getDialog(String msg) {
        if (null != hintDialog && hintDialog.isShowing()) {
            hintDialog.dismiss();
        }
        hintDialog = DialogHelper.getHintDialog(PrintReverseErrorAty.this);
        hintDialog.setTextHint(msg);
        return hintDialog;
    }
}
