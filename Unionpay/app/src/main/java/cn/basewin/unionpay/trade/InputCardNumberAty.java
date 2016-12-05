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
 * 描述: 卡号  <br>
 */
public class InputCardNumberAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_CARD_NUMBER;
    public static final String KEY_DATA = "InputCardNumberAty";


    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_card_number));
        setLabel2("");
        setLenghtEdit(19);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s) && s.length() > 12 && s.length() < 20) {
            FlowControl.MapHelper.getCard().type = 1;
            FlowControl.MapHelper.setCardNO(s);
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
