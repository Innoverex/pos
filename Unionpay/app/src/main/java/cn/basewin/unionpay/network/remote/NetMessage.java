package cn.basewin.unionpay.network.remote;

import android.content.Context;

import com.basewin.packet8583.factory.Iso8583Manager;

import java.util.Map;

import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/14 10:19<br>
 * 描述: 一个联网的数据类 <br>
 * 这个类依赖 NetClient 访问网络
 * 1.mac
 * mac(byte[] message);    mac校验方式
 * IsMAC();                是否进行mac校验
 * 2.组包
 * getBeforEncryption(Map<String, Object> map)                     组包之前进行的操作
 * getEncryption(Map<String, Object> map, Iso8583Manager iso)      组包的操作 组包的iso 就是组包之前函数 回的iso
 * 3.联机
 * beforeRequest(final Iso8583Manager iso)                         组装报文之后  联机之前的操作
 * afterRequest(Iso8583Manager iso, NetResponseListener lis);                  联机之后的操作
 * 4.流程
 * getEncryptionPack();                                            netCLient 通过这个方法进行及时打包操作
 * 5.其他
 * errorNet(int code, String msg);                                 网络错误通知
 */
public abstract class NetMessage {
    private static final String TAG = NetMessage.class.getName();
    protected Map<String, Object> data;
    protected Iso8583Manager msg8583Data;//打包报文 之后 发送报文之前被赋值，网络返回之后 会被拿去校验数据

    protected Context context() {
        return AppContext.getInstance().getApplicationContext();
    }

    /**
     * mac 校验 每次网络回调完后的校验
     * mac 方法必须 子类实现不推荐在父类改动
     *
     * @return
     */
    public abstract boolean checkMAC(Iso8583Manager isoMessage);

    /**
     * 是否校验mac
     *
     * @return
     */
    protected abstract boolean IsMAC();

    /**
     * 抽象方法  把传入的数据map 有子类实现处理 并且封装成一个 Iso8583Manager对象返回
     * 每次联机时都会去执行一次这个方法， 拿到8583数据，然后打包返回byte【】 netClient 发送出去
     *
     * @param map serParameter（Map） 的参数
     * @return 8583对象
     * @throws Exception
     */
    protected abstract Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception;

    protected abstract Iso8583Manager getBeforEncryption(Map<String, Object> map) throws Exception;

    /**
     * 联机的byte【】 数据 就是调用这个函数得到的。
     *
     * @return 通过参数拿到数据集进行打包返回
     * @throws Exception 打包异常 可能你的数据和 xml匹配规则不同就会出现这个异常
     */
    public byte[] getEncryptionPack() throws Exception {
        msg8583Data = getEncryption(data, getBeforEncryption(data));
        for (int q = 1; q < 65; q++) {
            String bit = msg8583Data.getBit(q);
            if (bit != null) {
                TLog.e(TAG, q + "域:" + bit);
            }
        }
        byte[] pack = msg8583Data.pack();
        beforeRequest(msg8583Data);
        return pack;
    }

    /**
     * 设置参数集
     *
     * @param map 收集到的参数
     */
    public void setParameter(Map<String, Object> map) {
        data = map;
    }

    /**
     * 得到传入的参数
     *
     * @return
     */
    public Map<String, Object> getParameter() {
        return this.data;
    }

    /**
     * 联机之后  ，您可能要对这次的联机返回做一个独特的操作比如 加载密钥 那么就可以在这里面去实现
     *
     * @param iso 8583解密后的数据包
     * @param lis 网络自定义回调
     * @return 这个返回值 如果您在这个独有的操作里面 出现了异常 即可以返回false 这样就可以中断 成功监听的回调 ，并且在这个函数里面去手动执行lis 的失败回调 不然 成功和失败监听都无法执行。
     */
    public abstract boolean afterRequest(Iso8583Manager iso, NetResponseListener lis);

    public boolean afterRequestByIC(Iso8583Manager iso, NetResponseListener lis) {
        return false;
    }

    /**
     * 联机之前的 会执行到这个函数。
     *
     * @param iso 注意： 这个 iso 是 打包后的iso 不能对这个iso进行数据处理 ，不然就改变了发送报文的内容 您只能取出数据。
     */
    protected void beforeRequest(final Iso8583Manager iso) {
    }

    /**
     * 联机错误
     *
     * @param code 错误码
     * @param msg  错误提示
     */
    public abstract void errorNet(int code, String msg);

    /**
     * 获取一个打包或者解包的实例对象
     *
     * @return
     */
    public Iso8583Manager get8583() {
        return new Iso8583Manager(context());
    }

    public void isNetSuccess(boolean b) {
    }
}
