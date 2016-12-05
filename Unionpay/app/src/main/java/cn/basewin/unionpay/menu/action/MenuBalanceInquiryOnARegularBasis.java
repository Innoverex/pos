package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 定期余额查询
 */
@AnnotationMenu(action = ActionConstant.ACTION_BALANCE_INQUIRY_ON_A_REGULAR_BASIS)
public class MenuBalanceInquiryOnARegularBasis extends MenuAction {
    @Override
    public String getResName() {
        return "定期余额查询";
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