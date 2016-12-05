package cn.basewin.unionpay.setting;

import android.view.View;
import android.widget.Button;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;

public class DownloadTmkFromMposActivity extends BaseSysSettingAty {

    private Button btn_download;

    @Override
    public int getContentView() {
        return R.layout.activity_download_tmk_from_mpos;
    }

    @Override
    public String getAtyTitle() {
        return "母POS导密钥";
    }

    @Override
    public void afterSetContentView() {
        setRightBtnVisible(false);
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TLog.showToast("功能暂未实现！");
            }
        });
    }

    @Override
    public void save() {

    }
}
