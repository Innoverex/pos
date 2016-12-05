package cn.basewin.unionpay.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import org.xutils.ex.DbException;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.entity.OperatorInfo;
import cn.basewin.unionpay.menu.action.MenuPosSignIn;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.BaseSignInAty;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIHelper;
import cn.basewin.unionpay.view.InputWidget;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/23 15:05<br>
 * 描述:  签到登录的界面<br>
 */
public class SignInAty extends BaseSignInAty {
    public static final String TAG = SignInAty.class.getName();
    public static final int ACTION_LOGIN = 0;//普通的登录 --就是操作员签到
    private int _action = ACTION_LOGIN;//意图
    private String operatorNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TDevice.getScreenHeight();
        TDevice.getScreenWidth();
        _action = getIntent().getIntExtra(AppConfig.KEY_REQUEST_CODE, ACTION_LOGIN);
        Log.d(TAG, "onCreate: _action="+_action);
        setInputType(InputWidget.editTypeNumber);
        switch (_action) {
            case ACTION_LOGIN:
                //如果是普通登录操作 判断操作员操作员 签过到没 如果签到 直接跳转 菜单 没签到 不做处理
                Log.d(TAG, "onCreate: 跳转到login界面");
                initLogin();
                break;
        }
    }

    private void initLogin() {
        if (MenuPosSignIn.judgeLogin()) {
            return;
        } else {
            //今天签到过或者SP OPERATOR_NO有数据，调到主页面
            UIHelper.menu(SignInAty.this, ActionConstant.MENU_MAIN);
            finish();
        }

    }

    /**
     * 判断今日是否签到
     * 1.签到过 跳转菜单
     * 2.签到后跳转菜单
     */
    public void judgeSign() {
        if (!MenuPosSignIn.judgeSign()) {
            //如果签到的时间和今天不一样 就会进行一次pos签到
            MenuPosSignIn menuPosSignIn = new MenuPosSignIn();
            menuPosSignIn.setContext(this);
            runOnUiThread(menuPosSignIn.getRun());
            finish();
        } else {
            //今天签到过
            UIHelper.menu(SignInAty.this, ActionConstant.MENU_MAIN);
            finish();
        }

    }

    /**
     * 通过类型拿到这个 login 状态的intent
     *
     * @param context
     * @param type
     * @return
     */
    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, SignInAty.class);
        switch (type) {
            case ACTION_LOGIN:
                break;
            case ActionConstant.ACTION_LOCK_TERMINAL:
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            default:
                type = ACTION_LOGIN;
                break;
        }
        intent.putExtra(AppConfig.KEY_REQUEST_CODE, type);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onClickCancel() {
//        cleanText();
//        String ip = NetTools.getIP("basewinkk.oicp.net");
//        TLog.l("111");
//        TLog.l(ip);
//        UIHelper.menu(this, ActionConstant.MENU_MAIN);
        finish();
    }


    @Override
    protected void onClickOK(String name, String pw) {

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pw)) {
            TLog.showToast("用户名或密码不能为空！");
            return;
        }
        boolean b = false;
        if (name.equals(AppConfig.operator_sys) && pw.equals(AppConfig.operator_sys_default_pw)) {
            //系统管理员界面
            UIHelper.menu(SignInAty.this, ActionConstant.ACTION_SETTING_GROUP);
            return;
        }
        try {
            b = OperatorInfo.dbCheck(name, pw);
        } catch (DbException e) {
            TLog.showToast("数据读取错误请重试！");
            e.printStackTrace();
            return;
        }
        if (!b) {
            TLog.showToast("用户名或密码错误！");
            return;
        }
        SettingConstant.setOPERATOR_NO(operatorNo);
        Log.d(TAG, "onClickOK: "+operatorNo);
        if (name.equals(AppConfig.operator_staff)) {//操作员管理界面
            Intent intent = new Intent(this, OperatorAty.class);
            startActivity(intent);
            return;
        } else {
            operatorNo = name;
            judgeSign();

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && _action == ActionConstant.ACTION_LOCK_TERMINAL) {
            TLog.showToast("已经锁定终端，请登录后在操作！");
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
