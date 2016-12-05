package cn.basewin.unionpay.trade;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.basewin.unionpay.ExternalCallAty;
import cn.basewin.unionpay.base.BaseFlowAty;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.network.NetInstallment;
import cn.basewin.unionpay.ui.ShowBalanceAty;
import cn.basewin.unionpay.utils.LedUtil;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/18 10:59<br>
 * 描述：流程控制类
 */
public final class FlowControl {
    public static String TAG = FlowControl.class.getName();
    public static final String KEY_FLOW_LIST = "FlowControl_flow_list";
    public static final String KEY_ACTION = "FlowControl_action";
    public static final String KEY_NEXT_ACTION = "FlowControl_next_action";
    public static final String KEY_BRANCH = "FlowControl_branch";
    private static HashMap<String, Object> sFieldMap = new HashMap<>();
    private Class mBeginFlow;
    private ArrayList<String> mFlowList;
    private ArrayList<ArrayList<String>> mBranch;

    public FlowControl() {
        sFieldMap = new HashMap<>();
        mFlowList = new ArrayList<>();
        mBranch = new ArrayList<>();
    }

    /**
     * 流程中的第一个页面
     *
     * @param start
     * @return
     */
    public FlowControl begin(Class start) {
        if (!BaseFlowAty.class.isAssignableFrom(start)) {
            throw new IllegalArgumentException("The " + start.getName() + " must extend BaseFlowAty");
        }
        this.mBeginFlow = start;
        return this;
    }

    /**
     * 添加流程页面
     *
     * @param next
     * @return
     */
    public FlowControl next(Class next) {
        TLog.l("111：" + next.getName());
        if (!BaseFlowAty.class.isAssignableFrom(next)) {
            throw new IllegalArgumentException("The " + next.getName() + " must extend BaseFlowAty");
        }
        mFlowList.add(next.getName());
        TLog.l("流程加入：" + next.getName());
        return this;
    }

    /**
     * 增加分支<br>
     * index相同的分支只能存在一个，后者会覆盖前者<br>
     * 请从0开始依次递增index,否则会抛出IndexOutOfBoundsException异常
     *
     * @param index
     * @param branches
     * @return
     */
    public FlowControl branch(int index, Class... branches) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (index > mBranch.size()) {
            throw new IndexOutOfBoundsException("Invalid index " + index + ", size is " + mBranch.size());
        }

        for (Class c : branches) {
            if (!BaseFlowAty.class.isAssignableFrom(c)) {
                throw new IllegalArgumentException("The " + c.getName() + " must extend BaseFlowAty");
            }
            arrayList.add(c.getName());
        }
        mBranch.add(index, arrayList);
        return this;
    }

    /**
     * 启动流程
     *
     * @param mContext 上下文环境
     * @param action   流程意图
     */
    public void start(Context mContext, int action) {
        if (mBeginFlow == null) {
            throw new IllegalArgumentException("The \"begin\" method which is used to set start activity is never called. The flow must begin with a start activity.");
        }
        LedUtil.startTransaction();
        Intent intent = new Intent(mContext, mBeginFlow);
        intent.putStringArrayListExtra(KEY_FLOW_LIST, mFlowList);
        intent.putExtra(KEY_BRANCH, mBranch);
        FlowControl.sFieldMap.put(KEY_ACTION, action);
        mContext.startActivity(intent);
    }

    /**
     * 存入键值供后续流程参数使用
     *
     * @param key
     * @param obj
     * @return
     */
    public FlowControl map(String key, Object obj) {
        FlowControl.sFieldMap.put(key, obj);
        return this;
    }

    /**
     * 存入键值供后续流程参数使用
     *
     * @param map
     * @return
     */
    public FlowControl map(HashMap<String, Object> map) {
        FlowControl.sFieldMap.putAll(map);
        return this;
    }

    @Override
    public String toString() {
        if (mFlowList.size() > 0 && mBeginFlow != null) {
            Log.d(TAG, "start Aty:" + mBeginFlow.getName());
            for (String str : mFlowList) {
                Log.d(TAG, "flow Aty:" + str);
            }
        }
        return super.toString();
    }

    /**
     * Map管理类
     * 描述： 1.流程中所有的数据以流水号为数据源，所以在报文中应该设置流水号到 map中。    >>>>>>>注意一定要执行<<<<<<<
     */
    public static class MapHelper {

        public static void clear() {
            sFieldMap.clear();
        }

        public static Map<String, Object> getMap() {
            return sFieldMap;
        }

        /**
         * 设置外部调用标志
         *
         * @return
         */
        public static void setExternalCallFlag(boolean callFlag) {
            sFieldMap.put(ExternalCallAty.KEY_CARD, callFlag);
        }

        public static boolean getExternalCallFlag() {
            return sFieldMap.containsKey(ExternalCallAty.KEY_CARD) && (boolean) sFieldMap.get(ExternalCallAty.KEY_CARD);
        }

        /**
         * 刷卡 的卡数据
         *
         * @return
         */
        public static Card getCard() {
            if (!sFieldMap.containsKey(SwipingCardAty.KEY_CARD)) {
                Card card = new Card();
                setCard(card);
                return card;
            }
            return (Card) sFieldMap.get(SwipingCardAty.KEY_CARD);
        }

        public static void setCard(Card card) {
            sFieldMap.put(SwipingCardAty.KEY_CARD, card);
        }

        /**
         * 刷第2张卡的数据
         *
         * @return
         */
        public static Card getSecondCard() {
            if (!sFieldMap.containsKey(SwipingSecondCardAty.KEY_CARD)) {
                return null;
            }
            return (Card) sFieldMap.get(SwipingSecondCardAty.KEY_CARD);
        }

        public static void setSecondCard(Card card) {
            sFieldMap.put(SwipingSecondCardAty.KEY_CARD, card);
        }

        /**
         * 金额
         */
        public static String getMoney() {
            if (!sFieldMap.containsKey(InputMoneyAty.KEY_MONEY)) {
                return "";
            }
            return (String) sFieldMap.get(InputMoneyAty.KEY_MONEY);
        }

        public static void setMoney(String money) {
            sFieldMap.put(InputMoneyAty.KEY_MONEY, money);
        }

        /**
         * 小费
         */
        public static String getFee() {
            if (!sFieldMap.containsKey(InputFeeAty.KEY_MONEY)) {
                return "";
            }
            return (String) sFieldMap.get(InputFeeAty.KEY_MONEY);
        }

        public static void setFee(String money) {
            sFieldMap.put(InputFeeAty.KEY_MONEY, money);
        }

        /**
         * 开启pboc的刷卡类型
         */
        public static int getSwipingType() {
            if (!sFieldMap.containsKey(SwipingCardAty.KEY_INPUT_TYPE)) {
                return -1;
            }
            return (int) sFieldMap.get(SwipingCardAty.KEY_INPUT_TYPE);
        }

        public static void setSwipingType(int type) {
            sFieldMap.put(SwipingCardAty.KEY_INPUT_TYPE, type);
        }

        /**
         * 开启联机类型
         */
        public static int getTransactionType() {
            if (!sFieldMap.containsKey(SwipingCardAty.KEY_TRANSACTION)) {
                return -1;
            }
            return (int) sFieldMap.get(SwipingCardAty.KEY_TRANSACTION);
        }

        public static void setTransactionType(int type) {
            sFieldMap.put(SwipingCardAty.KEY_TRANSACTION, type);
        }

        public static void setPWD(byte[] bytes) {
            sFieldMap.put(InputPWDAty.KEY_PW, bytes);
        }

        public static byte[] getPWD() {
            if (!sFieldMap.containsKey(InputPWDAty.KEY_PW)) {
                return null;
            }
            return (byte[]) sFieldMap.get(InputPWDAty.KEY_PW);
        }

        public static void setSignPath(String path) {
            sFieldMap.put(SignatureAty.KEY_PATH, path);
        }

        public static String getSignPath() {
            if (!sFieldMap.containsKey(SignatureAty.KEY_PATH)) {
                return "";
            }
            return (String) sFieldMap.get(SignatureAty.KEY_PATH);
        }

        /**
         * 分期数
         *
         * @param installmentPeriod
         */
        public static void setInstallNO(String installmentPeriod) {
            sFieldMap.put(InputInstallNOAty.KEY_DATA, installmentPeriod);
        }

        public static String getInstallNO() {
            if (!sFieldMap.containsKey(InputInstallNOAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputInstallNOAty.KEY_DATA);
        }

        /**
         * 商品id
         *
         * @param productId
         */
        public static void setProductId(String productId) {
            sFieldMap.put(InputProductIdAty.KEY_DATA, productId);
        }

        public static String getProductId() {
            if (!sFieldMap.containsKey(InputProductIdAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputProductIdAty.KEY_DATA);
        }

        public static void setTerminal(String terminalId) {
            sFieldMap.put(InputTerminalAty.KEY_DATA, terminalId);
        }

        public static String getTerminal() {
            if (!sFieldMap.containsKey(InputTerminalAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputTerminalAty.KEY_DATA);
        }

        public static void setManagerPWD(String pwd) {
            sFieldMap.put(InputManagerPWDAty.KEY_DATA, pwd);
        }

        public static String getManagerPWD() {
            if (!sFieldMap.containsKey(InputManagerPWDAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputManagerPWDAty.KEY_DATA);
        }

        /**
         * 时间
         *
         * @param date
         */
        public static void setDate(String date) {
            sFieldMap.put(InputDateAty.KEY_DATA, date);
        }

        public static String getDate() {
            if (!sFieldMap.containsKey(InputDateAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputDateAty.KEY_DATA);
        }

        /**
         * 凭证号
         *
         * @param certificate
         */
        public static void setTrace(String certificate) {
            sFieldMap.put(InputTraceAty.KEY_DATA, certificate);
        }

        public static String getTrace() {
            if (!sFieldMap.containsKey(InputTraceAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputTraceAty.KEY_DATA);
        }

        /**
         * 参考号
         *
         * @param msg
         */
        public static void setReferNo(String msg) {
            sFieldMap.put(InputReferNo.KEY_DATA, msg);
        }

        public static String getReferNo() {
            if (!sFieldMap.containsKey(InputReferNo.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputReferNo.KEY_DATA);
        }

        /**
         * 设置批次号
         *
         * @param batch
         */
        public static void setBatch(String batch) {
            sFieldMap.put(InputBatchAty.KEY_DATA, batch);
        }

        /**
         * 获取批次号
         *
         * @return
         */
        public static String getBatch() {
            if (!sFieldMap.containsKey(InputBatchAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputBatchAty.KEY_DATA);
        }

        /**
         * 设置流水号
         *
         * @param batch
         */
        public static void setSerial(String batch) {
            sFieldMap.put("FlowControl_serial", batch);
        }

        /**
         * 获取流水号
         *
         * @return
         */
        public static String getSerial() {
            if (!sFieldMap.containsKey("FlowControl_serial")) {
                return "";
            }
            return (String) sFieldMap.get("FlowControl_serial");
        }

        public static String KeySerial = "FlowControl_serial";

        /**
         * 设置授权码
         *
         * @param autorization
         */
        public static void setAuthCode(String autorization) {
            sFieldMap.put(InputAuthCodeAty.KEY_DATA, autorization);
        }

        /**
         * 获取授权码
         *
         * @return
         */
        public static String getAuthCode() {
            if (!sFieldMap.containsKey(InputAuthCodeAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputAuthCodeAty.KEY_DATA);
        }

        /**
         * 卡有效期
         *
         * @param msg
         */
        public static void setCardExpDate(String msg) {
            sFieldMap.put(InputCardExpDateAty.KEY_DATA, msg);
        }

        public static String getCardExpDate() {
            if (!sFieldMap.containsKey(InputCardExpDateAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputCardExpDateAty.KEY_DATA);
        }

        /**
         * 手输卡号
         *
         * @param msg
         */
        public static void setCardNO(String msg) {
            sFieldMap.put(InputCardNumberAty.KEY_DATA, msg);
        }

        public static String getCardNO() {
            if (!sFieldMap.containsKey(InputCardNumberAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputCardNumberAty.KEY_DATA);
        }

        /**
         * 手机号
         */
        public static void setPhoneNO(String msg) {
            sFieldMap.put(InputPhoneNoAty.KEY_DATA, msg);
        }

        public static String getPhoneNO() {
            if (!sFieldMap.containsKey(InputPhoneNoAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputPhoneNoAty.KEY_DATA);
        }

        /**
         * 名字
         */
        public static void setName(String msg) {
            sFieldMap.put(InputNameAty.KEY_DATA, msg);
        }

        public static String getName() {
            if (!sFieldMap.containsKey(InputNameAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputNameAty.KEY_DATA);
        }

        /**
         * 商品代码
         *
         * @return
         */
        public static void setGoodsCode(String msg) {
            sFieldMap.put(InputGoodsCodeAty.KEY_DATA, msg);
        }

        public static String getGoodsCode() {
            if (!sFieldMap.containsKey(InputGoodsCodeAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputGoodsCodeAty.KEY_DATA);
        }

        /**
         * CVN
         *
         * @return
         */
        public static void setCVNCode(String msg) {
            sFieldMap.put(InputCVN2Aty.KEY_DATA, msg);
        }

        public static String getCVNCode() {
            if (!sFieldMap.containsKey(InputCVN2Aty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputCVN2Aty.KEY_DATA);
        }

        /**
         * 身份证后6位
         *
         * @return
         */
        public static void setID6(String msg) {
            sFieldMap.put(InputID6Aty.KEY_DATA, msg);
        }

        public static String getID6() {
            if (!sFieldMap.containsKey(InputID6Aty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(InputID6Aty.KEY_DATA);
        }

        /**
         * CVN
         *
         * @return
         */
        public static void setCardOrganizationCode(String msg) {
            sFieldMap.put(ChooseCardOrganizationAty.KEY_DATA, msg);
        }

        public static String getCardOrganizationCode() {
            if (!sFieldMap.containsKey(ChooseCardOrganizationAty.KEY_DATA)) {
                return "";
            }
            return (String) sFieldMap.get(ChooseCardOrganizationAty.KEY_DATA);
        }

        /**
         * 行为id
         *
         * @return
         */
        public static void setAction(int action) {
            sFieldMap.put(KEY_ACTION, action);
        }

        public static int getAction() {
            if (!sFieldMap.containsKey(KEY_ACTION)) {
                return -1;
            }
            return (int) sFieldMap.get(KEY_ACTION);
        }

        public static String getBalance() {
            if (!sFieldMap.containsKey(ShowBalanceAty.KEY_BALANCE)) {
                return "";
            }
            return (String) sFieldMap.get(ShowBalanceAty.KEY_BALANCE);
        }

        public static void setBalance(String balance) {
            sFieldMap.put(ShowBalanceAty.KEY_BALANCE, balance);
        }

        /**
         * 获取首期还款金额
         *
         * @return
         */
        public static String getFirstPayment() {
            if (!sFieldMap.containsKey(NetInstallment.KEY_FIRSTPAYMENT)) {
                return "";
            }
            return (String) sFieldMap.get(NetInstallment.KEY_FIRSTPAYMENT);
        }

        /**
         * 持卡人分期付款手续费
         *
         * @return
         */
        public static String getHandlingCharge() {
            if (!sFieldMap.containsKey(NetInstallment.KEY_HANDLING_CHARGE)) {
                return "";
            }
            return (String) sFieldMap.get(NetInstallment.KEY_HANDLING_CHARGE);
        }

        /**
         * 获取分期支付方式
         *
         * @return
         */
        public static String getHandlingType() {
            if (!sFieldMap.containsKey(NetInstallment.KEY_HANDLING_TYPE)) {
                return "";
            }
            return (String) sFieldMap.get(NetInstallment.KEY_HANDLING_TYPE);
        }

        /**
         * 获取首期手续费
         *
         * @return
         */
        public static String getFirstHandlingCharge() {
            if (!sFieldMap.containsKey(NetInstallment.KEY_FIRST_HANDLING_CHARGE)) {
                return "";
            }
            return (String) sFieldMap.get(NetInstallment.KEY_FIRST_HANDLING_CHARGE);
        }

        /**
         * 获取每期手续费
         *
         * @return
         */
        public static String getEveryHandlingCharge() {
            if (!sFieldMap.containsKey(NetInstallment.KEY_EVERY_HANDLING_CHARGE)) {
                return "";
            }
            return (String) sFieldMap.get(NetInstallment.KEY_EVERY_HANDLING_CHARGE);
        }

        //电子现金交易明细查询
        public static ArrayList<String> ECDetail;

        /**
         * 获取可充余额
         *
         * @return
         */
        public static double getCanRechargeMoney() {
            if (!sFieldMap.containsKey(CanRechargeAty.KEY_CAN_RECHARGE_MONEY)) {
                return 0;
            }
            return (double) sFieldMap.get(CanRechargeAty.KEY_CAN_RECHARGE_MONEY);
        }

        /**
         * 设置可充余额
         *
         * @param canRechargeMoney
         */
        public static void setCanRechargeMoney(double canRechargeMoney) {
            sFieldMap.put(CanRechargeAty.KEY_CAN_RECHARGE_MONEY, canRechargeMoney);
        }

        /**
         * 获取下个一个意图
         *
         * @return
         */
        public static int getNextAction() {
            if (!sFieldMap.containsKey(FlowControl.KEY_NEXT_ACTION)) {
                return -1;
            }
            return (int) sFieldMap.get(FlowControl.KEY_NEXT_ACTION);
        }

    }
}
