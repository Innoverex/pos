package cn.basewin.unionpay.base;

import android.app.Activity;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.services.PrinterBinder;

import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.BeeperUtil;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/3 16:37<br>
 * 描述: 这个类的作用：这个类作为一个标准的存在，打印监听的基类，所有不同的子类都去实现这个类 或者重载这个类的方法 <br>
 */
public abstract class BasePrintImp implements OnPrinterListener {
    private static final String TAG = BasePrintImp.class.getName();
    private Activity aty;

    public BasePrintImp(Activity a) {
        super();
        aty = a;
    }

    @Override
    public void onError(final int i, String s) {
        timeStop();
        TLog.l("打印错误监听执行");
        TDevice.power_open();
        if (aty == null || aty.isFinishing()) {
            TLog.l("aty is  null  下面不执行了");
            return;
        }
        TLog.l("aty is  no null  执行了");
        String detail = "";
        switch (i) {
            case PrinterBinder.PRINTER_ERROR_NO_PAPER:
                detail = TLog.getString(R.string.dayinmeizi);
                break;
            case PrinterBinder.PRINTER_ERROR_OVER_HEAT:
                detail = TLog.getString(R.string.dayinguore);
                break;
            default:
                detail = TLog.getString(R.string.dayingyichang);
                break;
        }
        BeeperUtil.defShort();
        final String finalDetail = detail;
        aty.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onPrintError(i, finalDetail);
            }
        });
    }

    public void onPrintError(int code, String msg) {
    }

    ;

    @Override
    public void onFinish() {
        timeStop();
        TLog.l("打印结束监听执行");
        TDevice.power_open();
        BeeperUtil.defLong();
    }

    private Timer timer;

    @Override
    public void onStart() {
        TLog.l("打印开始监听执行");
        TDevice.power_shut();
        LedUtil.closeAll();
        reset();
    }

    private void timeStop() {
        timer.cancel();
    }

    private void reset() {
        if (timer != null) {
            timer.cancel();
        }
        set();
    }

    private void set() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onError(0, "自定义延时超时！");
//                TLog.l("自定义延时超时！");
            }
        }, AppConfig.DISMISS_DIALOG_TIMEOUT * 1000);
    }
}
