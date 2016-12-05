package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 09:52<br>
 * 描述: 输入日期  <br>
 */
public class InputDateAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_DATA;
    public static final String KEY_DATA = "date";

    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_date));
        setLabel2("");
        setLenghtEdit(4);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        if (s.length() != 4) {
            TLog.showToast("日期格式错误");
            return;
        } else {
            if (!isValidDate(s)) {
                TLog.showToast("月份或日期格式错误");
                return;
            }
        }

        TLog.e(this, s);
        //TODO 日期的要匹配格式
        if (!TextUtils.isEmpty(s)) {
            FlowControl.MapHelper.setDate(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_date));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }

    private boolean isValidDate(String strValue) {
        int d = Integer.parseInt(strValue.substring(2, 4));
        int m = Integer.parseInt(strValue.substring(0, 2));
        int y = Integer.parseInt(TDevice.getYear());

        if (d < 1 || m < 1 || m > 12) {
            return false;
        }

        if (m == 2) {
            if (isLeapYear(y)) {
                return d <= 29;
            } else {
                return d <= 28;
            }
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            return d <= 30;
        } else {
            return d <= 31;
        }
    }

    private boolean isLeapYear(int y) {
        return y % 4 == 0 && (y % 400 == 0 || y % 100 != 0);
    }

}
