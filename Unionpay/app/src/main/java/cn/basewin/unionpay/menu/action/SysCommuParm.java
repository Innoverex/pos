package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 09:44<br>
 * 描述：通讯参数设置
 */
public class SysCommuParm extends MenuAction {
    @Override
    public String getResName() {
        return "通讯参数设置";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_tongxuncanshu;
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
