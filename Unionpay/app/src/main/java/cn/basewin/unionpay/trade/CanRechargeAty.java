package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseFlowAty;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/19 18:33<br>
 * 描述：可充余额提示页面
 */
public class CanRechargeAty extends BaseFlowAty {
    /**
     * 可充金额
     */
    public static String KEY_CAN_RECHARGE_MONEY = "can_recharge_money";
    /**
     * 可充金额
     */
    private TextView tv_can_recharge_money;
    /**
     * 下一步
     */
    private Button btn_next;
    /**
     * 提示
     */
    private TextView tv_hint;
    /**
     * 充值金额
     */
    private TextView tv_recharge_money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canrecharge);
        initTitle();
        tv_can_recharge_money = (TextView) findViewById(R.id.tv_can_recharge_money);
        tv_recharge_money = (TextView) findViewById(R.id.tv_recharge_money);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        double canRechargeMoney = FlowControl.MapHelper.getCanRechargeMoney();//可充金额
        double rechargeMoney = Double.parseDouble(FlowControl.MapHelper.getMoney());//充值金额
        tv_can_recharge_money.setText("可充余额：" + canRechargeMoney);
        tv_recharge_money.setText("充值金额:" + rechargeMoney);
        if (rechargeMoney > canRechargeMoney) {
            btn_next.setText("返回");
            tv_hint.setText("可充余额不足，无法充值");
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            btn_next.setText("下一步");
            tv_hint.setText("请点\"下一步\"继续充值");
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int action = FlowControl.MapHelper.getNextAction();
                    FlowControl.MapHelper.getMap().put(FlowControl.KEY_ACTION, action);
                    startNextFlow();
                }
            });
        }
    }
}
