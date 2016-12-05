package cn.basewin.unionpay.setting;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;

public class DownloadTmkFromICActivity extends BaseSysSettingAty {

    private Button btn_download;

    @Override
    public int getContentView() {
        return R.layout.activity_download_tmk_from_ic;
    }

    @Override
    public String getAtyTitle() {
        return "IC卡导密钥";
    }

    @Override
    public void afterSetContentView() {
        setRightBtnVisible(false);
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (AppConfig.LOAD_TMK_USE_EXTERNAL_APP) {
                    try {
                        intent = new Intent();
//                        ComponentName cn = new ComponentName("com.app.makd", "com.app.activity.MainActivity");
                        ComponentName cn = new ComponentName("com.bw.downloadtmkbyic", "com.bw.downloadtmkbyic.activity.MainActivity");
                        intent.setComponent(cn);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        TLog.showToast("请先安装导密钥的app！");
                    }
                } else {
                    intent = new Intent(DownloadTmkFromICActivity.this, LoadTmkByIcMainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void save() {

    }
}
