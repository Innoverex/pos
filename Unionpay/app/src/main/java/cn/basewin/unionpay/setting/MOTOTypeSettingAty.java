package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：订购类交易设置
 */
public class MOTOTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = MOTOTypeSettingAty.class.getName();
    /**
     * 订购消费
     */
    private static final String KEY_MOTO_SALE = TAG + "MOTO_sale";
    /**
     * 订购消费撤销
     */
    private static final String KEY_MOTO_VOID = TAG + "MOTO_void";
    /**
     * 订购退货
     */
    private static final String KEY_MOTO_REFUND = TAG + "MOTO_refund";
    /**
     * 订购预授权
     */
    private static final String KEY_MOTO_AUTH = TAG + "MOTO_auth";
    /**
     * 订购预授权撤销
     */
    private static final String KEY_MOTO_CANCEL = TAG + "MOTO_cancel";
    /**
     * 订购预授权完成请求
     */
    private static final String KEY_MOTO_AUTH_COMPLETE = TAG + "MOTO_auth_complete";
    /**
     * 订购预授权完成通知
     */
    private static final String KEY_MOTO_AUTH_SETTLEMENT = TAG + "MOTO_auth_settlement";
    /**
     * 订购预授权完成撤销
     */
    private static final String KEY_MOTO_COMPLETE_VOID = TAG + "MOTO_complete_void";
    /**
     * 订购持卡人身份验证
     */
    private static final String KEY_MOTO_VERIFY = TAG + "MOTO_verify";
    /**
     * 订购消费
     */
    private ToggleButton togbtn_MOTO_sale;
    /**
     * 订购消费撤销
     */
    private ToggleButton togbtn_MOTO_void;
    /**
     * 订购退货
     */
    private ToggleButton togbtn_MOTO_refund;
    /**
     * 订购预授权
     */
    private ToggleButton togbtn_MOTO_auth;
    /**
     * 订购预授权撤销
     */
    private ToggleButton togbtn_MOTO_cancel;
    /**
     * 订购预授权完成请求
     */
    private ToggleButton togbtn_MOTO_auth_complete;
    /**
     * 订购预授权完成通知
     */
    private ToggleButton togbtn_MOTO_auth_settlement;
    /**
     * 订购预售完成撤销
     */
    private ToggleButton togbtn_MOTO_complete_void;
    /**
     * 订购持卡人身份验证
     */
    private ToggleButton togbtn_MOTO_verify;

    private void initViews() {
        togbtn_MOTO_sale = (ToggleButton) findViewById(R.id.togbtn_action_order_consumption);
        togbtn_MOTO_void = (ToggleButton) findViewById(R.id.togbtn_action_order_consumption_undo);
        togbtn_MOTO_refund = (ToggleButton) findViewById(R.id.togbtn_action_order_return);
        togbtn_MOTO_auth = (ToggleButton) findViewById(R.id.togbtn_action_order_authorization);
        togbtn_MOTO_cancel = (ToggleButton) findViewById(R.id.togbtn_action_order_authorization_undo);
        togbtn_MOTO_auth_complete = (ToggleButton) findViewById(R.id.togbtn_dinggouyushouquan_qingqiu);
        togbtn_MOTO_auth_settlement = (ToggleButton) findViewById(R.id.togbtn_dinggouyushouquan_tongzhi);
        togbtn_MOTO_complete_void = (ToggleButton) findViewById(R.id.togbtn_action_order_authorization_over_undo);
        togbtn_MOTO_verify = (ToggleButton) findViewById(R.id.togbtn_action_order_cardholder_authentication);
    }

    private void initData() {
        togbtn_MOTO_sale.setChecked(isMotoSale());
        togbtn_MOTO_void.setChecked(isMotoVoid());
        togbtn_MOTO_refund.setChecked(isMotoRefund());
        togbtn_MOTO_auth.setChecked(isMotoAuth());
        togbtn_MOTO_cancel.setChecked(isMotoCancel());
        togbtn_MOTO_auth_complete.setChecked(isMotoAuthComplete());
        togbtn_MOTO_auth_settlement.setChecked(isMotoAuthSettlement());
        togbtn_MOTO_complete_void.setChecked(isMotoCompleteVoid());
        togbtn_MOTO_verify.setChecked(isMotoVerify());
    }

    public static boolean isMotoVerify() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_VERIFY, AppConfig.DEFAULT_VALUE_MOTO_VERIFY);
    }

    public static boolean isMotoCompleteVoid() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_COMPLETE_VOID, AppConfig.DEFAULT_VALUE_MOTO_COMPLETE_VOID);
    }

    public static boolean isMotoAuthSettlement() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_AUTH_SETTLEMENT, AppConfig.DEFAULT_VALUE_MOTO_AUTH_SETTLEMENT);
    }

    public static boolean isMotoAuthComplete() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_AUTH_COMPLETE, AppConfig.DEFAULT_VALUE_MOTO_AUTH_COMPLETE);
    }

    public static boolean isMotoCancel() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_CANCEL, AppConfig.DEFAULT_VALUE_MOTO_CANCEL);
    }

    public static boolean isMotoAuth() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_AUTH, AppConfig.DEFAULT_VALUE_MOTO_AUTH);
    }

    public static boolean isMotoRefund() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_REFUND, AppConfig.DEFAULT_VALUE_MOTO_REFUND);
    }

    public static boolean isMotoVoid() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_VOID, AppConfig.DEFAULT_VALUE_MOTO_VOID);
    }

    public static boolean isMotoSale() {
        return SPTools.get(MOTOTypeSettingAty.KEY_MOTO_SALE, AppConfig.DEFAULT_VALUE_MOTO_SALE);
    }

    public static List<Integer> getActionID() {
        List<Integer> ints = new ArrayList<>();
        isAdd(ints, isMotoSale(), ActionConstant.ACTION_MOTO_SALE);
        isAdd(ints, isMotoVoid(), ActionConstant.ACTION_MOTO_VOID);
        isAdd(ints, isMotoRefund(), ActionConstant.ACTION_MOTO_REFUND);
        isAdd(ints, isMotoAuth(), ActionConstant.ACTION_MOTO_AUTH);
        isAdd(ints, isMotoCancel(), ActionConstant.ACTION_MOTO_CANCEL);
        isAdd(ints, isMotoAuthComplete(), ActionConstant.ACTION_MOTO_AUTH_COMPLETE);
        isAdd(ints, isMotoAuthSettlement(), ActionConstant.ACTION_MOTO_AUTH_SETTLEMENT);
        isAdd(ints, isMotoCompleteVoid(), ActionConstant.ACTION_MOTO_COMPLETE_VOID);
        isAdd(ints, isMotoVerify(), ActionConstant.ACTION_MOTO_VERIFY);
        return ints;
    }

    public static void isAdd(List<Integer> list, boolean is, int data) {
        if (list != null && is) {
            list.add(data);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_booktype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.dinggouleijiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_SALE, togbtn_MOTO_sale.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_VOID, togbtn_MOTO_void.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_REFUND, togbtn_MOTO_refund.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_AUTH, togbtn_MOTO_auth.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_CANCEL, togbtn_MOTO_cancel.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_AUTH_COMPLETE, togbtn_MOTO_auth_complete.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_AUTH_SETTLEMENT, togbtn_MOTO_auth_settlement.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_COMPLETE_VOID, togbtn_MOTO_complete_void.isChecked());
        SPTools.set(MOTOTypeSettingAty.KEY_MOTO_VERIFY, togbtn_MOTO_verify.isChecked());
        TLog.showToast("修改订购类交易保存成功！");
    }

}
