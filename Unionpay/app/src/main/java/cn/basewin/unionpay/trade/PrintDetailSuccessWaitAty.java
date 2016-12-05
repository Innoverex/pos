package cn.basewin.unionpay.trade;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.setting.TradeSettlementSettingAty;
import cn.basewin.unionpay.view.HintDialog;

/**
 * Created by kxf on 2016/8/22.
 * 打印成功的交易明细
 */
public class PrintDetailSuccessWaitAty extends BasePrintSettleWaitAty {
    @Override
    protected void onPrintFinish() {
        boolean isPrintNext = demo.getIsPrintNext();
        if (isPrintNext) {
            try {
                demo.printNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (TradeSettlementSettingAty.getNeedPrintFailure()) {
                hintDialog = getDialog("打印完成，是否打印失败的交易明细？");
                hintDialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                    @Override
                    public void ok() {
                        startNextFlow();
                    }

                    @Override
                    public void calcel() {
                        deleteAllAndSigin();
                        finish();
                    }
                });
                hintDialog.show();
            } else {
                showCuatomDialogPrintSuccess();
            }
        }
    }

    @Override
    protected boolean isPrintSettle() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            new AsyncTask<Void, Void, PrintClient>() {
                @Override
                protected PrintClient doInBackground(Void... params) {
                    try {
                        tds = TransactionDataDao.selectAllValid();
                        Log.i(TAG, "tds=" + tds);
                        demo = PrintHelper.PrintTxnList(tds);
                        demo.setPrintListen(cpu);
                        demo.printNext();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return demo;
                }
            }.execute();
        } catch (Exception e) {
            deleteAllAndSigin();
            finish();
            e.printStackTrace();
        }
    }
}