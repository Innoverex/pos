package cn.basewin.unionpay.base;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/18 16:32<br>
 * 描述:  <br>
 */
public class BaseActivity extends FragmentActivity {
    protected TextView tv_title;
    protected LinearLayout layout_left, layout_right;
    private int delay_time = 60 * 1000;
    /**
     * 计时标志位
     */
    private boolean isCountDown = true;

    /**
     * 计时用的handler
     */
    private android.os.Handler countDownHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (isCountDown) {
            resetTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownHandler.removeCallbacksAndMessages(null);
    }

    protected void initTitle() {
        layout_left = (LinearLayout) findViewById(R.id.layout_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        layout_right = (LinearLayout) findViewById(R.id.layout_right);
        layout_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLeft();
            }
        });
        layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRight();
            }
        });
        int action = FlowControl.MapHelper.getAction();
        String actionName = TLog.getString(ActionConstant.getAction(action));
        TLog.l("拿到的意图：" + action + "        title:" + actionName);
        setTitleContent(actionName);
    }

    protected void clickLeft() {
        finish();
    }

    protected void clickRight() {
    }

    protected void hideLeft() {
        layout_left.setVisibility(View.GONE);
    }

    protected void hideLeftKeepMargin() {
        findViewById(R.id.iv_left).setVisibility(View.GONE);
        findViewById(R.id.layout_left).setOnClickListener(null);
    }

    protected void hideRight() {
        layout_right.setVisibility(View.GONE);
    }

    protected void setTitleContent(String msg) {
        tv_title.setText(msg);
    }

    protected void setTitleContent(int action) {
        tv_title.setText(ActionConstant.getAction(action));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        resetTimer();
        return super.onTouchEvent(event);
    }

    /**
     * 是否计时
     * true - 开始计时，如果原先正在计时，将充值时间
     * false - 停止计时
     *
     * @param flag
     */
    public void setTimerFlag(boolean flag) {
        this.isCountDown = flag;
        countDownHandler.removeCallbacksAndMessages(null);
        if (isCountDown) {
            countDownHandler.sendEmptyMessageDelayed(0, delay_time);
        }
    }

    /**
     * 重置倒计时
     */
    public void resetTimer() {
        if (isCountDown) {
            countDownHandler.removeCallbacksAndMessages(null);
            countDownHandler.sendEmptyMessageDelayed(0, delay_time);
        }
    }


    public void setDelay_time(int delay_time) {
        this.delay_time = delay_time * 1000;
    }


}
