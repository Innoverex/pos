package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.ui.VersionAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 版本 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_VERSION)
public class MenuVersion extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_VERSION));
    }

    @Override
    public int getResIcon() {
        return R.drawable.mng_version_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), VersionAty.class);
                getContext().startActivity(intent);
            }
        };
    }
}
