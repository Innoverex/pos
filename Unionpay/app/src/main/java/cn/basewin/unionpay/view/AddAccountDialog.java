package cn.basewin.unionpay.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/24 10:47<br>
 * 描述: 添加柜员的一个dialog<br>
 */
public class AddAccountDialog extends Dialog {
    private Context mContext;
    private AddAccountDialogClickListening mListening;
    private boolean isOkDismiss = false;//是否点击ok键关闭当前dialog

    public AddAccountDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private Button btn_dialog_account_unok, btn_dialog_account_ok;
    private InputWidget inputview0, inputview1, inputview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, R.layout.dialog_addaccount, null);
        inputview0 = (InputWidget) view.findViewById(R.id.inputview0);
        inputview1 = (InputWidget) view.findViewById(R.id.inputview1);
        inputview2 = (InputWidget) view.findViewById(R.id.inputview2);
        btn_dialog_account_unok = (Button) view.findViewById(R.id.btn_dialog_account_unok);
        btn_dialog_account_ok = (Button) view.findViewById(R.id.btn_dialog_account_ok);

        inputview0.setEtLenght(AppConfig.operator_name_default_length);
        inputview1.setEtLenght(AppConfig.operator_default_length);
        inputview2.setEtLenght(AppConfig.operator_default_length);

        btn_dialog_account_unok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListening != null) {
                    mListening.cancel();
                    AddAccountDialog.this.dismiss();
                }
            }
        });
        btn_dialog_account_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListening != null) {
                    String t0 = inputview0.getEditTextText();
                    String t1 = inputview1.getEditTextText();
                    String t2 = inputview2.getEditTextText();
                    mListening.ok(t0, t1, t2);
                }
            }
        });
        inputview0.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setPWLength(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setContentView(view);
    }

    //按钮点击监听设置
    public void setAddAccountDialogClickListening(AddAccountDialogClickListening mListening) {
        this.mListening = mListening;
    }

    public void setOkDismiss(boolean okDismiss) {
        isOkDismiss = okDismiss;
    }

    //2个按钮的监听
    public interface AddAccountDialogClickListening {
        void ok(String account, String pw, String newpw);

        void cancel();
    }

    //通过传入的账户 修改密码的长度
    private void setPWLength(String account) {
        int i = AppConfig.operator_default_length;
        if (account.equals(AppConfig.operator_sys)) {
            i = AppConfig.operator_sys_length;
        } else if (account.equals(AppConfig.operator_staff)) {
            i = AppConfig.operator_staff_length;
        } else {

        }
        inputview1.setEtLenght(i);
        inputview2.setEtLenght(i);
    }
}
