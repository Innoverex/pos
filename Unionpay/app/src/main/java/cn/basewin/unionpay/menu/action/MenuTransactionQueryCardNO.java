package cn.basewin.unionpay.menu.action;

import android.content.Intent;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.ShowDetailByCardnoAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * Created by kxf on 2016/8/3.
 */
@AnnotationMenu(action = ActionConstant.ACTION_QUERY_TRANSACTION_CARDNO)
public class MenuTransactionQueryCardNO extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_QUERY_TRANSACTION_CARDNO));
    }

    @Override
    public int getResIcon() {
        return R.drawable.mng_query_trans_state_cardno;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), ShowDetailByCardnoAty.class);
                getContext().startActivity(intent);
            }
        };
    }
}