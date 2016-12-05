package cn.basewin.unionpay.entity;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.define.CardType;
import com.basewin.define.OutputCardInfoData;
import com.basewin.define.OutputMagCardInfo;
import com.basewin.define.OutputPBOCAAData;
import com.basewin.define.OutputQPBOCResult;

import java.util.HashMap;
import java.util.Map;

import cn.basewin.unionpay.utils.TLog;


public class Card {
    private static String TAG = Card.class.getSimpleName();
    private Map<String, Object> map = new HashMap<>();
    /**
     * 00  未指明  01 手工  02 磁条  03 条形码 04  光学字符阅读 05  (插卡)集成电路卡 07（挥卡）  快速 PBOC 借/贷记 IC 卡读入（非接触式）
     */
    //
    public int type = 0;// 获取卡数据方式
    public OutputMagCardInfo magCardInfo;
    public OutputCardInfoData icCardInfo;
    public OutputPBOCAAData icAAData;
    public OutputQPBOCResult qicCard;
    private String pan = "";
    private String expDate = "";
    public byte[] password = null;
    private String money = "0";//金额 单位元

    private byte[] TC = null;

    public byte[] getTC() {
        return TC;
    }

    public void setTC(byte[] TC) {
        this.TC = TC;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 卡号
     *
     * @return
     */
    public String getPan() {
        String card = "";
        switch (type) {
            case CardType.MAG_CARD:
                card = magCardInfo != null ? magCardInfo.getPAN() : "";
                break;
            case CardType.RF_CARD:
                card = qicCard != null ? qicCard.getPAN() : "";
                break;
            case CardType.IC_CARD:
                card = icCardInfo != null ? icCardInfo.getPAN() : "";
                break;
        }
        if (TextUtils.isEmpty(card)) {
            card = pan;
        }
        return card;
    }

    public void print() {
        if (type == CardType.MAG_CARD) {
            Log.e(TAG, "track2: " + magCardInfo.getTrack2HexString());
            Log.e(TAG, "track3: " + magCardInfo.getTrack3HexString());

        } else if (type == CardType.IC_CARD) {
            Log.e(TAG, "track: " + icCardInfo.getTrack());
        } else if (type == CardType.RF_CARD) {
            Log.e(TAG, "track: " + qicCard.getTrack());
        } else {

        }

    }

    public String getTrack2() {
        return getTrack2(this);
    }

    public String getTrack2ToD() {
        return getTrack2(this).replace("=", "D");
    }

    public static String getTrack2(Card card) {
        if (card == null)
            return "";
        if (card.type == CardType.IC_CARD) {
            return card.icCardInfo.getTrack();
        } else if (card.type == CardType.MAG_CARD) {
            return card.magCardInfo.getTrack2HexString();
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.getTrack();
        } else {
            return "";
        }


    }

    public String getTrack3() {
        return getTrack3(this);
    }

    public String getTrack3ToD() {
        if (TextUtils.isEmpty(getTrack3())) {
            return "";
        }
        return getTrack3().replace("=", "D");
    }

    public static String getTrack3(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return "";
        } else if (card.type == CardType.MAG_CARD) {
            return card.magCardInfo.getTrack3HexString();
        } else if (card.type == CardType.RF_CARD) {
            return "";
        } else {
            return "";
        }
    }

    /**
     * 是否是ic类型的卡
     *
     * @return
     */
    public boolean isIC() {
        return this.type == CardType.IC_CARD || this.type == CardType.RF_CARD;
    }

    /**
     * 是否是ic 插卡
     *
     * @return
     */
    public boolean isICCard() {
        if (type == CardType.IC_CARD) {
            return true;
        }
        return false;
    }

    public static boolean isICCARD(Card card) {
        if (card.type == CardType.IC_CARD || card.type == CardType.RF_CARD) {
            return true;
        }

        return false;
    }

    public static boolean isICCARD(int type) {
        if (type == CardType.IC_CARD || type == CardType.RF_CARD) {
            return true;
        }
        return false;
    }

    public String get55() {
        if (this.type == CardType.IC_CARD) {
            return this.icAAData.get55Field();
        } else if (this.type == CardType.MAG_CARD) {
            return "";
        } else if (this.type == CardType.RF_CARD) {
            return this.qicCard.get55Field();
        } else {
            return "";
        }
    }

    public static String get55(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return card.icAAData.get55Field();
        } else if (card.type == CardType.MAG_CARD) {
            return "";
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.get55Field();
        } else {
            return "";
        }
    }

    public String get23() {
        if (this.type == CardType.IC_CARD) {
            Log.i(TAG, "icAAData=" + icAAData);
            return this.icAAData.getCardSeqNum();
        } else if (this.type == CardType.MAG_CARD) {
            return "";
        } else if (this.type == CardType.RF_CARD) {
            return this.qicCard.getCardSN();
        } else {
            return "";
        }
    }

    public static String get23(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return card.icAAData.getCardSeqNum();
        } else if (card.type == CardType.MAG_CARD) {
            return "";
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.getCardSN();
        } else {
            return "";
        }
    }

    public String getICData() {
        if (this.type == CardType.IC_CARD) {
            return this.icCardInfo.getICData();
        } else if (this.type == CardType.MAG_CARD) {
            return "";
        } else if (this.type == CardType.RF_CARD) {
            return this.qicCard.getICData();
        } else {
            return "";
        }
    }

    public static String getICData(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return card.icCardInfo.getICData();
        } else if (card.type == CardType.MAG_CARD) {
            return "";
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.getICData();
        } else {
            return "";
        }
    }

    public static String getMaskPan(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            if (card.icCardInfo == null)
                return "";
            else {
                return card.icCardInfo.getMaskedPAN();
            }

        } else if (card.type == CardType.MAG_CARD) {
            if (card.magCardInfo == null)
                return "";
            else {
                return card.magCardInfo.getMaskedPAN();
            }

        } else if (card.type == CardType.RF_CARD) {
            if (card.qicCard == null)
                return "";
            else {
                return card.qicCard.getMaskedPan();
            }
        } else {
            return "";
        }

    }

    public String getMaskPan() {
        return getMaskPan(this);
    }

    public String getExpDate() {
        String tmp = "";
        if (this.type == CardType.MAG_CARD) {
            tmp = magCardInfo != null ? magCardInfo.getExpiredDate() : "";
        } else if (this.type == CardType.IC_CARD) {
            tmp = icCardInfo != null ? icCardInfo.getExpiredDate() : "";
        } else if (this.type == CardType.RF_CARD) {
            tmp = qicCard != null ? qicCard.getExpiredDate() : "";
        }
        expDate = tmp;
        TLog.pos(TAG, "有效期(YYMM):" + expDate);
        return expDate;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}
