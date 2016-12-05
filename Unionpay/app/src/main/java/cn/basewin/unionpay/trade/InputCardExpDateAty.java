package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 09:52<br>
 * 描述: 卡片有效期  <br>
 */
public class InputCardExpDateAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_CARD_VALIDITY_PERIOD;
    public static final String KEY_DATA = "InputCardExpDateAty";


    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_card_validity_period));
        setLabel2("");
        setLenghtEdit(4);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s) && s.length() <= 6) {
            FlowControl.MapHelper.setCardExpDate(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_card_validity_period));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
