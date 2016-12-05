package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/30 18:22<br>
 * 描述: 输入参考号 <br>
 */
public class InputReferNo extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_REFER_NO;
    public static final String KEY_DATA = "InputReferNo";

    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_refer));
        setLabel2("");
        setLenghtEdit(12);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s)) {
            FlowControl.MapHelper.setReferNo(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_refer));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }


}
