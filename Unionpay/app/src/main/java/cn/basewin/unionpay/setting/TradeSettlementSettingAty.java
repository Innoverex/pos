package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：结算交易控制
 */
public class TradeSettlementSettingAty extends BaseSysSettingAty {
    private static final String TAG = TradeSettlementSettingAty.class.getName();
    /**
     * 结算是否自动签退
     */
    private static final String KEY_NEED_AUTO_SIGNOUT = TAG + "need_auto_signout";
    /**
     * 结算是否打印明细
     */
    private static final String KEY_NEED_PRINT_DETAIL = TAG + "need_print_detail";
    /**
     * 是否提示打印失败明细
     */
    private static final String KEY_NEED_PRINT_FAILURE = TAG + "need_print_failure";
    /**
     * 结算是否自动签退
     */
    private ToggleButton togbtn_need_auto_signout;
    /**
     * 结算是否打印明细
     */
    private ToggleButton togbtn_need_print_detail;
    /**
     * 是否提示打印失败明细
     */
    private ToggleButton togbtn_need_print_failure;

    private void initViews() {
        togbtn_need_auto_signout = (ToggleButton) findViewById(R.id.togbtn_if_settlement_auto_signout);
        togbtn_need_print_detail = (ToggleButton) findViewById(R.id.togbtn_if_print_detail);
        togbtn_need_print_failure = (ToggleButton) findViewById(R.id.togbtn_if_remind_print_unsuccessful_detail);
    }

    private void initData() {
        togbtn_need_auto_signout.setChecked(getNeedAutoSignout());
        togbtn_need_print_detail.setChecked(getNeedPrintDetail());
        togbtn_need_print_failure.setChecked(getNeedPrintFailure());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_settlementcontrol;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.settlement_control);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(TradeSettlementSettingAty.KEY_NEED_AUTO_SIGNOUT, togbtn_need_auto_signout.isChecked());
        SPTools.set(TradeSettlementSettingAty.KEY_NEED_PRINT_DETAIL, togbtn_need_print_detail.isChecked());
        SPTools.set(TradeSettlementSettingAty.KEY_NEED_PRINT_FAILURE, togbtn_need_print_failure.isChecked());
        showModifyHint();
    }

    public static boolean getNeedAutoSignout() {
        return SPTools.get(TradeSettlementSettingAty.KEY_NEED_AUTO_SIGNOUT, AppConfig.DEFAULT_VALUE_NEED_AUTO_SIGNOUT);
    }

    public static boolean getNeedPrintDetail() {
        return SPTools.get(TradeSettlementSettingAty.KEY_NEED_PRINT_DETAIL, AppConfig.DEFAULT_VALUE_NEED_PRINT_DETAIL);
    }

    public static boolean getNeedPrintFailure() {
        return SPTools.get(TradeSettlementSettingAty.KEY_NEED_PRINT_FAILURE, AppConfig.DEFAULT_VALUE_NEED_PRINT_FAILURE);
    }
}
