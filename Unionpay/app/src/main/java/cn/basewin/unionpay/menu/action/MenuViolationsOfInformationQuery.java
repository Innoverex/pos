package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 违章罚款信息查询
 */
@AnnotationMenu(action = ActionConstant.ACTION_VIOLATIONS_OF_INFORMATION_QUERY)
public class MenuViolationsOfInformationQuery extends MenuAction {
    @Override
    public String getResName() {
        return "违章罚款信息查询";
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