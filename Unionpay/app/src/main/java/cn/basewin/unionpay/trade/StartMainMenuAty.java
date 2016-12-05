package cn.basewin.unionpay.trade;

import android.os.Bundle;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.utils.UIHelper;

public class StartMainMenuAty extends BaseFlowAty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.menu(this, ActionConstant.MENU_MAIN);
        finish();
    }
}
