package cn.basewin.unionpay.setting;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/25 16:55<br>
 * 描述：密码管理
 */
public class PwdManageAty extends BaseListAty {
    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"系统管理密码", "安全密码"};

        Bundle sysbundle = new Bundle();
        sysbundle.putString(SysPwdMangeAty.EXTRA_TYPE, SysPwdMangeAty.KEY_SYS_PWD);
        Bundle safepwdbundle = new Bundle();
        safepwdbundle.putString(SysPwdMangeAty.EXTRA_TYPE, SysPwdMangeAty.KEY_SAVE_PWD);

        data.add(new TabActionBean(-1, s[0], R.drawable.pw_1, SysPwdMangeAty.class, sysbundle));
        data.add(new TabActionBean(-1, s[1], R.drawable.pw_2, SysPwdMangeAty.class, safepwdbundle));
        return data;
    }
}
