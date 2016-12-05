package cn.basewin.unionpay.trade;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.InputWidget;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/20 17:58<br>
 * 描述:  <br>
 */
public class BaseSignInAty extends Activity {
    private Button button_cancel, button_ok;
    protected InputWidget inputview_oper, inputview_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baselogin);
        inputview_oper = (InputWidget) findViewById(R.id.inputview_oper);
        inputview_password = (InputWidget) findViewById(R.id.inputview_password);

        inputview_password.setEtLenght(AppConfig.operator_default_length);
        inputview_password.setEtLenght(inputview_oper.getEditText());

        //2个button
        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_ok = (Button) findViewById(R.id.button_ok);

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputview_oper.getEditTextText();
                String pw = inputview_password.getEditTextText();
                onClickOK(name, pw);
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCancel();
            }
        });
    }

    public void setInputType(int type) {
        inputview_password.setEditType(type);
        inputview_oper.setEditType(type);
    }

    protected String getOper() {
        return inputview_oper.getEditTextText();
    }

    protected String getpw() {
        return inputview_password.getEditTextText();
    }

    protected void onClickOK(String name, String pw) {

    }

    protected void onClickCancel() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (inputview_oper != null && inputview_oper.getEditText().isSelected()) {
        }
        return super.onKeyDown(keyCode, event);
    }

    public void cleanText() {
        inputview_password.setEditText("");
        inputview_oper.setEditText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        TLog.l("BaseSignInAty onResume");
        cleanText();
    }
}
