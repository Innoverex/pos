package cn.basewin.unionpay.menu.action;

import android.os.AsyncTask;

import org.xutils.ex.DbException;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BasePrintImp;
import cn.basewin.unionpay.entity.SettleInfo;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.HintDialog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 重打印结算单 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_PRINT_SETTLEMENT)
public class MenuPrintSettlement extends MenuPrintAction {
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
                dialog.show();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                handler.sendEmptyMessage(MSG_DISMISS_DIALOG);
                TLog.showToast("打印完成！");
            }
        };
    }

    @Override
    public String getResName() {
        return "重打印结算单";
    }

    @Override
    public int getResIcon() {
        return R.drawable.reprint_settle_state;
    }

    @Override
    public Runnable getRunChild() {
        return new Runnable() {
            @Override
            public void run() {
                SettleInfo so = null;
                try {
                    so = AppContext.db().selector(SettleInfo.class).findFirst();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if (so != null) {
                    handler.sendEmptyMessage(MSG_SHOW_DIALOG);
                    final SettleInfo finalSo = so;
                    new AsyncTask<Void, Void, PrintClient>() {

                        @Override
                        protected PrintClient doInBackground(Void... params) {
                            try {
                                FlowControl.MapHelper.setAction(ActionConstant.ACTION_PRINT_SETTLEMENT);
                                demo = PrintHelper.summary(finalSo, true);
                                demo.setPrintListen(getPrint());
                                demo.printNext();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return demo;
                        }
                    }.execute();
                } else {
                    TLog.showToast("当前没有结算单");
                }
            }
        };
    }
}