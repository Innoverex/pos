package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.CanRechargeAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputIdCardNumAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.trade.SwipingSecondCardAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 开启磁条卡充值账户充值 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT)
public class MenuAccountLoadAccount extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT));
    }

    @Override
    public int getResIcon() {
        return R.drawable.query_byvoucher_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                FlowControl flowControl = new FlowControl();
                flowControl.begin(SwipingSecondCardAty.class)
                        .next(SwipingCardAty.class)
                        .next(InputIdCardNumAty.class)
                        .next(InputMoneyAty.class)
                        .next(NetWaitAty.class)
                        .next(CanRechargeAty.class)
                        .next(InputPWDAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .map(FlowControl.KEY_NEXT_ACTION, ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT)
                        .start(getContext(), ActionConstant.ACTION_ACCOUNT_LOAD_CONFIRM);
            }
        };
    }
}
