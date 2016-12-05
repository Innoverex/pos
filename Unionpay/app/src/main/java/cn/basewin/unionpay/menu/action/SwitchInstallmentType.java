package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.InstallmentTypeSettingAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 14:19<br>
 * 描述：分期付款类交易
 */
public class SwitchInstallmentType extends MenuAction {
    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_tongxuncanshu;
    }

    @Override
    public String getResName() {
        return "分期付款类交易";
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), InstallmentTypeSettingAty.class));
            }
        };
    }
}
