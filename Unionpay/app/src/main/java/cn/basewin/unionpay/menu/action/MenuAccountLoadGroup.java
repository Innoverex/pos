package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 开启磁条卡充值菜单 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_ACCOUNT_LOAD_GROUP)
public class MenuAccountLoadGroup extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_ACCOUNT_LOAD_GROUP));
    }

    @Override
    public int getResIcon() {
        return R.drawable.magcard_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                UIHelper.menu(getContext(), ActionConstant.ACTION_ACCOUNT_LOAD_GROUP);
            }
        };
    }
}
