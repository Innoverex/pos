package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.ui.OperatorAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 开启签到菜单 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_OPER_MANAGEMENT)
public class MenuOperManagement extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_OPER_MANAGEMENT));
    }

    @Override
    public int getResIcon() {
        return R.drawable.oper_mng_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), OperatorAty.class);
                getContext().startActivity(intent);
            }
        };
    }
}
