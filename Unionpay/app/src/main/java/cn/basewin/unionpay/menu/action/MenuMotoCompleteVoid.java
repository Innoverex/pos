package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.trade.InputManagerPWDAty;
import cn.basewin.unionpay.trade.InputTraceAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.ui.CheckInfoAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 订购预授权完成撤销 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_MOTO_COMPLETE_VOID)
public class MenuMotoCompleteVoid extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_MOTO_COMPLETE_VOID));
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_cancel_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                //主管密码 --输入原凭证号（判断交易存在）--核对信息--刷卡(如果是一样的卡就退货)
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputManagerPWDAty.class)
                        .next(InputTraceAty.class)
                        .next(CheckInfoAty.class)
                        .next(InputCardNumberAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_MOTO_COMPLETE_VOID);
            }
        };
    }
}
