package cn.basewin.unionpay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import cn.basewin.unionpay.broadcast.BroadcastManage;
import cn.basewin.unionpay.menu.action.MenuAction;
import cn.basewin.unionpay.menu.action.MenuPosSignIn;
import cn.basewin.unionpay.menu.action.MenuRefund;
import cn.basewin.unionpay.menu.action.MenuSale;
import cn.basewin.unionpay.menu.action.MenuSettlement;
import cn.basewin.unionpay.menu.action.MenuVoid;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.UIHelper;

/**
 * Created by hanlei on 2016/9/21.
 * 意图：外部调用入口
 */

public class ExternalCallAty extends AppCompatActivity {
    private static final String TAG = ExternalCallAty.class.getName();
    public static final String KEY_CARD = "ExternalCallFlag";
    private Context context = ExternalCallAty.this;
    private String className;
    private int actionRun = 0;

    private int support_action[] = {
            ActionConstant.ACTION_SALE,
            ActionConstant.ACTION_VOID,
            ActionConstant.ACTION_REFUND,
            ActionConstant.ACTION_SETTLEMENT,
            ActionConstant.ACTION_SETTING_GROUP
    };
    private String support_name[] = {
            "消费",
            "撤销",
            "退货",
            "结算",
            "设置"
    };
    //流程监控广播
    private flowBroadcastReceiver nextFlowCall = new flowBroadcastReceiver();
    private String amount;
    private String oldTrace;
    private String oldReference;
    private String oldDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getActionPara()) {
            return;
        }
        registerReceiver(nextFlowCall, BroadcastManage.getNextFlowFlag());
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (actionRun != ActionConstant.ACTION_SETTLEMENT ||
//                actionRun != ActionConstant.ACTION_PRINT_LAST) {
//            finishWithError("交易流程中断！");
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nextFlowCall);
        ResetTermianl();
    }

    private boolean getActionPara() {
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
//        actionRun = intent.getIntExtra("transAction", -1);
        String transName = intent.getStringExtra("transName");
        oldTrace = intent.getStringExtra("oldTrace");
        oldReference = intent.getStringExtra("oldReference");
        oldDate = intent.getStringExtra("oldDate");

        if (null == transName) {
            finishWithError("无效交易！");
            return false;
        }

        if (!isTranSupport(transName)) {
            finishWithError("交易暂不支持！");
            return false;
        }

        switch (actionRun) {
            case ActionConstant.ACTION_SALE:
                if (null == amount || !amount.matches("^[0-9]{12}$")) {
                    finishWithError("无效金额！");
                    return false;
                }
                if (Double.parseDouble(amount) < 0.001) {
                    finishWithError("消费金额不能为零！");
                    return false;
                }
                className = MenuSale.class.getName();
                break;
            case ActionConstant.ACTION_VOID:
                if (oldTrace == null || !oldTrace.matches("^[0-9]{6}$")) {
                    finishWithError("无效凭证号！");
                    return false;
                }
                className = MenuVoid.class.getName();
                break;
            case ActionConstant.ACTION_REFUND:
                if (null == amount || !amount.matches("^[0-9]{12}$")) {
                    finishWithError("无效金额！");
                    return false;
                }
                if (Double.parseDouble(amount) < 0.001) {
                    finishWithError("退货金额不能为零！");
                    return false;
                }

                if (Double.parseDouble(amount) > SettingConstant.getREFUND_LIMIT()) {
                    finishWithError("超过最大退货金额！");
                    return false;
                }

                if (null == oldReference || !oldReference.matches("^[0-9]{12}$")) {
                    finishWithError("无效参考号！");
                    return false;
                }


                if (null == oldDate || !oldDate.matches("^[0-9]{4}$") || !PosUtil.isValidDate(oldDate)) {
                    finishWithError("无效日期！");
                    return false;
                }

                className = MenuRefund.class.getName();

                break;
            case ActionConstant.ACTION_SETTLEMENT:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MenuSettlement menuSettlement = new MenuSettlement();
                        menuSettlement.setContext(ExternalCallAty.this);
                        menuSettlement.getRun();
                    }
                });
                break;
            case ActionConstant.ACTION_SETTING_GROUP:
                UIHelper.menu(context, ActionConstant.ACTION_SETTING_GROUP);
                finishWithSuccess();
            default:
                break;
        }
        if (!TextUtils.isEmpty(className)) {
            //如果需要签到就先签到。 否则就直接执行 在广播接受的时候在去执行要消费 等一系列要有签到前置的流程。
            boolean b = true;
            if (b) {
                boolean siginpos = siginpos();
                if (!siginpos) {
                    realTransaction(className);
                }
            } else {
                realTransaction(className);
            }
        }
        return true;
    }

    private boolean isActionSupproted(int transAction) {
        for (int action : support_action) {
            if (transAction == action) {
                return true;
            }
        }
        return false;
    }

    private boolean isTranSupport(String transName) {
        int len = support_name.length;
        for (int i = 0; i < len; i++) {
            if (transName.equals(support_name[i])) {
                actionRun = support_action[i];
                return true;
            }
        }
        return false;
    }

    public void ResetTermianl() {
        TDevice.power_open();
        LedUtil.closeAll();
    }

    private void finishWithError(String errorMessage) {
        Intent intent = new Intent();
        intent.putExtra("reason", errorMessage);
        setResult(Activity.RESULT_CANCELED, intent);
        ResetTermianl();
        finish();
    }

    private void finishWithSuccess() {
        Intent intent = new Intent();
        intent.putExtra("amount", "000000001000");
        intent.putExtra("trace", "000010");
        intent.putExtra("reference", "138796173701");
        intent.putExtra("cardNo", "62258821242701111");
        setResult(Activity.RESULT_OK, intent);
        ResetTermianl();
        finish();
    }

    /**
     * @return 是否签到
     */
    private boolean siginpos() {
        //自动签到
        if (!MenuPosSignIn.judgeSign()) {
            Log.d(TAG, "autoLogin now");
            //如果签到的时间和今天不一样 就会进行一次pos签到
            SettingConstant.setOPERATOR_NO("01");
            FlowControl flowControl = new FlowControl();
            flowControl.begin(NetWaitAty.class).start(context, ActionConstant.ACTION_SIGN_POS);
            return true;
        }
        return false;
    }

    /**
     * 开启流程
     *
     * @param msg
     */
    private void realTransaction(String msg) {
        try {
            Class<?> aClass = Class.forName(msg);
            MenuAction action = (MenuAction) aClass.newInstance();
            Log.d(TAG, action.toString());
            action.setContext(this);
            action.getRun().run();
            switch (actionRun) {
                case ActionConstant.ACTION_SALE:
                    FlowControl.MapHelper.setExternalCallFlag(true);
                    FlowControl.MapHelper.setMoney(PosUtil.centToYuan(amount));
                    break;
                case ActionConstant.ACTION_VOID:
                    FlowControl.MapHelper.setExternalCallFlag(true);
                    FlowControl.MapHelper.setTrace(oldTrace);
                    break;
                case ActionConstant.ACTION_REFUND:
                    FlowControl.MapHelper.setExternalCallFlag(true);
                    FlowControl.MapHelper.setMoney(PosUtil.centToYuan(amount));
                    FlowControl.MapHelper.setReferNo(oldReference);
                    FlowControl.MapHelper.setDate(oldDate);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            finishWithError("未找到行为！");
        }
    }

    public class flowBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int nextFlowAction = BroadcastManage.getNextFlowAction(intent);
            if (nextFlowAction == ActionConstant.ACTION_SIGN_POS && nextFlowAction != actionRun) {
                //也就是说外部调用的不是签到，而我们执行了签到 所以这个行为为前置操作，然后执行流程
                realTransaction(className);
                return;
            }
            switch (nextFlowAction) {
                case 0:
                    finishWithError("交易异常结束!");
                    break;
                default:
                    finishWithSuccess();
                    break;
            }
        }
    }
}
