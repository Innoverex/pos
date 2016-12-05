package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/6 16:06<br>
 * 描述: 分期期数 <br>
 */
public class InputInstallNOAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_NSTALLMENT;
    public static final String KEY_DATA = "InputNstallment";

    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_the_stage_periods));
        setLabel2("");
        setLenghtEdit(2);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        //TODO 需要判断是否存在
        if (!TextUtils.isEmpty(s)) {
            FlowControl.MapHelper.setInstallNO(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_certificate));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
