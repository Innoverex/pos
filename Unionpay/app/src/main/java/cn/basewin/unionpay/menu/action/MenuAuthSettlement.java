package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputAuthCodeAty;
import cn.basewin.unionpay.trade.InputDateAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.trade.SwipingCardAty;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 14:14<br>
 * 描述: 预授权完成(通知)<br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_AUTH_SETTLEMENT)
public class MenuAuthSettlement extends MenuAction {
    @Override
    public String getResName() {
        return "预授权完成(通知)";
    }

    @Override
    public int getResIcon() {
        return R.drawable.auth_confirm_off_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                //刷卡--日期--输入原授权码（6位）--金额--密码
                FlowControl flowControl = new FlowControl();
                flowControl.begin(SwipingCardAty.class)
                        .next(InputDateAty.class)
                        .next(InputAuthCodeAty.class)
                        .next(InputMoneyAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_AUTH_SETTLEMENT);
            }
        };
    }
}
