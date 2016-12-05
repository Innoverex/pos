package cn.basewin.unionpay;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.basewin.aidl.OnBarcodeCallBack;
import com.basewin.services.ServiceManager;

import org.xutils.ex.DbException;

import java.util.List;
import java.util.Timer;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.setting.DownloadTMKActivity;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputBatchAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.InputProductIdAty;
import cn.basewin.unionpay.trade.InputTerminalAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.ui.SignInAty;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIHelper;
import cn.basewin.unionpay.view.HintDialog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LedUtil.redClose(200);
        return super.onTouchEvent(event);
    }

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate...");
        setContentView(R.layout.activity_main);
    }

    public void cs(View view) {
//        BeepManager beepManager = new BeepManager(this);
//        beepManager.playBeepSoundAndVibrate();
//        beepManager.close();
        TransactionData first = null;
        try {
            first = TransactionDataDao.db().findFirst(TransactionData.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        FlowControl.MapHelper.setSerial(first.getTrace());
        Intent intent = new Intent(this, PrintWaitAty.class);
        startActivity(intent);
    }


    public void login(View view) {
        Intent intent = new Intent(this, SignInAty.class);
        startActivity(intent);
    }

    public void menu(View view) {
        UIHelper.menu(this, ActionConstant.ACTION_SETTING_GROUP);
    }

    public void data(View view) {
        Log.e(TAG, "data");
        try {
            List<TransactionData> transactionDatas = TransactionDataDao.selectAll();
            if (transactionDatas != null) {
                for (TransactionData t : transactionDatas) {
                    TLog.l(t.toString());
                }
            } else {
                TLog.showToast("无数据");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void cleandata(View view) {
        try {
            TransactionDataDao.deleteAll();
            TLog.showToast("清除交易数据 成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private int cc = 0;

    public void loadKey(View view) {
        Log.e(TAG, "loadKey");
        final HintDialog hd = DialogHelper.getHintDialog(this);
        hd.setTextHint("sss");
        hd.timerTask(10);
        hd.setCanceledOnTouchOutside(false);
        hd.setHintDialogListening(new HintDialog.HintDialogListening() {
            @Override
            public void ok() {
                TLog.l("点击了确定");
            }

            @Override
            public void calcel() {
                TLog.l("点击了calcel");
            }
        });
        hd.show();
    }

    public void xiaofei(View view) {
        FlowControl flowControl = new FlowControl();
//        flowControl.begin(InputMoneyAty.class)
//                .next(SwipingCardAty.class)
//                .next(InputPWAty.class)
//                .next(NetWaitAty.class)
//                .next(SignatureAty.class)
//                .next(PrintWaitAty.class)
//                .start(MainActivity.this, ActionConstant.ACTION_SALE);

        flowControl.begin(InputMoneyAty.class)
                .next(SwipingCardAty.class)
                .next(InputPWDAty.class)
                .next(InputBatchAty.class)
                .next(InputProductIdAty.class)
                .next(InputTerminalAty.class)
                .next(NetWaitAty.class)
                .next(SignatureAty.class)
                .next(PrintWaitAty.class)
                .branch(0, PrintWaitAty.class, SignatureAty.class)
                .start(MainActivity.this, ActionConstant.ACTION_SALE);
    }

    public void download_tmk(View view) {
        Log.i(TAG, "download_tmk");
        Intent intent = new Intent(this, DownloadTMKActivity.class);
        startActivity(intent);
    }

    public void sm(View view) {
        Log.i(TAG, "sm");
        try {
            ServiceManager.getInstence().getScan().startScan(20, new OnBarcodeCallBack() {
                @Override
                public void onScanResult(String s) throws RemoteException {
                    TLog.l("onScanResult");
                    TLog.showToast(s);
                }

                @Override
                public void onFinish() throws RemoteException {
                    TLog.l("onFinish");
                    TLog.showToast("扫描失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        TLog.l("event");
        TLog.l(event.toString());
        return super.onKeyDown(keyCode, event);
    }
}
