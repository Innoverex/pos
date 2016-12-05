package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 跨行非约定转账
 */
@AnnotationMenu(action = ActionConstant.ACTION_AN_INTER_BANK_TRANSFER_NOT_AGREED)
public class MenuAnInterBankTransferNotAgreed extends MenuAction {
    @Override
    public String getResName() {
        return "跨行非约定转账";
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_other_state;
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