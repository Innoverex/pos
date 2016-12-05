package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 定期转活期
 */
@AnnotationMenu(action = ActionConstant.ACTION_TURN_CHECKING_REGULARLY)
public class MenuTurnCheckingRegularly extends MenuAction {
    @Override
    public String getResName() {
        return "定期转活期";
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