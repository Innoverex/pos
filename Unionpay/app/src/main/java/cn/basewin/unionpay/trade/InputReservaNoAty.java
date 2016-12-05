package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * Created by kxf on 2016/8/18.
 * 输入预约号码
 */
public class InputReservaNoAty extends BaseInputAty {
    public static final String KEY_RESERVA_NO = "key_InputReservaNoAty";

    @Override
    protected void init() {
        super.init();
        setHint("请输入预约号码");
        setLabel2("");
        setLenghtEdit(6);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        if (!TextUtils.isEmpty(s)) {
            if (s.length() != 6) {
                TLog.showToast("请输入6位预约号码");
            } else {
                FlowControl.MapHelper.getMap().put(KEY_RESERVA_NO, s);
                startNextFlow();
            }
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
