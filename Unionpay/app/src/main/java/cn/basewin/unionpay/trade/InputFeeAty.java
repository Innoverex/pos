package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: init方法设置虚拟键盘监听<br>
 * 创建时间:  2016/6/27 15:53<br>
 * 描述:  输入小费<br>
 */
public class InputFeeAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_FEE;
    public static final String KEY_MONEY = "InputFee";

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
                TLog.showToast(getString(R.string.please_enter_the_fee));
            } else {
                FlowControl.MapHelper.setFee(s);
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
