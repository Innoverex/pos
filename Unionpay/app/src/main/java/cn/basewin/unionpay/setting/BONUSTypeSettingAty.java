package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：积分类交易设置
 */
public class BONUSTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = BONUSTypeSettingAty.class.getName();
    /**
     * 联盟积分消费
     */
    private static final String KEY_CUPBONUS = TAG + "cupbonus";
    /**
     * 发卡行积分消费
     */
    private static final String KEY_BONUS = TAG + "bonus";
    /**
     * 联盟积分消费撤销
     */
    private static final String KEY_CUPBONUS_VOID = TAG + "cupbonus_void";
    /**
     * 发卡行积分消费撤销
     */
    private static final String KEY_BONUS_VOID = TAG + "bonus_void";
    /**
     * 联盟积分查询
     */
    private static final String KEY_CUPBONUS_QUERY = TAG + "cupbonus_query";
    /**
     * 联盟积分退货
     */
    private static final String KEY_CUPBONUS_REFUND = TAG + "cupbonus_refund";
    /**
     * 联盟积分消费
     */
    private ToggleButton togbtn_cupbonus;
    /**
     * 发卡行积分消费
     */
    private ToggleButton togbtn_bonus;
    /**
     * 联盟积分消费撤销
     */
    private ToggleButton togbtn_cupbonus_void;
    /**
     * 发卡行积分消费撤销
     */
    private ToggleButton togbtn_bonus_void;
    /**
     * 联盟积分查询
     */
    private ToggleButton togbtn_cupbonus_query;
    /**
     * 联盟积分退货
     */
    private ToggleButton togbtn_cupbonus_refund;

    private void initViews() {
        togbtn_cupbonus = (ToggleButton) findViewById(R.id.togbtn_shield_uni_integral_sale);
        togbtn_bonus = (ToggleButton) findViewById(R.id.togbtn_shield_iss_integral_sale);
        togbtn_cupbonus_void = (ToggleButton) findViewById(R.id.togbtn_shield_uni_integral_sale_void);
        togbtn_bonus_void = (ToggleButton) findViewById(R.id.togbtn_shield_iss_integral_sale_void);
        togbtn_cupbonus_query = (ToggleButton) findViewById(R.id.togbtn_action_integral_query);
        togbtn_cupbonus_refund = (ToggleButton) findViewById(R.id.togbtn_action_integral_return);
    }

    private void initData() {
        togbtn_cupbonus.setChecked(isCUPBONUS());
        togbtn_bonus.setChecked(isBONUS());
        togbtn_cupbonus_void.setChecked(isCUPBONUS_VOID());
        togbtn_bonus_void.setChecked(isBONUS_VOID());
        togbtn_cupbonus_query.setChecked(isCUPBONUS_QUERY());
        togbtn_cupbonus_refund.setChecked(isCUPBONUS_REFUND());
    }

    public static boolean isCUPBONUS_REFUND() {
        return SPTools.get(BONUSTypeSettingAty.KEY_CUPBONUS_REFUND, AppConfig.DEFAULT_VALUE_CUPBONUS_REFUND);
    }

    public static boolean isCUPBONUS_QUERY() {
        return SPTools.get(BONUSTypeSettingAty.KEY_CUPBONUS_QUERY, AppConfig.DEFAULT_VALUE_CUPBONUS_QUERY);
    }

    public static boolean isBONUS_VOID() {
        return SPTools.get(BONUSTypeSettingAty.KEY_BONUS_VOID, AppConfig.DEFAULT_VALUE_BONUS_VOID);
    }

    public static boolean isCUPBONUS_VOID() {
        return SPTools.get(BONUSTypeSettingAty.KEY_CUPBONUS_VOID, AppConfig.DEFAULT_VALUE_CUPBONUS_VOID);
    }

    public static boolean isBONUS() {
        return SPTools.get(BONUSTypeSettingAty.KEY_BONUS, AppConfig.DEFAULT_VALUE_BONUS);
    }

    public static boolean isCUPBONUS() {
        return SPTools.get(BONUSTypeSettingAty.KEY_CUPBONUS, AppConfig.DEFAULT_VALUE_CUPBONUS);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_cumulativescoretype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.jifenjiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(BONUSTypeSettingAty.KEY_CUPBONUS, togbtn_cupbonus.isChecked());
        SPTools.set(BONUSTypeSettingAty.KEY_BONUS, togbtn_bonus.isChecked());
        SPTools.set(BONUSTypeSettingAty.KEY_CUPBONUS_VOID, togbtn_cupbonus_void.isChecked());
        SPTools.set(BONUSTypeSettingAty.KEY_BONUS_VOID, togbtn_bonus_void.isChecked());
        SPTools.set(BONUSTypeSettingAty.KEY_CUPBONUS_QUERY, togbtn_cupbonus_query.isChecked());
        SPTools.set(BONUSTypeSettingAty.KEY_CUPBONUS_REFUND, togbtn_cupbonus_refund.isChecked());
        showModifyHint();
    }
}
