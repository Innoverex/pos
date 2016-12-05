package cn.basewin.unionpay.trade;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;

import org.xutils.ex.DbException;

import java.util.List;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.NetHelper;
import cn.basewin.unionpay.network.remote.NetResponseListener;

/**
 * Created by kxf on 2016/8/23.
 * 上传ic卡的tc值
 */
public class NetUploadTCWaitAty extends BaseSettleWaitAty {
    private static final String TAG = "NetUploadTCWaitAty";
    public List<TransactionData> tdsIC;//ic卡交易
    public static final String currentTransactionData = "currentTransactionData";

    @Override
    protected void net() {
        Log.i(TAG, "net()...");
        try {
            tdsIC = TransactionDataDao.selectAllValidIC();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (null == tdsIC || tdsIC.size() < 1) {
            startNextFlow();
            finish();
            return;
        }
        Log.i(TAG, "开始上送TC值");
        setHint(getString(R.string.upload_settle_tc_wait));
        start();
        handlerUp.sendEmptyMessage(msg_ic);
    }

    /**
     * 上送Tc
     */
    @Override
    protected void netIC() {
        super.netIC();
        FlowControl.MapHelper.getMap().put(currentTransactionData, tdsIC.get(index));
        currentNum = 1;
        retryCount += 1;
        NetHelper.distribution(ActionConstant.ACTION_BATCH_UP_IC_SETTLEMENT, FlowControl.MapHelper.getMap(), new NetResponseListener() {
            @Override
            public void onSuccess(Iso8583Manager data) {
                Log.e(TAG, "NetUploadTCWaitAty onSuccess");
                index += currentNum;
                retryCount = 0;
                if (index >= tdsIC.size()) {
                    startNextFlow();
                    finish();
                } else {
                    handlerUp.sendEmptyMessage(msg_ic);
                }
            }

            @Override
            public void onFailure(int code, String s) {
                if (retryCount < retryCountMax) {

                } else {
                    index += currentNum;
                }
                handlerUp.sendEmptyMessage(msg_ic);
            }
        });
    }
}