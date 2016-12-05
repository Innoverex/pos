package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.PwdManageAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 09:47<br>
 * 描述：密码管理
 */
public class SysPwdManagement extends MenuAction {
    @Override
    public String getResName() {
        return "密码管理";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_mimaguanli;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), PwdManageAty.class));
            }
        };
    }
}
