package cn.basewin.unionpay.setting;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;

/**
 * kxf 密钥下载
 */
public class DownloadTMKActivity extends BaseListAty {


    private static final String TAG = "DownloadTMKActivity";

    @Override
    protected List<TabActionBean> getListData() {
        Log.i(TAG, "getListData");
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"IC卡导密钥", "设置主密钥索引", "设置主密钥值", "设置DES算法", "母POS导入密钥"};
        data.add(new TabActionBean(-1, s[0], R.drawable.k_1, DownloadTmkFromICActivity.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.k_2, SetTmkIndexActivity.class));
        data.add(new TabActionBean(-1, s[2], R.drawable.k_3, SetTmkValueActivity.class));
        data.add(new TabActionBean(-1, s[3], R.drawable.k_4, SetDesActivity.class));
        data.add(new TabActionBean(-1, s[4], R.drawable.k_5, DownloadTmkFromMposActivity.class));
        return data;
    }
}
