package cn.basewin.unionpay.setting;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.view.ListDialog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 12:03<br>
 * 描述：通讯参数设置-通讯类型
 */
public class CommuParmAty extends BaseSysSettingAty {
    /**
     * 通讯方式
     */
    private static final String KEY_COMMU_TYPE = "CommuParmAty_commu_type";
    private TextView tv_commu_type;
    private BaseSysFragment mFragment;
    private String[] commuParms = {"WiFi", "无线"};

    private void initViews() {
        tv_commu_type = (TextView) findViewById(R.id.tv_commu_type);
        tv_commu_type.setText(SPTools.get(CommuParmAty.KEY_COMMU_TYPE, AppConfig.DEFAULT_VALUE_COMMU_TYPE));
        tv_commu_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new ListDialog(mContext, tv_commu_type.getText().toString(), commuParms
                        , new ListDialog.OnRadioBtnCheckedChangedListener() {
                    @Override
                    public void onCheckedRadioBtnChange(String str) {
                        tv_commu_type.setText(str);
                        changeFragment(str);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_commuparm;
    }

    @Override
    public String getAtyTitle() {
        return "通讯参数设置";
    }

    @Override
    public void afterSetContentView() {
        initViews();
        changeFragment(tv_commu_type.getText().toString());
    }

    @Override
    public void save() {
        if (mFragment != null) {
            mFragment.save();
            showModifyHint();
        }
    }

    private void changeFragment(String function) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if ("无线".equals(function)) {
            mFragment = new CommuParmWireLessFragment();
        } else if ("以太网".equals(function)) {
            mFragment = new CommuParmEthernetFragment();
        } else if ("拨号".equals(function)) {
            mFragment = new CommuParmDialFragment();
        } else {
            mFragment = new CommuParmWifiFragment();
        }
        ft.replace(R.id.fl_container, mFragment).commit();
    }

    public static String getCommuType() {
        return SPTools.get(CommuParmAty.KEY_COMMU_TYPE, AppConfig.DEFAULT_VALUE_COMMU_TYPE);
    }

    public static void setCommuType(String type) {
        SPTools.set(CommuParmAty.KEY_COMMU_TYPE, type);
    }
}
