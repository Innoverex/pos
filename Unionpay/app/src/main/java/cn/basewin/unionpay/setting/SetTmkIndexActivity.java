package cn.basewin.unionpay.setting;

import android.widget.EditText;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;

public class SetTmkIndexActivity extends BaseSysSettingAty {
    private EditText et_id;

    @Override
    public int getContentView() {
        return R.layout.activity_set_tmk_id;
    }

    @Override
    public String getAtyTitle() {
        return "密钥管理";
    }

    @Override
    public void afterSetContentView() {
        et_id = (EditText) findViewById(R.id.et_id);
        et_id.setText("01");
        et_id.setSelection(et_id.length());
    }

    @Override
    public void save() {
        TLog.showToast("密钥索引号为：" + et_id.getText().toString().trim());
    }
}
