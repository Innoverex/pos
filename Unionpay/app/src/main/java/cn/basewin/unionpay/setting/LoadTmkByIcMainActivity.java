package cn.basewin.unionpay.setting;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.view.ListDialog;

public class LoadTmkByIcMainActivity extends BaseSysSettingAty {
    private static final String TAG = "LoadTmkByIcMainActivity";
    private LinearLayout ll_type;
    private TextView tv_type;
    private ListDialog ld;
    private String[] cp;
    private String typeStr;
    private Button btn_next;

    @Override
    public int getContentView() {
        return R.layout.activity_load_tmk_by_ic_main;
    }

    @Override
    public String getAtyTitle() {
        return "选择导入方式";
    }

    @Override
    public void afterSetContentView() {
        setRightBtnVisible(false);
        cp = new String[]{"自动导入", "手动导入"};
        ll_type = (LinearLayout) findViewById(R.id.ll_type);
        tv_type = (TextView) findViewById(R.id.tv_type);
        btn_next = (Button) findViewById(R.id.btn_next);
        typeStr = cp[0];
        ll_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ld = new ListDialog(mContext, typeStr, cp, new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        typeStr = str;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_type.setText(typeStr);
                            }
                        });
                        Log.i(TAG, "str=" + str);
                    }
                });
                ld.show();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "已选择导入方式：" + typeStr);
                Intent intent = null;
                if (cp[0].equals(typeStr)) {
                    intent = new Intent(LoadTmkByIcMainActivity.this, LoadTmkSwipeCardActivity.class);
                } else {
                    intent = new Intent(LoadTmkByIcMainActivity.this, ManuallyEnterKeyActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void save() {

    }
}
