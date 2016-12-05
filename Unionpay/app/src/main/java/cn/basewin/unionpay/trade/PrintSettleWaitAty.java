package cn.basewin.unionpay.trade;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.xutils.ex.DbException;

import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.entity.SettleInfo;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.setting.TradeSettlementSettingAty;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.view.HintDialog;

/**
 * Created by kxf on 2016/8/8.
 * 打印结算单
 */
public class PrintSettleWaitAty extends BasePrintSettleWaitAty {
    protected static final String TAG = "PrintSettleWaitAty";

    @Override
    protected void onPrintFinish() {
        if (TradeSettlementSettingAty.getNeedPrintDetail()) {
            hintDialog = getDialog("打印完成，是否打印明细？");
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

    @Override
    protected boolean isPrintSettle() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");
        final SettleInfo so = saveSettleInfo();
        new AsyncTask<Void, Void, PrintClient>() {

            @Override
            protected PrintClient doInBackground(Void... params) {
                try {
                    demo = PrintHelper.summary(so, true);
                    demo.setPrintListen(cpu);
                    demo.printNext();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return demo;
            }
        }.execute();
    }

    protected SettleInfo saveSettleInfo() {
        Log.i(TAG, "saveSettleInfo()");
        Log.i(TAG, FlowControl.MapHelper.getMap().toString());
        String field48Result = (String) FlowControl.MapHelper.getMap().get("field48Result");
        String field48 = (String) FlowControl.MapHelper.getMap().get("field48");
        SettleInfo so = new SettleInfo();
        int offset = 31;
        so.setBatchNo(SettingConstant.getBatch());
        so.setMerchantName(SettingConstant.getMERCHANT_NAME());
        so.setMerchantId(SettingConstant.getMERCHANT_NO());
        so.setTerminalId(SettingConstant.getTERMINAL_NO());
        so.setDate(TDevice.getNowTimeByFormat("yyyy/MM/dd"));
        so.setTime(TDevice.getNowTimeByFormat("HH:mm:ss"));
        so.setTotalitem_debit_n(field48.substring(12, 15));
        so.setTotalmoney_debit_n(field48.substring(0, 12));
        so.setTotalitem_credit_n(field48.substring(27, 30));
        so.setTotalmoney_credit_n(field48.substring(15, 27));
        so.setTotalitem_debit_w(field48.substring(12 + offset, 15 + offset));
        so.setTotalmoney_debit_w(field48.substring(0 + offset, 12 + offset));
        so.setTotalitem_credit_w(field48.substring(27 + offset, 30 + offset));
        so.setTotalmoney_credit_w(field48.substring(15 + offset, 27 + offset));
        so.setStatus_n(field48Result.substring(30, 31));
        so.setStatus_w(field48Result.substring(30 + offset, 31 + offset));
        try {
            AppContext.db().dropTable(SettleInfo.class);
            AppContext.db().save(so);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
        return so;
    }
}