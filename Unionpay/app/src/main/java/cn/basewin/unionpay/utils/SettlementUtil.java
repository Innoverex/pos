package cn.basewin.unionpay.utils;

import android.util.Log;

import org.xutils.ex.DbException;

import java.math.BigInteger;
import java.util.List;

import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.SettleInfo;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.setting.MerchantSetting;
import cn.basewin.unionpay.setting.SettingConstant;

/**
 * Created by kxf on 2016/9/2.
 */
public class SettlementUtil {
    private final static String TAG = "SettlementUtil";
    private final static String CURRENT_SETTLE_STATE = "current_settle_state";
    private final static String CURRENT_SETTLE_FIELD48 = "current_settle_field48";
    private final static String CURRENT_SETTLE_FIELD48_RESULT = "current_settle_field48_result";

    /**
     * domestic 国内
     * foreign 国外
     * <p/>
     * debit 借记
     * credit 贷记
     */
    private static BigInteger ddAmount;//金额
    private static BigInteger dcAmount;
    private static BigInteger fdAmount;
    private static BigInteger fcAmount;
    private static int ddAcount;//笔数
    private static int dcAcount;
    private static int fdAcount;
    private static int fcAcount;

    /**
     * 设置保存的数据库中的数据是否结算
     *
     * @param isSettle true为已经结算
     */
    public static void setCurrentSettleState(boolean isSettle) {
        SPTools.set(CURRENT_SETTLE_STATE, isSettle);
    }

    public static boolean getCurrentSettleState() {
        return SPTools.get(CURRENT_SETTLE_STATE, false);
    }

    public static void setCurrentSettleField48(String field48) {
        SPTools.set(CURRENT_SETTLE_FIELD48, field48);
    }

    public static String getCurrentSettleField48() {
        return SPTools.get(CURRENT_SETTLE_FIELD48, "");
    }

    public static void setCurrentSettleField48Result(String field48_result) {
        SPTools.set(CURRENT_SETTLE_FIELD48_RESULT, field48_result);
    }

    public static String getCurrentSettleField48Result() {
        return SPTools.get(CURRENT_SETTLE_FIELD48_RESULT, "");
    }

    public static boolean sumData() {
        Log.i(TAG, "sumData...");
        List<TransactionData> ls = null;
        try {
            ls = TransactionDataDao.selectAllValid();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (ls == null || ls.size() < 1) {
            Log.e(TAG, "无交易");
            return false;
        }
        ddAmount = new BigInteger("000000000000");
        dcAmount = new BigInteger("000000000000");
        fdAmount = new BigInteger("000000000000");
        fcAmount = new BigInteger("000000000000");
        ddAcount = 0;//笔数
        dcAcount = 0;
        fdAcount = 0;
        fcAcount = 0;
        Log.i(TAG, "trans[" + ls.size() + "]=" + ls);
        for (TransactionData td : ls) {
            /**
             * 结算类型, 1借记、2贷记、其它都是结算无关
             * 卡类型, 1内卡、2外卡
             */
            if ("1".equals(td.getCardType())) {
                if ("1".equals(td.getSettleType())) {
                    ddAmount = ddAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    ddAcount++;
                } else if ("2".equals(td.getSettleType())) {
                    dcAmount = dcAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    dcAcount++;
                } else {
                    Log.e(TAG, "结算类型不匹配 TransactionData=" + td);
                }
            } else if ("2".equals(td.getCardType())) {
                if ("1".equals(td.getSettleType())) {
                    fdAmount = fdAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    fdAcount++;
                } else if ("2".equals(td.getSettleType())) {
                    fcAmount = fcAmount.add(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                    fcAcount++;
                } else {
                    Log.e(TAG, "结算类型不匹配 TransactionData=" + td);
                }
            } else {
                Log.e(TAG, "卡类型不匹配 TransactionData=" + td);
            }
        }
        return true;
    }

    public static SettleInfo getSettleInfo() {
        SettleInfo so = new SettleInfo();
        so.setBatchNo(SettingConstant.getBatch());
        so.setMerchantName(MerchantSetting.getMerchantName());
        so.setMerchantId(MerchantSetting.getMerchantNo());
        so.setTerminalId(MerchantSetting.getTerminalNo());
        so.setDate(TDevice.getNowTimeByFormat("yyyy/MM/dd"));
        so.setTime(TDevice.getNowTimeByFormat("HH:mm:ss"));
        so.setTotalitem_debit_n(ddAcount + "");
        so.setTotalmoney_debit_n(ddAmount.toString());
        so.setTotalitem_credit_n(dcAcount + "");
        so.setTotalmoney_credit_n(dcAmount.toString());
        so.setTotalitem_debit_w(fdAcount + "");
        so.setTotalmoney_debit_w(fdAmount.toString());
        so.setTotalitem_credit_w(fcAcount + "");
        so.setTotalmoney_credit_w(fcAmount.toString());
        return so;
    }

    public static String getInitField48() {
        String field48 = String.format("%012d", ddAmount.longValue()) +
                String.format("%03d", ddAcount) +
                String.format("%012d", dcAmount.longValue()) +
                String.format("%03d", dcAcount) + 0 +
                String.format("%012d", fdAmount.longValue()) +
                String.format("%03d", fdAcount) +
                String.format("%012d", fcAmount.longValue()) +
                String.format("%03d", fcAcount) + 0;
        return field48;
    }
}