package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：电子现金类交易
 */
public class ECashTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = ECashTypeSettingAty.class.getName();
    /**
     * 接触式电子现金消费
     */
    private static final String KEY_EC_SALE = TAG + "EC_sale";
    /**
     * 非接快速支付消费
     */
    private static final String KEY_EC_QUICKPASS = TAG + "EC_quickpass";
    /**
     * 电子现金指定账户圈存
     */
    private static final String KEY_ECLOAD_ACCOUNT = TAG + "ECLoad_account";
    /**
     * 电子现金非指定账户圈存
     */
    private static final String KEY_ECLOAD_NONACCOUNT = TAG + "ECLoad_nonaccount";
    /**
     * 电子现金现金充值
     */
    private static final String KEY_ECLOAD_CASH = TAG + "ECLoad_cash";
    /**
     * 电子现金撤销
     */
    private static final String KEY_ECLOAD_CASH_VOID = TAG + "ECLoad_cash_void";
    /**
     * 电子现金脱机退货
     */
    private static final String KEY_EC_REFUND = TAG + "EC_refund";
    /**
     * 接触式电子现金消费
     */
    private ToggleButton togbtn_EC_sale;
    /**
     * 非接快速支付消费
     */
    private ToggleButton togbtn_EC_quickpass;
    /**
     * 电子现金指定账户圈存
     */
    private ToggleButton togbtn_EC_load_account;
    /**
     * 电子现金非指定账户圈存
     */
    private ToggleButton togbtn_EC_load_nonaccount;
    /**
     * 电子现金现金充值
     */
    private ToggleButton togbtn_EC_load_cash;
    /**
     * 电子现金充值撤销
     */
    private ToggleButton togbtn_EC_load_cash_void;
    /**
     * 电子现金脱机退货
     */
    private ToggleButton togbtn_EC_refund;

    private void initViews() {
        togbtn_EC_sale = (ToggleButton) findViewById(R.id.togbtn_shield_consumer_electronic_cash);
        togbtn_EC_quickpass = (ToggleButton) findViewById(R.id.togbtn_feijiekuaisuzhifuxiaofei);
        togbtn_EC_load_account = (ToggleButton) findViewById(R.id.togbtn_dianzixianjinzhidingzhanghuquanchun);
        togbtn_EC_load_nonaccount = (ToggleButton) findViewById(R.id.togbtn_shield_transfer_nofrom);
        togbtn_EC_load_cash = (ToggleButton) findViewById(R.id.togbtn_shield_transfer_cash);
        togbtn_EC_load_cash_void = (ToggleButton) findViewById(R.id.togbtn_dianzhixianjinchongzhicexiao);
        togbtn_EC_refund = (ToggleButton) findViewById(R.id.togbtn_shield_ec_refund);
    }

    private void initData() {
        togbtn_EC_sale.setChecked(isECquickpass());
        togbtn_EC_quickpass.setChecked(isECsale());
        togbtn_EC_load_account.setChecked(isECloadAccount());
        togbtn_EC_load_nonaccount.setChecked(isECloadNonaccount());
        togbtn_EC_load_cash.setChecked(isECloadCash());
        togbtn_EC_load_cash_void.setChecked(isECloadCashVoid());
        togbtn_EC_refund.setChecked(isECrefund());
    }

    //电子现金脱机退货
    public static boolean isECrefund() {
        return SPTools.get(ECashTypeSettingAty.KEY_EC_REFUND, AppConfig.DEFAULT_VALUE_EC_REFUND);
    }

    //现金充值撤销
    public static boolean isECloadCashVoid() {
        return SPTools.get(ECashTypeSettingAty.KEY_ECLOAD_CASH_VOID, AppConfig.DEFAULT_VALUE_ECLOAD_CASH_VOID);
    }

    //现金充值
    public static boolean isECloadCash() {
        return SPTools.get(ECashTypeSettingAty.KEY_ECLOAD_CASH, AppConfig.DEFAULT_VALUE_ECLOAD_CASH);
    }

    //非指定帐号圈存
    public static boolean isECloadNonaccount() {
        return SPTools.get(ECashTypeSettingAty.KEY_ECLOAD_NONACCOUNT, AppConfig.DEFAULT_VALUE_ECLOAD_NONACCOUNT);
    }

    //指定账户圈存
    public static boolean isECloadAccount() {
        return SPTools.get(ECashTypeSettingAty.KEY_ECLOAD_ACCOUNT, AppConfig.DEFAULT_VALUE_ECLOAD_ACCOUNT);
    }

    public static boolean isECsale() {
        return SPTools.get(ECashTypeSettingAty.KEY_EC_QUICKPASS, AppConfig.DEFAULT_VALUE_EC_QUICKPASS);
    }

    public static boolean isECquickpass() {
        return SPTools.get(ECashTypeSettingAty.KEY_EC_SALE, AppConfig.DEFAULT_VALUE_EC_SALE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_ecashtype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.dianzixianjinjiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(ECashTypeSettingAty.KEY_EC_SALE, togbtn_EC_sale.isChecked());
        SPTools.set(ECashTypeSettingAty.KEY_EC_QUICKPASS, togbtn_EC_quickpass.isChecked());
        SPTools.set(ECashTypeSettingAty.KEY_ECLOAD_ACCOUNT, togbtn_EC_load_account.isChecked());
        SPTools.set(ECashTypeSettingAty.KEY_ECLOAD_NONACCOUNT, togbtn_EC_load_nonaccount.isChecked());
        SPTools.set(ECashTypeSettingAty.KEY_ECLOAD_CASH, togbtn_EC_load_cash.isChecked());
        SPTools.set(ECashTypeSettingAty.KEY_ECLOAD_CASH_VOID, togbtn_EC_load_cash_void.isChecked());
        SPTools.set(ECashTypeSettingAty.KEY_EC_REFUND, togbtn_EC_refund.isChecked());
        showModifyHint();
    }
}
