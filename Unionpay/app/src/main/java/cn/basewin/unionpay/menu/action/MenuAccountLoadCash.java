package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.CanRechargeAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.trade.InputIdCardNumAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 开启磁条卡充值现金充值 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_ACCOUNT_LOAD_CASH)
public class MenuAccountLoadCash extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_ACCOUNT_LOAD_CASH));
    }

    @Override
    public int getResIcon() {
        return R.drawable.ec_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                FlowControl flowControl = new FlowControl();
                flowControl.begin(SwipingCardAty.class)
                        .next(InputMoneyAty.class)
                        .next(InputCardNumberAty.class)
                        .next(InputIdCardNumAty.class)
                        .next(NetWaitAty.class)
                        .next(CanRechargeAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .map(FlowControl.KEY_NEXT_ACTION, ActionConstant.ACTION_ACCOUNT_LOAD_CASH)
                        .start(getContext(), ActionConstant.ACTION_ACCOUNT_LOAD_CONFIRM);
            }
        };
    }
}
