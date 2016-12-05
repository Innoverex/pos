package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：传统类交易设置
 */
public class TraditionalTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = TraditionalTypeSettingAty.class.getName();
    /**
     * 消费
     */
    private static final String KEY_SALE = TAG + "sale";
    /**
     * 消费撤销
     */
    private static final String KEY_VOID = TAG + "void";
    /**
     * 退货
     */
    private static final String KEY_REFUND = TAG + "refund";
    /**
     * 余额查询
     */
    private static final String KEY_QUERY_BALANCE = TAG + "query_balance";
    /**
     * 预授权
     */
    private static final String KEY_AUTH = TAG + "auth";
    /**
     * 预授权撤销
     */
    private static final String KEY_CANCEL = TAG + "cancel";
    /**
     * 预授权完成（请求）
     */
    private static final String KEY_AUTH_COMPLETE = TAG + "auth_complete";
    /**
     * 预授权完成（通知）
     */
    private static final String KEY_AUTH_SETTLEMENT = TAG + "auth_settlement";
    /**
     * 预授权完成撤销
     */
    private static final String KEY_COMPLETE_VOID = TAG + "complete_void";
    /**
     * 离线结算
     */
    private static final String KEY_OFFLINE_SETTLEMENT = TAG + "offline_settlement";
    /**
     * 结算调整
     */
    private static final String KEY_OFFLINE_ADJUST = TAG + "offline_adjust";
    /**
     * 小费
     */
    private static final String KEY_FEE = TAG + "fee";
    /**
     * 小额代授权
     */
    private static final String KEY_SMALL_AUTH = TAG + "small_auth";
    /**
     * 消费
     */
    private ToggleButton togbtn_sale;
    /**
     * 消费撤销
     */
    private ToggleButton togbtn_void;
    /**
     * 退货
     */
    private ToggleButton togbtn_refund;
    /**
     * 余额查询
     */
    private ToggleButton togbtn_query_balance;
    /**
     * 预授权
     */
    private ToggleButton togbtn_auth;
    /**
     * 预授权撤销
     */
    private ToggleButton togbtn_cancel;
    /**
     * 预授权完成（请求）
     */
    private ToggleButton togbtn_auth_complete;
    /**
     * 预授权完成（通知）
     */
    private ToggleButton togbtn_auth_settlement;
    /**
     * 预授权完成撤销
     */
    private ToggleButton togbtn_complete_void;
    /**
     * 离线结算
     */
    private ToggleButton togbtn_offline;
    /**
     * 结算调整
     */
    private ToggleButton togbtn_adjust;
    /**
     * 小费
     */
    private ToggleButton togbtn_fee;
    /**
     * 小额代授权
     */
    private ToggleButton togbtn_small_auth;

    private void initViews() {
        togbtn_sale = (ToggleButton) findViewById(R.id.togbtn_xiaofei);
        togbtn_void = (ToggleButton) findViewById(R.id.togbtn_xiaofeichexiao);
        togbtn_refund = (ToggleButton) findViewById(R.id.togbtn_tuihuo);
        togbtn_query_balance = (ToggleButton) findViewById(R.id.togbtn_yuechaxun);
        togbtn_auth = (ToggleButton) findViewById(R.id.togbtn_yushouquan);
        togbtn_cancel = (ToggleButton) findViewById(R.id.togbtn_yushouquanchexiao);
        togbtn_auth_settlement = (ToggleButton) findViewById(R.id.togbtn_yushouquanwanchengtongzhi);
        togbtn_complete_void = (ToggleButton) findViewById(R.id.togbtn_yushouquanwanchengchexiao);
        togbtn_offline = (ToggleButton) findViewById(R.id.togbtn_lixianjiesuan);
        togbtn_adjust = (ToggleButton) findViewById(R.id.togbtn_jiesuantiaozheng);
        togbtn_fee = (ToggleButton) findViewById(R.id.togbtn_tip);
        togbtn_small_auth = (ToggleButton) findViewById(R.id.togbtn_xiaoedaishouquan);
        togbtn_auth_complete = (ToggleButton) findViewById(R.id.togbtn_yushouquanwanchengqingqiu);
    }

    private void initData() {
        togbtn_sale.setChecked(isSale());
        togbtn_void.setChecked(isVoid());
        togbtn_refund.setChecked(isRefund());
        togbtn_query_balance.setChecked(isQueryBalance());
        togbtn_auth.setChecked(isAuth());
        togbtn_cancel.setChecked(isCancel());
        togbtn_auth_complete.setChecked(isAuthComplete());
        togbtn_auth_settlement.setChecked(isAuthSettlement());
        togbtn_complete_void.setChecked(isCompleteVoid());
        togbtn_offline.setChecked(isOffline());
        togbtn_adjust.setChecked(isAdjust());
        togbtn_fee.setChecked(SPTools.get(TraditionalTypeSettingAty.KEY_FEE, false));
        togbtn_small_auth.setChecked(SPTools.get(TraditionalTypeSettingAty.KEY_SMALL_AUTH, false));
    }

    //离线调整
    public static boolean isAdjust() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_OFFLINE_ADJUST, AppConfig.DEFAULT_VALUE_OFFLINE_ADJUST);
    }

    //离线结算
    public static boolean isOffline() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_OFFLINE_SETTLEMENT, AppConfig.DEFAULT_VALUE_OFFLINE_SETTLEMENT);
    }

    //预授权完成撤销
    public static boolean isCompleteVoid() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_COMPLETE_VOID, AppConfig.DEFAULT_VALUE_COMPLETE_VOID);
    }

    //预授权完成(通知)
    public static boolean isAuthSettlement() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_AUTH_SETTLEMENT, AppConfig.DEFAULT_VALUE_AUTH_SETTLEMENT);
    }

    //预授权完成(请求)
    public static boolean isAuthComplete() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_AUTH_COMPLETE, AppConfig.DEFAULT_VALUE_AUTH_COMPLETE);
    }

    /**
     * 预授权撤销
     *
     * @return
     */
    public static boolean isCancel() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_CANCEL, AppConfig.DEFAULT_VALUE_CANCEL);
    }

    public static boolean isAuth() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_AUTH, AppConfig.DEFAULT_VALUE_AUTH);
    }

    public static boolean isQueryBalance() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_QUERY_BALANCE, AppConfig.DEFAULT_VALUE_QUERY_BALANCE);
    }

    public static boolean isRefund() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_REFUND, AppConfig.DEFAULT_VALUE_REFUND);
    }

    public static boolean isVoid() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_VOID, AppConfig.DEFAULT_VALUE_VOID);
    }

    public static boolean isSale() {
        return SPTools.get(TraditionalTypeSettingAty.KEY_SALE, AppConfig.DEFAULT_VALUE_SALE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_traditionaltypeaty;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.chuantongjiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(TraditionalTypeSettingAty.KEY_SALE, togbtn_sale.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_VOID, togbtn_void.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_REFUND, togbtn_refund.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_QUERY_BALANCE, togbtn_query_balance.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_AUTH, togbtn_auth.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_CANCEL, togbtn_cancel.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_AUTH_COMPLETE, togbtn_auth_complete.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_AUTH_SETTLEMENT, togbtn_auth_settlement.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_COMPLETE_VOID, togbtn_complete_void.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_OFFLINE_SETTLEMENT, togbtn_offline.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_OFFLINE_ADJUST, togbtn_adjust.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_FEE, togbtn_fee.isChecked());
        SPTools.set(TraditionalTypeSettingAty.KEY_SMALL_AUTH, togbtn_small_auth.isChecked());
        showModifyHint();
    }
}
