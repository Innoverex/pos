package cn.basewin.unionpay.setting;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/26 16:20<br>
 * 描述：交易开关控制
 */
public class TradeSwitchControlManageAty extends BaseListAty {
    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"传统类交易", "电子现金类交易", "分期付款类交易"
                , "积分类交易", "预约类交易", "订购类交易", "其他类交易"};

        data.add(new TabActionBean(-1, s[0], R.drawable.t_1_1, TraditionalTypeSettingAty.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.t_1_2, ECashTypeSettingAty.class));
        data.add(new TabActionBean(-1, s[2], R.drawable.t_1_3, InstallmentTypeSettingAty.class));
        data.add(new TabActionBean(-1, s[3], R.drawable.t_1_4, BONUSTypeSettingAty.class));
        data.add(new TabActionBean(-1, s[4], R.drawable.t_1_5, ReservationTypeSettingAty.class));
        data.add(new TabActionBean(-1, s[5], R.drawable.t_1_6, MOTOTypeSettingAty.class));
        data.add(new TabActionBean(-1, s[6], R.drawable.t_1_7, OtherTypeSettingAty.class));
        return data;
    }
}
