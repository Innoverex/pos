package cn.basewin.unionpay.setting;

import android.widget.EditText;

import cn.basewin.unionpay.R;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/9 15:21<br>
 * 描述：外线号码
 */
public class SetExternalDialNumAty extends BaseSysSettingAty {
    private EditText et_outside_call;

    private void initViews() {
        et_outside_call = (EditText) findViewById(R.id.et_outside_call);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_diaonum;
    }

    @Override
    public String getAtyTitle() {
        return "外线号码";
    }

    @Override
    public void afterSetContentView() {
        initViews();
    }

    @Override
    public void save() {
        CommuParmDialFragment.setOutsideCall(et_outside_call.getText().toString());
        showModifyHint();
    }
}
