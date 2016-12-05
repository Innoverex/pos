package cn.basewin.unionpay.setting;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.network.remote.NetTools;
import cn.basewin.unionpay.utils.SPTools;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 10:27<br>
 * 描述：通讯参数设置之WIFI
 */
public class CommuParmWifiFragment extends BaseSysFragment {
    private static final String TAG = CommuParmWifiFragment.class.getName();
    /**
     * 设置主机IP
     */
    private static final String KEY_SET_HOST_IP = TAG + "set_wifi_host_ip";
    /**
     * 设置主机端口
     */
    private static final String KEY_SET_HOST_PORT = TAG + "set_wifi_host_port";
    /**
     * 设置备用IP
     */
    private static final String KEY_SET_BACKUP_IP = TAG + "set_wifi_backup_ip";
    /**
     * 设置备用端口
     */
    private static final String KEY_SET_BACKUP_PORT = TAG + "set_wifi_backup_port";
    /**
     * 设置端口及IP
     */
    private EditText et_set_host_ip, et_set_host_post, et_set_backup_ip, et_set_backup_post;
    private Button btn_scan_wifi;

    private void initView(View view) {
        et_set_host_ip = (EditText) view.findViewById(R.id.et_set_host_ip);
        et_set_host_post = (EditText) view.findViewById(R.id.et_set_host_post);
        et_set_backup_ip = (EditText) view.findViewById(R.id.et_set_backup_ip);
        et_set_backup_post = (EditText) view.findViewById(R.id.et_set_backup_post);
        btn_scan_wifi = (Button) view.findViewById(R.id.btn_scan_wifi);

        btn_scan_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        et_set_host_ip.setText(getHostIp());
        et_set_host_post.setText(getHostPost());
        et_set_backup_ip.setText(getBackupIp());
        et_set_backup_post.setText(getBackupPort());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_sysset_commuparm_wifi;
    }

    @Override
    public void afterGetView(View view) {
        initView(view);
        initData();
    }

    @Override
    public void save() {
        SPTools.set(KEY_SET_HOST_IP, et_set_host_ip.getText().toString());
        SPTools.set(KEY_SET_HOST_PORT, et_set_host_post.getText().toString());
        SPTools.set(KEY_SET_BACKUP_IP, et_set_backup_ip.getText().toString());
        SPTools.set(KEY_SET_BACKUP_PORT, et_set_backup_post.getText().toString());
        CommuParmAty.setCommuType("WiFi");
        NetTools.init();
    }

    public static String getHostIp() {
        return SPTools.get(KEY_SET_HOST_IP, AppConfig.DEFAULT_VALUE_SET_HOST_IP);
    }

    public static String getHostPost() {
        return SPTools.get(KEY_SET_HOST_PORT, AppConfig.DEFAULT_VALUE_SET_HOST_PORT);
    }

    public static void setHostIp(String ip) {
        SPTools.set(KEY_SET_HOST_IP, ip);
    }

    public static void setHostPort(String p) {
        SPTools.set(KEY_SET_HOST_PORT, p);
    }

    public static String getBackupIp() {
        return SPTools.get(KEY_SET_BACKUP_IP, AppConfig.DEFAULT_VALUE_SET_BACKUP_IP);
    }

    public static String getBackupPort() {
        return SPTools.get(KEY_SET_BACKUP_PORT, AppConfig.DEFAULT_VALUE_SET_BACKUP_PORT);
    }
}