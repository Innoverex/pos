package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 活期转定期
 */
@AnnotationMenu(action = ActionConstant.ACTION_CURRENT_TRANSFER_TIME)
public class MenuCurrentTransferTime extends MenuAction {
    @Override
    public String getResName() {
        return "活期转定期";
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