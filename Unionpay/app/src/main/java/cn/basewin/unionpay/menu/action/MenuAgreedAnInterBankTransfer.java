package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 跨行约定转账
 */
@AnnotationMenu(action = ActionConstant.ACTION_AGREED_AN_INTER_BANK_TRANSFER)
public class MenuAgreedAnInterBankTransfer extends MenuAction {
    @Override
    public String getResName() {
        return "跨行约定转账";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_purchase_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}