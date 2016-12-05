package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputDateAty;
import cn.basewin.unionpay.trade.InputManagerPWDAty;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.InputReferNo;
import cn.basewin.unionpay.trade.InputRefundMoneyAty;
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
 * 描述: 退货 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_REFUND)
public class MenuRefund extends MenuAction {
    @Override
    public String getResName() {
        return "退货";
    }

    @Override
    public int getResIcon() {
        return R.drawable.mobile_cancel_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.l("MenuRefund");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputManagerPWDAty.class)
                        .next(SwipingCardAty.class)
                        .next(InputReferNo.class)
                        .next(InputDateAty.class)
                        .next(InputRefundMoneyAty.class)
                        .next(InputPWDAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_REFUND);
            }
        };
    }
}
