package cn.basewin.unionpay.network.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.ErrorConstant;
import cn.basewin.unionpay.utils.AnnotationUtil;
import cn.basewin.unionpay.utils.TLog;
/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/20 10:51<br>
 * 描述: 网络合集 <br>
 */
public class NetHelper {
    private static final String SCANDIR = "cn.basewin.unionpay.net";

    /**
     * 联机交易报文
     *
     * @param action
     * @param map
     * @param netlistener
     */
    public static void distribution(int action, Map<String, Object> map, NetResponseListener netlistener) {
        NetMessage mes;
        mes = scanTradeTag(action, netlistener);

        if (mes != null) {
            mes.setParameter(map);
            NetClient.commu(mes, netlistener);
        } else {
            TLog.e("NetHelper", "没有找到意图！");
            errorNet(0, netlistener);
        }

    }

    /**
     * 脱机交易报文
     *
     * @param action
     * @param map
     * @param netlistener
     */
    public static void offlineDistribution(int action, Map<String, Object> map, NetResponseListener netlistener) {
        NetMessage mes;
        mes = scanTradeTag(action, netlistener);

        if (mes != null) {
            mes.setParameter(map);
            //脱机报文组包
            byte[] encryption = null;
            try {
                encryption = mes.getEncryptionPack();
                if (netlistener != null) {
                    netlistener.onSuccess(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (encryption == null) {
                    netlistener.onFailure(ErrorConstant.NET_PARAMETER, "交易失败，组包错误！");
                }
            }
        } else {
            errorNet(0, netlistener);
        }

    }

    private static void errorNet(int code, NetResponseListener netlistener) {
        TLog.l("没有意图与之匹配，请检测下这个包下是否有这类注解类！");
        if (netlistener != null) {
            netlistener.onFailure(code, "没有意图与之匹配！");
        }
    }

    public static List<Class<?>> getClazzs(String packagename, Class annotation, NetResponseListener netlistener) {
        ArrayList<Class<?>> net;
        try {
            net = AnnotationUtil.net(packagename, annotation);
        } catch (Exception e) {
            e.printStackTrace();
            errorNet(0, netlistener);
            return null;
        }

        if (net == null) {
            errorNet(0, netlistener);
            return null;
        }
        return net;
    }

    public static NetMessage scanTradeTag(int action, NetResponseListener netlistener) {
        List<Class<?>> net = getClazzs(SCANDIR, AnnotationNet.class, netlistener);
        NetMessage mes = null;
        for (Class<?> c : net) {
            AnnotationNet annotation = c.getAnnotation(AnnotationNet.class);
            if (annotation != null && annotation.action() == action) {
                try {
                    TLog.l("匹配到报文的id=" + annotation.action() + "    " + "名字：" + TLog.getString(ActionConstant.getAction(action)));
                    mes = (NetMessage) c.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorNet(0, netlistener);
                    return null;
                }
            }
        }
        return mes;
    }
}
