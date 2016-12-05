package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 09:46<br>
 * 描述：密钥管理
 */
public class SysTMKManagement extends MenuAction {
    @Override
    public String getResName() {
        return "密钥管理";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_miyaoguanli;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), null));
            }
        };
    }
}
