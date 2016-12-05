package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：分期付款类交易
 */
public class InstallmentTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = InstallmentTypeSettingAty.class.getName();
    /**
     * 分期付款消费
     */
    private static final String KEY_INSTALLMENT = TAG + "installment";
    /**
     * 分期付款消费撤销
     */
    private static final String KEY_INSTALLMENT_VOID = TAG + "installment_void";
    /**
     * 电子钱包消费
     */
    private ToggleButton togbtn_installment;
    /**
     * 电子钱包指定商户圈存
     */
    private ToggleButton togbtn_installment_void;


    private void initViews() {
        togbtn_installment = (ToggleButton) findViewById(R.id.togbtn_action_installment_consumption);
        togbtn_installment_void = (ToggleButton) findViewById(R.id.togbtn_action_installment_consumption_undo);
    }

    private void initData() {
        togbtn_installment.setChecked(isInstallment());
        togbtn_installment_void.setChecked(isInstallmentVoid());
    }

    //分期付款消费撤销
    public static boolean isInstallmentVoid() {
        return SPTools.get(InstallmentTypeSettingAty.KEY_INSTALLMENT_VOID, AppConfig.DEFAULT_VALUE_INSTALLMENT_VOID);
    }

    //分期付款消费
    public static boolean isInstallment() {
        return SPTools.get(InstallmentTypeSettingAty.KEY_INSTALLMENT, AppConfig.DEFAULT_VALUE_INSTALLMENT);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_intstallmenttype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.fenqifukuanleijiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(InstallmentTypeSettingAty.KEY_INSTALLMENT, togbtn_installment.isChecked());
        SPTools.set(InstallmentTypeSettingAty.KEY_INSTALLMENT_VOID, togbtn_installment_void.isChecked());
        showModifyHint();
    }
}
