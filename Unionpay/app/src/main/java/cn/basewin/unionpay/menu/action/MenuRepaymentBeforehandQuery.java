package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 还款预查询
 */
@AnnotationMenu(action = ActionConstant.ACTION_REPAYMENT_BEFOREHAND_QUERY)
public class MenuRepaymentBeforehandQuery extends MenuAction {
    @Override
    public String getResName() {
        return "还款预查询";
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