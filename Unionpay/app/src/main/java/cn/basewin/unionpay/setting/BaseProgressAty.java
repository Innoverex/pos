package cn.basewin.unionpay.setting;

import android.content.Context;
import android.os.Bundle;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseWaitAty;
import cn.basewin.unionpay.view.HintDialog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/2 15:02<br>
 * 描述：
 */
public abstract class BaseProgressAty extends BaseWaitAty {
    private Context mContext;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideLeftKeepMargin();
        setTitleContent(getTitleString());
        mContext = this;
        start();
        setHint(getString(R.string.comm_wait));
        afterSetContentView();
    }

    protected abstract String getTitleString();

    protected abstract void afterSetContentView();

    protected Context getContext() {
        return mContext;
    }

    protected void onShowDialog(final String str) {
        stop();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HintDialog hd = new HintDialog(getContext());
                hd.setHasBtn_ok();
                hd.setTextHint(str);
                hd.setHintDialogListening(new HintDialog.HintDialogListening() {
                    @Override
                    public void ok() {
                        finish();
                    }

                    @Override
                    public void calcel() {

                    }
                });
                hd.show();
            }
        });
    }
}
