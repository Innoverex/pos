package cn.basewin.unionpay.network.remote;

import android.util.Log;

import com.basewin.commu.Commu;
import com.basewin.commu.define.CommuParams;
import com.basewin.commu.define.CommuType;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cn.basewin.unionpay.setting.CommuParmAty;
import cn.basewin.unionpay.setting.CommuParmWifiFragment;
import cn.basewin.unionpay.setting.CommuParmWireLessFragment;
import cn.basewin.unionpay.utils.LogUtil;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/20 09:58<br>
 * 描述: 设置网络连接 ip 和端口的工具 <br>
 */
public class NetTools {
    private static final String TAG = NetTools.class.getName();

    /**
     * 获取设置的IP地址，若没有设置成功则使用XML设置的默认IP
     */
    public static String getIp() {
        return CommuParmWireLessFragment.getSetHostIp();
    }

    /**
     * 获取设置的端口号
     *
     * @return
     */
    public static String getPort() {
        return CommuParmWireLessFragment.getSetHostPort();
    }

    public static void setIPandPort(String ip, String port) {
        // TODO: 2016/5/6 如果输入了ip和port则在此会有设置
        try {
            if (port == null || ip == null) {
                Log.e(TAG, "TradeProcess:未设置IP和端口号");
                return;
            }

            CommuParams params = new CommuParams();
            params.setIp(ip);
            params.setType(CommuType.SOCKET);
            params.setPort(Integer.valueOf(port));
            params.setTimeout(10);
//            params.setIfSSL(true);
//            params.setCer("cacert");
            Commu.getInstence().setCommuParams(params);
        } catch (Exception e) {
            Log.d(TAG, "setIPandPort: IP或Port数据有误，使用默认设置");
        }
    }

    /**
     * 修改默认的IP及端口
     */
    public static void init() {
        String commuType = CommuParmAty.getCommuType();
        String ip = "";
        String port = "";
        if ("无线".equals(commuType)) {
            ip = CommuParmWireLessFragment.getSetHostIp();
            port = CommuParmWireLessFragment.getSetHostPort();
        } else if ("WiFi".equals(commuType)) {
            ip = CommuParmWifiFragment.getHostIp();
            port = CommuParmWifiFragment.getHostPost();
        }
        LogUtil.d("通信方式:" + commuType + "|ip:" + ip + "|port:" + port);
        if (!"".equals(ip) && !"".equals(port)) {
            setIPandPort(ip, port);
        }
    }

    public static String getIP(String name) {
        InetAddress address = null;
        String s = "000.000.000.000";
        try {
            address = InetAddress.getByName(name);
            s = address.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("获取失败");
            TLog.l("获取失败");
        }
        TLog.l(s);
        return s;
    }
}
