package cn.basewin.unionpay.setting;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 11:12<br>
 * 描述：通讯参数设置之以太网
 */
public class CommuParmEthernetFragment extends BaseSysFragment {
    private static final String TAG = CommuParmWireLessFragment.class.getName();
    /**
     * 是否使用DHCP
     */
    private static final String KEY_IS_USE_DHCP = TAG + "is_use_dhcp";
    /**
     * 设置本机IP
     */
    private static final String KEY_SET_CP_IP = TAG + "set_cp_ip";
    /**
     * 设置网关IP
     */
    private static final String KEY_SET_GATEWAY_IP = TAG + "set_gateway_ip";
    /**
     * 设置子网掩码
     */
    private static final String KEY_SET_SUBNET_MASK = TAG + "set_subnet_mask";
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

    private CheckBox cb_isuse_dhcp;
    private LinearLayout ll_set_cp_ip, ll_set_gateway_ip, ll_set_subnet_mask;
    private EditText et_set_cp_ip;
    private EditText et_set_gateway_ip;
    private EditText et_set_subnet_mask;
    private EditText et_set_host_ip;
    private EditText et_set_host_port;
    private EditText et_set_backup_ip;
    private EditText et_set_backup_post;

    private void initViews(View view) {
        cb_isuse_dhcp = (CheckBox) view.findViewById(R.id.cb_isuse_dhcp);
        ll_set_cp_ip = (LinearLayout) view.findViewById(R.id.ll_set_cp_ip);
        ll_set_gateway_ip = (LinearLayout) view.findViewById(R.id.ll_set_gateway_ip);
        ll_set_subnet_mask = (LinearLayout) view.findViewById(R.id.ll_set_subnet_mask);
        et_set_cp_ip = (EditText) view.findViewById(R.id.et_set_cp_ip);
        et_set_gateway_ip = (EditText) view.findViewById(R.id.et_set_gateway_ip);
        et_set_subnet_mask = (EditText) view.findViewById(R.id.et_set_subnet_mask);
        et_set_host_ip = (EditText) view.findViewById(R.id.et_set_host_ip);
        et_set_host_port = (EditText) view.findViewById(R.id.et_set_host_post);
        et_set_backup_ip = (EditText) view.findViewById(R.id.et_set_backup_ip);
        et_set_backup_post = (EditText) view.findViewById(R.id.et_set_backup_post);
    }

    private void initData() {
        cb_isuse_dhcp.setChecked(SPTools.get(KEY_IS_USE_DHCP, true));
        if (cb_isuse_dhcp.isChecked()) {
            ll_set_cp_ip.setVisibility(View.GONE);
            ll_set_gateway_ip.setVisibility(View.GONE);
            ll_set_subnet_mask.setVisibility(View.GONE);
        }
        et_set_cp_ip.setText(SPTools.get(KEY_SET_CP_IP, ""));
        et_set_gateway_ip.setText(SPTools.get(KEY_SET_GATEWAY_IP, ""));
        et_set_subnet_mask.setText(SPTools.get(KEY_SET_SUBNET_MASK, ""));
        et_set_host_ip.setText(SPTools.get(KEY_SET_HOST_IP, ""));
        et_set_host_port.setText(SPTools.get(KEY_SET_HOST_PORT, ""));
        et_set_backup_ip.setText(SPTools.get(KEY_SET_BACKUP_IP, ""));
        et_set_backup_post.setText(SPTools.get(KEY_SET_BACKUP_PORT, ""));

    }

    private void setListener() {
        cb_isuse_dhcp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_set_cp_ip.setVisibility(View.GONE);
                    ll_set_gateway_ip.setVisibility(View.GONE);
                    ll_set_subnet_mask.setVisibility(View.GONE);
                } else {
                    ll_set_cp_ip.setVisibility(View.VISIBLE);
                    ll_set_gateway_ip.setVisibility(View.VISIBLE);
                    ll_set_subnet_mask.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_sysset_commuparm_ethernet;
    }

    @Override
    public void afterGetView(View view) {
        initViews(view);
        initData();
        setListener();
    }

    @Override
    public void save() {
        SPTools.set(KEY_IS_USE_DHCP, cb_isuse_dhcp.isChecked());
        if (!cb_isuse_dhcp.isChecked()) {
            SPTools.set(KEY_SET_CP_IP, et_set_cp_ip.getText().toString());
            SPTools.set(KEY_SET_GATEWAY_IP, et_set_gateway_ip.getText().toString());
            SPTools.set(KEY_SET_SUBNET_MASK, et_set_subnet_mask.getText().toString());
        }
        SPTools.set(KEY_SET_HOST_IP, et_set_host_ip.getText().toString());
        SPTools.set(KEY_SET_HOST_PORT, et_set_host_port.getText().toString());
        SPTools.set(KEY_SET_BACKUP_IP, et_set_backup_ip.getText().toString());
        SPTools.set(KEY_SET_BACKUP_PORT, et_set_backup_post.getText().toString());
        CommuParmAty.setCommuType("以太网");
    }

    public static boolean getIsUseDhcp() {
        return SPTools.get(KEY_IS_USE_DHCP, AppConfig.DEFAULT_VALUE_IS_USE_DHCP);
    }

    public static String getSetCpIp() {
        return SPTools.get(KEY_SET_CP_IP, AppConfig.DEFAULT_VALUE_SET_CP_IP);
    }

    public static String getSetGatewayIp() {
        return SPTools.get(KEY_SET_GATEWAY_IP, AppConfig.DEFAULT_VALUE_SET_GATEWAY_IP);
    }

    public static String getSetSubnetMask() {
        return SPTools.get(KEY_SET_SUBNET_MASK, AppConfig.DEFAULT_VALUE_SET_SUBNET_MASK);
    }

    public static String getSetHostIp() {
        return SPTools.get(KEY_SET_HOST_IP, AppConfig.DEFAULT_VALUE_SET_DHCP_HOST_IP);
    }

    public static String getSetHostPort() {
        return SPTools.get(KEY_SET_HOST_PORT, AppConfig.DEFAULT_VALUE_SET_DHCP_HOST_PORT);
    }

    public static String getSetBackupIp() {
        return SPTools.get(KEY_SET_BACKUP_IP, AppConfig.DEFAULT_VALUE_SET_DHCP_BACKUP_IP);
    }

    public static String getSetBackupPort() {
        return SPTools.get(KEY_SET_BACKUP_PORT, AppConfig.DEFAULT_VALUE_SET_DHCP_BACKUP_PORT);
    }
}