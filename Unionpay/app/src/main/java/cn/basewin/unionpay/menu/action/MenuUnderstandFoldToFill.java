package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 明白折补登
 */
@AnnotationMenu(action = ActionConstant.ACTION_UNDERSTAND_FOLD_TO_FILL)
public class MenuUnderstandFoldToFill extends MenuAction {
    @Override
    public String getResName() {
        return "明白折补登";
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