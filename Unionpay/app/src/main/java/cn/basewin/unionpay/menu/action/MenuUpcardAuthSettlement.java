package cn.basewin.unionpay.menu.action;

import com.basewin.define.InputPBOCInitData;

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
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 手机芯片预授权完成通知 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT)
public class MenuUpcardAuthSettlement extends MenuAction {
    private static final String TAG = MenuUpcardAuthSettlement.class.getName();

    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT));
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
                FlowControl.MapHelper.setSwipingType(InputPBOCInitData.USE_RF_CARD);
                flowControl.begin(SwipingCardAty.class)
                        .next(InputDateAty.class)
                        .next(InputAuthCodeAty.class)
                        .next(InputMoneyAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT);
            }
        };
    }
}
