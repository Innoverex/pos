package cn.basewin.unionpay.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.InputWidget;
import cn.basewin.unionpay.view.VirtualKeyboardView;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/27 13:42<br>
 * 描述: 输入的一个ui界面 此界面没有任何效果 需要实现（VirtualKeyboardView）键盘控件 输入监听 用输入监听改变 <br>
 */
public abstract class BaseInputAty extends BaseFlowAty {
    private static final String TAG = BaseInputAty.class.getName();
    protected InputWidget inputview_input_money;
    protected LinearLayout btn_input_money;
    protected VirtualKeyboardView keyboard_input_money;
    protected Button btn_dialog_account_unok, btn_dialog_account_ok;
    protected TextView tv_baseinputmoney_hint;
    protected int code_rule1 = 1;//输入规则 1：金额 2：数字 3其他规则（待开发）   自定义规则的时候 请重写init 在init里面设置规则

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseinput_text);
        tv_baseinputmoney_hint = (TextView) findViewById(R.id.tv_baseinputmoney_hint);//提示语
        inputview_input_money = (InputWidget) findViewById(R.id.inputview_input_money);//显示
        inputview_input_money.setKeyBoard(true);

        btn_input_money = (LinearLayout) findViewById(R.id.btn_input_money);//确定和取消的按钮
        keyboard_input_money = (VirtualKeyboardView) findViewById(R.id.keyboard_input_money);//虚拟键盘

        //P8000 显示 按钮  隐藏 虚拟键盘
        if (AppConfig.hasPhysicalKey()) {
            btn_input_money.setVisibility(View.VISIBLE);
            keyboard_input_money.setVisibility(View.GONE);
        } else {
            btn_input_money.setVisibility(View.GONE);
            keyboard_input_money.setVisibility(View.VISIBLE);
        }
        //2个按钮
        btn_dialog_account_unok = (Button) btn_input_money.findViewById(R.id.btn_dialog_account_unok);
        btn_dialog_account_ok = (Button) btn_input_money.findViewById(R.id.btn_dialog_account_ok);
        initAction();
        init();
        initTitle();
    }

    protected void init() {

    }

    private void initAction() {
        btn_dialog_account_unok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickunOK();
            }
        });
        btn_dialog_account_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = inputview_input_money.getEditTextText();
                clickOK(s);
            }
        });
    }

    protected abstract void clickOK(String s);

    protected abstract void clickunOK();

    //设置提示语
    protected void setHint(String msg) {
        tv_baseinputmoney_hint.setText(msg);
    }

    //设置 密码输入框
    protected void setIsPW() {
        inputview_input_money.setIsPW(true);
    }

    //设置 输入框长度
    protected void setLenghtEdit(int i) {
        inputview_input_money.setEtLenght(i);
    }

    //设置 标签
    protected void setLabel2(String msg) {
        inputview_input_money.setTextLabel(msg);
    }

    protected void setLabel(String msg) {
        inputview_input_money.setTextLabel(msg + ":");
    }

    //设置输入框内的内容
    protected void setTextContent(String msg) {
        inputview_input_money.setEditText(msg);
    }

    public static final int rule_money = 1;
    public static final int rule_number = 2;

    //设置虚拟键盘 和 edit 的匹配规则
    //如果不想
    protected void setRule(int i) {
        switch (i) {
            case 1:
                rule1Money();
                break;
            case 2:
                rule1Number();
                break;
            case 9:
                break;
        }
    }

    //金额匹配
    protected void rule1Money() {
        final EditText edit = inputview_input_money.getEditText();
        //设置初识值
        inputview_input_money.setEditText(AppConfig.money_default);
        keyboard_input_money.setOnKeyListener(new VirtualKeyboardView.KeyListener() {
            @Override
            public void click(String s) {
                String num = edit.getText().toString();
                int index = edit.getSelectionStart();
                int indexOf = num.indexOf(".");
                String nums = num.replace(".", "");
                String start = "";
                String end = "";
                if (index <= indexOf) {
                    start = nums.substring(0, index);
                    end = nums.substring(index, nums.length());
                } else {
                    start = nums.substring(0, index - 1);
                    end = nums.substring(index - 1, nums.length());
                }
                StringBuilder builder = new StringBuilder(start);
                TLog.l("start：" + start.toString());
                TLog.l("end：" + end.toString());
                if (s.equals(VirtualKeyboardView.OK)) {
                    String st = inputview_input_money.getEditTextText();
                    clickOK(st);
                    return;
                } else if (s.equals(VirtualKeyboardView.BACK)) {
                    if (edit.getText().toString().trim().length() > 0) {
                        builder.delete(builder.length() - 1, builder.length());
                    }
                } else if (s.equals(VirtualKeyboardView.CANCEL)) {
                    edit.setText(AppConfig.money_default);
                    edit.setSelection(edit.length());
                    return;
                } else {
                    builder.append(s);
                }
                StringBuilder append = builder.append(end);
                String result = append.insert(append.length() - 2, ".").toString();
                Double money_f = Double.valueOf(result);
                TLog.l("result" + result);
                TLog.l("money_f" + money_f);
                if (money_f < AppConfig.MAX_MONEY || s.equals(VirtualKeyboardView.BACK)) {
                    edit.setText(PosUtil.changeAmout(result));
                } else {
                    TLog.showToast(getResources().getString(R.string.money_over_max));
                }
                edit.setSelection(edit.length());
            }
        });
    }

    //普通数字匹配规则
    protected void rule1Number() {
        final EditText edit = inputview_input_money.getEditText();
        keyboard_input_money.setOnKeyListener(new VirtualKeyboardView.KeyListener() {
            @Override
            public void click(String s) {
                String start = edit.getText().toString().substring(0, edit.getSelectionStart());
                String end = edit.getText().toString().substring(edit.getSelectionStart(), edit.getText().length());
                StringBuilder builder = new StringBuilder(start);
                if (s.equals(VirtualKeyboardView.OK)) {
                    String st = inputview_input_money.getEditTextText();
                    clickOK(st);
                    return;
                } else if (s.equals(VirtualKeyboardView.BACK)) {
                    if (edit.getText().toString().trim().length() > 0) {
                        builder.delete(builder.length() - 1, builder.length());
                    }
                } else if (s.equals(VirtualKeyboardView.CANCEL)) {
                    edit.setText("");
                    edit.setSelection(edit.length());
                    return;
                } else {
                    builder.append(s);
                }
                String result = builder.append(end).toString();
                edit.setText(result);
                edit.setSelection(edit.length());
            }
        });
    }
}
