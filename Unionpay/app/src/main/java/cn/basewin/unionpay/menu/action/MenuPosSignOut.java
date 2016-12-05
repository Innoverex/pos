package cn.basewin.unionpay.menu.action;

import android.util.Log;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 签退 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SIGN_OUT)
public class MenuPosSignOut extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_SIGN_OUT));
    }

    @Override
    public int getResIcon() {
        return R.drawable.logout_state;
    }

    @Override
    public Runnable getRun() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(NetWaitAty.class).start(getContext(), ActionConstant.ACTION_SIGN_OUT);
            }
        };
        return r;
    }

    /**
     * 签退初始化pos机具 调用
     * 1.清空pos 签到 时间
     * 2.清空操作员
     */
    public static void signOutPos() {
        SettingConstant.setSignTime("");
        SettingConstant.setOPERATOR_NO("");
    }
}
