package cn.basewin.unionpay.view;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StyleRes;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;

public class CustomInputDialog extends AlertDialog {
    private TextView tv_dialog_msg;
    private EditText et_dialog_input;
    private FrameLayout layout_content;
    private KeyBoardView view_KeyBoard;

    private Button bt_dialog_bt1;
    private Button bt_dialog_bt2;
    private Button bt_dialog_bt3;

    private CharSequence msg;
    private CharSequence bt1text;
    private CharSequence bt2text;
    private CharSequence bt3text;
    private boolean superInput = false;
    private int inputType;
    private int len;
    private boolean checkLen = true;
    private boolean resultWithDismiss = true;
    private boolean isFinishIfDismiss = false;//当前activity是否关闭
    private Activity activity;

    public void setResultWithDismiss(boolean resultWithDismiss) {
        this.resultWithDismiss = resultWithDismiss;
    }

    public void setFinishIfDismiss(Activity activity) {
        this.activity = activity;
        isFinishIfDismiss = true;
    }

    public void setCheckLen(boolean checkLen) {
        this.checkLen = checkLen;
    }

    private View.OnClickListener bt1listener;
    private View.OnClickListener bt2listener;
    private View.OnClickListener bt3listener;
    private RedialogInputResult rr;

    private Context mcontext;

    public CustomInputDialog(Context context) {
        super(context);
        mcontext = context;
    }

    public CustomInputDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_custom);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        tv_dialog_msg = (TextView) findViewById(R.id.tv_dialog_msg);
        et_dialog_input = (EditText) findViewById(R.id.et_dialog_input);
        bt_dialog_bt1 = (Button) findViewById(R.id.bt_dialog_bt1);
        bt_dialog_bt2 = (Button) findViewById(R.id.bt_dialog_bt2);
        bt_dialog_bt3 = (Button) findViewById(R.id.bt_dialog_bt3);
        view_KeyBoard = new KeyBoardView(mcontext);

        if (msg != null) {
            tv_dialog_msg.setText(msg);
        }

        if (superInput) {
            et_dialog_input.setVisibility(View.VISIBLE);
            et_dialog_input.setInputType(InputType.TYPE_CLASS_TEXT | inputType);
            if (len > 0) {
                et_dialog_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len)});
            }
            layout_content = (FrameLayout) findViewById(R.id.layout_content);
            layout_content.setVisibility(View.VISIBLE);
            layout_content.addView(view_KeyBoard.getKeyBoardView());
            view_KeyBoard.setNumKeyListener(new KeyBoardView.NumKeyListener() {
                @Override
                public void onClick(View view) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(et_dialog_input.getText());
                    switch (view.getId()) {
                        case R.id.num00:
                            builder.append("00");
                            break;
                        case R.id.num0:
                            builder.append(0);
                            break;
                        case R.id.num1:
                            builder.append(1);
                            break;
                        case R.id.num2:
                            builder.append(2);
                            break;
                        case R.id.num3:
                            builder.append(3);
                            break;
                        case R.id.num4:
                            builder.append(4);
                            break;
                        case R.id.num5:
                            builder.append(5);
                            break;
                        case R.id.num6:
                            builder.append(6);
                            break;
                        case R.id.num7:
                            builder.append(7);
                            break;
                        case R.id.num8:
                            builder.append(8);
                            break;
                        case R.id.num9:
                            builder.append(9);
                            break;
                        case R.id.num_cancel:
                            if (builder.toString().length() > 0) {
                                builder.delete(0, builder.length());
                            } else {
                            }
                            break;
                        case R.id.num_back:
                            try {
                                builder = builder
                                        .delete(builder.length() - 1, builder.length());
                            } catch (Exception e) {

                            }
                            break;
                        case R.id.num_ok:
                            dismissWithReturnResult();
                            return;
                        default:
                            break;
                    }
                    et_dialog_input.setText(builder.toString());
                    et_dialog_input.setSelection(et_dialog_input.getText().length());
                }
            });
        } else {
        }

        if (bt1text != null) {
            bt_dialog_bt1.setVisibility(View.VISIBLE);
            bt_dialog_bt1.setText(bt1text);
            bt_dialog_bt1.setOnClickListener(bt1listener);
            bt_dialog_bt1.setOnTouchListener(mTouchListener);
        } else {
            bt_dialog_bt1.setVisibility(View.GONE);
        }
        if (bt2text != null) {
            bt_dialog_bt2.setVisibility(View.VISIBLE);
            bt_dialog_bt2.setText(bt2text);
            bt_dialog_bt2.setOnClickListener(bt2listener);
            bt_dialog_bt2.setOnTouchListener(mTouchListener);
        } else {
            bt_dialog_bt2.setVisibility(View.GONE);
        }
        if (bt3text != null) {
            bt_dialog_bt3.setVisibility(View.VISIBLE);
            bt_dialog_bt3.setText(bt3text);
            bt_dialog_bt3.setOnClickListener(bt3listener);
            bt_dialog_bt3.setOnTouchListener(mTouchListener);
        } else {
            bt_dialog_bt3.setVisibility(View.GONE);
        }
    }

    public CustomInputDialog setRdMsg(String str) {
        msg = str;
        if (tv_dialog_msg != null) {
            tv_dialog_msg.setText(str);
        }
        return this;
    }

    public CustomInputDialog setRdLeftButton(CharSequence text, View.OnClickListener l) {
        bt1text = text;
        bt1listener = l;
        return this;
    }

    public CustomInputDialog setRdMiddleButton(CharSequence text, View.OnClickListener l) {
        bt2text = text;
        bt2listener = l;
        return this;
    }

    public CustomInputDialog setRdRightButton(CharSequence text, View.OnClickListener l) {
        bt3text = text;
        bt3listener = l;
        return this;
    }

    public CustomInputDialog setEditText(int inputType, RedialogInputResult rr, int len) {
        this.superInput = true;
        this.rr = rr;
        this.len = len;
        this.inputType = inputType;
        return this;
    }

    //触摸按钮底色文字色更改
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                ((Button) arg0).setTextColor(0xffff0000);
            } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                ((Button) arg0).setTextColor(0xffffffff);
            }
            return false;
        }
    };

    int secs = 0;
    Handler prtHandler = null;
    boolean bSendFlag = true;
    Thread thread = null;

    public void startTimeout(int secs, Handler prtHandler, boolean bSendFlag) {
        this.secs = secs;
        this.prtHandler = prtHandler;
        this.bSendFlag = bSendFlag;
        if (thread == null)
            thread = new Thread(runnable);
        thread.start();
    }

    public void stopTimeout() {
        secs = -1;
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                while (true) {
                    Thread.sleep(1000);
                    secs--;
                    if (secs == 0) {
                        if (bSendFlag == true)
                            prtHandler.sendEmptyMessage(102);
                        dismiss();
                    }
                    if (secs < 0) break;
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    public interface RedialogInputResult {
        void InputResult(String str);
    }


    public void dismissWithReturnResult() {
        String result = et_dialog_input.getText().toString().trim();
        if ("".equals(result)) {
            TLog.showToast("请输入有效信息！");
            return;
        }
        if (len > 0 && result.length() < len && checkLen) {
            TLog.showToast("请输入 " + len + " 位！");
            return;
        }
        clearInput();
        if (resultWithDismiss) {
            dismiss();
        }

        if (rr != null) {
            rr.InputResult(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFinishIfDismiss && null != activity) {
            activity.finish();
        }
    }

    public void clearInput() {
        if (et_dialog_input != null) {
            et_dialog_input.setText(null);
        }
    }

    /**
     * 禁止Edittext弹出软件盘，光标依旧正常显示。
     */
    public void disableShowSoftInput() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            et_dialog_input.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(et_dialog_input, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(et_dialog_input, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void undisableShowSoftInput() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            et_dialog_input.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(et_dialog_input, true);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(et_dialog_input, true);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    /**
     * @throws
     * @MethodName:closeInputMethod
     * @Description:关闭系统软键盘
     */

    public void closeInputMethod(Context context) {

        try {
            //获取输入法的服务
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //判断是否在激活状态
            if (imm.isActive()) {
                //隐藏输入法!!,
                imm.hideSoftInputFromWindow(et_dialog_input.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
        } finally {
        }

    }

    /**
     * @throws
     * @MethodName:openInputMethod
     * @Description:打开系统软键盘
     */

    public void openInputMethod() {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            public void run() {

                InputMethodManager inputManager = (InputMethodManager) et_dialog_input

                        .getContext().getSystemService(

                                Context.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(et_dialog_input, 0);

            }

        }, 200);

    }
}
