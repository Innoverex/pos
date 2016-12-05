package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.TradeManageAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 13:57<br>
 * 描述：交易管理设置
 */
public class SysTradeManage extends MenuAction {
    @Override
    public String getResName() {
        return "交易管理设置";
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_btn_jiaoyicanshu;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), TradeManageAty.class));
            }
        };
    }
}
