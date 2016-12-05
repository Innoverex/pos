package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: init方法设置虚拟键盘监听<br>
 * 创建时间:  2016/6/27 15:53<br>
 * 描述:  获取金额 如果对这个界面有其他使用方式 请继承这个界面<br>
 */
public class InputRefundMoneyAty extends InputMoneyAty {
    @Override
    protected void clickOK(String s) {
        if (!TextUtils.isEmpty(s)) {
            double v = Double.parseDouble(s);
            if (v <= 0) {
                TLog.showToast(getString(R.string.Please_set_the_amount));
            } else {
                if (v <= SettingConstant.getREFUND_LIMIT()) {
                    FlowControl.MapHelper.setMoney(s);
                    startNextFlow();
                } else {
                    DialogHelper.showAndClose(this, "超过最大退货金额！");
                }
            }
        }
    }

}
