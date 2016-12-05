package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 购房付款查询
 */
@AnnotationMenu(action = ActionConstant.ACTION_INQUIRY_OF_THE_PAYMENT)
public class MenuInquiryOfThePayment extends MenuAction {
    @Override
    public String getResName() {
        return "购房付款查询";
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