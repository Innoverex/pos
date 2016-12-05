package cn.basewin.unionpay.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.setting.PrintStyleSettingAty;
import cn.basewin.unionpay.setting.SystemParSettingAty;
import cn.basewin.unionpay.setting.TradeSwipCardSettingAty;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/8/6 15:29<br>
 * 描述:  <br>
 */
public class IDUtil {
    /**
     * 通过发卡行id 获得发卡行名字
     *
     * @param id
     * @return
     */
    public static String getISSUER(String id) {
        Map<String, String> issuer = getISSUER();
        String tmpID = "";
        try {
            tmpID = id.substring(0, 4);
        } catch (Exception e) {
            TLog.l("发卡行id 少于4位");
        }

        String s = issuer.get(tmpID);
        if (TextUtils.isEmpty(s)) {
            s = PrintStyleSettingAty.getDefaultIss();
        }
        return s;

    }

    public static Map<String, String> getISSUER() {
        Map<String, String> map = new HashMap<>();
        map.put("0001", "交换中心");
        map.put("0102", "工商银行");
        map.put("0103", "农业银行");
        map.put("0104", "中国银行");
        map.put("0105", "建设银行");
        map.put("0100", "邮储银行");
        map.put("0301", "交通银行");
        map.put("0302", "中信银行");
        map.put("0303", "光大银行");
        map.put("0304", "华夏银行");
        map.put("0305", "民生银行");
        map.put("0306", "广发银行");
        map.put("0307", "深发银行");
        map.put("0308", "招商银行");
        map.put("0309", "兴业银行");
        map.put("0310", "浦发银行");
        map.put("0311", "恒丰银行");
        map.put("0316", "浙商银行");
        map.put("0317", "渤海银行");
        map.put("0401", "上海银行");
        map.put("0402", "厦门银行");
        map.put("0403", "北京银行");
        map.put("4802", "银联商务");
        map.put("0464", "泉州银行");
        map.put("0405", "海峡银行");
        map.put("1420", "鄞州银行");
        return map;
    }

    /**
     * 判断是否是电子现金
     * 基于 PBOC借/贷记应用的小额支付脱机消费交易
     *
     * @param action
     * @return
     */
    public static boolean isEC(int action) {
        int[] ints = {ActionConstant.ACTION_EC_QUICKPASS,
                ActionConstant.ACTION_EC_QUERY_BALANCE,
                ActionConstant.ACTION_EC_QUERY_DETAIL,
                ActionConstant.ACTION_EC_SALE,
                ActionConstant.ACTION_EC_REFUND,
                ActionConstant.ACTION_ECLOAD_CASH,
                ActionConstant.ACTION_ECLOAD_ACCOUNT,
                ActionConstant.ACTION_ECLOAD_NONACCOUNT,
                ActionConstant.ACTION_ECLOAD_CASH_VOID
        };
        return compare(action, ints);
    }

    public static boolean isEC(String action) {
        TLog.l("action:" + action);
        int i = Integer.parseInt(action);
        return isEC(i);
    }

    /**
     * 是否分期交易
     *
     * @param action
     * @return
     */
    public static boolean isINSTALLMENT(int action) {
        if (action == ActionConstant.ACTION_INSTALLMENT) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isINSTALLMENT(String action) {
        int i = Integer.parseInt(action);
        return isINSTALLMENT(i);
    }

    /**
     * 是否积分交易
     *
     * @param action
     * @return
     */
    public static boolean isBONUS(int action) {
        if (action == ActionConstant.ACTION_BONUS) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBONUS(String action) {
        int i = Integer.parseInt(action);
        return isBONUS(i);
    }

    /**
     * 是撤销交易
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean hasOLDTRACE(int action) {
        int[] ints = {ActionConstant.ACTION_VOID, ActionConstant.ACTION_COMPLETE_VOID, ActionConstant.ACTION_INSTALLMENT_VOID};
        return compare(action, ints);
    }

    public static boolean hasOLDTRACE(String action) {
        int i = Integer.parseInt(action);
        return hasOLDTRACE(i);
    }

    /**
     * 是退货交易
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean isREFUND(int action) {
        int[] ints = {ActionConstant.ACTION_REFUND};
        return compare(action, ints);
    }

    public static boolean isREFUND(String action) {
        int i = Integer.parseInt(action);
        return isREFUND(i);
    }

    /**
     * 是否为预授权交易
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean hasAUTHCODE(int action) {
        int[] ints = {ActionConstant.ACTION_AUTH, ActionConstant.ACTION_AUTH_COMPLETE,
                ActionConstant.ACTION_COMPLETE_VOID, ActionConstant.ACTION_AUTH_SETTLEMENT,
                ActionConstant.ACTION_CANCEL,
                ActionConstant.ACTION_MOTO_AUTH, ActionConstant.ACTION_MOTO_AUTH_COMPLETE,
                ActionConstant.ACTION_MOTO_COMPLETE_VOID, ActionConstant.ACTION_MOTO_AUTH_SETTLEMENT,
                ActionConstant.ACTION_MOTO_CANCEL};
        return compare(action, ints);
    }

    public static boolean hasAUTHCODE(String action) {
        int i = Integer.parseInt(action);
        return hasAUTHCODE(i);
    }

    /**
     * 是否有原授权码
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean hasOLDAUTHCODE(int action) {
        int[] ints = {ActionConstant.ACTION_AUTH_COMPLETE, ActionConstant.ACTION_COMPLETE_VOID, ActionConstant.ACTION_AUTH_SETTLEMENT, ActionConstant.ACTION_CANCEL, ActionConstant.ACTION_ACCOUNT_LOAD_CASH, ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT};
        return compare(action, ints);
    }

    public static boolean hasOLDAUTHCODE(String action) {
        int i = Integer.parseInt(action);
        return hasOLDAUTHCODE(i);
    }

    /**
     * 是否有原授权码
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean isDebit(int action) {
        int[] ints = {ActionConstant.ACTION_SALE, ActionConstant.ACTION_AUTH_COMPLETE, ActionConstant.ACTION_AUTH_SETTLEMENT, ActionConstant.ACTION_EC_SALE, ActionConstant.ACTION_EC_QUICKPASS, ActionConstant.ACTION_ECLOAD_NONACCOUNT};
        return compare(action, ints);
    }

    public static boolean isDebit(String action) {
        int i = Integer.parseInt(action);
        return isDebit(i);
    }

    /**
     * 比较 action是否在匹配的数组里面
     *
     * @param action
     * @param actions
     * @return
     */
    public static boolean compare(int action, int[] actions) {
        if (actions == null || actions.length <= 0) {
            return false;
        }
        for (int i = 0; i < actions.length; i++) {
            if (action == actions[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSignIn(int action) {
        return action == ActionConstant.ACTION_SIGN_POS;
    }

    public static boolean isSignOut(int action) {
        return action == ActionConstant.ACTION_SIGN_OUT;
    }

    public static String getSwipType(String code) {
        TLog.l("刷卡 类型" + code);
        HashMap<String, String> s = new HashMap<>();
        s.put("01", "M");
        s.put("02", "S");
        s.put("05", "I");
        s.put("07", "C");
        s.put("00", "N");
        String ss = "N";
        try {
            ss = s.get(code.substring(0, 2));
        } catch (Exception e) {
            ss = "N";
        }
        return TextUtils.isEmpty(ss) ? "N" : ss;
    }

    /**
     * 判断属于非接改造范围的交易
     * 基于 PBOC借/贷记应用的小额支付脱机消费交易
     *
     * @param action
     * @return
     */
    public static boolean isRfFirst(int action) {
        int[] ints = {ActionConstant.ACTION_SALE,
                ActionConstant.ACTION_VOID,
                ActionConstant.ACTION_REFUND,
                ActionConstant.ACTION_AUTH,
                ActionConstant.ACTION_CANCEL,
                ActionConstant.ACTION_AUTH_COMPLETE,
                ActionConstant.ACTION_AUTH_SETTLEMENT,
                ActionConstant.ACTION_COMPLETE_VOID};
        return compare(action, ints);
    }

    /**
     * 是否重打印
     *
     * @param action
     * @return
     */
    public static boolean isReprint(int action) {
        int[] ints = {ActionConstant.ACTION_PRINT_LAST,
                ActionConstant.ACTION_PRINT_RANDOM,
                ActionConstant.ACTION_PRINT_DETAIL,
                ActionConstant.ACTION_PRINT_SUMMARY,
                ActionConstant.ACTION_PRINT_SETTLEMENT};
        return compare(action, ints);
    }

    /**
     * 是否刷卡  默认返回 true 执行刷卡
     *
     * @param action
     * @return
     */
    public static boolean needSwipeCard(int action) {
        int[] ints = {ActionConstant.ACTION_VOID,
                ActionConstant.ACTION_COMPLETE_VOID,
        };
        boolean b = true;
        switch (action) {
            case ActionConstant.ACTION_VOID:
                b = TradeSwipCardSettingAty.isVOID();
                break;
            case ActionConstant.ACTION_COMPLETE_VOID:
                b = TradeSwipCardSettingAty.isCOMPLETE_VOID();
                break;
        }
        return b;
    }

    /**
     * 是否刷卡  默认返回 true 执行刷卡
     *
     * @param action
     * @return
     */
    public static boolean hasAmountMinus(int action) {
        if (!SystemParSettingAty.getPrintVoidMinus()) {
            return false;
        }
        int[] ints = {
                ActionConstant.ACTION_VOID,
                ActionConstant.ACTION_REFUND,
                ActionConstant.ACTION_CANCEL,
                ActionConstant.ACTION_COMPLETE_VOID,
                ActionConstant.ACTION_INSTALLMENT_VOID,
                ActionConstant.ACTION_BONUS_VOID,
                ActionConstant.ACTION_CUPBONUS_REFUND,
                ActionConstant.ACTION_RESERVATION_VOID,
                ActionConstant.ACTION_MOTO_VOID,
                ActionConstant.ACTION_MOTO_REFUND,
                ActionConstant.ACTION_MOTO_CANCEL,
                ActionConstant.ACTION_MOTO_COMPLETE_VOID,
        };

        return compare(action, ints);
    }

}
