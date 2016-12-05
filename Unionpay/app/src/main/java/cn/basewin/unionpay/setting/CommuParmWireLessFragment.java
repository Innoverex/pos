package cn.basewin.unionpay.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.network.remote.NetTools;
import cn.basewin.unionpay.utils.SPTools;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 10:27<br>
 * 描述：通讯参数设置之无线
 */
public class CommuParmWireLessFragment extends BaseSysFragment {
    private static final String TAG = CommuParmWireLessFragment.class.getName();
    /**
     * APN设置
     */
    private static final String KEY_APN_SETTING = TAG + "apn_setting";
    /**
     * 用户名
     */
    private static final String KEY_USER_NAME_SETTING = TAG + "user_name_setting";
    /**
     * 用户密码
     */
    private static final String KEY_USER_PWD_SETTING = TAG + "user_pwd_setting";
    /**
     * 是否启用自定义VPN
     */
    private static final String KEY_IF_USE_CUSTOM_APN = TAG + "if_use_custom_apn";
    /**
     * 设置主机IP
     */
    private static final String KEY_SET_HOST_IP = TAG + "set_host_ip";
    /**
     * 设置主机端口
     */
    private static final String KEY_SET_HOST_PORT = TAG + "set_host_port";
    /**
     * 设置备用IP
     */
    private static final String KEY_SET_BACKUP_IP = TAG + "set_backup_ip";
    /**
     * 设置备用端口
     */
    private static final String KEY_SET_BACKUP_PORT = TAG + "set_backup_port";
    /**
     * APN设置
     */
    private EditText et_apn_setting;
    /**
     * 用户名
     */
    private EditText et_user_name_setting;
    /**
     * 用户密码
     */
    private EditText et_user_pwd_setting;
    /**
     * 是否启用自定义VPN
     */
    private ToggleButton togbtn_if_use_custom_apn;
    /**
     * 设置端口及IP
     */
    private EditText et_set_host_ip, et_set_host_post, et_set_backup_ip, et_set_backup_post;

    private void initView(View view) {
        et_apn_setting = (EditText) view.findViewById(R.id.et_apn_setting);
        et_user_name_setting = (EditText) view.findViewById(R.id.et_user_name_setting);
        et_user_pwd_setting = (EditText) view.findViewById(R.id.et_user_pwd_setting);
        togbtn_if_use_custom_apn = (ToggleButton) view.findViewById(R.id.togbtn_if_use_custom_apn);
        et_set_host_ip = (EditText) view.findViewById(R.id.et_set_host_ip);
        et_set_host_post = (EditText) view.findViewById(R.id.et_set_host_post);
        et_set_backup_ip = (EditText) view.findViewById(R.id.et_set_backup_ip);
        et_set_backup_post = (EditText) view.findViewById(R.id.et_set_backup_post);
    }

    private void initData() {
        et_apn_setting.setText(getApnSetting());
        et_user_name_setting.setText(getUserNameSetting());
        et_user_pwd_setting.setText(getUserPwdSetting());
        togbtn_if_use_custom_apn.setChecked(getIfUseCustomApn());
        et_set_host_ip.setText(getSetHostIp());
        et_set_host_post.setText(getSetHostPort());
        et_set_backup_ip.setText(getSetBackupIp());
        et_set_backup_post.setText(getSetBackupPort());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_sysset_commuparm_wireless;
    }

    @Override
    public void afterGetView(View view) {
        initView(view);
        initData();
    }

    @Override
    public void save() {
        SPTools.set(KEY_APN_SETTING, et_apn_setting.getText().toString());
        SPTools.set(KEY_USER_NAME_SETTING, et_user_name_setting.getText().toString());
        SPTools.set(KEY_USER_PWD_SETTING, et_user_pwd_setting.getText().toString());
        SPTools.set(KEY_IF_USE_CUSTOM_APN, togbtn_if_use_custom_apn.isChecked());
        SPTools.set(KEY_SET_HOST_IP, et_set_host_ip.getText().toString());
        SPTools.set(KEY_SET_HOST_PORT, et_set_host_post.getText().toString());
        SPTools.set(KEY_SET_BACKUP_IP, et_set_backup_ip.getText().toString());
        SPTools.set(KEY_SET_BACKUP_PORT, et_set_backup_post.getText().toString());
        CommuParmAty.setCommuType("无线");
        NetTools.init();
    }

    public static String getApnSetting() {
        return SPTools.get(KEY_APN_SETTING, AppConfig.DEFAULT_VALUE_APN_SETTING);
    }

    public static String getUserNameSetting() {
        return SPTools.get(KEY_USER_NAME_SETTING, AppConfig.DEFAULT_VALUE_USER_NAME_SETTING);
    }

    public static String getUserPwdSetting() {
        return SPTools.get(KEY_USER_PWD_SETTING, AppConfig.DEFAULT_VALUE_USER_PWD_SETTING);
    }

    public static boolean getIfUseCustomApn() {
        return SPTools.get(KEY_IF_USE_CUSTOM_APN, AppConfig.DEFAULT_VALUE_IF_USE_CUSTOM_APN);
    }

    public static String getSetHostIp() {
        return SPTools.get(KEY_SET_HOST_IP, AppConfig.DEFAULT_VALUE_SET_WIRELESS_HOST_IP);
    }

    public static String getSetHostPort() {
        return SPTools.get(KEY_SET_HOST_PORT, AppConfig.DEFAULT_VALUE_SET_WIRELESS_HOST_PORT);
    }

    public static void setHostIp(String ip) {
        SPTools.set(KEY_SET_HOST_IP, ip);
    }

    public static void setHostPort(String p) {
        SPTools.set(KEY_SET_HOST_PORT, p);
    }

    public static String getSetBackupIp() {
        return SPTools.get(KEY_SET_BACKUP_IP, AppConfig.DEFAULT_VALUE_SET_WIRELESS_BACKUP_IP);
    }

    public static String getSetBackupPort() {
        return SPTools.get(KEY_SET_BACKUP_PORT, AppConfig.DEFAULT_VALUE_SET_WIRELESS_BACKUP_PORT);
    }
}
