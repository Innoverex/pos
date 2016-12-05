package cn.basewin.unionpay.trade;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;

/**
 * Created by kxf on 2016/8/22.
 * 打印失败的交易明细
 */
public class PrintDetailFailWaitAty extends BasePrintSettleWaitAty {
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
            showCuatomDialogPrintSuccess();
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
                        tds = TransactionDataDao.selectAllNotValid();
                        Log.i(TAG, "tds=" + tds);
                        demo = PrintHelper.PrintFaileTxnList(tds);
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
