package cn.basewin.unionpay.setting;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.view.ListDialog;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：离线交易控制
 */
public class TradeOfflineSettingAty extends BaseSysSettingAty {
    private static final String TAG = TradeOfflineSettingAty.class.getName();
    /**
     * 离线上送方式(SharePreference Key值)
     */
    private static final String KEY_UPLOAD_TYPE = TAG + "upload_type";
    /**
     * 离线上送重发次数（1-9）(SharePreference Key值)
     */
    private static final String KEY_UPLOAD_TIMES = TAG + "upload_times";
    /**
     * 自动上送累计笔数(SharePreference Key值)
     */
    private static final String KEY_UPLOAD_TOTAL_NUMBERS = TAG + "upload_total_numbers";
    /**
     * 离线上送方式
     */
    private TextView tv_upload_type;
    /**
     * 离线上送重发次数（1-9）
     */
    private EditText et_upload_times;
    /**
     * 自动上送累计笔数
     */
    private EditText et_upload_total_numbers;
    private String[] upload_type = {"联机前", "结算前"};

    private void initViews() {
        tv_upload_type = (TextView) findViewById(R.id.tv_waytoofflineupload);
        et_upload_times = (EditText) findViewById(R.id.et_offline_upload_time);
        et_upload_total_numbers = (EditText) findViewById(R.id.et_auto_upload_accmulative_time);
    }

    private void initData() {
        tv_upload_type.setText(SPTools.get(TradeOfflineSettingAty.KEY_UPLOAD_TYPE, getUploadType()));
        et_upload_times.setText(SPTools.get(TradeOfflineSettingAty.KEY_UPLOAD_TIMES, getUploadTimes()));
        et_upload_total_numbers.setText(SPTools.get(TradeOfflineSettingAty.KEY_UPLOAD_TOTAL_NUMBERS, getUploadTotalNumbers()));
    }

    private void setListener() {
        tv_upload_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new ListDialog(mContext, tv_upload_type.getText().toString(), upload_type
                        , new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_upload_type.setText(str);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_offlinetradecontrol;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.offline_trade_control);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
        setListener();
    }

    @Override
    public void save() {
        SPTools.set(TradeOfflineSettingAty.KEY_UPLOAD_TYPE, tv_upload_type.getText().toString());
        SPTools.set(TradeOfflineSettingAty.KEY_UPLOAD_TIMES, et_upload_times.getText().toString());
        SPTools.set(TradeOfflineSettingAty.KEY_UPLOAD_TOTAL_NUMBERS, et_upload_total_numbers.getText().toString());
        showModifyHint();
    }

    public static String getUploadType() {
        return SPTools.get(TradeOfflineSettingAty.KEY_UPLOAD_TYPE, AppConfig.DEFAULT_VALUE_UPLOAD_TYPE);
    }

    public static String getUploadTimes() {
        return SPTools.get(TradeOfflineSettingAty.KEY_UPLOAD_TIMES, AppConfig.DEFAULT_VALUE_UPLOAD_TIMES);
    }

    public static String getUploadTotalNumbers() {
        return SPTools.get(TradeOfflineSettingAty.KEY_UPLOAD_TOTAL_NUMBERS, AppConfig.DEFAULT_VALUE_UPLOAD_TOTAL_NUMBERS);
    }
}
