package cn.basewin.unionpay.menu.action;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.StartMainMenuAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: pos签到 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SIGN_POS)
public class MenuPosSignIn extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_SIGN_POS));
    }

    @Override
    public int getResIcon() {
        return R.drawable.login_state;
    }

    @Override
    public Runnable getRun() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(NetWaitAty.class).next(StartMainMenuAty.class).start(getContext(), ActionConstant.ACTION_SIGN_POS);
            }
        };
        return r;
    }

    /**
     *
     * @return true 今天签过到  ；false 今天没签到
     */
    public static boolean judgeSign() {
        SimpleDateFormat time = new SimpleDateFormat("MMdd");
        TLog.l("存储的签到时间" + SettingConstant.getSignTime());
        TLog.l("今天时间" + time.format(new Date()));
        if (!SettingConstant.getSignTime().equals(time.format(new Date()))) {
            //如果签到的时间和今天不一样 就会进行一次pos签到
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否进行 登录操作
     * 1.操作员为null
     * 2.签到时间为null
     *
     * @return
     */
    public static boolean judgeLogin() {
        TLog.l("签到操作员" + SettingConstant.getOPERATOR_NO());
        if (TextUtils.isEmpty(SettingConstant.getOPERATOR_NO()) || !judgeSign()) {
//            签到或者SP内OPERATOR_NO有数据
            Log.d(TAG, "judgeLogin:登录过 或者没签到，跳转");
            return true;
        } else {
//      今天没签到，并且SP没有存OPERATOR_NO的数据
            return false;
        }
    }
}
