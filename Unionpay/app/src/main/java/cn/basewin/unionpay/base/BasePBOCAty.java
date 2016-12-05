package cn.basewin.unionpay.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import com.basewin.define.CardType;
import com.basewin.define.InputPBOCInitData;
import com.basewin.define.OutputCardInfoData;
import com.basewin.define.OutputMagCardInfo;
import com.basewin.define.OutputPBOCAAData;
import com.basewin.define.OutputQPBOCResult;
import com.basewin.define.PBOCErrorCode;
import com.basewin.define.PBOCOption;
import com.basewin.define.PBOCTransactionResult;
import com.basewin.services.ServiceManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 16:20<br>
 * 描述: 这是一个刷卡的activity  但是没设置界面 子类实现界面<br>
 */
public abstract class BasePBOCAty extends BaseFlowAty {
    private static final String TAG = BasePBOCAty.class.getName();
    /**
     * 是否允许全部的 刷卡方式   非接改造
     */
    protected boolean isRfFirst = false;
    //刷卡的3个类型：InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD
    /**
     * 以下3个数据都是 开启pboc必备的数据。
     */
    protected int type_pay = PBOCOption.ONLINE_PAY;//开启pboc 的类型 如果不是这个需要改变
    /**
     * 默认money
     */
    private int _money = 0;
    /**
     * 默认刷卡的类型
     */
    protected int type_card = InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD;
    /**
     * 是否为电子现金类交易
     */
    private boolean EC_flag = false;
    /**
     * 作用与 寻卡错误 重启后set为false 寻卡设置为true
     */
    private boolean isFindCard = false;
    /**
     *
     */
    /**
     * 刷卡结果数据
     */
    public Card mCard = new Card();

    public boolean getRfFirst() {
        return isRfFirst;
    }

    public void setRfFirst(boolean rfFirst) {
        isRfFirst = rfFirst;
    }

    public void setType_pay(int pay_type) {
        this.type_pay = pay_type;
    }

    public void setECFlag(boolean ecFlag) {
        this.EC_flag = ecFlag;
    }

    public boolean getECFlag() {
        return EC_flag;
    }

    public void set_money(int _money) {
        this._money = _money;
    }

    public void setType_card(int type_card) {
        this.type_card = type_card;
    }

    public int getType_pay() {
        return type_pay;
    }

    public int get_money() {
        return _money;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTimerFlag(false);
        TLog.l("PBOC onCreate");
        LedUtil.startFree();
    }

    @Override
    protected boolean getIsLoadPBOC() {
        return false;
    }

    public int getType_card() {
        final int ic1 = InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD;
        final int ic2 = InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_IC_CARD;
        final int ic3 = InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD;
        if (getRfFirst()) {
            switch (type_card) {
                case ic1:
                    TLog.l("ic1" + ic1);
                    return InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD;
                case ic2:
                    TLog.l("ic2" + ic2);
                    return InputPBOCInitData.USE_MAG_CARD;
                case ic3:
                    TLog.l("ic3" + ic3);
                    return InputPBOCInitData.USE_RF_CARD;
            }
            return type_card;
        }
        return type_card;
    }

    /**
     * 正常刷卡手机数据结束后都会去执行这个函数  子类如果想拿到卡的数据 实现这个函数即合
     *
     * @param _card
     */
    protected abstract void swipingCardEnd(Card _card);

    protected void startPBOCEnd() {
    }

    //默认是显示toast 如果想对这个显示的ui做操作 那么就重载他
    protected void setShowError(String msg) {
        TLog.showToast(msg);
    }


    //刷卡后 得到了 所有的数据
    public void startPBOC() {
        TLog.l("startPBOC");
        mCard = new Card();
        Intent intent = new Intent();
        intent.putExtra(InputPBOCInitData.AMOUNT_FLAG, _money);
        intent.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, getType_card());
        intent.putExtra(InputPBOCInitData.IS_SUPPERT_EC_FLAG, getECFlag());
        try {
            ServiceManager.getInstence().getPboc().startTransfer(getType_pay(), intent, this);
        } catch (Exception e) {
            unSolveError();
            e.printStackTrace();
        }
    }

    public void stopPBOC() {
        TLog.l("stopPBOC");
        try {
            ServiceManager.getInstence().getPboc().stopTransfer();
        } catch (Exception e) {
            TLog.l("stopPBOC error");
            unSolveError();
            e.printStackTrace();
        }
    }

    /**
     * 首次开启请执行
     *
     * @param cardtype 支持刷卡的类型
     */
    public void startPBOC(int cardtype) {
        TLog.l("开启PBOC：" + cardtype);
        setType_card(cardtype);
        startPBOCEnd();
        startPBOC();
    }

    /**
     * 重新开启请执行
     */
    protected void reStartPBOC() {
        TLog.l("reStartPBOC");
        startPBOCEnd();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TLog.l("2000 reStartPBOC");
                startPBOC();
            }
        }, 2000);
    }

    //这个是无法解决的问题 目前是关闭处理 如果不想这样处理 就重载
    protected void unSolveError() {
        finish();
    }

    @Override
    public void onError(Intent intent) throws RemoteException {
        if (isFindCard) {
            //是寻卡后发生的错误
            setShowError(getString(R.string.shuakashibaiqingchongshua));
            isFindCard = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reStartPBOC();
        } else {
            int code = intent.getIntExtra("code", 65282);
            String errorMsg = PBOCErrorCode.getErrorMsgByCode(code);
            setShowError(errorMsg);

            if (this != null && !this.isFinishing()) {
                this.finish();
            }
        }
    }

    @Override
    public void onFindingCard(int i, Intent intent) throws RemoteException {
        LedUtil.startActivation();
        isFindCard = true;
        switch (i) {
            case CardType.MAG_CARD:
                Log.d(TAG, "磁条卡");
                OutputMagCardInfo magCardInfo = new OutputMagCardInfo(intent);
                Log.d(TAG, "卡号: " + magCardInfo.getPAN());
                Log.d(TAG, "二磁道数据: " + magCardInfo.getTrack2HexString());
                Log.d(TAG, "三磁道数据: " + magCardInfo.getTrack3HexString());
                Log.d(TAG, "有效期: " + magCardInfo.getExpiredDate());
                Log.d(TAG,
                        "ServiceCode: " + magCardInfo.getServiceCode());
                String ServiceCode = "";
                if (magCardInfo.getServiceCode() != null) {
                    ServiceCode = magCardInfo.getServiceCode().substring(0, 1);
                }
                if (ServiceCode.equals("2") || ServiceCode.equals("6")) {
                    // ic卡
                    setShowError(getString(R.string.cikaweiickadukashibai));
                    reStartPBOC();
                } else {
                    mCard.type = CardType.MAG_CARD;
//                    mCard.type = 2;
                    mCard.magCardInfo = magCardInfo;
                    // 输入密码
                    String pan = mCard.magCardInfo.getPAN();
                    if (pan != null) {
                        swipingCardEnd(mCard);
                    } else {
                        setShowError(getString(R.string.shuakashibaiqingchongshua));
                        reStartPBOC();
                    }
                }
                break;
            case CardType.IC_CARD:
                mCard.type = CardType.IC_CARD;
//                mCard.type = 5;
                Log.d(TAG, "IC卡");
                break;
            case CardType.RF_CARD:
                mCard.type = CardType.RF_CARD;
//                mCard.type = 7;
                Log.d(TAG, "非接卡");
                break;
            default:
                unSolveError();
                break;
        }
    }


    @Override
    public void onSelectApplication(List<String> list) throws RemoteException {
        try {
            ServiceManager.getInstence().getPboc().selectApplication(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConfirmCertInfo(String s, String s1) throws RemoteException {

    }

    @Override
    public void onConfirmCardInfo(Intent intent) throws RemoteException {
        OutputCardInfoData out = new OutputCardInfoData(intent);
        mCard.icCardInfo = out;
        try {
            ServiceManager.getInstence().getPboc().confirmCardInfo();
        } catch (Exception e) {
//            onError(new Intent());
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestInputPIN(boolean b, int i) throws RemoteException {
        swipingCardEnd(mCard);
    }

    @Override
    public void onTransactionResult(int i, Intent intent) throws RemoteException {
        if (i == PBOCTransactionResult.QPBOC_ARQC) {
            mCard.qicCard = new OutputQPBOCResult(intent);
            swipingCardEnd(mCard);
        } else if (i == PBOCTransactionResult.APPROVED) {
            Log.d(TAG, "IC卡交易成功");
            if (CardType.IC_CARD == mCard.type) {
                mCard.icCardInfo = new OutputCardInfoData(intent);
                mCard.icAAData = new OutputPBOCAAData(intent);
            } else if (CardType.RF_CARD == mCard.type) {
                mCard.qicCard = new OutputQPBOCResult(intent);
            }
            getCardInfo(mCard);
            swipingCardEnd(mCard);
        } else if (i == PBOCTransactionResult.TERMINATED) {
            // IC卡交易拒绝，如果联机成功了需要冲正
            Log.d(TAG, "IC卡交易拒绝,如果联机成功了需要冲正");
            //这个地方需要重点搞下
            icError();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stopPBOC();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getCardInfo(Card _card) {
        byte[] DataOut = null;
        byte[] buffer = new byte[300];
        byte[] IAD;
        int offset = 0;

        //打包55域数据
        try {
            IAD = ServiceManager.getInstence().getPboc().getEmvTlvData(40720);
            if (IAD != null) {
                offset = PosUtil.TLVAppend((short) 40720, IAD, 0, buffer, offset, IAD.length);
            }

            byte[] CID = ServiceManager.getInstence().getPboc().getEmvTlvData(40743);

            if (CID != null) {
                offset = PosUtil.TLVAppend((short) 40743, CID, 0, buffer, offset, 1);
            } else if (IAD != null) {
                CID = new byte[1];

                byte cid = (byte) ((IAD[4] & 0x30) >> 4);
                if (0 == cid) {
                    CID[0] = 0;
                } else if (1 == cid) {
                    CID[0] = 64;
                } else if (2 == cid) {
                    CID[0] = -128;
                } else if (3 == cid) {
                    CID[0] = 32;
                }
                offset = PosUtil.TLVAppend((short) 40743, CID, 0, buffer, offset, 1);
            }

            byte[] unpredNum = ServiceManager.getInstence().getPboc().getEmvTlvData(40759);
            if (unpredNum != null) {
                offset = PosUtil.TLVAppend((short) 40759, unpredNum, 0, buffer, offset, 4);
            }

            byte[] ATC = new byte[2];
            ATC = ServiceManager.getInstence().getPboc().getEmvTlvData(40758);
            if (ATC != null) {
                offset = PosUtil.TLVAppend((short) 40758, ATC, 0, buffer, offset, 2);
            }

            byte[] TVR = ServiceManager.getInstence().getPboc().getEmvTlvData(149);
            if (TVR != null) {
                offset = PosUtil.TLVAppend((short) 149, TVR, 0, buffer, offset, 5);
            }

            byte[] transDate = ServiceManager.getInstence().getPboc().getEmvTlvData(154);
            if (transDate != null) {
                offset = PosUtil.TLVAppend((short) 154, transDate, 0, buffer, offset, 3);
            }

            byte[] transType = ServiceManager.getInstence().getPboc().getEmvTlvData(156);
            if (transType != null) {
                offset = PosUtil.TLVAppend((short) 156, transType, 0, buffer, offset, 1);
            }

            byte[] transAmount = ServiceManager.getInstence().getPboc().getEmvTlvData(40706);
            if (transAmount == null) {
                transAmount = "000000".getBytes();
            } else {
                offset = PosUtil.TLVAppend((short) 40706, transAmount, 0, buffer, offset, 6);
            }

            byte[] currencyCode = ServiceManager.getInstence().getPboc().getEmvTlvData(24362);
            if (currencyCode != null) {
                offset = PosUtil.TLVAppend((short) 24362, currencyCode, 0, buffer, offset, 2);
            }

            byte[] appInter = ServiceManager.getInstence().getPboc().getEmvTlvData(130);
            if (appInter != null) {
                offset = PosUtil.TLVAppend((short) 130, appInter, 0, buffer, offset, 2);
            }

            byte[] terCounCode = ServiceManager.getInstence().getPboc().getEmvTlvData(40730);
            if (terCounCode != null) {
                offset = PosUtil.TLVAppend((short) 40730, terCounCode, 0, buffer, offset, 2);
            }

            byte[] amountOther = ServiceManager.getInstence().getPboc().getEmvTlvData(40707);
            if (amountOther == null)
                amountOther = new byte[6];
            offset = PosUtil.TLVAppend((short) 40707, amountOther, 0, buffer, offset, 6);

            byte[] terCapab = new byte[3];
            terCapab = ServiceManager.getInstence().getPboc().getEmvTlvData(40755);
            offset = PosUtil.TLVAppend((short) 40755, terCapab, 0, buffer, offset, 3);

            int action = FlowControl.MapHelper.getAction();

            if (ActionConstant.ACTION_EC_QUICKPASS == action) {
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40734);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40734, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(132);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 132, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40713);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40713, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40769);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40769, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40756);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40756, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40757);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40757, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40820);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40820, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40803);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40803, DataOut, 0, buffer, offset, DataOut.length);
                }
                byte[] authResCode = ServiceManager.getInstence().getPboc().getEmvTlvData(138);
                if (authResCode != null) {
                    offset = PosUtil.TLVAppend((short) 138, authResCode, 0, buffer, offset, 2);
                } else {
                    offset = PosUtil.TLVAppend((short) 138, "Y1".getBytes(), 0, buffer, offset, 2);
                }
            } else {
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40756);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40756, DataOut, 0, buffer, offset, DataOut.length);
                } else {
                    offset = PosUtil.TLVAppend((short) 40756, new byte[3], 0, buffer, offset, 3);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40757);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40757, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40734);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40734, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(132);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 132, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40713);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40713, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40769);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40769, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(145);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 145, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(113);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 113, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(114);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 114, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(57137);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 57137, DataOut, 0, buffer, offset, DataOut.length);
                }
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(40803);
                if (DataOut != null) {
                    offset = PosUtil.TLVAppend((short) 40803, DataOut, 0, buffer, offset, DataOut.length);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

        //获取卡片的主要数据。卡号、二磁道数据、有效期、卡片序列号
        if (CardType.RF_CARD == _card.type) {
            try {
                //主账号
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(90);
                if (DataOut != null) {
                    String date = BCDHelper.bcdToString(DataOut, 0, DataOut.length);
                    date = PosUtil.removeTailF(date);
                    _card.qicCard.setPAN(date);
                }

                //二磁道数据
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(87);
                if (DataOut != null) {
                    _card.qicCard.setTrack(BCDHelper.bcdToString(DataOut, 0, DataOut.length));
                }

                //卡片序列号
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(24372);
                if (DataOut != null) {
                    _card.qicCard.setCardSeqNum(BCDHelper.bcdToString(DataOut, 0, DataOut.length));
                }

                //卡片有效期
                byte[] date1 = ServiceManager.getInstence().getPboc().getEmvTlvData(24356);
                if (date1 != null) {
                    _card.qicCard.setExpiredDate(BCDHelper.bcdToString(date1, 0, date1.length));
                }

                byte[] appCryp = new byte[8];
                appCryp = ServiceManager.getInstence().getPboc().getEmvTlvData(40742);
                if (appCryp != null) {
                    _card.qicCard.setTCData(BCDHelper.bcdToString(appCryp, 0, appCryp.length));
                    offset = PosUtil.TLVAppend((short) 40742, appCryp, 0, buffer, offset, 8);
                }

                //设置55域数据
                _card.qicCard.set55Field(BCDHelper.bcdToString(buffer, 0, offset));
                Log.d(TAG, "EC field 55：" + BCDHelper.bcdToString(buffer, 0, offset));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (CardType.IC_CARD == _card.type) {
            try {
                //主账号
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(90);
                if (DataOut != null) {
                    String date = BCDHelper.bcdToString(DataOut, 0, DataOut.length);
                    date = PosUtil.removeTailF(date);
                    _card.icCardInfo.setPAN(date);
                }

                //二磁道数据
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(87);
                if (DataOut != null) {
                    _card.icCardInfo.setTrack(BCDHelper.bcdToString(DataOut, 0, DataOut.length));
                }

                //卡片序列号
                DataOut = ServiceManager.getInstence().getPboc().getEmvTlvData(24372);
                if (DataOut != null) {
                    _card.icAAData.setCardSeqNum(BCDHelper.bcdToString(DataOut, 0, DataOut.length));
                }

                //卡片有效期
                byte[] date1 = ServiceManager.getInstence().getPboc().getEmvTlvData(24356);
                if (date1 != null) {
                    _card.icCardInfo.setExpiredDate(BCDHelper.bcdToString(date1, 0, date1.length));
                }

                byte[] appCryp = new byte[8];
                appCryp = ServiceManager.getInstence().getPboc().getEmvTlvData(40742);
                if (appCryp != null) {
                    _card.icAAData.setTCData(BCDHelper.bcdToString(appCryp, 0, appCryp.length));
                    offset = PosUtil.TLVAppend((short) 40742, appCryp, 0, buffer, offset, 8);
                }

                //设置55域数据
                _card.icAAData.set55Field(BCDHelper.bcdToString(buffer, 0, offset));
                Log.d(TAG, "EC field 55：" + BCDHelper.bcdToString(buffer, 0, offset));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
