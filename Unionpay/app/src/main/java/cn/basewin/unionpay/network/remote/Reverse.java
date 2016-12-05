package cn.basewin.unionpay.network.remote;

import android.os.RemoteException;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.setting.SystemParSettingAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 19:27<br>
 * 描述: 冲正公用类 <br>
 * 使用：
 * 1.请在 设置报文的时候调用 访问网络之前调用 save()
 * 2.在网络返回之后调用 updataBy39()
 * 3.冲正执行请调用 reverseExcute（listen） 内部已经自动设置了 冲正次数
 */
public class Reverse {
    private static final String TAG = Reverse.class.getName();
    //sp key 值
    /**
     * 冲正的数据 key
     */
    public static String KEY_REVERSE_DATA = "KEY_REVERSE_DATA";
    /**
     * 冲正的交易类型 key
     */
    public static String KEY_REVERSE_ACTION = "KEY_REVERSE_ACTION";
    /**
     * 冲正标志位 key
     */
    public static String KEY_REVERSE_FLAG = "KEY_REVERSE_FLAG";

    /**
     * 冲正执行游标
     */
    private static int cursor = 1;
    /**
     * 要冲正的次数
     */
    private static int number = 0;

    /**
     * 通过 文件拿到冲正的报文String 转换成 byte
     *
     * @return
     */
    public static byte[] getMessage() {
        byte[] bytes = null;
        try {
            bytes = BCDHelper.stringToBcd(getReverseData());
        } catch (Exception e) {

        }
        return bytes;
    }

    public static Iso8583Manager getOldReverseIso() throws Exception {
        byte[] message = getMessage();
        Log.e(TAG, "message=" + BCDHelper.bcdToString(message));
        Iso8583Manager iso = new Iso8583Manager(AppContext.getInstance().getApplicationContext());
        iso.unpack(message);
        return iso;
    }

    /**
     * 联机调用的报文
     * 1.取出不带mac的byte转换成iso
     * 2.计算mac
     * 3.设置64域
     * 4.打包返回
     *
     * @return
     * @throws Exception
     */
    public static byte[] getReverseMessage() throws Exception {
        Iso8583Manager iso = getOldReverseIso();
        iso.deleteBit(26);
        iso.deleteBit(35);
        iso.deleteBit(36);
        iso.deleteBit(37);
        iso.deleteBit(52);
        iso.deleteBit(53);
        iso.setBit("msgid", "0400");
        iso.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        iso.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return iso.pack();
    }

    /**
     * 校验mac
     *
     * @param iso 服务器返回的数据
     * @return
     */
    public static boolean checkMAC(Iso8583Manager iso) {
        byte[] macInput = new byte[0];
        try {
            macInput = iso.getMacData("msgid", "63");
        } catch (Exception e) {
            TLog.e(TAG, "checkMAC 函数" + "取出msgid 异常");
            e.printStackTrace();
        }
        String calcMAC = null;
        try {
            calcMAC = TradeEncUtil.getMacECB(macInput);
        } catch (RemoteException e) {
            TLog.e(TAG, "checkMAC 函数" + "取出getMacECB 异常");
            e.printStackTrace();
        }
        String oldMAC = BCDHelper.bcdToString(iso.getBitBytes(64));
        return (oldMAC.equals(calcMAC));
    }


    /**
     * 第一次保存 冲正数据到 文件中
     * 1.设置39域为06
     * 2.数据保存在sp中
     * 3.打包 转换成字符串保存
     *
     * @param _iso 上送前的数据
     */
    public static void save(Iso8583Manager _iso) {
        try {
            Iso8583Manager iso = new Iso8583Manager(AppContext.getInstance().getApplicationContext());
            byte[] pack = new byte[500];
            pack = _iso.pack();
            iso.unpack(pack);
            iso.setBit(39, "98");
            pack = iso.pack();
            String s = BCDHelper.bcdToString(pack);
            setReverseData(s);
            setReverseFlag(true);
        } catch (Exception e) {
            TLog.e(TAG, "save 函数" + "Iso8583Manager 异常");
            e.printStackTrace();
            return;
        }

    }

    /**
     * 通过39域改变冲正数据
     *
     * @param _39 39域数据
     */
    public static void updataBy39(String _39) {
        byte[] bytes = getMessage();
        Iso8583Manager iso8583Manager = new Iso8583Manager(AppContext.getInstance().getApplicationContext());
        try {
            iso8583Manager.unpack(bytes);
            iso8583Manager.setBit(39, _39);
            //重新打包并且保存
            byte[] pack = iso8583Manager.pack();
            String s = BCDHelper.bcdToString(pack);
            setReverseData(s);
            setReverseFlag(true);
        } catch (Exception e) {
            TLog.e(TAG, "updataBy39 函数" + "Iso8583Manager 异常");
            e.printStackTrace();
        }
    }

    /**
     * 冲正网络请求 自动判断次数   当冲正次数达到了给定次数后才执行错误回调
     *
     * @param listener
     */
    public static void reverseCommu(final NetResponseListener listener) {
        Log.e(TAG, "reverseCommu...");
        byte[] reverseMessage = null;
        try {
            reverseMessage = getReverseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NetClient.commu(reverseMessage, new NetResponseListener() {
            @Override
            public void onSuccess(final Iso8583Manager data) {
                setReverseFlag(false);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onSuccess(data);
                        }
                    }
                }, 1000);
            }

            @Override
            public void onFailure(final int code, final String s) {
                cursor++;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            TLog.e(TAG, "冲正失败，开始新的交易！");
                            listener.onFailure(code, s);
                        }
                    }
                }, 1000);
                if (cursor <= number) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            TLog.l("冲正执行" + number + "次  当前执行到" + (cursor) + "次");
                            reverseCommu(listener);
                        }
                    }, 1000);
                } else {
                    setReverseFlag(false);
                }
            }
        });
    }

    /**
     * 执行冲正的网络请求
     *
     * @param num      执行冲正的次数
     * @param listener 网络监听
     */
    public static void reverseExcute(int num, final NetResponseListener listener) {
        Log.e(TAG, "reverseExcute...");
        cursor = 1;
        number = num;
        reverseCommu(listener);
    }

    /**
     * 执行冲正的网络请求 冲正次数读取的 设置数据
     * 开发人员请 执行这个
     *
     * @param listener
     */
    public static void reverseExcute(final NetResponseListener listener) {
        reverseExcute(Integer.parseInt(SystemParSettingAty.getReverseTimes()), listener);
    }

    /**
     * 设置冲正的数据 保存到文件中
     *
     * @param msg
     */
    public static void setReverseData(String msg) {
        SPTools.set(KEY_REVERSE_DATA, msg);
        setReverseAction(FlowControl.MapHelper.getAction());
    }

    public static String getReverseData() {
        return SPTools.get(KEY_REVERSE_DATA, "");
    }

    /**
     * 设置冲正的交易类型 保存到文件中
     *
     * @param action
     */
    public static void setReverseAction(int action) {
        SPTools.set(KEY_REVERSE_ACTION, action);
    }

    public static int getReverseAction() {
        return SPTools.get(KEY_REVERSE_ACTION, 0);
    }

    /**
     * 设置冲正标志位 保存到文件中
     *
     * @param msg
     */
    public static void setReverseFlag(boolean msg) {
        SPTools.set(KEY_REVERSE_FLAG, msg);
    }

    /**
     * 判断是否有冲正数据
     *
     * @return
     */
    public static boolean getReverseFlag() {
        return SPTools.get(KEY_REVERSE_FLAG, false);
    }

    /**
     * 校验报文数据
     * 在数据返回时校验服务器返回数据 是否发错  是否被窜包
     *
     * @param oldiso 发送前的原始打包数据
     * @param iso    服务器返回数据
     * @return
     */
    public static boolean checkMsg(Iso8583Manager oldiso, Iso8583Manager iso) {
        //TODO
//        if (!oldiso.getBit(11).equals(iso.getBit(11))) {
//            return false;
//        }
        if (!oldiso.getBit(41).equals(iso.getBit(41))) {
            return false;
        }
        if (!oldiso.getBit(42).equals(iso.getBit(42))) {
            return false;
        }
        return true;
    }

    public static int getCursor() {
        return cursor;
    }

    public static int getNumber() {
        return number;
    }
}
