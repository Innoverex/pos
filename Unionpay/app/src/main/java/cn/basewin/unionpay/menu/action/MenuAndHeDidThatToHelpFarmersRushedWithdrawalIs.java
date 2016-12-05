package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;

/**
 * Created by kxf on 2016/9/29.
 * 他行助农取款冲正
 */
@AnnotationMenu(action = ActionConstant.ACTION_AND_HE_DID_THAT_TO_HELP_FARMERS_RUSHED_WITHDRAWAL_IS)
public class MenuAndHeDidThatToHelpFarmersRushedWithdrawalIs extends MenuAction {
    @Override
    public String getResName() {
        return "他行助农取款冲正";
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