package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:04<br>
 * 描述: 消费 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SALE)
public class MenuSale extends MenuAction {
    @Override
    public String getResName() {
        return "消费";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_purchase_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.pos(TAG, "MenuSale");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputMoneyAty.class)
                        .next(SwipingCardAty.class)
                        .next(InputPWDAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_SALE);
            }
        };
    }
}
