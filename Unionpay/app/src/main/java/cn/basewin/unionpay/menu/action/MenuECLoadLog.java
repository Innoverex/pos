package cn.basewin.unionpay.menu.action;

import android.util.Log;

import com.basewin.define.InputPBOCInitData;
import com.basewin.define.PBOCOption;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.ShowECDetailAty;
import cn.basewin.unionpay.trade.SwipingCardAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 圈存日志 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_EC_LOAD_LOG)
public class MenuECLoadLog extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_EC_LOAD_LOG));
    }

    @Override
    public int getResIcon() {
        return R.drawable.load_log_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "MenuECLoadLog");
                FlowControl flowControl = new FlowControl();
                FlowControl.MapHelper.setSwipingType(InputPBOCInitData.USE_IC_CARD | InputPBOCInitData.USE_RF_CARD);
                FlowControl.MapHelper.setTransactionType(PBOCOption.FUN_UPCASH_QUERY_DETAIL);
                flowControl.begin(SwipingCardAty.class)
                        .next(ShowECDetailAty.class)
                        .start(getContext(), ActionConstant.ACTION_EC_LOAD_LOG);
            }
        };
    }
}
