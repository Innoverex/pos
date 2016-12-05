package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：手机芯片交易设置
 */
public class UpCardSettingAty extends BaseSysSettingAty {
    private static final String TAG = UpCardSettingAty.class.getName();
    /**
     * 手机消费
     */
    private static final String KEY_MOBILE_CONSUME = TAG + "mobile_consume";
    /**
     * 手机消费撤销
     */
    private static final String KEY_MOBILE_REVOKE_CONSUME = TAG + "mobile_revoke_consume";
    /**
     * 手机芯片退货
     */
    private static final String KEY_MOBILE_RETURN_GOODS = TAG + "mobile_return_goods";
    /**
     * 手机芯片预授权
     */
    private static final String KEY_MOBILE_AUTHORIZATION = TAG + "mobile_authorization";
    /**
     * 手机芯片预授权撤销
     */
    private static final String KEY_MOBILE_REVOKE_PRE_AUTHORIZATION = TAG + "revoke_pre-authorization";
    /**
     * 手机芯片预授权完成（请求）
     */
    private static final String KEY_MOBILE_ASK_PREAUTHORIZATION = TAG + "ask_mobile_pre-authorization";
    /**
     * 手机芯片预授权完成（通知）
     */
    private static final String KEY_MOBILE_NOTIFY_PREAUTHORIZATION = TAG + "notify_mobile_pre-authorization";
    /**
     * 手机芯片预授权完成撤销
     */
    private static final String KEY_MOBILE_REVOKE_AFTER_PREAUTHORIZATION = TAG + "mobile_revoke_after_pre-authorization";
    /**
     * 手机芯片余额查询
     */
    private static final String KEY_MOBILE_QUERY = TAG + "mobile_query";
    /**
     * 手机消费
     */
    private ToggleButton togbtn_shield_upcard_sale;
    /**
     * 手机消费撤销
     */
    private ToggleButton togbtn_shield_upcard_sale_void;
    /**
     * 手机芯片退货
     */
    private ToggleButton togbtn_shield_upcard_refund;
    /**
     * 手机芯片预授权
     */
    private ToggleButton togbtn_shoujixinpianyushouquan;
    /**
     * 手机芯片预授权撤销
     */
    private ToggleButton togbtn_shoujixinpianyushouquanchexiao;
    /**
     * 手机芯片预授权完成（请求）
     */
    private ToggleButton togbtn_shoujixinpian_qingqiu;
    /**
     * 手机芯片预授权完成（通知）
     */
    private ToggleButton togbtn_shoujixinpian_tongzhi;
    /**
     * 预授权完成撤销
     */
    private ToggleButton togbtn_shoujixinpian_wanchengchexiao;
    /**
     * 手机芯片余额查询
     */
    private ToggleButton togbtn_shoujixinpian_yuechaxun;

    private void initViews() {
        togbtn_shield_upcard_sale = (ToggleButton) findViewById(R.id.togbtn_shield_upcard_sale);
        togbtn_shield_upcard_sale_void = (ToggleButton) findViewById(R.id.togbtn_shield_upcard_sale_void);
        togbtn_shield_upcard_refund = (ToggleButton) findViewById(R.id.togbtn_shield_upcard_refund);
        togbtn_shoujixinpianyushouquan = (ToggleButton) findViewById(R.id.togbtn_shoujixinpianyushouquan);
        togbtn_shoujixinpianyushouquanchexiao = (ToggleButton) findViewById(R.id.togbtn_shoujixinpianyushouquanchexiao);
        togbtn_shoujixinpian_qingqiu = (ToggleButton) findViewById(R.id.togbtn_shoujixinpian_qingqiu);
        togbtn_shoujixinpian_tongzhi = (ToggleButton) findViewById(R.id.togbtn_shoujixinpian_tongzhi);
        togbtn_shoujixinpian_wanchengchexiao = (ToggleButton) findViewById(R.id.togbtn_shoujixinpian_wanchengchexiao);
        togbtn_shoujixinpian_yuechaxun = (ToggleButton) findViewById(R.id.togbtn_shoujixinpian_yuechaxun);
    }

    private void initData() {
        togbtn_shield_upcard_sale.setChecked(isUPCARD());
        togbtn_shield_upcard_sale_void.setChecked(isUPCARD_VOID());
        togbtn_shield_upcard_refund.setChecked(isUPCARD_REFUND());
        togbtn_shoujixinpianyushouquan.setChecked(isUPCARD_AUTH());
        togbtn_shoujixinpianyushouquanchexiao.setChecked(isUPCARD_CANCEL());
        togbtn_shoujixinpian_qingqiu.setChecked(isUPCARD_AUTH_COMPLETE());
        togbtn_shoujixinpian_tongzhi.setChecked(isUPCARD_AUTH_SETTLEMENT());
        togbtn_shoujixinpian_wanchengchexiao.setChecked(isUPCARD_COMPLETE_VOID());
        togbtn_shoujixinpian_yuechaxun.setChecked(isUPCARD_QUERY_BALANCE());
    }

    //手机芯片余额查询
    public static boolean isUPCARD_QUERY_BALANCE() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_QUERY, AppConfig.DEFAULT_VALUE_MOBILE_QUERY);
    }

    //手机芯片预授权完成撤销
    public static boolean isUPCARD_COMPLETE_VOID() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_REVOKE_AFTER_PREAUTHORIZATION, AppConfig.DEFAULT_VALUE_MOBILE_REVOKE_AFTER_PREAUTHORIZATION);
    }

    //手机芯片预授权完成（通知）
    public static boolean isUPCARD_AUTH_SETTLEMENT() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_NOTIFY_PREAUTHORIZATION, AppConfig.DEFAULT_VALUE_MOBILE_NOTIFY_PREAUTHORIZATION);
    }

    //手机芯片预授权完成（请求）
    public static boolean isUPCARD_AUTH_COMPLETE() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_ASK_PREAUTHORIZATION, AppConfig.DEFAULT_VALUE_MOBILE_ASK_PREAUTHORIZATION);
    }

    public static boolean isUPCARD_CANCEL() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_REVOKE_PRE_AUTHORIZATION, AppConfig.DEFAULT_VALUE_MOBILE_REVOKE_PRE_AUTHORIZATION);
    }

    public static boolean isUPCARD_AUTH() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_AUTHORIZATION, AppConfig.DEFAULT_VALUE_MOBILE_AUTHORIZATION);
    }

    public static boolean isUPCARD_REFUND() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_RETURN_GOODS, AppConfig.DEFAULT_VALUE_MOBILE_RETURN_GOODS);
    }

    public static boolean isUPCARD_VOID() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_REVOKE_CONSUME, AppConfig.DEFAULT_VALUE_MOBILE_REVOKE_CONSUME);
    }

    public static boolean isUPCARD() {
        return SPTools.get(UpCardSettingAty.KEY_MOBILE_CONSUME, AppConfig.DEFAULT_VALUE_MOBILE_CONSUME);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_mobileconsumetypeaty;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.shoujixinpianjiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(UpCardSettingAty.KEY_MOBILE_CONSUME, togbtn_shield_upcard_sale.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_REVOKE_CONSUME, togbtn_shield_upcard_sale_void.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_RETURN_GOODS, togbtn_shield_upcard_refund.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_AUTHORIZATION, togbtn_shoujixinpianyushouquan.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_REVOKE_PRE_AUTHORIZATION, togbtn_shoujixinpianyushouquanchexiao.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_ASK_PREAUTHORIZATION, togbtn_shoujixinpian_qingqiu.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_NOTIFY_PREAUTHORIZATION, togbtn_shoujixinpian_tongzhi.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_REVOKE_AFTER_PREAUTHORIZATION, togbtn_shoujixinpian_wanchengchexiao.isChecked());
        SPTools.set(UpCardSettingAty.KEY_MOBILE_QUERY, togbtn_shoujixinpian_yuechaxun.isChecked());
        showModifyHint();
    }
}
