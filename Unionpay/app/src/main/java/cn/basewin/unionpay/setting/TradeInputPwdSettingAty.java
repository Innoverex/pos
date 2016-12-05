package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：交易输密控制
 */
public class TradeInputPwdSettingAty extends BaseSysSettingAty {
    private static final String TAG = TradeInputPwdSettingAty.class.getName();
    /**
     * 消费撤销是否输密
     */
    private static final String KEY_NEED_PIN_VOID = TAG + "need_pin_void";
    /**
     * 分期付款撤销是否输密
     */
    private static final String KEY_NEED_PIN_INSTALLMENT_VOID = TAG + "need_pin_installment_void";
    /**
     * 预授权撤销是否输密
     */
    private static final String KEY_NEED_PIN_CANCEL = TAG + "need_pin_cancel";
    /**
     * 预授权完成撤销是否输密
     */
    private static final String KEY_NEED_PIN_COMPLETE_VOID = TAG + "need_pin_complete_void";
    /**
     * 预授权完成（请求）是输密
     */
    private static final String KEY_NEED_PIN_AUTH_COMPLETE = TAG + "need_pin_auth_complete";
    /**
     * 消费撤销是否输密
     */
    private ToggleButton togbtn_need_pin_void;
    /**
     * 分期付款撤销是否输密
     */
    private ToggleButton togbtn_need_pin_installment_void;
    /**
     * 预授权撤销是否输密
     */
    private ToggleButton togbtn_need_pin_cancel;
    /**
     * 预授权完成撤销是否输密
     */
    private ToggleButton togbtn_need_pin_complete_void;
    /**
     * 预授权完成（请求）是输密
     */
    private ToggleButton togbtn_need_pin_auth_complete;

    private void initViews() {
        togbtn_need_pin_void = (ToggleButton) findViewById(R.id.togbtn_revoke_input_pwd);
        togbtn_need_pin_installment_void = (ToggleButton) findViewById(R.id.togbtn_installment_input_pwd);
        togbtn_need_pin_cancel = (ToggleButton) findViewById(R.id.togbtn_preauthorization_input_pwd);
        togbtn_need_pin_complete_void = (ToggleButton) findViewById(R.id.togbtn_preauthorization_finish_input_pwd);
        togbtn_need_pin_auth_complete = (ToggleButton) findViewById(R.id.togbtn_preauthorization_finish_ask_input_pwd);
    }

    private void initData() {
        togbtn_need_pin_void.setChecked(isVoid());
        togbtn_need_pin_installment_void.setChecked(isINSTALLMENT_VOID());
        togbtn_need_pin_cancel.setChecked(isCANCEL());
        togbtn_need_pin_complete_void.setChecked(isCOMPLETE_VOID());
        togbtn_need_pin_auth_complete.setChecked(isAUTH_COMPLETE());
    }

    //预授权完成(请求)
    public static boolean isAUTH_COMPLETE() {
        return SPTools.get(TradeInputPwdSettingAty.KEY_NEED_PIN_AUTH_COMPLETE, AppConfig.DEFAULT_VALUE_NEED_PIN_AUTH_COMPLETE);
    }

    //预授权完成撤销
    public static boolean isCOMPLETE_VOID() {
        return SPTools.get(TradeInputPwdSettingAty.KEY_NEED_PIN_COMPLETE_VOID, AppConfig.DEFAULT_VALUE_NEED_PIN_COMPLETE_VOID);
    }

    //预授权撤销
    public static boolean isCANCEL() {
        return SPTools.get(TradeInputPwdSettingAty.KEY_NEED_PIN_CANCEL, AppConfig.DEFAULT_VALUE_NEED_PIN_CANCEL);
    }

    //分期付款消费撤销
    public static boolean isINSTALLMENT_VOID() {
        return SPTools.get(TradeInputPwdSettingAty.KEY_NEED_PIN_INSTALLMENT_VOID, AppConfig.DEFAULT_VALUE_NEED_PIN_INSTALLMENT_VOID);
    }

    //消费撤销
    public static boolean isVoid() {
        return SPTools.get(TradeInputPwdSettingAty.KEY_NEED_PIN_VOID, AppConfig.DEFAULT_VALUE_NEED_PIN_VOID);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_inputpwdtype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.trade_input_pwd_control);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(TradeInputPwdSettingAty.KEY_NEED_PIN_VOID, togbtn_need_pin_void.isChecked());
        SPTools.set(TradeInputPwdSettingAty.KEY_NEED_PIN_INSTALLMENT_VOID, togbtn_need_pin_installment_void.isChecked());
        SPTools.set(TradeInputPwdSettingAty.KEY_NEED_PIN_CANCEL, togbtn_need_pin_cancel.isChecked());
        SPTools.set(TradeInputPwdSettingAty.KEY_NEED_PIN_COMPLETE_VOID, togbtn_need_pin_complete_void.isChecked());
        SPTools.set(TradeInputPwdSettingAty.KEY_NEED_PIN_AUTH_COMPLETE, togbtn_need_pin_auth_complete.isChecked());
        showModifyHint();
    }

    /**
     * 通过action 判断是否开启密码
     *
     * @param action
     * @return
     */
    public static boolean select(int action) {
        boolean b = true;
        switch (action) {
            case ActionConstant.ACTION_VOID:
                b = isVoid();
                break;
            case ActionConstant.ACTION_INSTALLMENT_VOID:
                b = isINSTALLMENT_VOID();
                break;
            case ActionConstant.ACTION_RESERVATION_VOID:
                b = isCANCEL();
                break;
            case ActionConstant.ACTION_COMPLETE_VOID:
                b = isCOMPLETE_VOID();
                break;
            case ActionConstant.ACTION_AUTH_COMPLETE:
                b = isAUTH_COMPLETE();
                break;
        }
        return b;
    }
}
