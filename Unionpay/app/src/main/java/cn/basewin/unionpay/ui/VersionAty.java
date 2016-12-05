package cn.basewin.unionpay.ui;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.BuildConfig;
import cn.basewin.unionpay.base.KeyValueAty;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.utils.UIDataHelper;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/6 13:24<br>
 * 描述: 版本页面 <br>
 */
public class VersionAty extends KeyValueAty {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContent("版本");
        setBtnVisibility(View.GONE);
        String[] ss = new String[5];
        ss[0] = "V" + AppConfig.VERSION_NUM;
        ss[1] = SettingConstant.getMERCHANT_NO();
        ss[2] = SettingConstant.getTERMINAL_NO();
        ss[3] = BuildConfig.RELEASE_TIME;
        ss[4] = BuildConfig.VERSION_NAME;

        List<Map> maps = UIDataHelper.setListMap_Vaule(UIDataHelper.VersionAty(), ss);
        setAdapterData(maps);
    }
}
