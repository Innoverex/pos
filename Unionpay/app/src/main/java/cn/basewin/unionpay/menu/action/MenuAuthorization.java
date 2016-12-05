package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 14:14<br>
 * 描述: 开启 预授权 菜单<br>
 */
@AnnotationMenu(action = ActionConstant.MENU_AUTH)
public class MenuAuthorization extends MenuAction {
    @Override
    public String getResName() {
        return "预授权";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_preauth_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                UIHelper.menu(getContext(), ActionConstant.MENU_AUTH);
            }
        };
    }
}
