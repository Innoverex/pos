package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 实时代付确认接口
 */
@AnnotationMenu(action = ActionConstant.ACTION_REAL_TIME_PAYMENT_CONFIRMATION_INTERFACE)
public class MenuRealTimePaymentConfirmationInterface extends MenuAction {
    @Override
    public String getResName() {
        return "实时代付确认接口";
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