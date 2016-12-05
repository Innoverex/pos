package cn.basewin.unionpay.trade;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/15 14:04<br>
 * 描述：分期付款支付方式选择
 */
public class ChoosePayTypeAty extends BaseChooseAty {
    public static final String KEY_CHOOSE_PAY_TYPE = "ChoosePayTypeAty_choose_pay_type";

    @Override
    public List<String> getLists() {
        List<String> list = new ArrayList<>();
        list.add("一次性支付");
        list.add("分期支付");
        return list;
    }

    @Override
    public void onItemClick(String str) {
        FlowControl.MapHelper.getMap().put(ChoosePayTypeAty.KEY_CHOOSE_PAY_TYPE, str);
        startNextFlow();
    }

    @Override
    public String getAtyTitle() {
        return "请选手续费支付方式";
    }
}
