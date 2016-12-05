package cn.basewin.unionpay.trade;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.basewin.define.InputPBOCInitData;
import com.basewin.define.OutputECBalance;
import com.basewin.define.OutputOfflineRecord;

import java.util.ArrayList;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.utils.IDUtil;
import cn.basewin.unionpay.utils.PosUtil;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/13 13:49<br>
 * 描述:因为刷卡界面可能不同，键盘也可能不一样，所以这个类的功能为整合 刷卡和键盘 <br>
 * 实现具体的键盘和确定具体的刷卡ui界面，以及获取数据后对这写键盘或者ui 进行操作
 * --下一级子类 实现获取数据后 的流程操作
 */
public class SwipingSecondCardAty extends SwipingCardUI1Aty {
    private static final String TAG = SwipingSecondCardAty.class.getName();
    /**
     * 存储刷卡类型的Key
     */
    public static final String KEY_INPUT_TYPE = "SwipingSecondCardAty_input_initdata";
    /**
     * 存储Card对象的Key
     */
    public static final String KEY_CARD = "SwipingSecondCardAty_key_card";
    /**
     * 存储联机类型。是联机交易还是电子现金
     */
    public static final String KEY_TRANSACTION = "SwipingSecondCardAty_Transaction_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int intExtra = getIntent().getIntExtra(AppConfig.KEY_MONEY, 1);
        set_money(intExtra);
        setSwipeCardTips(getString(R.string.swipe_transfer_in_card));
        new Thread() {
            @Override
            public void run() {
                //设置是否为电子现金标志
                setECFlag(IDUtil.isEC(FlowControl.MapHelper.getAction()));
                //设置非接改造标志
                setRfFirst(IDUtil.isRfFirst(FlowControl.MapHelper.getAction()));
                //设置交易类型。联机或脱机的各种类型（查询电子现金余额、查询电子现金最近10笔明细）
                int transactionType = FlowControl.MapHelper.getTransactionType();
                if (transactionType != -1) {
                    setType_pay(transactionType);
                }
                int type = FlowControl.MapHelper.getSwipingType();
                if (type == -1) {
                    type = InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD;
                }
                startPBOC(type);
            }
        }.start();
    }

    @Override
    protected void swipingCardEnd(Card _card) {
        Log.d(TAG, "卡号[2]：" + _card.getPan());
        FlowControl.MapHelper.setSecondCard(_card);
        startNextFlow();
    }

//    @Override
//    protected void swipingCardEndOAA(Card _card) {
//        Log.d(TAG, "卡信息[2]:" + _card.toString());
//        FlowControl.MapHelper.setSecondCard(_card);
//    }

    @Override
    public void onReadECBalance(Intent intent) throws RemoteException {
        OutputECBalance ecBalance = new OutputECBalance(intent);
        FlowControl.MapHelper.setBalance(PosUtil.centToYuan(String.valueOf((ecBalance.getECBalance()))));
        startNextFlow();
    }

    @Override
    public void onReadCardOfflineRecord(Intent intent) throws RemoteException {
        OutputOfflineRecord list = new OutputOfflineRecord(intent);
        ArrayList<String> ECDetail = new ArrayList<>();
        String recordStr;
        for (int i = 0; i < 10; i++) {
            recordStr = list.getRecord(i);
            if (recordStr != null) {
                Log.d(TAG, "第" + (i + 1) + "条记录：\n" + recordStr);
                ECDetail.add(recordStr);
            }
        }
        FlowControl.MapHelper.ECDetail = ECDetail;
        startNextFlow();
    }
}
