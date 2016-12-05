package cn.basewin.unionpay.menu.action;

import android.util.Log;
import android.view.View;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.NetSettleWaitAty;
import cn.basewin.unionpay.trade.NetUploadTCWaitAty;
import cn.basewin.unionpay.trade.PrintDetailFailWaitAty;
import cn.basewin.unionpay.trade.PrintDetailSuccessWaitAty;
import cn.basewin.unionpay.trade.PrintSettleWaitAty;
import cn.basewin.unionpay.utils.SettlementUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.CustomInputDialog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 结算 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SETTLEMENT)
public class MenuSettlement extends MenuAction {

    private CustomInputDialog dialog;
    private CustomInputDialog dialog_info;

    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_SETTLEMENT));
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
                showDialog();
            }
        };
    }

    private void showDialog() {
        dialog = new CustomInputDialog(getContext(), R.style.Dialog_Fullscreen_title);
        dialog.setRdLeftButton("取消", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.setRdRightButton("确定", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                initSettle();
            }
        });
        dialog.setRdMsg("是否结算？");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void initSettle() {
        if (SettlementUtil.getCurrentSettleState()) {
            TLog.showToast("上次结算未完成！");
            startSettlement(SettlementUtil.getCurrentSettleField48());
        } else {
            if (SettlementUtil.sumData()) {
                startSettlement(SettlementUtil.getInitField48());
            } else {
                Log.e(TAG, "无交易");
                TLog.showToast("无交易");
                dialog_info = new CustomInputDialog(getContext(), R.style.Dialog_Fullscreen_title);
                dialog_info.setRdRightButton("确定", new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog_info.dismiss();
                    }
                });
                dialog_info.setRdMsg("当前无交易，不需要结算！");
                dialog_info.setCancelable(false);
                dialog_info.setCanceledOnTouchOutside(false);
                dialog_info.show();
            }
        }
    }

    private void startSettlement(String field48) {
        FlowControl flowControl = new FlowControl();
        Log.i(TAG, "field48=" + field48);
        flowControl.map("field48", field48);
        SettlementUtil.setCurrentSettleField48(field48);
        flowControl.begin(NetSettleWaitAty.class)
                .next(NetUploadTCWaitAty.class)
                .next(PrintSettleWaitAty.class)
                .next(PrintDetailSuccessWaitAty.class)
                .next(PrintDetailFailWaitAty.class)
                .start(getContext(), ActionConstant.ACTION_SETTLEMENT);
    }
}