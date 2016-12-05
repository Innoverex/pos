package cn.basewin.unionpay.trade;

import android.os.Bundle;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: hanlei <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 显示电子现金日志 <br>
 */

public class ShowECDetailAty extends BaseShowDetailAty {
    private static final String TAG = ShowECDetailAty.class.getName();
    private List<String> ecDetail = FlowControl.MapHelper.ECDetail;
    private int recordNum = ecDetail.size();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            for (int i = 0; i < recordNum; i++) {
                String[] l = {" "};
                String[] r = new String[5];
                setName("电子现金");
                r[0] = ecDetail.get(i);
                setData(l, r);
            }
            excute();
        } catch (Exception e) {
            finish();
            e.printStackTrace();
        }
        List<List<Map>> map = getMap();
        for (List<Map> m : map) {
            TLog.l(m.toString());
        }
    }

}
