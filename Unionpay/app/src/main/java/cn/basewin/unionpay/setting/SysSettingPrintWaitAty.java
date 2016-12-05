package cn.basewin.unionpay.setting;

import android.os.Bundle;

import com.basewin.aidl.OnPrinterListener;

import org.json.JSONException;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.print.PrintClient;
import cn.basewin.unionpay.print.PrintHelper;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/25 16:44<br>
 * 描述：
 */
public class SysSettingPrintWaitAty extends BaseWaitAty {
    public static final String KEY_SYS_PRINT_ID = "syssetting_print_wait_id";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();
        id = getIntent().getIntExtra(SysSettingPrintWaitAty.KEY_SYS_PRINT_ID, 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintClient printClient = null;
                try {
                    switch (id) {
                        case R.id.btn_print_merchantparam:
                            printClient = PrintHelper.PrintMerchantParam();
                            break;
                        case R.id.btn_print_tradecontrol:
                            printClient = PrintHelper.PrintTradeParam();
                            break;
                        case R.id.btn_print_syscontrol:
                            printClient = PrintHelper.PrintSysParam();
                            break;
                        case R.id.btn_print_commucontrol:
                            printClient = PrintHelper.PrintCommuParam();
                            break;
                        case R.id.btn_print_other:
                            printClient = PrintHelper.PrintOtherParam();
                            break;
                        case R.id.btn_print_version:
                            printClient = PrintHelper.PrintVersion();
                            break;
                    }
                    if (printClient != null) {
                        printClient.setPrintListen(mListener);
                        printClient.print();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    OnPrinterListener mListener = new OnPrinterListener() {
        @Override
        public void onError(int i, String s) {
            TLog.showToast("打印失败！");
            finish();
        }

        @Override
        public void onFinish() {
            TLog.showToast("打印成功！");
            finish();
        }

        @Override
        public void onStart() {

        }
    };

    @Override
    protected void clickLeft() {
    }
}
