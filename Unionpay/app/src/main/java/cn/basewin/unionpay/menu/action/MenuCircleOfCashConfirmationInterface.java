package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 现金圈提确认接口
 */
@AnnotationMenu(action = ActionConstant.ACTION_CIRCLE_OF_CASH_CONFIRMATION_INTERFACE)
public class MenuCircleOfCashConfirmationInterface extends MenuAction {
    @Override
    public String getResName() {
        return "现金圈提确认接口";
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