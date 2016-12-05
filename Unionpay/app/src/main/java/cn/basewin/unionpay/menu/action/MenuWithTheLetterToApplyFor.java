package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 用信申请
 */
@AnnotationMenu(action = ActionConstant.ACTION_WITH_THE_LETTER_TO_APPLY_FOR)
public class MenuWithTheLetterToApplyFor extends MenuAction {
    @Override
    public String getResName() {
        return "用信申请";
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