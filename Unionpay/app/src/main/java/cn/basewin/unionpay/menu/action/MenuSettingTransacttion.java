package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.TradeManageAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 14:14<br>
 * 描述: 交易管理设置<br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SET_TRANSACTION)
public class MenuSettingTransacttion extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_SET_TRANSACTION));
    }

    @Override
    public int getResIcon() {
        return R.drawable.jf_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(new Intent(getContext(), TradeManageAty.class));
            }
        };
    }
}
