package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 用信协议查询
 */
@AnnotationMenu(action = ActionConstant.ACTION_WITH_THE_QUERY_LETTER_AGREEMENT)
public class MenuWithTheQueryLetterAgreement extends MenuAction {
    @Override
    public String getResName() {
        return "用信协议查询";
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