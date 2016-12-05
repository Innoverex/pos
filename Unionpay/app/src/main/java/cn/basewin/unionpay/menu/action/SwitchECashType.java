package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.ECashTypeSettingAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 14:16<br>
 * 描述：电子现金类交易
 */
public class SwitchECashType extends MenuAction {

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_jiaoyicanshu;
    }

    @Override
    public String getResName() {
        return "电子现金类交易";
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), ECashTypeSettingAty.class));
            }
        };
    }
}
