package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/22 12:01<br>
 * 描述：输入持卡人姓名
 */
public class InputNameAty extends BaseFlowAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_NAME;
    public static final String KEY_DATA = "InputNameAty_input_name";
    /**
     * 输入提示
     */
    private TextView tv_input_hint;
    /**
     * 内容
     */
    private EditText et_input_txt;
    private Button btn_finish;
    private Button btn_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_inputname);
        initViews();
        setListener();
    }

    private void initViews() {
        et_input_txt = (EditText) findViewById(R.id.et_input_txt);
        tv_input_hint = (TextView) findViewById(R.id.tv_input_hint);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_finish = (Button) findViewById(R.id.btn_finish);
    }

    private void setListener() {
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTxt(et_input_txt.getText().toString());
            }
        });
    }

    private void checkTxt(String str) {
        FlowControl.MapHelper.setName(str);
        startNextFlow();
    }
}
