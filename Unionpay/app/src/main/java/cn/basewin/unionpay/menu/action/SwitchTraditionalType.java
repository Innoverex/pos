package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.TraditionalTypeSettingAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 14:11<br>
 * 描述：传统类交易
 */
public class SwitchTraditionalType extends MenuAction {
    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_shanghucanshu;
    }

    @Override
    public String getResName() {
        return "传统类交易";
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), TraditionalTypeSettingAty.class));
            }
        };
    }
}
