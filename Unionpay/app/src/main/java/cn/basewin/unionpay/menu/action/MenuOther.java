package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 开启其他菜单 <br>
 */
@AnnotationMenu(action = ActionConstant.MENU_OTHER)
public class MenuOther extends MenuAction {
    @Override
    public String getResName() {
        return "其他";
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
                UIHelper.menu(getContext(), ActionConstant.MENU_OTHER);
            }
        };
    }
}
