package cn.basewin.unionpay.ui;

import android.os.Bundle;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.base.KeyValueAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.IDUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.UIDataHelper;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/9 16:27<br>
 * 描述: 余额显示 <br>
 */
public class ShowBalanceAty extends KeyValueAty {
    public static final String KEY_BALANCE = "ShowBalanceAty_KEY_BALANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDelay_time(5);//余额最多显示5s
        setBtnVisibility(true, false);
        setTitleContent(TLog.getString(ActionConstant.getAction(FlowControl.MapHelper.getAction())));
        String[] key = new String[1];
        String[] value = new String[1];
        key[0] = IDUtil.isEC(FlowControl.MapHelper.getAction()) ? "电子现金余额" : "可用余额";
        String balance = FlowControl.MapHelper.getBalance();
        value[0] = balance == null ? "" : balance;
        List<Map> maps = UIDataHelper.setListMap_Vaule(key, value);
        setAdapterData(maps);
    }

    @Override
    protected void onClickOK() {
        super.onClickOK();
        this.startNextFlow();
    }

    @Override
    protected void onClickUnOK() {
        super.onClickUnOK();
        finish();
    }
}
