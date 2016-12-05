package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * Created by kxf on 2016/10/8.
 * 输入要转入的卡号
 */
public class InputIntoCardNumberAty extends BaseInputAty {
    public static final String KEY_DATA = "InputIntoCardNumberAty";

    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_into_card_number));
        setLabel2("");
        setLenghtEdit(19);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s) && s.length() > 12 && s.length() < 20) {
            FlowControl.MapHelper.getMap().put(KEY_DATA, s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_card_number));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}