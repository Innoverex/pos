package cn.basewin.unionpay.menu.action;

import com.basewin.define.InputPBOCInitData;

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
import cn.basewin.unionpay.trade.SwipingSecondCardAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 电子现金下的圈存非指定帐号圈存<br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_ECLOAD_NONACCOUNT)
public class MenuECLoadNonaccount extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_ECLOAD_NONACCOUNT));
    }

    @Override
    public int getResIcon() {
        return R.drawable.selector_authorization0;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                FlowControl flowControl = new FlowControl();
                FlowControl.MapHelper.setSwipingType(InputPBOCInitData.USE_IC_CARD | InputPBOCInitData.USE_RF_CARD);
                //刷转出卡--刷待充值卡--输金额--输PIN
                flowControl.begin(SwipingCardAty.class)
                        .next(SwipingSecondCardAty.class)
                        .next(InputMoneyAty.class)
                        .next(InputPWDAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_ECLOAD_NONACCOUNT);
            }
        };
    }
}
