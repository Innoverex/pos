package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.ui.ShowBalanceAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 余额查询 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_QUERY_BALANCE)
public class MenuQueryBalance extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_QUERY_BALANCE));
    }

    @Override
    public int getResIcon() {
        return R.drawable.query_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.pos(TAG, "MenuQueryBalance");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(SwipingCardAty.class)
                        .next(InputPWDAty.class)
                        .next(NetWaitAty.class)
                        .next(ShowBalanceAty.class)
                        .start(getContext(), ActionConstant.ACTION_QUERY_BALANCE);
            }
        };
    }
}
