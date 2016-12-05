package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.MerchantSetting;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 11:55<br>
 * 描述：商户参数设置
 */
public class SysMerchantSetting extends MenuAction {
    @Override
    public String getResName() {
        return "商户参数设置";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_shanghucanshu;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), MerchantSetting.class));
            }
        };
    }
}
