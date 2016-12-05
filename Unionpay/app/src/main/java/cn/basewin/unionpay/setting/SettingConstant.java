package cn.basewin.unionpay.setting;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/20 10:09<br>
 * 描述: pos 专属常量 <br>
 */
public class SettingConstant {

    private static final String HEADER = "613100" + AppConfig.VERSION_NUM;
    private static final String APP_VER = "310";
    private static final String SECURE_SESSION = "2610000000000000";
    public static final String KEY_SIGN_TIME = "KEY_SIGN_TIME";//签到时间

    public static String getSignTime() {
        return SPTools.get(KEY_SIGN_TIME, "");
    }

    public static void setSignTime(String msg) {
        SPTools.set(KEY_SIGN_TIME, msg);
    }

    /**
     * 获取流水号(自增)
     *
     * @return
     */
    public static String getTraceAuto() {
        String s = SystemParSettingAty.getTrace();
        String s1 = PosUtil.StrNumAuto(s);
        setTrace(s1);
        return PosUtil.numToStr6(s);
    }

    /**
     * 获取流水号(不自增)
     *
     * @return
     */
    public static String getTrace() {
        String s = SystemParSettingAty.getTrace();
        return PosUtil.numToStr6(s);
    }

    /**
     * 设置流水号
     *
     * @param msg
     */
    public static void setTrace(String msg) {
        TLog.l("保存流水号：" + msg);
        String s = PosUtil.numToStr6(msg);
        SystemParSettingAty.setTrace(s);
    }

    /**
     * 原批次号+1
     *
     * @return
     */
    public static void setBatchAuto() {
        String s = SystemParSettingAty.getBatch();
        setBatch(PosUtil.StrNumAuto(s));
    }

    /**
     * 获取批次号
     *
     * @return
     */
    public static String getBatch() {
        String s = SystemParSettingAty.getBatch();
        return PosUtil.numToStr6(s);
    }

    /**
     * 设置批次号
     *
     * @param no
     */
    public static void setBatch(String no) {
        SystemParSettingAty.setBatch(no);
    }

    public static String getHEADER() {
        return HEADER;
    }

    public static String getAppVer() {
        return APP_VER;
    }

    public static String getSecureSession() {
        if (!isEncTrack()) {
            return "2600000000000000";
        }
        return SECURE_SESSION;
    }

    /**
     * 退货最大值
     */
    public static double getREFUND_LIMIT() {
        return TradeOtherSettingAty.getRefundLimit();
    }

    /**
     * 终端号
     */
    public static String getTERMINAL_NO() {
        return MerchantSetting.getTerminalNo();
    }

    /**
     * 商户号
     */
    public static String getMERCHANT_NO() {
        return MerchantSetting.getMerchantNo();
    }

    /**
     * 商户名
     */
    public static String getMERCHANT_NAME() {
        return MerchantSetting.getMerchantName();
    }

    public final static String KEY_OPERATOR_NO = "OPERATOR_NO";

    /**
     * 登录的操作员号
     */
    public static String getOPERATOR_NO() {
        String s = SPTools.get(KEY_OPERATOR_NO, "01");
        return s;
    }

    /**
     * 登录的操作员号
     */
    public static void setOPERATOR_NO(String no) {
        SPTools.set(KEY_OPERATOR_NO, no);
    }

    /**
     * 2.3磁道是否加密
     *
     * @return
     */
    public static boolean isEncTrack() {
        return TradeOtherSettingAty.getNeedTrackEncrypt();//2.3磁道是否加密
    }
}
