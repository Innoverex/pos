package cn.basewin.unionpay.trade;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.basewin.widgets.HandWriteView;

import org.xutils.common.util.KeyValue;
import org.xutils.ex.DbException;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.SignatureHelper;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/14 17:10<br>
 * 描述:  <br>
 */
public class SignatureAty extends BaseFlowAty {
    public static final int resultCode = AppConfig.RESULT_CODE_SIGNATURE;
    public static final String KEY_PATH = "Signature_path";
    private static final String TAG = SignatureAty.class.getName();

    private HandWriteView handwriteview;
    private Button deletebutton, button_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTimerFlag(false);
        setContentView(R.layout.activity_signature);
        initTitle();
        hideLeftKeepMargin();
        Log.d(TAG, "debug disable back key");
        setDate("");
        handwriteview = (HandWriteView) findViewById(R.id.handwriteview);
        deletebutton = (Button) findViewById(R.id.deletebutton);
        button_ok = (Button) findViewById(R.id.button_ok);
        //R.mipmap.elc
        handwriteview.setBackgroudView(BitmapFactory.decodeResource(getResources(), R.mipmap.elc));

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handwriteview.clear();
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTouch) {
                    return;
                }
                Log.d(TAG, " #####  bSign :" + handwriteview.isValid());
                if (!handwriteview.isValid()) {
                    TLog.showToast(getString(R.string.sign_screen));
                } else {
                    Bitmap cachebBitmap = handwriteview.getCachebBitmap();
                    final String path = SignatureHelper.saveBitmap(cachebBitmap);

                    SignatureAty.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!path.isEmpty()) {
                                FlowControl.MapHelper.setSignPath(path);
                                savePath(path);
                                startNextFlow();
                            } else {
                                TLog.showToast(getString(R.string.sign_save_fail));
                            }
                        }
                    });

                }
            }
        });
    }

    private boolean isTouch = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                TLog.e("dispatchTouchEvent", "MainActivity.dispatchTouchEvent is down");
                break;
            case MotionEvent.ACTION_MOVE:
                TLog.e("dispatchTouchEvent", "MainActivity.dispatchTouchEvent is moving");
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                TLog.e("dispatchTouchEvent", "MainActivity.dispatchTouchEvent is up");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setDate(String s) {
        TLog.e(TAG, s);
        Intent intent = new Intent();
        intent.putExtra(KEY_PATH, s);
        setResult(resultCode, intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    void savePath(String path) {
        Log.i(TAG, "savePath(String path)....");
        String trace = FlowControl.MapHelper.getSerial();
        try {
            Log.i(TAG, "trace=" + trace);
            KeyValue kv = new KeyValue("signPath", path);
            TransactionDataDao.updateByTrace(trace, kv);
            TransactionData t = TransactionDataDao.selectByTrace(trace);
            Log.i(TAG, "TransactionData=" + t);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}