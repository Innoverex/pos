package cn.basewin.unionpay.setting;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;


/**
 * 作者：lhc<br>
 * 创建时间：2016/7/15 15:05<br>
 * 描述：交易管理设置
 */
public class TradeManageAty extends BaseListAty {
    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"交易开关控制", "交易输密控制", "交易刷卡控制", "结算交易控制", "离线交易控制", "其他交易控制"};
        //TODO 修改ICON
        data.add(new TabActionBean(-1, s[0], R.drawable.t_1, TradeSwitchControlManageAty.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.t_2, TradeInputPwdSettingAty.class));
        data.add(new TabActionBean(-1, s[2], R.drawable.t_3, TradeSwipCardSettingAty.class));
        data.add(new TabActionBean(-1, s[3], R.drawable.t_4, TradeSettlementSettingAty.class));
        data.add(new TabActionBean(-1, s[4], R.drawable.t_5, TradeOfflineSettingAty.class));
        data.add(new TabActionBean(-1, s[5], R.drawable.t_6, TradeOtherSettingAty.class));
        return data;
    }
}
