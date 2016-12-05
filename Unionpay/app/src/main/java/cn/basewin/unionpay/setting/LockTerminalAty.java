package cn.basewin.unionpay.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseActivity;
import cn.basewin.unionpay.ui.SignInAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/10 13:28<br>
 * 描述：锁定终端页面
 */
public class LockTerminalAty extends BaseActivity {
    private LinearLayout ll_lock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_terminal);
        setTimerFlag(false);
        ll_lock = (LinearLayout) findViewById(R.id.ll_lock);
        ll_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SignInAty.getIntent(LockTerminalAty.this, ActionConstant.ACTION_LOCK_TERMINAL));
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
