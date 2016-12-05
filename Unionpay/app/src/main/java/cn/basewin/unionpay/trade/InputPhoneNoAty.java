package cn.basewin.unionpay.trade;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.base.BaseInputAty;

/**
 * Created by kxf on 2016/8/18.
 * 输入手机号码
 */
public class InputPhoneNoAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_PHONE;
    public static final String KEY_DATA = "key_InputPhoneNoAty";

    @Override
    protected void init() {
        super.init();
        setHint("请输入手机号");
        setLabel2("");
        setLenghtEdit(11);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
//        if (!TextUtils.isEmpty(s)) {
//            if (s.length() != 11) {
//                TLog.showToast("请输入11位手机号");
//            } else {
        FlowControl.MapHelper.setPhoneNO(s);
        startNextFlow();
//            }
//        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
