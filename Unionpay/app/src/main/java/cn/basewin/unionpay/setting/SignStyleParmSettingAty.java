package cn.basewin.unionpay.setting;

import android.widget.EditText;
import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：签名版参数设置
 */
public class SignStyleParmSettingAty extends BaseSysSettingAty {
    private static final String TAG = SignStyleParmSettingAty.class.getName();
    /**
     * 是否支持电子签名
     */
    private static final String KEY_IF_SUPPORT_ESIGN = TAG + "if_support_eletronic_signature";
    /**
     * 等待签字时间
     */
    private static final String KEY_WAIT_TIME_FOR_SIGN = TAG + "wait_time_for_sign";
    /**
     * 上送签字重试次数
     */
    private static final String KEY_UPLOAD_SIGN_TRY_TIME = TAG + "upload_sign_try_time";
    /**
     * 两笔联机交易间最大离线交易笔数
     */
    private static final String KEY_OFF_LINE_BETWEEN_ONLINE = TAG + "offline_count_between_online";
    /**
     * 电子签名最大交易笔数
     */
    private static final String KEY_MAX_COUNT_OF_SIGN = TAG + "max_count_of_sign";
    /**
     * 电子签名重签次数
     */
    private static final String KEY_ESIGN_TRY_TIME = TAG + "esign_try_time";
    /**
     * 是否输入手机号码
     */
    private static final String KEY_IF_INPUT_PHONE_NUM = TAG + "if_input_phone_num";
    /**
     * 是否支持分包上送
     */
    private static final String KEY_IF_SUPPORT_SUBPACKAGE_UPLOAD = TAG + "if_support_subpackage_upload";
    /**
     * 是否支持电子签名
     */
    private ToggleButton togbtn_if_support_eletronic_signature;
    /**
     * 等待签字时间
     */
    private EditText et_wait_time_for_sign;
    /**
     * 上送签字重试次数
     */
    private EditText et_upload_sign_try_time;
    /**
     * 两笔联机交易间最大离线交易笔数
     */
    private EditText et_offline_count_between_online;
    /**
     * 电子签名最大交易笔数
     */
    private EditText et_max_count_of_sign;
    /**
     * 电子签名重签次数
     */
    private EditText et_esign_try_time;
    /**
     * 是否输入手机号码
     */
    private ToggleButton togbtn_if_input_phone_num;
    /**
     * 是否支持分包上送
     */
    private ToggleButton togbtn_if_support_subpackage_upload;

    private void initViews() {
        togbtn_if_support_eletronic_signature = (ToggleButton) findViewById(R.id.togbtn_if_support_eletronic_signature);
        et_wait_time_for_sign = (EditText) findViewById(R.id.et_wait_time_for_sign);
        et_upload_sign_try_time = (EditText) findViewById(R.id.et_upload_sign_try_time);
        et_offline_count_between_online = (EditText) findViewById(R.id.et_offline_count_between_online);
        et_max_count_of_sign = (EditText) findViewById(R.id.et_max_count_of_sign);
        et_esign_try_time = (EditText) findViewById(R.id.et_esign_try_time);
        togbtn_if_input_phone_num = (ToggleButton) findViewById(R.id.togbtn_if_input_phone_num);
        togbtn_if_support_subpackage_upload = (ToggleButton) findViewById(R.id.togbtn_if_support_subpackage_upload);
    }

    private void initData() {
        togbtn_if_support_eletronic_signature.setChecked(getIfSupportEsign());
        et_wait_time_for_sign.setText(getWaitTimeForSign());
        et_upload_sign_try_time.setText(SPTools.get(KEY_UPLOAD_SIGN_TRY_TIME, getUploadSignTryTime()));
        et_offline_count_between_online.setText(SPTools.get(KEY_OFF_LINE_BETWEEN_ONLINE, getOffLineBetweenOnline()));
        et_max_count_of_sign.setText(SPTools.get(KEY_MAX_COUNT_OF_SIGN, getMaxCountOfSign()));
        et_esign_try_time.setText(SPTools.get(KEY_ESIGN_TRY_TIME, getEsignTryTime()));
        togbtn_if_input_phone_num.setChecked(SPTools.get(KEY_IF_INPUT_PHONE_NUM, getIfInputPhoneNum()));
        togbtn_if_support_subpackage_upload.setChecked(SPTools.get(KEY_IF_SUPPORT_SUBPACKAGE_UPLOAD, getIfSupportSubpackageUpload()));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_signstyleparam;
    }

    public static boolean getIfSupportEsign() {
        return SPTools.get(KEY_IF_SUPPORT_ESIGN, AppConfig.DEFAULT_VALUE_IF_SUPPORT_ESIGN);
    }

    public static String getWaitTimeForSign() {
        return SPTools.get(KEY_WAIT_TIME_FOR_SIGN, AppConfig.DEFAULT_VALUE_WAIT_TIME_FOR_SIGN);
    }

    public static String getUploadSignTryTime() {
        return SPTools.get(KEY_UPLOAD_SIGN_TRY_TIME, AppConfig.DEFAULT_VALUE_UPLOAD_SIGN_TRY_TIME);
    }

    public static String getOffLineBetweenOnline() {
        return SPTools.get(KEY_OFF_LINE_BETWEEN_ONLINE, AppConfig.DEFAULT_VALUE_OFF_LINE_BETWEEN_ONLINE);
    }

    public static String getMaxCountOfSign() {
        return SPTools.get(KEY_MAX_COUNT_OF_SIGN, AppConfig.DEFAULT_VALUE_MAX_COUNT_OF_SIGN);
    }

    public static String getEsignTryTime() {
        return SPTools.get(KEY_ESIGN_TRY_TIME, AppConfig.DEFAULT_VALUE_ESIGN_TRY_TIME);
    }

    public static boolean getIfInputPhoneNum() {
        return SPTools.get(KEY_IF_INPUT_PHONE_NUM, AppConfig.DEFAULT_VALUE_IF_INPUT_PHONE_NUM);
    }

    public static boolean getIfSupportSubpackageUpload() {
        return SPTools.get(KEY_IF_SUPPORT_SUBPACKAGE_UPLOAD, AppConfig.DEFAULT_VALUE_IF_SUPPORT_SUBPACKAGE_UPLOAD);
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.sign_style_param);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(KEY_IF_SUPPORT_ESIGN, togbtn_if_support_eletronic_signature.isChecked());
        SPTools.set(KEY_WAIT_TIME_FOR_SIGN, et_wait_time_for_sign.getText().toString());
        SPTools.set(KEY_UPLOAD_SIGN_TRY_TIME, et_upload_sign_try_time.getText().toString());
        SPTools.set(KEY_OFF_LINE_BETWEEN_ONLINE, et_offline_count_between_online.getText().toString());
        SPTools.set(KEY_MAX_COUNT_OF_SIGN, et_max_count_of_sign.getText().toString());
        SPTools.set(KEY_ESIGN_TRY_TIME, et_esign_try_time.getText().toString());
        SPTools.set(KEY_IF_INPUT_PHONE_NUM, togbtn_if_input_phone_num.isChecked());
        SPTools.set(KEY_IF_SUPPORT_SUBPACKAGE_UPLOAD, togbtn_if_support_subpackage_upload.isChecked());
        showModifyHint();
    }
}
