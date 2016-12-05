package cn.basewin.unionpay.trade;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lhc<br>
 * 创建时间：2016/8/26 10:31<br>
 * 描述：选择预授权方式
 */
public class ChooseAuthTypeAty extends BaseChooseAty {
    /**
     * 选择预授权方式
     */
    public static final String KEY_CHOOSE_PAY_TYPE = "ChooseAuthTypeAty_choose_auth_type";
    private String[] strs = {"预授权", "电话授权", "小额代授权"};

    @Override
    public List<String> getLists() {
        List<String> list = new ArrayList<>();
        list.add(strs[0]);
        list.add(strs[1]);
        list.add(strs[2]);
        return list;
    }

    @Override
    public void onItemClick(String str) {
        String authType = "";
        if ("预授权".equals(str)) {
            authType = "01";
        } else if ("电话授权".equals(str)) {
            authType = "02";
        } else if ("小额代授权".equals(str)) {
            authType = "03";
        }
        FlowControl.MapHelper.getMap().put(ChooseAuthTypeAty.KEY_CHOOSE_PAY_TYPE, authType);
        if (strs[0].equals(str)) {
            //预授权
            addBranchBeforeNextFlow(0);
        } else if (strs[1].equals(str)) {
            //电话授权
            addBranchBeforeNextFlow(1);
        }
        startNextFlow();
    }

    @Override
    public String getAtyTitle() {
        return "请选择授权方式";
    }
}
