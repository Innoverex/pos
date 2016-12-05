package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.setting.TradeOtherSettingAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 09:52<br>
 * 描述: 核对管理员的账户  <br>
 */
public class InputManagerPWDAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_MANAGE;
    public static final String KEY_DATA = "KEY_MANAGE";
    private boolean isSuccessful = false;

    @Override
    protected void init() {
        boolean managerPWD = TradeOtherSettingAty.isManagerPWD();
        if (!managerPWD) {
            this.finish();
            startNextFlow();
            return;
        }
        super.init();
        setIsPW();
        setHint(getString(R.string.Please_enter_the_supervisor_password));
        setLabel(getString(R.string.mima));
        setLenghtEdit(6);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s)) {
            if (s.equals(AppConfig.pw_supervisor)) {
//                isSuccessful=true;
                FlowControl.MapHelper.setManagerPWD(s);
                startNextFlow();
            } else {
                TLog.showToast(getString(R.string.Password_input_error));
            }
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
