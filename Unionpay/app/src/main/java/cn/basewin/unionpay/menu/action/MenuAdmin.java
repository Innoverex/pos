package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 开启管理菜单 <br>
 */
@AnnotationMenu(action = ActionConstant.MENU_ADMIN)
public class MenuAdmin extends MenuAction {
    @Override
    public String getResName() {
        return "管理";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_mng_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                UIHelper.menu(getContext(), ActionConstant.MENU_ADMIN);
            }
        };
    }
}
