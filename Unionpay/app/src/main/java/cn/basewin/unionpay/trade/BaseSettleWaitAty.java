package cn.basewin.unionpay.trade;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.utils.TLog;

/**
 * Created by kxf on 2016/8/22.
 */
public abstract class BaseSettleWaitAty extends NetWaitAty {

    private static final String TAG = "BaseSettleWaitAty";

    public Activity activity = this;
    public List<TransactionData> currentUpTds = new ArrayList<>();//当前上传的交易

    public static final int msg = 3639;
    public static final int msg_s = msg + 1;//上传磁条卡交易
    public static final int msg_ic = msg + 2;//上传磁条卡交易
    public int index = 0;//上传到哪条
    public int currentNum;//当前上传明细的条数
    public int retryCount = 0;//重试次数
    public int retryCountMax;//最大重试次数
    public boolean isEnd;//是否上传完成

    public Handler handlerUp = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_s:
                    Log.i(TAG, "handleMessage msg_s");
                    netS();
                    break;
                case msg_ic:
                    netIC();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 开始网络请求
     */
    protected abstract void net();

    /**
     * 上送磁条卡
     */
    protected void netS() {
        TLog.l("网络请求 netS()");
    }

    protected void netIC() {
        TLog.l("网络请求 netS()");
    }

    protected String formatField48(TransactionData td) {
        String s = null;
        String str0 = "00000000000000000000";
        try {
            String pan = td.getPan();
            if (pan.length() < 20) {
                pan = str0.substring(0, 20 - pan.length()) + pan;
            }

            String cardType = td.getCardType();
            if (cardType.length() < 2) {
                cardType = str0.substring(0, 2 - cardType.length()) + cardType;
            }

            String trace = td.getTrace();
            if (trace.length() < 6) {
                trace = str0.substring(0, 6 - trace.length()) + trace;
            }

            String amt = td.getAmount();
            if (amt.length() < 12) {
                amt = str0.substring(0, 12 - amt.length()) + amt;
            }

            s = cardType + trace + pan + amt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    protected void clickLeft() {

    }

    public static String getAccountsState(int state) {
        String stateStr = "";
        switch (state) {
            case 0:
            case 1:
                stateStr = "平";
                break;
            case 2:
                stateStr = "不平";
                break;
            case 3:
                stateStr = "出错";
                break;
            default:
                break;
        }
        return stateStr;
    }
}