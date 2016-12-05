package cn.basewin.unionpay.setting;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/27 10:08<br>
 * 描述：
 */
public class OtherFunctionManageAty extends BaseListAty {
    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"清除数据", "签购单打印", "下载功能", "参数打印", "签名版参数设置"};
        data.add(new TabActionBean(-1, s[0], R.drawable.o_1, ClearTradeFlow.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.o_2, PrintStyleSettingAty.class));
        data.add(new TabActionBean(-1, s[2], R.drawable.o_3, TestManageAty.class));
        data.add(new TabActionBean(-1, s[3], R.drawable.o_4, PrintParamAty.class));
        data.add(new TabActionBean(-1, s[4], R.drawable.o_5, SignStyleParmSettingAty.class));
        return data;
    }
}
