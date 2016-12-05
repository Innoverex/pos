package cn.basewin.unionpay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/20 16:17<br>
 * 描述:  <br>
 */
public class InputWidget extends LinearLayout {

    private static final String TAG = InputWidget.class.getName();
    private boolean isPW = false; //默认不是密码框
    private int maxLenght = 0;

    public InputWidget(Context context) {
        this(context, null);
    }

    private String label;
    private int backgroundResource;

    public InputWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        applyAttributes(context, attrs);
    }

    public InputWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttributes(context, attrs);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            // 初始化参数
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.WidgetInput);
            label = (String) a.getText(R.styleable.WidgetInput_labelInput);
            isPW = a.getBoolean(R.styleable.WidgetInput_pw, false);
            maxLenght = a.getInt(R.styleable.WidgetInput_maxLenght, 0);
            backgroundResource = a.getInt(R.styleable.WidgetInput_maxLenght, 0);
            a.recycle();
        }
        initView();
    }

    private TextView tv;
    private EditText et;
    private LinearLayout ll_input;

    private void initView() {
        View view = View.inflate(this.getContext(), R.layout.widget_input_window, this);
        tv = (TextView) view.findViewById(R.id.text_imput_window);
        et = (EditText) view.findViewById(R.id.edit_imput_window);
        ll_input = (LinearLayout) view.findViewById(R.id.ll_input);

        setIsPW(isPW);
        //设置长度
        if (maxLenght != 0) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenght)});
        }
        //设置内容
        tv.setText(label);
        if (AppConfig.POS_MODEL.equals(AppConfig.POS_P8000)) {
            disableShowSoftInput(et);
        }
    }

    //设置编辑框的长度
    public void setEtLenght(int i) {
        if (i > 0) {
            TLog.e("inputWidget", "设置长度：" + i);
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
        }
    }

    //设置编辑框的长度
    public void setEtLenght(TextView t) {
        if (t == null) {
            return;
        }
        t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(AppConfig.operator_staff)) {
                    setEtLenght(AppConfig.operator_staff_length);
                } else if (s.toString().equals(AppConfig.operator_sys)) {
                    setEtLenght(AppConfig.operator_sys_length);
                } else {
                    setEtLenght(AppConfig.operator_default_length);
                }
                setEditText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //设置标注
    public void setTextLabel(String msg) {
        if (tv != null) {
            tv.setText(msg);
        }
    }

    //设置编辑狂内容
    public void setEditText(String msg) {
        if (et != null) {
            et.setText(msg);
            et.setSelection(et.length());
        }
    }

    //得到edit的String
    public String getEditTextText() {
        String s = "";
        if (et != null) {
            s = et.getText().toString().trim();
        }
        return s;
    }

    //如果你对ettext有什么其他操作可以直接获取到这个实例然后进行操作
    public EditText getEditText() {
        return et;
    }

    /**
     * 禁止Edittext弹出软件盘，光标依旧正常显示。
     */
    public static void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static void undisableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, true);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, true);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    //设置不能弹出键盘
    public void setKeyBoard(boolean b) {
        if (b) {
            disableShowSoftInput(et);
        } else {
            undisableShowSoftInput(et);
        }
    }

    //设置密码输入框
    public void setIsPW(boolean b) {
        isPW = b;
        if (b) {
            int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL | InputType.TYPE_CLASS_TEXT;
            et.setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输入框
        } else {
        }
    }

    public static final int editTypeNumber = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL | InputType.TYPE_CLASS_TEXT;

    //设置输入类型
    public void setEditType(int inputType) {
        et.setInputType(inputType);
        setIsPW(isPW);
    }
}
