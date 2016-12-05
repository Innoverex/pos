package cn.basewin.unionpay.setting;

import android.widget.ToggleButton;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.SPTools;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/14 14:14<br>
 * 描述：交易刷卡控制
 */
public class TradeSwipCardSettingAty extends BaseSysSettingAty {
    private static final String TAG = TradeSwipCardSettingAty.class.getName();
    /**
     * 消费撤销是否刷卡(Key值)
     */
    private static final String KEY_NEED_CARD_VOID = TAG + "need_card_void";
    /**
     * 预授权完成撤销是否刷卡
     */
    private static final String KEY_NEED_CARD_COMPLETE_VOID = TAG + "need_card_complete_void";
    /**
     * 消费撤销是否刷卡
     */
    private ToggleButton togbtn_need_card_void;
    /**
     * 预授权完成撤销是否刷卡
     */
    private ToggleButton togbtn_need_card_complete_void;

    private void initViews() {
        togbtn_need_card_void = (ToggleButton) findViewById(R.id.togbtn_if_swipcard_after_revoke);
        togbtn_need_card_complete_void = (ToggleButton) findViewById(R.id.togbtn_if_swipcard_after_authorization);
    }

    private void initData() {
        togbtn_need_card_void.setChecked(isVOID());
        togbtn_need_card_complete_void.setChecked(isCOMPLETE_VOID());
    }

    //预授权完成撤销
    public static boolean isCOMPLETE_VOID() {
        return SPTools.get(TradeSwipCardSettingAty.KEY_NEED_CARD_COMPLETE_VOID, AppConfig.DEFAULT_VALUE_NEED_CARD_COMPLETE_VOID);
    }

    //撤销
    public static boolean isVOID() {
        return SPTools.get(TradeSwipCardSettingAty.KEY_NEED_CARD_VOID, AppConfig.DEFAULT_VALUE_NEED_CARD_VOID);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_syssetting_tradeswipcard;
    }

    @Override
    public String getAtyTitle() {
        return getString(R.string.swipcard_control);
    }

    @Override
    public void afterSetContentView() {
        initViews();
        initData();
    }

    @Override
    public void save() {
        SPTools.set(TradeSwipCardSettingAty.KEY_NEED_CARD_VOID, togbtn_need_card_void.isChecked());
        SPTools.set(TradeSwipCardSettingAty.KEY_NEED_CARD_COMPLETE_VOID, togbtn_need_card_complete_void.isChecked());
        showModifyHint();
    }
}
