package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 09:49<br>
 * 描述：其他功能设置
 */
public class SysOtherFunction extends MenuAction {
    @Override
    public String getResName() {
        return "其他功能设置";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_othergongnengshezhi;
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
