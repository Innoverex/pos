package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.ChooseAuthTypeAty;
import cn.basewin.unionpay.trade.ChooseCardOrganizationAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputAuthCodeAty;
import cn.basewin.unionpay.trade.InputAuthOrganizationCodeAty;
import cn.basewin.unionpay.trade.InputCardExpDateAty;
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 离线结算 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_OFFLINE)
public class MenuOfflineSettlement extends MenuAction {
    @Override
    public String getResName() {
        return "离线结算";
    }

    @Override
    public int getResIcon() {
        return R.drawable.offline_adjust_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.l("MenuOfflineSettlement");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputCardNumberAty.class)
                        .next(InputCardExpDateAty.class)
                        .next(ChooseAuthTypeAty.class)//选择授权方式
                        .branch(0, InputAuthCodeAty.class)//预授权分支：提示输入原授权码
                        .branch(1, InputAuthOrganizationCodeAty.class, InputAuthCodeAty.class)//电话授权分支：提示输入授权机构代码→提示输入原授权码
                        .next(ChooseCardOrganizationAty.class)//小额代授权
                        .next(InputMoneyAty.class)
//                        .next(OfflineDealWaitAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_OFFLINE);
            }
        };
    }
}
