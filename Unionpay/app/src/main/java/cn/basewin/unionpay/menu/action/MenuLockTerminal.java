package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.LockTerminalAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 锁定终端 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_LOCK_TERMINAL)
public class MenuLockTerminal extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_LOCK_TERMINAL));
    }

    @Override
    public int getResIcon() {
        return R.drawable.mng_lock_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), LockTerminalAty.class));

            }
        };
    }
}
