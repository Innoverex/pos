package cn.basewin.unionpay.trade;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: init方法设置虚拟键盘监听<br>
 * 创建时间:  2016/6/27 15:53<br>
 * 描述:  获取金额 如果对这个界面有其他使用方式 请继承这个界面<br>
 */
public class InputMoneyAty extends BaseInputAty {
    private static final String TAG = InputMoneyAty.class.getName();
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_MONEY;
    public static final String KEY_MONEY = "InputMoneyAty_money";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "money:" + FlowControl.MapHelper.getMoney());
        if (FlowControl.MapHelper.getExternalCallFlag()) {
            startNextFlow();
        }
    }

    @Override
    protected void init() {
        super.init();
        setRule(BaseInputAty.rule_money);
    }

    @Override
    protected void clickOK(String s) {
        if (!TextUtils.isEmpty(s)) {
            double v = Double.parseDouble(s);
            if (v <= 0) {
                TLog.showToast(getString(R.string.Please_set_the_amount));
            } else {
                FlowControl.MapHelper.setMoney(s);
                startNextFlow();
                addNextFlow(PrintWaitAty.class, SignatureAty.class);
            }
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }

}
