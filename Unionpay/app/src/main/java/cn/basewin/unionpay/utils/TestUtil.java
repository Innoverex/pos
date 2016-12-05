package cn.basewin.unionpay.utils;

import android.util.Log;

import com.basewin.services.ServiceManager;

import cn.basewin.unionpay.setting.CommuParmOtherAyt;
import cn.basewin.unionpay.setting.CommuParmWifiFragment;
import cn.basewin.unionpay.setting.CommuParmWireLessFragment;
import cn.basewin.unionpay.setting.MerchantSetting;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/4 14:30<br>
 * 描述:  <br>
 */
public class TestUtil {
    public static void init() {
//        MerchantSetting.setTerminalNo("10000005");
//        MerchantSetting.setMerchantNo("123456789012345");
//        String ip = "116.25.161.39";
//        CommuParmWireLessFragment.setHostIp(ip);
//        CommuParmWifiFragment.setHostIp(ip);
//        String port = "6600";
//        CommuParmWireLessFragment.setHostPort(port);
//        CommuParmWifiFragment.setHostPort(port);
//        CommuParmOtherAyt.setTPDU("6000030000");
//
//        try {
//            boolean iRet = ServiceManager.getInstence().getPinpad().loadMainKey("31313131313131313232323232323232");
//            Log.d(TestUtil.class.getName(), "write main key result: " + iRet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        MerchantSetting.setTerminalNo("18075911");
//        MerchantSetting.setMerchantNo("309441983980002");
//        String ip = "61.145.199.90";
//        CommuParmWireLessFragment.setHostIp(ip);
//        CommuParmWifiFragment.setHostIp(ip);
//        String port = "4000";
//        CommuParmWireLessFragment.setHostPort(port);
//        CommuParmWifiFragment.setHostPort(port);
//        CommuParmOtherAyt.setTPDU("6000020000");
//
//        try {
//            boolean iRet = ServiceManager.getInstence().getPinpad().loadMainKey("b962cb6d0d766429bc070dc432865dea");
//            Log.d(TestUtil.class.getName(), "write main key result: " + iRet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        MerchantSetting.setTerminalNo("18075913");
//        MerchantSetting.setMerchantNo("309441983980002");
//        String ip = "61.145.199.90";
//        CommuParmWireLessFragment.setHostIp(ip);
//        CommuParmWifiFragment.setHostIp(ip);
//        String port = "4000";
//        CommuParmWireLessFragment.setHostPort(port);
//        CommuParmWifiFragment.setHostPort(port);
//        CommuParmOtherAyt.setTPDU("6000020000");
//
//        try {
//            boolean iRet = ServiceManager.getInstence().getPinpad().loadMainKey("16D910BA8FEF10549857C245D93E1570");
//            Log.d(TestUtil.class.getName(), "write main key result: " + iRet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        MerchantSetting.setTerminalNo("94100033");
        MerchantSetting.setMerchantNo("402077115200001");
        String ip = "219.143.240.216";
        CommuParmWireLessFragment.setHostIp(ip);
        CommuParmWifiFragment.setHostIp(ip);
        String port = "8580";
        CommuParmWireLessFragment.setHostPort(port);
        CommuParmWifiFragment.setHostPort(port);
        CommuParmOtherAyt.setTPDU("6000180001");

        try {
            boolean iRet = ServiceManager.getInstence().getPinpad().loadMainKey("00000000000000000000000000000000");
            Log.d(TestUtil.class.getName(), "write main key result: " + iRet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
