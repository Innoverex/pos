package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 余额信息通知
 */
@AnnotationMenu(action = ActionConstant.ACTION_BALANCE_INFORMATION_NOTICE)
public class MenuBalanceInformationNotice extends MenuAction {
    @Override
    public String getResName() {
        return "余额信息通知";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_purchase_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}