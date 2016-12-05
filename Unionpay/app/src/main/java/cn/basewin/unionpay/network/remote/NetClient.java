package cn.basewin.unionpay.network.remote;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.basewin.commu.Commu;
import com.basewin.commu.define.CommuListener;
import com.basewin.packet8583.factory.Iso8583Manager;

import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.ErrorConstant;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/14 11:03<br>
 * 描述:  <br>
 */
public class NetClient {
    private static final String TAG = NetClient.class.getName();

    private static Context context() {
        return AppContext.getInstance().getApplicationContext();
    }

    /**
     * 1.校验网络
     * 2.执行报文的组包方法
     * 3.联机
     * 4.解包
     * 5.把解包数据交给 报文类处理
     *
     * @param netMessage 报文基类
     * @param net        网络回调
     */
    public static void commu(final NetMessage netMessage, final NetResponseListener net) {
        if (!TDevice.isNetworkConnected()) {
            if (net != null) {
                net.onFailure(ErrorConstant.NET_NULL_NET, "交易失败，网络异常请检查您的网络！");
            }
            return;
        }
        byte[] encryption = null;
        try {
            encryption = netMessage.getEncryptionPack();
        } catch (Exception e) {
            e.printStackTrace();
            if (encryption == null) {
                if (net != null) {
                    net.onFailure(ErrorConstant.NET_PARAMETER, "交易失败，组包错误！");
                }
                return;
            }
        }

        Log.e(TAG, "Normal communication start...");

        Commu.getInstence().dataCommu(context(), encryption, new CommuListener() {
            @Override
            public void OnStatus(int i, final byte[] receive) {
                TLog.l("OnStatus网络返回：" + i);
                if (i == 2) {
                    netMessage.isNetSuccess(true);
                }
                if (i == 4 && receive != null) {
                    Handler handler = new Handler(AppContext.getInstance().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Iso8583Manager iso;
                            try {
                                Log.d(TAG, "receive:" + BCDHelper.bcdToString(receive));
                                iso = netMessage.get8583();
                                Log.e(TAG, "解包 receive[" + receive.length + "]=" + new String(receive));
                                iso.unpack(receive);
                            } catch (Exception e) {
                                netMessage.errorNet(ErrorConstant.NET_CHECK, "解包错误！");
                                OnError(ErrorConstant.NET_CHECK, "解包错误！");
                                e.printStackTrace();
                                return;
                            }
                            for (int q = 1; q < 100; q++) {
                                String bit = iso.getBit(q);
                                if (bit != null) {
                                    TLog.l(q + "域:" + bit);
                                }
                            }
                            boolean de = netMessage.afterRequest(iso, net);
                            if (net != null && de) {
                                net.onSuccess(iso);
                            } else {
                                TLog.l("返回数据自定义处理没通过。如果没反应 请手动设置监听");
                            }
                        }
                    });
                }
            }

            @Override
            public void OnError(int i, final String s) {
                Handler handler = new Handler(AppContext.getInstance().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TLog.l("OnError网络返回：" + s);
                        if (net != null) {
                            net.onFailure(0, s);
                        }
                    }
                });
            }
        });
    }

    /**
     * 冲正专用
     *
     * @param message
     * @param net
     */
    public static void commu(final byte[] message, final NetResponseListener net) {
        Log.e(TAG, "Reverse communication start...");
        Commu.getInstence().dataCommu(context(), message, new CommuListener() {
            @Override
            public void OnStatus(int i, byte[] bytes) {
                TLog.l("OnStatus网络返回：" + i);
                if (i == 4 && bytes != null) {
                    Iso8583Manager iso;
                    try {
                        iso = new Iso8583Manager(context());
                        iso.unpack(bytes);
                    } catch (Exception e) {
                        OnError(0, "解包错误！");
                        e.printStackTrace();
                        return;
                    }
                    for (int q = 1; q < 100; q++) {
                        String bit = iso.getBit(q);
                        if (bit != null) {
                            TLog.l(q + "域:" + bit);
                        }
                    }
                    if (!"00".equals(iso.getBit(39))) {
                        OnError(0, "冲正失败，39域不为00");
                        return;
                    }
                    if (!Reverse.checkMAC(iso)) {
                        OnError(0, "冲正mac校验错误");
                    } else {
                        net.onSuccess(iso);
                    }
                }
            }

            @Override
            public void OnError(int i, String s) {
                TLog.l("OnError网络返回：" + s);
                if (net != null) {
                    net.onFailure(0, s);
                }
            }
        });
    }
}
