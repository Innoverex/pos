package cn.basewin.unionpay.setting;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 16:55<br>
 * 描述：下载功能
 */
public class TestManageAty extends BaseListAty {
    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"回响测试", "参数传递", "IC卡公钥下载", "IC卡参数下载", "状态上传", "黑名单下载"};
        data.add(new TabActionBean(-1, s[0], R.drawable.d_1, SysEchoTest.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.d_2, SysParamsLoad.class));
        data.add(new TabActionBean(-1, s[2], R.drawable.d_3, SysDownloadICKey.class));
        data.add(new TabActionBean(-1, s[3], R.drawable.d_4, SysDownloadICParams.class));
        data.add(new TabActionBean(-1, s[4], R.drawable.d_5, SysPosStateUpload.class));
        data.add(new TabActionBean(-1, s[5], R.drawable.d_6, SysDownloadCardBin.class));
        return data;
    }
}
