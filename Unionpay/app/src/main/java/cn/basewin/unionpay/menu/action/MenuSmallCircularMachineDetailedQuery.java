package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 小额循环机明细查询
 */
@AnnotationMenu(action = ActionConstant.ACTION_SMALL_CIRCULAR_MACHINE_DETAILED_QUERY)
public class MenuSmallCircularMachineDetailedQuery extends MenuAction {
    @Override
    public String getResName() {
        return "小额循环机明细查询";
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