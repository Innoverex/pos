package cn.basewin.unionpay.setting;

import android.util.Log;

import com.basewin.interfaces.OnApduCmdListener;
import com.basewin.services.CardBinder;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BytesUtil;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;

import java.util.ArrayList;
import java.util.List;

import cn.basewin.unionpay.utils.ApduErrCode;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.TradeEncUtil;


public class IccPersonal {
    private static IccPersonal iccOp = new IccPersonal();
    private static String TAG = "IccPersonal";
    private static List<String> tmkList;
    private static String tMKStringFromCard = null;
    private static String syTMK;
    private static String mingwTMK;
    private static String jyTMK;
    private static String miwTMK;
    private static CardBinder card;
    private static PosByteArray resultPBA;
    private static String resultCode;
    private static int ret;

    static {
        try {
            card = ServiceManager.getInstence().getCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int removeCard() {
        return card.removeCard();
    }

    public static IccPersonal getInstance() {
        return iccOp;
    }

    public static int ResetCard() {
        return card.resetCard();
    }


    public static int loadTMK() {
        ret = ApduErrCode.SUCCESS;
        /*2）读有效期：
0x00, 0xA4, 0x00, 0x0C, 0x02, 0xEF, 0x08 选择文件
0x00, 0xB0, 0x00, 0x00, 0x08 此指令返回8节有效期YYYYMMDD*/
        String sCmd = "";
        byte pCmd[];
        byte pSw[] = new byte[2];
        sCmd = "00A4000C02EF08";
        pCmd = BCDHelper.StrToBCD(sCmd);
        resultCode = "";
        card.transmitApduToCard(pCmd, new OnApduCmdListener() {
            @Override
            public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                resultCode = BCDHelper.bcdToString(bytes);
                Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                resultPBA = posByteArray;
            }

            @Override
            public void onError() {
                ret = ApduErrCode.ERR_APDU;
            }
        });
        if (!"9000".equals(resultCode)) {
            return ApduErrCode.ERR_DATE_TIME;
        }

        sCmd = "00B0000008";
        pCmd = BCDHelper.StrToBCD(sCmd);
        resultCode = "";
        card.transmitApduToCard(pCmd, new OnApduCmdListener() {
            @Override
            public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                resultCode = BCDHelper.bcdToString(bytes);
                Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                resultPBA = posByteArray;
            }

            @Override
            public void onError() {
                ret = ApduErrCode.ERR_APDU;
            }
        });
        if (!"9000".equals(resultCode)) {
            return ApduErrCode.ERR_CHOOSE_FILE;
        }

		/*3）解密：
0x80, 0xF8, 0x01, 0x02, 0x10, _in, 0x10  (_in为16字节终端密钥密文，此指令返回16字节终端密钥明文)*/
        sCmd = "80F8010210";
        sCmd = sCmd + miwTMK;
        sCmd = sCmd + "10";
        pCmd = BCDHelper.StrToBCD(sCmd);
        resultCode = "";
        card.transmitApduToCard(pCmd, new OnApduCmdListener() {
            @Override
            public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                resultCode = BCDHelper.bcdToString(bytes);
                Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                resultPBA = posByteArray;
            }

            @Override
            public void onError() {
                ret = ApduErrCode.ERR_APDU;
            }
        });
        if (!"9000".equals(resultCode)) {
            return ApduErrCode.ERR_JIEMI;
        }
        Log.i(TAG, "解密成功");
        byte[] des = null;
        try {
            des = TradeEncUtil.TDesEnc(resultPBA.buffer, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        } catch (Exception e) {
            e.printStackTrace();
        }
        String desString = BytesUtil.bytes2HexString(des);
        Log.d(TAG, "desString 3DES加密结果[ " + desString.length() + "]:"
                + desString);
        Log.i(TAG, "jyTMK=" + jyTMK);
        Boolean b = jyTMK.equalsIgnoreCase(desString.substring(0, 8));
        if (b) {
            if (!TradeEncUtil.loadMainKey(BytesUtil.bytes2HexString(resultPBA.buffer))) {
                ret = ApduErrCode.ERR_LOAD_TMK;
            }
        } else {
            ret = ApduErrCode.ERR_JIAOYAN;
        }
        return ret;
    }

    public static int checkPin(String pin) {
        ret = ApduErrCode.SUCCESS;
        String sCmd = "";
        String strF = "FFFFFFFFFFFFFFFF";
        if (pin.length() < 16) {
            sCmd = pin + strF.substring(0, 16 - pin.length());
        }
        sCmd = "0020008108" + sCmd;
        Log.e(TAG, "sCmd:" + sCmd);
        byte pCmd[];
        pCmd = BCDHelper.StrToBCD(sCmd);
        int Count = 5;
        resultCode = "";
        while (Count > 0 && (!"9000".equals(resultCode))) {
            final int finalCount = Count;
            card.transmitApduToCard(pCmd, new OnApduCmdListener() {
                @Override
                public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                    resultCode = BCDHelper.bcdToString(bytes);
                    Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                    resultPBA = posByteArray;
                }

                @Override
                public void onError() {
                    if (finalCount <= 0) {
                        return;
                    }
                }
            });
            Count--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!"9000".equals(resultCode)) {
            ret = ApduErrCode.ERR_PASSWORD;
        }
        return ret;
    }

    public static int getTMKCount() {
        ret = ApduErrCode.SUCCESS;
        String sCmd = "";
        sCmd = "00A4000C02EF09";//2）选择存放密钥的二进制文件：
        //0x00, 0xA4, 0x00, 0x0C, 0x02, 0xEF, 0x09
        byte pCmd[];
        pCmd = BCDHelper.StrToBCD(sCmd);
        resultCode = "";
        card.transmitApduToCard(pCmd, new OnApduCmdListener() {
            @Override
            public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                resultCode = BCDHelper.bcdToString(bytes);
                Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                resultPBA = posByteArray;
            }

            @Override
            public void onError() {
                ret = ApduErrCode.ERR_APDU;
            }
        });
        if (!"9000".equals(resultCode)) {
            return ApduErrCode.ERR_CHOOSE_FILE;
        }
        sCmd = "00B0000003";//3）读出密钥条数：
        //0x00, 0xB0, 0x00, 0x00, 0x03
        //IC卡返回3字节，表示条数，如050为50条。
        pCmd = BCDHelper.StrToBCD(sCmd);
        resultCode = "";
        card.transmitApduToCard(pCmd, new OnApduCmdListener() {
            @Override
            public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                resultCode = BCDHelper.bcdToString(bytes);
                Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                resultPBA = posByteArray;
            }

            @Override
            public void onError() {
                ret = ApduErrCode.ERR_APDU;
            }
        });
        if (!"9000".equals(resultCode)) {
            return ApduErrCode.ERR_READ_COUNT;
        }
        String rs = BytesUtil.bytes2HexString(resultPBA.buffer);
        rs = new String(BCDHelper.StrToBCD(rs));
        Log.e(TAG, "rs:" + rs);
        try {
            ret = Integer.parseInt(rs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        getTMK(ret);
        return ret;
    }

    public static int selectTMK(String str) {
        String sy = IntString10TohexString(str);
        for (int i = 0; i < tmkList.size(); i++) {
            Log.i(TAG, "i=" + i);
            if (tmkList.get(i).substring(0, 8).equals(sy)) {
                Log.e(TAG, "找到密钥");
                tMKStringFromCard = tmkList.get(i);
                miwTMK = tMKStringFromCard.substring(8, 40);
                jyTMK = tMKStringFromCard.substring(40, 48);
                return ApduErrCode.SUCCESS;
            }
        }
        return ApduErrCode.ERR_SUOYING_BUCUNZAI;
    }

    public static String IntString10TohexString(String intString) {
        String str = "";
        String hstr = "";
        hstr = Integer.toHexString(Integer.parseInt(intString.substring(0, 3)));
        if (hstr.length() < 2) {
            hstr = "0" + hstr;
        }
        str = str + hstr;
        hstr = Integer.toHexString(Integer.parseInt(intString.substring(3, 5)));
        if (hstr.length() < 2) {
            hstr = "0" + hstr;
        }
        str = str + hstr;
        hstr = Integer.toHexString(Integer.parseInt(intString.substring(5, 8)));
        if (hstr.length() < 2) {
            hstr = "0" + hstr;
        }
        str = str + hstr;
        hstr = Integer.toHexString(Integer.parseInt(intString.substring(8, 11)));
        if (hstr.length() < 2) {
            hstr = "0" + hstr;
        }
        str = str + hstr;
        Log.i(TAG, "IntString10TohexString==" + str);
        return str;
    }

    public static int getTMK(int count) {
        tmkList = new ArrayList<String>();
        ret = count;
        String sCmd = "";/*4）根据此条数，在IC卡中查找前4字节为序号的密钥：每次读取24字节（0x18），
0x00, 0xB0, P1, P2, 0x18
P1,P2为偏移值，第一条时P1应为0x00,P2应为0x03。第二条时，P1，P2应分别为0x03+0x18的高底字节。以此类推。一直读到序号相同的24字节。*/
        String p1 = null;
        String p2 = null;
        String p;
        byte pCmd[];
        for (int i = 0; i < count; i++) {
            sCmd = "00B0";
            p = Integer.toHexString(24 * i + 3);
            if (p.length() < 4) {
                p = "0000".substring(0, 4 - p.length()) + p;
            }
            p1 = p.substring(0, 2);
            p2 = p.substring(2, 4);
            Log.e(TAG, "p1=" + p1 + "  ,p2=" + p2);
            sCmd = sCmd + p1 + p2 + "18";
            pCmd = BCDHelper.StrToBCD(sCmd);
            resultCode = "";
            card.transmitApduToCard(pCmd, new OnApduCmdListener() {
                @Override
                public void onSuccess(PosByteArray posByteArray, byte[] bytes) {
                    resultCode = BCDHelper.bcdToString(bytes);
                    Log.e(TAG, "posByteArray=" + posByteArray + "   bytes=" + resultCode);
                    resultPBA = posByteArray;
                }

                @Override
                public void onError() {
                    ret = ApduErrCode.ERR_APDU;
                }
            });
            if (!"9000".equals(resultCode)) {
                return ApduErrCode.ERR_READ_MY;
            }
            String rs = PosUtils.bytesToHexString(resultPBA.buffer, 0, resultPBA.buffer.length);
            Log.e(TAG, "rs:" + rs);
            tmkList.add(rs);
        }
        Log.e(TAG, tmkList.toString());
        return ret;
    }

    public static void setSyTMK(String syTMK) {
        IccPersonal.syTMK = syTMK;
    }

    public static void setMiwTMK(String miwTMK) {
        IccPersonal.miwTMK = miwTMK;
    }

    public static void setJyTMK(String jyTMK) {
        IccPersonal.jyTMK = jyTMK;
    }
}