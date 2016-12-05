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
public class CommuParmManageAty extends BaseListAty {
    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"通讯类型", "其他"};
        //TODO 修改ICON
        data.add(new TabActionBean(-1, s[0], R.drawable.s_1, CommuParmAty.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.t_6, CommuParmOtherAyt.class));
        return data;
    }
}
