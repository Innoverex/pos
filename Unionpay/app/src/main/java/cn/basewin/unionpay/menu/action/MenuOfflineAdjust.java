package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputFeeAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputTraceAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.ui.OfflineAdjustCheckInfoAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 离线调整 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_ADJUST)
public class MenuOfflineAdjust extends MenuAction {
    @Override
    public String getResName() {
        return "离线调整";
    }

    @Override
    public int getResIcon() {
        return R.drawable.offline_settle_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.l("MenuOfflineAdjust");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputTraceAty.class)
                        .next(OfflineAdjustCheckInfoAty.class)
                        .branch(0, InputFeeAty.class)
                        .branch(1, InputMoneyAty.class)
//                        .next(OfflineDealWaitAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_ADJUST);
            }
        };
    }
}
