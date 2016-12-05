package cn.basewin.unionpay.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;

public class ManuallyEnterKeyActivity extends BaseSysSettingAty {
    private String syh;
    private String zmy;
    private String jym;

    private EditText et_syh;
    private EditText et_zmy;
    private EditText et_jym;
    private Button btn_next;

    @Override
    public int getContentView() {
        return R.layout.activity_manually_enter_key;
    }

    @Override
    public String getAtyTitle() {
        return "手输密钥";
    }

    @Override
    public void afterSetContentView() {
        setRightBtnVisible(false);
        btn_next = (Button) findViewById(R.id.btn_next);
        et_syh = (EditText) findViewById(R.id.et_syh);
        et_zmy = (EditText) findViewById(R.id.et_zmy);
        et_jym = (EditText) findViewById(R.id.et_jym);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syh = et_syh.getText().toString().trim();
                zmy = et_zmy.getText().toString().trim();
                jym = et_jym.getText().toString().trim();
                if (TextUtils.isEmpty(zmy)) {
                    TLog.showToast("主密钥不能为空");
                    return;
                }
                if (TextUtils.isEmpty(jym)) {
                    TLog.showToast("校验码不能为空");
                    return;
                }
                IccPersonal.setMiwTMK(zmy);
                IccPersonal.setJyTMK(jym);
                Intent intent = new Intent(ManuallyEnterKeyActivity.this, LoadTmkSwipeCardActivity.class);
                intent.putExtra(LoadTmkSwipeCardActivity.TAG_DOWNLOAD_TMK_TYPE, LoadTmkSwipeCardActivity.MES_DOWNLOAD_TMK_BY_HAND);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void save() {

    }
}