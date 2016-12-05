package cn.basewin.unionpay.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import cn.basewin.unionpay.R;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/19 11:35<br>
 * 描述:  <br>
 */
public class BaseWaitAty extends BaseFlowAty {
    private View v_wait;
    private TextView tv_wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_wait);
        v_wait = findViewById(R.id.v_wait);
        tv_wait = (TextView) findViewById(R.id.tv_wait);
        initTitle();
    }

    @Override
    protected void clickLeft() {
        super.clickLeft();
    }

    protected void setHint(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_wait.setText(s);
            }
        });
    }

    protected void start() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (v_wait.getVisibility() == View.GONE) {
                    v_wait.setVisibility(View.VISIBLE);
                    AnimationDrawable b = (AnimationDrawable) v_wait.getBackground();
                    b.start();
                }
            }
        });
    }

    protected void stop() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable b = (AnimationDrawable) v_wait.getBackground();
                b.stop();
                v_wait.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
