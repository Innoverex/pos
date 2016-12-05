package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.SystemParSettingAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 14:03<br>
 * 描述：系统参数设置
 */
public class SysParamSetting extends MenuAction {
    @Override
    public String getResName() {
        return "系统参数设置";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_xitongcanshu;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), SystemParSettingAty.class));
            }
        };
    }
}
