package cn.basewin.unionpay.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.ListDialog;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：结算交易控制
 */
public class TradeOtherSettingAty extends BaseSysSettingAty {
    private static final String TAG = TradeOtherSettingAty.class.getName();
    /**
     * 是否输入主管密码(Key值)
     */
    private static final String KEY_NEED_MANAGER_PWD = TAG + "need_manager_pwd";
    /**
     * 是否允许手输卡号(Key值)
     */
    private static final String KEY_NEED_GETCARD_MANUALLY = TAG + "need_getcard_manually";
    /**
     * 默认刷卡交易(Key值)
     */
    private static final String KEY_DEFAULT_TRANS = TAG + "default_trans";
    /**
     * 退货限额(Key值)
     */
    private static final String KEY_REFUND_LIMIT = TAG + "refund_limit";
    /**
     * 磁道是否加密(Key值)
     */
    private static final String KEY_NEED_TRACK_ENCRYPT = TAG + "need_track_encrypt";
    /**
     * 预授权是否允许手输卡号
     */
    private static final String KEY_NEED_GETCARD_MANUALLY_AUTH = TAG + "need_getcard_manually_auth";
    /**
     * 是否输入主管密码
     */
    private ToggleButton togbtn_need_manager_pwd;
    /**
     * 是否允许手输卡号
     */
    private ToggleButton togbtn_need_getcard_manually;
    /**
     * 默认刷卡交易
     */
    private TextView tv_default_trans;
    /**
     * 退货限额
     */
    private EditText et_refund_limit;
    /**
     * 磁道是否加密
     */
    private ToggleButton togbtn_need_track_encrypt;
    /**
     * 预授权是否允许手输卡号
     */
    private ToggleButton togbtn_need_getcard_manually_auth;
    private String[] default_trans = {"消费", "预授权"};

    private void initViews() {
        togbtn_need_manager_pwd = (ToggleButton) findViewById(R.id.togbtn_if_input_manager_pwd);
        togbtn_need_getcard_manually = (ToggleButton) findViewById(R.id.togbtn_allow_input_cardnum_manually);
        tv_default_trans = (TextView) findViewById(R.id.tv_default_swipcard_type);
        et_refund_limit = (EditText) findViewById(R.id.et_refund_limit);
        togbtn_need_track_encrypt = (ToggleButton) findViewById(R.id.togbtn_if_trace_encrypt);
        togbtn_need_getcard_manually_auth = (ToggleButton) findViewById(R.id.togbtn_if_authorization_input_cardid);
    }

    private void initData() {
        togbtn_need_manager_pwd.setChecked(isManagerPWD());
        togbtn_need_getcard_manually.setChecked(getNeedGetcardManually());
        tv_default_trans.setText(getDefaultTrans());
        et_refund_limit.setText(String.valueOf(getRefundLimit()));
        togbtn_need_track_encrypt.setChecked(getNeedTrackEncrypt());
        togbtn_need_getcard_manually_auth.setChecked(getNeedGetcardManuallyAuth());
    }

    //是否输入主管密码
    public static boolean isManagerPWD() {
        return SPTools.get(KEY_NEED_MANAGER_PWD, AppConfig.DEFAULT_VALUE_NEED_MANAGER_PWD);
    }

    //最大退货的钱
    public static double getRefundLimit() {
        String s = SPTools.get(KEY_REFUND_LIMIT, AppConfig.DEFAULT_VALUE_REFUND_LIMIT);
        double v = 10000.00;
        try {
            v = Double.parseDouble(s);
        } catch (Exception e) {
        }
        return v;
    }

    public static boolean getNeedGetcardManually() {
        return SPTools.get(KEY_NEED_GETCARD_MANUALLY, AppConfig.DEFAULT_VALUE_NEED_GETCARD_MANUALLY);
    }

    public static String getDefaultTrans() {
        return SPTools.get(KEY_DEFAULT_TRANS, AppConfig.DEFAULT_VALUE_DEFAULT_TRANS);
    }

    public static boolean getNeedTrackEncrypt() {
        return SPTools.get(KEY_NEED_TRACK_ENCRYPT, AppConfig.DEFAULT_VALUE_NEED_TRACK_ENCRYPT);
    }

    public static boolean getNeedGetcardManuallyAuth() {
        return SPTools.get(KEY_NEED_GETCARD_MANUALLY_AUTH, AppConfig.DEFAULT_VALUE_NEED_GETCARD_MANUALLY_AUTH);
    }

    //判断是否在 规定的退货金额内  是为true
    public static boolean isRefund(Double v) {
        boolean b = true;
        if (v > getRefundLimit()) {
            b = false;
        }
        return b;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_othercontol;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.other_trade_control);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
        setListener();
    }

    private void setListener() {
        tv_default_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choose = tv_default_trans.getText().toString();
                ListDialog dialog = new ListDialog(mContext, choose, default_trans
                        , new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_default_trans.setText(str);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void save() {
        double refundLimit = Double.parseDouble(et_refund_limit.getText().toString());
        if (refundLimit > 10000) {
            TLog.showToast("退货限额最大为10000元");
            return;
        }

        SPTools.set(KEY_NEED_MANAGER_PWD, togbtn_need_manager_pwd.isChecked());
        SPTools.set(KEY_NEED_GETCARD_MANUALLY, togbtn_need_getcard_manually.isChecked());
        SPTools.set(KEY_DEFAULT_TRANS, tv_default_trans.getText().toString());
        SPTools.set(KEY_REFUND_LIMIT, et_refund_limit.getText().toString());
        SPTools.set(KEY_NEED_TRACK_ENCRYPT, togbtn_need_track_encrypt.isChecked());
        SPTools.set(KEY_NEED_GETCARD_MANUALLY_AUTH, togbtn_need_getcard_manually_auth.isChecked());
        showModifyHint();
    }
}
