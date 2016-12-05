package cn.basewin.unionpay.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.R;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/26 16:37<br>
 * 描述:  <br>
 */
public class UIDataHelper {
    public static List<Map> arrayToListMap(String[] s) {
        List<Map> maps = new ArrayList<>();
        for (String str : s) {
            HashMap<String, String> map = new HashMap<>();
            map.put("key", str);
            maps.add(map);
        }
        return maps;
    }

    public static List<Map> setListMap_Vaule(String[] k, String[] v) {
        List<Map> maps = new ArrayList<>();
        for (int i = 0; i < k.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("key", k[i]);
            map.put("value", v[i]);
            maps.add(map);
        }
        return maps;
    }

    //确定信息
    public static String[] CheckInfoAty() {
        return AppContext.getInstance().getResources().getStringArray(R.array.CheckInfoAty);
    }

    //版本信息
    public static String[] VersionAty() {
        return AppContext.getInstance().getResources().getStringArray(R.array.VersionAty);
    }

    public static String[] detail() {
        return AppContext.getInstance().getResources().getStringArray(R.array.detail);
    }
}
