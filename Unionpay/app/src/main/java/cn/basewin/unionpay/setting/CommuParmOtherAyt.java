package cn.basewin.unionpay.setting;

import android.widget.CheckBox;
import android.widget.EditText;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 17:35<br>
 * 描述：通讯参数设置-其他
 */
public class CommuParmOtherAyt extends BaseSysSettingAty {
    private static final String TAG = CommuParmOtherAyt.class.getName();
    /**
     * TPDU
     */
    private static final String KEY_TPDU = TAG + "tpdu";
    /**
     * 连接超时时间
     */
    private static final String KEY_CONNECT_TIME_OUT = TAG + "connect_time_out";
    /**
     * 是否支持长链接
     */
    private static final String KEY_IF_SUPPORT_SOCKET = TAG + "if_support_socket";
    /**
     * 是否支持长链接
     */
    private static final String KEY_IF_SUPPORT_SSL = TAG + "if_support_ssl";
    /**
     * TPDU
     */
    private EditText et_tpdu;
    /**
     * 连接超时时间
     */
    private EditText et_connect_time_out;
    /**
     * 是否支持长链接
     */
    private CheckBox cb_if_support_socket;

    /**
     * 是否支持SSL通讯
     */
    private CheckBox cb_if_support_ssl;

    private void initViews() {
        et_tpdu = (EditText) findViewById(R.id.et_tpdu);
        et_connect_time_out = (EditText) findViewById(R.id.et_connect_time_out);
        cb_if_support_socket = (CheckBox) findViewById(R.id.cb_if_support_socket);
        cb_if_support_ssl = (CheckBox) findViewById(R.id.cb_if_support_ssl);
    }

    private void initData() {
        et_tpdu.setText(getTPDU());
        et_connect_time_out.setText(getTimeOut());
        cb_if_support_socket.setChecked(getSupportSocket());
        cb_if_support_ssl.setChecked(getSupportSSL());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_commuparm_other;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.tongxuncanshushezhi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(KEY_TPDU, et_tpdu.getText().toString());
        SPTools.set(KEY_CONNECT_TIME_OUT, et_connect_time_out.getText().toString());
        SPTools.set(KEY_IF_SUPPORT_SOCKET, cb_if_support_socket.isChecked());
        SPTools.set(KEY_IF_SUPPORT_SSL, cb_if_support_ssl.isChecked());
        showModifyHint();
    }

    public static String getTPDU() {
        return SPTools.get(KEY_TPDU, AppConfig.DEFAULT_VALUE_TPDU);
    }

    public static void setTPDU(String tpdu) {
        SPTools.set(KEY_TPDU, tpdu);
    }

    public static String getTimeOut() {
        return SPTools.get(KEY_CONNECT_TIME_OUT, AppConfig.DEFAULT_VALUE_CONNECT_TIME_OUT);
    }

    public static boolean getSupportSocket() {
        return SPTools.get(KEY_IF_SUPPORT_SOCKET, AppConfig.DEFAULT_VALUE_IF_SUPPORT_SOCKET);
    }

    public static boolean getSupportSSL() {
        return SPTools.get(KEY_IF_SUPPORT_SSL, AppConfig.DEFAULT_VALUE_IF_SUPPORT_SSL);
    }
}
