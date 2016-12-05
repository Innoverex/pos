package cn.basewin.unionpay.trade;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.xutils.common.util.KeyValue;
import org.xutils.ex.DbException;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BasePrintImp;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.HintDialog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/19 11:39<br>
 * 描述: 处理打印的界面 <br>
 */
public class PrintWaitAty extends BaseWaitAty {
    private static final String TAG = PrintWaitAty.class.getName();
    private HintDialog hintDialog;
    public static final String THIS_TRACE_IS_NEED_PRINT = "this_trace_is_need_print";
    public static final String PRINT_STATE = "print_state";
    public static final int PRINT_TRACE = 1028;//交易过程中打印,默认
    public static final int PRINT_AGAIN = PRINT_TRACE + 1;//补打
    private String tracePrint;//当前打印的交易的流水号


    private BasePrintImp cpu = new BasePrintImp(this) {
        @Override
        public void onPrintError(int code, String msg) {
            TLog.l("msg");
            TLog.l(msg);
            hintDialog = getDialog(msg + ",是否重新打印？");
            hintDialog.setHasBtn();
            hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                @Override
                public void ok() {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                demo.printNext();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

                @Override
                public void calcel() {
                    startNextFlow();
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
            KeyValue kv = new KeyValue("needPrint", false);
            try {
                TransactionDataDao.updateByTrace(tracePrint, kv);
            } catch (DbException e) {
                e.printStackTrace();
            }
            final boolean isPrintNext = demo.getIsPrintNext();
            String s = isPrintNext ? ",是否打印下一联?" : "";
            hintDialog = getDialog("打印完成" + s);
            hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                @Override
                public void ok() {
                    if (isPrintNext) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    demo.printNext();
                                } catch (Exception e) {
                                    finish();
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    } else {
                        startNextFlow();
                    }
                }

                @Override
                public void calcel() {
                    startNextFlow();
                }
            });
            hintDialog.timerTask(AppConfig.PRINT_TIMEOUT);
            hintDialog.show();
        }
    };
    PrintClient demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTimerFlag(false);//关闭倒计时
        TLog.l(FlowControl.MapHelper.getMap().toString());
        hideLeftKeepMargin();
        setHint(getString(R.string.printing));
        start();
        final int printState = getIntent().getIntExtra(PRINT_STATE, PRINT_TRACE);
        new AsyncTask<Void, Void, PrintClient>() {

            @Override
            protected PrintClient doInBackground(Void... params) {
                try {
                    switch (printState) {
                        case PRINT_AGAIN:
                            TransactionData td = null;
                            try {
                                td = TransactionDataDao.selectLastNeedPrint();
                            } catch (DbException e) {
                                finish();
                                e.printStackTrace();
                            }
                            tracePrint = td.getTrace();
                            demo = PrintHelper.Trade(td);
                            break;
                        default:
                            tracePrint = FlowControl.MapHelper.getSerial();
                            demo = PrintHelper.Trade(FlowControl.MapHelper.getMap());
                            break;
                    }
                    demo.setPrintListen(cpu);
                    demo.printNext();
                    Log.i(TAG, "开始打印。。。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return demo;
            }
        }.execute();
    }

    private HintDialog getDialog(String msg) {
        if (hintDialog != null) {
            hintDialog.dismiss();
            hintDialog.cancel();
        }
        hintDialog = DialogHelper.getHintDialog(PrintWaitAty.this);
        hintDialog.setTextHint(msg);
        return hintDialog;
    }
}
