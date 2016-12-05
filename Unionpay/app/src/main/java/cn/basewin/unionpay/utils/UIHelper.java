package cn.basewin.unionpay.utils;

import android.content.Context;
import android.content.Intent;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.menu.ui.GridMenuAty;
import cn.basewin.unionpay.ui.SignInAty;

/**
 * 内容摘要: <br>
 * 创建时间:  2016/7/25 11:46<br>
 * 描述: 带参数的启动 劲量写这个类， 不带参数的启动 随意写<br>
 */
public class UIHelper {
    /**
     * 开启菜单界面
     *
     * @param context
     * @param action  actionConstent里面的action
     */
    public static void menu(Context context, int action) {
        Intent intent = new Intent(context, GridMenuAty.class);
        intent.putExtra(GridMenuAty.KEY_ACTION, action);
        if (action == ActionConstant.MENU_MAIN) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void SignInAty(Context context) {
        Intent intent = new Intent(context, SignInAty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
