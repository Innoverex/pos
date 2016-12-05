package cn.basewin.unionpay.trade;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 09:52<br>
 * 描述: 国际银行卡组织  <br>
 */
public class ChooseCardOrganizationAty extends BaseChooseAty {
    /**
     * 选择预授权方式
     */
    public static final String KEY_DATA = "ChooseCardOrganizationAty_card_organization";
    private String[] strs = {"CUP", "VIS", "MCC", "MAE", "JCB", "DCC", "AMX"};

    @Override
    public List<String> getLists() {
        List<String> list = new ArrayList<>();
        list.add(strs[0]);
        list.add(strs[1]);
        list.add(strs[2]);
        list.add(strs[3]);
        list.add(strs[4]);
        list.add(strs[5]);
        list.add(strs[6]);
        return list;
    }

    @Override
    public void onItemClick(String str) {
        FlowControl.MapHelper.setCardOrganizationCode(str);
        startNextFlow();
    }

    @Override
    public String getAtyTitle() {
        return "请选择银行卡组织代码";
    }
}
