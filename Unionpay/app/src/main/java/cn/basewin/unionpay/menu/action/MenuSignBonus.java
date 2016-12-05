package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 收银员积分签到 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_SIGN_BONUS)
public class MenuSignBonus extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_SIGN_BONUS));
    }

    @Override
    public int getResIcon() {
        return R.drawable.mng_query_trans_state;
    }

}
