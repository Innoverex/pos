package cn.basewin.unionpay.ui;

import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.TabActionBean;
import cn.basewin.unionpay.setting.BaseListAty;
import cn.basewin.unionpay.setting.ManagerQueryAty;
import cn.basewin.unionpay.setting.MangagerPwdSettingAty;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/20 18:36<br>
 * 描述: 操作员管理界面<br>
 */
public class OperatorAty extends BaseListAty {

    @Override
    protected List<TabActionBean> getListData() {
        List<TabActionBean> data = new ArrayList<>();
        String[] s = {"签到", "签退", "改密码", "查柜员"};
        data.add(new TabActionBean(-1, s[0], R.drawable.selector_acquiring_bank0, SignInAty.class));
        data.add(new TabActionBean(-1, s[1], R.drawable.selector_acquiring_bank1, SignInAty.class));
        data.add(new TabActionBean(-1, s[2], R.drawable.selector_acquiring_bank1, MangagerPwdSettingAty.class));
        data.add(new TabActionBean(-1, s[3], R.drawable.selector_acquiring_bank1, ManagerQueryAty.class));
        return data;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
