package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 助农转账
 */
@AnnotationMenu(action = ActionConstant.ACTION_TO_HELP_FARMERS_TRANSFER)
public class MenuToHelpFarmersTransfer extends MenuAction {
    @Override
    public String getResName() {
        return "助农转账";
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