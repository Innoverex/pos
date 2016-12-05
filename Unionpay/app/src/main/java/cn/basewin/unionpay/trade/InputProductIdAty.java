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
 * 描述: 商品编号 <br>
 */
public class InputProductIdAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_PRODUCT_ID;
    public static final String KEY_DATA = "InputProductId";

    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_the_code_the_items));
        setLabel2("");
        setLenghtEdit(16);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s)) {
            FlowControl.MapHelper.setProductId(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_the_code_the_items));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
