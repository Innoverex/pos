package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 付款成功通知
 */
@AnnotationMenu(action = ActionConstant.ACTION_A_PAYMENT_SUCCESS)
public class MenuAPaymentSuccess extends MenuAction {
    @Override
    public String getResName() {
        return "付款成功通知";
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