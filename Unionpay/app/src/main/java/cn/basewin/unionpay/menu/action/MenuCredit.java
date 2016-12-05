package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * Created by kxf on 2016/9/30.
 * 信贷
 */
@AnnotationMenu(action = ActionConstant.MENU_CREDIT)
public class MenuCredit extends MenuAction {
    @Override
    public String getResName() {
        return "信贷";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_print_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                UIHelper.menu(getContext(), ActionConstant.MENU_CREDIT);
            }
        };
    }
}