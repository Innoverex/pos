package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/26 10:36<br>
 * 描述：提示输入授权机构代码
 */
public class InputAuthOrganizationCodeAty extends BaseInputAty {
    public static final String KEY_AUTH_ORGANIZATION_CODE = "InputAuthOrganizationCodeAty_authorization_code";


    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.please_enter_auth_organization_code));
        setLabel2("");
        setLenghtEdit(6);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        if (!TextUtils.isEmpty(s)) {
            FlowControl.MapHelper.getMap().put(InputAuthOrganizationCodeAty.KEY_AUTH_ORGANIZATION_CODE, s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.please_enter_auth_organization_code));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
