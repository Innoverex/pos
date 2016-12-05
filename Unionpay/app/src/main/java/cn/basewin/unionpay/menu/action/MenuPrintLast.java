package cn.basewin.unionpay.menu.action;

import android.os.AsyncTask;

import org.xutils.ex.DbException;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BasePrintImp;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.HintDialog;

/**
 * 作者: kxf <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 重打印最后一笔 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_PRINT_LAST)
public class MenuPrintLast extends MenuPrintAction {
    private BasePrintImp getPrint() {
        return new BasePrintImp(getContext()) {
            @Override
            public void onPrintError(int code, String msg) {
                dialog = getDialog(msg + ",是否重新打印？");
                dialog.setHasBtn();
                dialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                    @Override
                    public void ok() {
                        try {
                            handler.sendEmptyMessage(MSG_PRINT_NEXT_AND_SHOW_DIALOD);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void calcel() {
                    }
                });
                dialog.timerTask(AppConfig.PRINT_TIMEOUT);
                dialog.show();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                final boolean isPrintNext = demo.getIsPrintNext();
                String s = isPrintNext ? ",是否打印下一联?" : "";
                dialog = getDialog("打印完成" + s);
                dialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                    @Override
                    public void ok() {
                        if (isPrintNext) {
                            try {
                                handler.sendEmptyMessage(MSG_PRINT_NEXT_AND_SHOW_DIALOD);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                        }
                    }

                    @Override
                    public void calcel() {
                    }
                });
                dialog.timerTask(AppConfig.PRINT_TIMEOUT);
                dialog.show();
            }
        };
    }

    @Override
    public String getResName() {
        return "重打印最后一笔";
    }

    @Override
    public int getResIcon() {
        return R.drawable.reprint_last_state;
    }

    @Override
    public Runnable getRunChild() {
        return new Runnable() {
            @Override
            public void run() {
                TransactionData td = null;
                try {
                    td = TransactionDataDao.selectLastValid();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if (td == null) {
                    TLog.showToast("无交易！");
                } else {
                    final TransactionData finalTd = td;
                    handler.sendEmptyMessage(MSG_SHOW_DIALOG);
                    new AsyncTask<Void, Void, PrintClient>() {

                        @Override
                        protected PrintClient doInBackground(Void... params) {
                            try {
                                FlowControl.MapHelper.setAction(ActionConstant.ACTION_PRINT_LAST);
                                demo = PrintHelper.Trade(finalTd);
                                demo.setPrintListen(getPrint());
                                demo.printNext();
                            } catch (Exception e) {
                                TLog.l(e.getMessage());
                                e.printStackTrace();
                            }
                            return demo;
                        }
                    }.execute();
                }
            }
        };
    }
}