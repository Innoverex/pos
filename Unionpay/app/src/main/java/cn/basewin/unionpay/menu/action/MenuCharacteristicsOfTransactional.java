package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * Created by kxf on 2016/9/29.
 * 特色交易
 */
@AnnotationMenu(action = ActionConstant.MENU_CHARACTERISTICS_OF_TRANSACTIONAL)
public class MenuCharacteristicsOfTransactional extends MenuAction {
    @Override
    public String getResName() {
        return "特色交易";
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
                UIHelper.menu(getContext(), ActionConstant.MENU_CHARACTERISTICS_OF_TRANSACTIONAL);
            }
        };
    }
}