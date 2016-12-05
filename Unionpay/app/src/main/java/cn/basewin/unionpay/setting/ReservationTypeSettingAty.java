package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：预约类交易设置
 */
public class ReservationTypeSettingAty extends BaseSysSettingAty {
    private static final String TAG = ReservationTypeSettingAty.class.getName();
    /**
     * 预约消费
     */
    private static final String KEY_RESERVATION_SALE = TAG + "reservation_sale";
    /**
     * 预约消费撤销
     */
    private static final String KEY_RESERVATION_VOID = TAG + "reservation_void";
    /**
     * 预约消费
     */
    private ToggleButton togbtn_reservation_sale;
    /**
     * 预约消费撤销
     */
    private ToggleButton togbtn_reservation_void;


    private void initViews() {
        togbtn_reservation_sale = (ToggleButton) findViewById(R.id.togbtn_action_appointment);
        togbtn_reservation_void = (ToggleButton) findViewById(R.id.togbtn_action_appointment_consumption_undo);
    }

    private void initData() {
        togbtn_reservation_sale.setChecked(isRESERVATION_SALE());
        togbtn_reservation_void.setChecked(isRESERVATION_VOID());
    }

    //预约消费撤销
    public static boolean isRESERVATION_VOID() {
        return SPTools.get(ReservationTypeSettingAty.KEY_RESERVATION_VOID, AppConfig.DEFAULT_VALUE_RESERVATION_VOID);
    }

    //预约消费
    public static boolean isRESERVATION_SALE() {
        return SPTools.get(ReservationTypeSettingAty.KEY_RESERVATION_SALE, AppConfig.DEFAULT_VALUE_RESERVATION_SALE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_ordertype;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.yuyueleijiaoyi);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(ReservationTypeSettingAty.KEY_RESERVATION_SALE, togbtn_reservation_sale.isChecked());
        SPTools.set(ReservationTypeSettingAty.KEY_RESERVATION_VOID, togbtn_reservation_void.isChecked());
        showModifyHint();
    }
}