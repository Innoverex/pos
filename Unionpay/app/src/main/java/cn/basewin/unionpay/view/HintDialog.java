package cn.basewin.unionpay.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/24 14:06<br>
 * 描述: 提示的一个类<br>
 */
public class HintDialog extends Dialog {
    private Context mContext;
    private TextView tv_dialog_hint, tv_dialog_hint_title;
    private Button btn_dialog_account_unok, btn_dialog_account_ok;
    private HintDialogListening mListening;
    private String hintText = "";

    public HintDialog(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HintDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected HintDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(view);
    }

    private int i = 0;

    private View init() {
        view = View.inflate(mContext, R.layout.dialog_hint, null);
        tv_dialog_hint = (TextView) view.findViewById(R.id.tv_dialog_hint);
        tv_dialog_hint_title = (TextView) view.findViewById(R.id.tv_dialog_hint_title);
        tv_dialog_hint_title.setVisibility(View.GONE);
        btn_dialog_account_unok = (Button) view.findViewById(R.id.btn_dialog_account_unok);
        btn_dialog_account_ok = (Button) view.findViewById(R.id.btn_dialog_account_ok);
        btn_dialog_account_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                TLog.l("Ok键，自动关闭dialog");
                if (mListening != null) {
                    mListening.ok();
                }
            }
        });
        btn_dialog_account_unok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListening != null) {
                    mListening.calcel();
                }
                TLog.l("Cancel键，自动关闭dialog");
                dismiss();
            }
        });
        tv_dialog_hint.setText(hintText);
        setCancelable(false);
        return view;
    }

    public void setBtnOKText(String msg) {
        btn_dialog_account_ok.setText(msg);
    }

    public void setBtnUnOKText(String msg) {
        btn_dialog_account_unok.setText(msg);
    }

    public void setTextHint(final String msg) {
        if (tv_dialog_hint != null) {
            tv_dialog_hint.post(new Runnable() {
                @Override
                public void run() {
                    tv_dialog_hint.setText(msg);
                }
            });
        }
    }

    /**
     * 设置头部提示语
     *
     * @param msg
     */
    public void setTextTitleHint(final String msg) {
        if (tv_dialog_hint_title != null) {
            tv_dialog_hint_title.post(new Runnable() {
                @Override
                public void run() {
                    tv_dialog_hint_title.setText(msg);
                }
            });
        }
    }

    public void setNoBtn() {
        btn_dialog_account_ok.setVisibility(View.GONE);
        btn_dialog_account_unok.setVisibility(View.GONE);
    }

    public void setHasBtn() {
        btn_dialog_account_ok.setVisibility(View.VISIBLE);
        btn_dialog_account_unok.setVisibility(View.VISIBLE);
    }

    public void setHasBtn_ok() {
        setNoBtn();
        btn_dialog_account_ok.setVisibility(View.VISIBLE);
    }

    public void setHasBtn_unok() {
        setNoBtn();
        btn_dialog_account_unok.setVisibility(View.VISIBLE);
    }

    public void setBtnVisibility(boolean b) {
        if (b) {
            setHasBtn();
        } else {
            setNoBtn();
        }
    }

    public interface HintDialogListening {
        void ok();

        void calcel();
    }

    public void setHintDialogListening(HintDialogListening mListening) {
        this.mListening = mListening;
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams WMLP = window.getAttributes();
        WMLP.height = (int) (TDevice.getScreenHeight() / 2);
        window.setAttributes(WMLP);
    }

    private int jsq = 0;
    private Timer timer;

    /**
     * 倒计时 自动确认 按钮
     *
     * @param time
     */
    public void timerTask(int time) {
        if (time <= 1 || tv_dialog_hint_title == null) {
            return;
        }
        tv_dialog_hint_title.setVisibility(View.VISIBLE);
        timer = new Timer();
        jsq = time;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TLog.l(TDevice.getSysTime());
                int i = --jsq;
                if (i <= 0) {
                    timer.cancel();
                    dismiss();
                    btn_dialog_account_ok.callOnClick();
                    return;
                }
                setTextTitleHint(i + "秒后自动确认！");
            }
        }, 500L, 1000L);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (timer != null) {
            timer.cancel();
        }
    }
}
