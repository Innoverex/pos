package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.SetExternalDialNumAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputManagerPWDAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 外线号码 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_EXTERNAL_NUMBER)
public class MenuExternalNumber extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_EXTERNAL_NUMBER));
    }

    @Override
    public int getResIcon() {
        return R.drawable.mng_outline_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputManagerPWDAty.class)
                        .next(SetExternalDialNumAty.class)
                        .start(getContext(), -1);
            }
        };
    }
}
