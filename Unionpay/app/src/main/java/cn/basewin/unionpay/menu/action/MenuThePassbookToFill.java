package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 存折补登
 */
@AnnotationMenu(action = ActionConstant.ACTION_THE_PASSBOOK_TO_FILL)
public class MenuThePassbookToFill extends MenuAction {
    @Override
    public String getResName() {
        return "存折补登";
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