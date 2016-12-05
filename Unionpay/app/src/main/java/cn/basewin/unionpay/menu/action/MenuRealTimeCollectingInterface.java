package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 实时代付接口
 */
@AnnotationMenu(action = ActionConstant.ACTION_REAL_TIME_COLLECTING_INTERFACE)
public class MenuRealTimeCollectingInterface extends MenuAction {
    @Override
    public String getResName() {
        return "实时代付接口";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_other_state;
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