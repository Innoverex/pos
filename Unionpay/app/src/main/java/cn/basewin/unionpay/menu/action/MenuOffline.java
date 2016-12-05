package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 离线 <br>
 */
@AnnotationMenu(action = ActionConstant.MENU_OFFLINE)
public class MenuOffline extends MenuAction {
    @Override
    public String getResName() {
        return "离线";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_offline_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                UIHelper.menu(getContext(), ActionConstant.MENU_OFFLINE);
            }
        };
    }
}
