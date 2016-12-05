package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 柜员签到 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SIGN_OPER)
public class MenuSignOper extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_SIGN_OPER));
    }

    @Override
    public int getResIcon() {
        return R.drawable.logout_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                UIHelper.SignInAty(getContext());
                operSignOut();
            }
        };
    }

    /**
     * 柜员 签退初始化
     */
    public static void operSignOut() {
        SettingConstant.setOPERATOR_NO("");
    }
}
