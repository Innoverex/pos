package cn.basewin.unionpay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.basewin.unionpay.R;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/27 15:24<br>
 * 描述:  把数字虚拟键盘 做成一个控件<br>
 */
public class VirtualKeyboardView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = VirtualKeyboardView.class.getName();
    public static final String OK = "ok";//确定
    public static final String BACK = "back";//回退
    public static final String CANCEL = "cancel";//清除
    public TextView num00, num0, num1, num2, num3, num4, num5, num6, num7, num8,
            num9;
    public RelativeLayout numCancel;
    public RelativeLayout numBack;
    public RelativeLayout numOk;

    public VirtualKeyboardView(Context context) {
        super(context);
        initView();
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs, Context mContext) {
        super(context, attrs);
        initView();
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, Context mContext) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = View.inflate(this.getContext(), R.layout.shuzijianpan, this);
        num00 = (TextView) view.findViewById(R.id.num00);
        num0 = (TextView) view.findViewById(R.id.num0);
        num1 = (TextView) view.findViewById(R.id.num1);
        num2 = (TextView) view.findViewById(R.id.num2);
        num3 = (TextView) view.findViewById(R.id.num3);
        num4 = (TextView) view.findViewById(R.id.num4);
        num5 = (TextView) view.findViewById(R.id.num5);
        num6 = (TextView) view.findViewById(R.id.num6);
        num7 = (TextView) view.findViewById(R.id.num7);
        num8 = (TextView) view.findViewById(R.id.num8);
        num9 = (TextView) view.findViewById(R.id.num9);
        numCancel = (RelativeLayout) view.findViewById(R.id.num_cancel);
        numBack = (RelativeLayout) view.findViewById(R.id.num_back);
        numOk = (RelativeLayout) view.findViewById(R.id.num_ok);

        num00.setOnClickListener(this);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        numCancel.setOnClickListener(this);
        numBack.setOnClickListener(this);
        numOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = "";
        switch (v.getId()) {
            case R.id.num00:
                str = "00";
                break;
            case R.id.num0:
                str = "0";
                break;
            case R.id.num1:
                str = "1";
                break;
            case R.id.num2:
                str = "2";
                break;
            case R.id.num3:
                str = "3";
                break;
            case R.id.num4:
                str = "4";
                break;
            case R.id.num5:
                str = "5";
                break;
            case R.id.num6:
                str = "6";
                break;
            case R.id.num7:
                str = "7";
                break;
            case R.id.num8:
                str = "8";
                break;
            case R.id.num9:
                str = "9";
                break;
            case R.id.num_cancel:
                str = CANCEL;
                break;
            case R.id.num_back:
                str = BACK;
                break;
            case R.id.num_ok:
                str = OK;
                break;
            default:
                break;
        }
        if (mKeyListener != null) {
            mKeyListener.click(str);
        }
    }

    private KeyListener mKeyListener;

    public void setOnKeyListener(KeyListener mKeyListener) {
        this.mKeyListener = mKeyListener;
    }

    public interface KeyListener {
        void click(String s);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (this.numOk == null) {
                return false;
            }
            this.numOk.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (this.numBack == null) {
                return false;
            }
            this.numBack.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            if (this.numCancel == null) {
                return false;
            }
            this.numCancel.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_0) {
            if (this.num0 == null) {
                return false;
            }
            this.num0.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_1) {
            if (this.num1 == null) {
                return false;
            }
            this.num1.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_2) {
            if (this.num2 == null) {
                return false;
            }
            this.num2.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_3) {
            if (this.num3 == null) {
                return false;
            }
            this.num3.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_4) {
            if (this.num4 == null) {
                return false;
            }
            this.num4.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_5) {
            if (this.num5 == null) {
                return false;
            }
            this.num5.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_6) {
            if (this.num6 == null) {
                return false;
            }
            this.num6.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_7) {
            if (this.num7 == null) {
                return false;
            }
            this.num7.performClick();
        }

        if (keyCode == KeyEvent.KEYCODE_8) {
            if (this.num8 == null) {
                return false;
            }
            this.num8.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_9) {
            if (this.num9 == null) {
                return false;
            }
            this.num9.performClick();
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return false;
        }
        return false;
    }
}
