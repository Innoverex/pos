package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputIntoCardNumberAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * Created by kxf on 2016/9/29.
 * 他行助农取款
 */
@AnnotationMenu(action = ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS)
public class MenuAndHeDidThatToHelpFarmers extends MenuAction {
    @Override
    public String getResName() {
        return "他行助农取款";
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
                TLog.pos(TAG, "MenuIndustryTransfer");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputMoneyAty.class)
                        .next(SwipingCardAty.class)
                        .next(InputPWDAty.class)
                        .next(InputIntoCardNumberAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS);
            }
        };
    }
}