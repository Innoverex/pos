package cn.basewin.unionpay.menu.action;

import android.util.Log;

import com.basewin.define.InputPBOCInitData;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputDateAty;
import cn.basewin.unionpay.trade.InputManagerPWDAty;
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
 * 描述: 手机芯片退货 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_UPCARD_REFUND)
public class MenuUpcardRefund extends MenuAction {
    private static final String TAG = MenuUpcardRefund.class.getName();

    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_UPCARD_REFUND));
    }

    @Override
    public int getResIcon() {
        return R.drawable.jf_refund_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "MenuUpcardRefund");
                FlowControl flowControl = new FlowControl();
                FlowControl.MapHelper.setSwipingType(InputPBOCInitData.USE_RF_CARD);
                flowControl.begin(InputManagerPWDAty.class)
                        .next(SwipingCardAty.class)
                        .next(InputReferNo.class)
                        .next(InputDateAty.class)
                        .next(InputRefundMoneyAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_UPCARD_REFUND);
            }
        };
    }
}
