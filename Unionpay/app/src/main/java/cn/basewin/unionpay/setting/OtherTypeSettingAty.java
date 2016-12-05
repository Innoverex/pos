package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：其他类交易控制
 */
public class OtherTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = OtherTypeSettingAty.class.getName();
    /**
     * 磁条卡现金充值
     */
    public static final String KEY_ACCOUNT_LOAD_CASH = TAG + "account_load_cash";
    /**
     * 磁条卡账户充值
     */
    public static final String KEY_ACCOUNT_LOAD_ACCOUNT = TAG + "account_load_account";
    /**
     * 磁条卡现金充值
     */
    private ToggleButton togbtn_account_load_cash;
    /**
     * 磁条卡账户充值
     */
    private ToggleButton togbtn_account_load_account;


    private void initViews() {
        togbtn_account_load_cash = (ToggleButton) findViewById(R.id.togbtn_shield_prepaycard_cash);
        togbtn_account_load_account = (ToggleButton) findViewById(R.id.togbtn_shield_prepaycard_account);
    }

    private void initData() {
        togbtn_account_load_cash.setChecked(isACCOUNT_LOAD_CASH());
        togbtn_account_load_account.setChecked(isACCOUNT_LOAD_ACCOUNT());
    }

    public static boolean isACCOUNT_LOAD_ACCOUNT() {
        return SPTools.get(OtherTypeSettingAty.KEY_ACCOUNT_LOAD_ACCOUNT, AppConfig.DEFAULT_VALUE_ACCOUNT_LOAD_ACCOUNT);
    }

    public static boolean isACCOUNT_LOAD_CASH() {
        return SPTools.get(OtherTypeSettingAty.KEY_ACCOUNT_LOAD_CASH, AppConfig.DEFAULT_VALUE_ACCOUNT_LOAD_CASH);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_othertype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.qitaleijiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(OtherTypeSettingAty.KEY_ACCOUNT_LOAD_CASH, togbtn_account_load_cash.isChecked());
        SPTools.set(OtherTypeSettingAty.KEY_ACCOUNT_LOAD_ACCOUNT, togbtn_account_load_account.isChecked());
        showModifyHint();
    }
}
