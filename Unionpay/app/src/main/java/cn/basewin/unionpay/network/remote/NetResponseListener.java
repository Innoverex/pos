package cn.basewin.unionpay.network.remote;

import com.basewin.packet8583.factory.Iso8583Manager;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/11 12:18<br>
 * 描述: 网络监听 <br>
 */
public interface NetResponseListener {
    void onSuccess(Iso8583Manager data);

    void onFailure(int code, String s);
}
