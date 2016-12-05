package cn.basewin.unionpay.menu.action;

import com.basewin.define.InputPBOCInitData;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputBatchAty;
import cn.basewin.unionpay.trade.InputDateAty;
import cn.basewin.unionpay.trade.InputManagerPWDAty;
import cn.basewin.unionpay.trade.InputRefundMoneyAty;
import cn.basewin.unionpay.trade.InputTerminalAty;
import cn.basewin.unionpay.trade.InputTraceAty;
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
 * 描述: 脱机退货 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_EC_REFUND)
public class MenuECRefund extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_EC_REFUND));
    }

    @Override
    public int getResIcon() {
        return R.drawable.ec_refund_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.l("MenuECRefund");
                //主管验证密码--刷卡--原交易日期--原终端号--原批次号--原流水号--原交易金额
                FlowControl flowControl = new FlowControl();
                FlowControl.MapHelper.setSwipingType(InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD);
                flowControl.begin(InputManagerPWDAty.class)
                        .next(SwipingCardAty.class)
                        .next(InputDateAty.class)
                        .next(InputTerminalAty.class)
                        .next(InputBatchAty.class)
                        .next(InputTraceAty.class)
                        .next(InputRefundMoneyAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_EC_REFUND);
            }
        };
    }
}
