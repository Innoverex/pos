package cn.basewin.unionpay.print;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.packet8583.factory.Iso8583Manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.AppContext;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.db.TransactionDataDao;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.entity.SettleInfo;
import cn.basewin.unionpay.entity.TransactionData;
import cn.basewin.unionpay.network.remote.Reverse;
import cn.basewin.unionpay.setting.BONUSTypeSettingAty;
import cn.basewin.unionpay.setting.CommuParmAty;
import cn.basewin.unionpay.setting.CommuParmDialFragment;
import cn.basewin.unionpay.setting.CommuParmEthernetFragment;
import cn.basewin.unionpay.setting.CommuParmWifiFragment;
import cn.basewin.unionpay.setting.CommuParmWireLessFragment;
import cn.basewin.unionpay.setting.ECashTypeSettingAty;
import cn.basewin.unionpay.setting.InstallmentTypeSettingAty;
import cn.basewin.unionpay.setting.MOTOTypeSettingAty;
import cn.basewin.unionpay.setting.MerchantSetting;
import cn.basewin.unionpay.setting.OtherTypeSettingAty;
import cn.basewin.unionpay.setting.PrintStyleSettingAty;
import cn.basewin.unionpay.setting.ReservationTypeSettingAty;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.setting.SystemParSettingAty;
import cn.basewin.unionpay.setting.TradeInputPwdSettingAty;
import cn.basewin.unionpay.setting.TradeOfflineSettingAty;
import cn.basewin.unionpay.setting.TradeOtherSettingAty;
import cn.basewin.unionpay.setting.TradeSettlementSettingAty;
import cn.basewin.unionpay.setting.TradeSwipCardSettingAty;
import cn.basewin.unionpay.setting.TraditionalTypeSettingAty;
import cn.basewin.unionpay.setting.UpCardSettingAty;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.NetSettleWaitAty;
import cn.basewin.unionpay.utils.IDUtil;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.SPTools;
import cn.basewin.unionpay.utils.TDevice;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/1 16:06<br>
 * 描述:  <br>
 */
public class PrintHelper {

    private static final String TAG = PrintHelper.class.getName();
    //text  模版
    public static Map<String, String> t1_l = PrintClient.getRulesTxt("1", "left", "0", "0", "0", "0");
    public static Map<String, String> t2_l = PrintClient.getRulesTxt("2", "left", "0", "0", "0", "0");
    public static Map<String, String> t2_c = PrintClient.getRulesTxt("2", "center", "0", "0", "0", "0");
    public static Map<String, String> t3_l = PrintClient.getRulesTxt("3", "left", "0", "0", "0", "0");
    public static Map<String, String> t3_c = PrintClient.getRulesTxt("3", "center", "0", "1", "0", "0");

    public static Map<String, String> tst2 = PrintClient.getRulesTxt("2", "center", "0", "1", "0", "0");
    public static Map<String, String> tst3 = PrintClient.getRulesTxt("3", "center", "0", "0", "1", "0");
    public static Map<String, String> tst4 = PrintClient.getRulesTxt("2", "left", "0", "0", "1", "0");
    public static Map<String, String> tst5 = PrintClient.getRulesTxt("2", "right", "0", "0", "1", "0");
    //二维码 模版
    public static Map<String, String> e1 = PrintClient.getRulestwo_dimension("4", "center", "0");
    //一维码 模版
    public static Map<String, String> y1 = PrintClient.getRulesone_dimension("2", "center", "1");
    //图片 模版
    public static Map<String, String> i1 = PrintClient.getRules_Image("center");

    //底部数据
    public static LinkedList<JSONObject> bottom;

    public static PrintClient.PrintData bottom0;
    public static PrintClient.PrintData bottom1;
    public static PrintClient.PrintData bottom2;

    static {
        PrintClient p1 = new PrintClient();
        try {
            p1.setText("本人确认以上交易，同意将其记入本卡账户", t1_l).add();
            p1.setText("I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE", t1_l).add();
            p1.setText("服务热线:" + PrintStyleSettingAty.getServiceHotline(), t1_l).add();
            bottom0 = p1.getPrintData();

            bottom1 = new PrintClient().setText("BW-P2000-V310003 商户存根(MERCHANT COPY)", t1_l).add().getPrintData();

            bottom2 = new PrintClient().setText("BW-P2000-V310003 持卡人存根(CARDHOLDER COPY)", t1_l).add().getPrintData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static OnPrinterListener demoListen;

    static {
        demoListen = new OnPrinterListener() {
            @Override
            public void onError(int i, String s) {
                TLog.e(TAG, "onError");
            }

            @Override
            public void onFinish() {
                TLog.e(TAG, "onFinish");
            }

            @Override
            public void onStart() {
                TLog.e(TAG, "onStart");
            }
        };
    }

    //交易打印
    public static PrintClient transaction() throws Exception {
        PrintClient p = new PrintClient();
        p.setText("银联POS签购单", t3_c).add();
        p.setText("商户名(MERCHANT NAME):", t2_l).add();
        p.setText("商户编号(MERCHANT NO):", t2_l).add();
        p.setText("终端号(TERMINAL NO):", t2_l).add();
        p.setText("操作员号(OPERATOR NO):", t2_l).add();
        p.setText("收单行(ACOUIRER):", t2_l).add();
        p.setText("发卡行(ISSUER):", t2_l).add();
        p.setText("卡号(CARD NO):", t2_l).add();
        p.setText("123**********456" + "      /s", t2_l).add();
        p.setText("有效期(EXP DATE):", t2_l).add();
        p.setText("交易类别(TRANS TYPE):", t2_l).add();
        p.setText("DOTO 待定交易类型数据:", t3_l).add();
        p.setText("批次号(BATCH NO):", t2_l).add();
        p.setText("凭证号(VOUCHER NO):", t2_l).add();
        p.setText("授权码(AUTH NO):", t2_l).add();
        p.setText("参考号(REFER NO):", t2_l).add();
        p.setText("日期/时间(DATA/TIME):", t2_l).add();
        p.setText("交易金额(AMOUNT):", t2_l).add();
        p.setText("RMB " + "300000000.00", t3_l).add();
        p.setText("备注(REFERENCE) :", t2_l).add();
        p.setText("原授权码(OLD AUTH NO) :", t2_l).add();
        p.setText("持卡人签名(CARDHOLDER SIGNATURE):", t1_l).add();
        p.setText("", t1_l).add();
        p.setText("", t1_l).add();
        p.setText("----------------------", t1_l).add();
        p.setTtitleText("商户存根", t3_c);
        p.addTPrintDataBottom(bottom0);
        p.addTPrintDataBottom(bottom1).TSave();
        p.setTtitleText("客户存根", t3_c);
        p.addTPrintDataBottom(bottom0);
        p.addTPrintDataBottom(bottom2).TSave();
        return p;
    }

    public static PrintClient demo() throws Exception {
        PrintClient printClient = new PrintClient();
        printClient.setText("你这骚猪正文！！", t2_l).add();
        printClient.setText("你这骚猪2", tst2).add();
        printClient.addPrintData(bottom0);
        printClient.setTTitleImage("print_title.bmp", i1);
        printClient.setTtitleText("-------------持卡人存根-----------", tst2).TSave();
        printClient.setTTitleImage("print_title.bmp", i1);
        printClient.setTtitleText("-------------商户存根-----------", tst2).TSave();
        return printClient;
    }

    //打印交易汇总
    public static PrintClient summary(SettleInfo so, boolean isSettle) throws JSONException {
        PrintClient p = new PrintClient();
        String strk = "            ";
        if (isSettle) {
            p.setText("结算总计单", t3_c).add();
        } else {
            p.setText("交易汇总单", t3_c).add();
        }
        p.setText("==========================", t2_l).add();
        p.setText("商户名:" + so.getMerchantName(), t2_l).add();
        p.setText("(MERCHANT NAME)", t1_l).add();
        p.setText("商户编号:" + so.getMerchantId(), t2_l).add();
        p.setText("(MERCHANT NO)", t1_l).add();
        p.setText("终端号:" + so.getTerminalId(), t2_l).add();
        p.setText("(TERMINAL NO)", t1_l).add();
        p.setText("批次号:" + so.getBatchNo(), t2_l).add();
        p.setText("(BATCH NO)", t1_l).add();
        p.setText("结算时间:" + so.getDate() + " " + so.getTime(), t2_l).add();
        p.setText("(DATE TIME)", t1_l).add();
        p.setText("类型" + strk + "笔数" + strk + "金额", t2_l).add();
        p.setText("TYPE" + strk + strk + "SUM" + strk + strk + "AMT", t1_l).add();
        p.setText("-----------------------------------------", t2_l).add();
        if (isSettle) {
            p.setText("内卡对账" + NetSettleWaitAty.getAccountsState(Integer.parseInt(so.getStatus_n())), t2_l).add();
        } else {
            p.setText("内卡", t2_l).add();
        }
        p.setText("借记" + strk + Integer.parseInt(so.getTotalitem_debit_n()) + strk + PosUtil.changeAmout(so.getTotalmoney_debit_n()), t2_l).add();
        p.setText("贷记" + strk + Integer.parseInt(so.getTotalitem_credit_n()) + strk + PosUtil.changeAmout(so.getTotalmoney_credit_n()), t2_l).add();

        p.setText("-----------------------------------------", t2_l).add();
        if (isSettle) {
            p.setText("外卡对账" + NetSettleWaitAty.getAccountsState(Integer.parseInt(so.getStatus_w())), t2_l).add();
        } else {
            p.setText("外卡", t2_l).add();
        }
        p.setText("借记" + strk + Integer.parseInt(so.getTotalitem_debit_w()) + strk + PosUtil.changeAmout(so.getTotalmoney_debit_w()), t2_l).add();
        p.setText("贷记" + strk + Integer.parseInt(so.getTotalitem_credit_w()) + strk + PosUtil.changeAmout(so.getTotalmoney_credit_w()), t2_l).add();
        p.setText("==========================", t2_l).add();
        p.addPrintData(bottom1).TSave();
        return p;
    }

    public static PrintClient Trade(TransactionData data) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put(FlowControl.MapHelper.KeySerial, data.getTrace());
        return Trade(map);
    }

    public static PrintClient Trade(Map<String, Object> map) throws Exception {
        Log.i(TAG, "Trade...");
        PrintClient p = new PrintClient();
        String serial = (String) map.get(FlowControl.MapHelper.KeySerial);
        TransactionData t = TransactionDataDao.selectByTrace(serial);

        int action = t.getAction();
        boolean isEC = IDUtil.isEC(action);
        boolean isINSTALLMENT = IDUtil.isINSTALLMENT(action);
        boolean isBONUS = IDUtil.isBONUS(action);
        boolean hasOLDTRACE = IDUtil.hasOLDTRACE(action);
        boolean isREFUND = IDUtil.isREFUND(action);
        boolean hasAUTHCODE = IDUtil.hasAUTHCODE(action);
        boolean hasOLDAUTHCODE = IDUtil.hasOLDAUTHCODE(action);
        boolean isDebit = IDUtil.isDebit(action);
        boolean isReprint = IDUtil.isReprint(FlowControl.MapHelper.getAction());
        boolean hasAmountMinus = IDUtil.hasAmountMinus(action);

        String local55 = t.getField55();
        String strIC = "";
        if (!TextUtils.isEmpty(t.getServiceCode()) && t.getServiceCode().length() >= 2) {
            strIC = t.getServiceCode().substring(0, 2);
        }
        boolean isIC = "05".equals(strIC) || "07".equals(strIC);

        p.setText("银联POS签购单", t3_c).add();
        p.setText("商户名(MERCHANT NAME):" + SettingConstant.getMERCHANT_NAME(), t2_l).add();
        p.setText("商户编号(MERCHANT NO):" + SettingConstant.getMERCHANT_NO(), t2_l).add();
        p.setText("终端号(TERMINAL NO):" + SettingConstant.getTERMINAL_NO(), t2_l).add();
        p.setText("操作员号(OPERATOR NO):" + SettingConstant.getOPERATOR_NO(), t2_l).add();
        String tempAcqId = "";
        try {
            tempAcqId = t.getAcqId().substring(0, 4);
        } catch (Exception e) {
        }
        String str = SystemParSettingAty.getNeedPrintAcquirerName() ? IDUtil.getISSUER(tempAcqId) : tempAcqId;
        p.setText("收单行(ACQUIRER):" + str, t2_l).add();
        String tempIssId = "";
        try {
            tempIssId = t.getIssuerId().substring(0, 4);
        } catch (Exception e) {
        }

        str = SystemParSettingAty.getNeedPrintIssuerName() ? IDUtil.getISSUER(tempIssId) : tempIssId;
        p.setText("发卡行(ISSUER):" + str, t2_l).add();
        p.setText("卡号(CARD NO):", t2_l).add();
        String cardNO = ActionConstant.ACTION_AUTH == action ? t.getPan() : TDevice.hiddenCardNum(t.getPan());
        p.setText(cardNO + "   /" + IDUtil.getSwipType(t.getServiceCode()), t2_l).add();
        try {
            String expDate = TDevice.getYear().substring(0, 2) + t.getExpDate().substring(2, 4) + "/" + t.getExpDate().substring(0, 2);
            p.setText("有效期(EXP DATE):" + expDate, t2_l).add();
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setText("交易类别(TRANS TYPE):", t2_l).add();
        p.setText(t.getName() + "(" + t.getEngName() + ")", t2_l).add();
        p.setText("批次号(BATCH NO):" + t.getBatch(), t2_l).add();
        p.setText("凭证号(VOUCHER NO):" + t.getTrace(), t2_l).add();
        String authCode = t.getAuthCode();
        p.setText("授权码(AUTH NO):" + authCode, t2_l).add(hasAUTHCODE && !"".equals(authCode));
        p.setText("参考号(REFER NO):" + t.getReferenceNo(), t2_l).add();
        p.setText("日期/时间(DATA/TIME):" + TDevice.formatDate(t.getDate() + t.getTime()), t2_l).add();
        p.setText("交易金额(AMOUNT):", t2_l).add();
        if (hasAmountMinus) {
            p.setText("RMB：-" + PosUtil.fenToYuan(t.getAmount()), t3_l).add();
        } else {
            p.setText("RMB：" + PosUtil.fenToYuan(t.getAmount()), t3_l).add();
        }

        p.setText("备注(REFERENCE):", t2_l).add();

        //原凭证号（撤销、预授权完成撤销）
        p.setText("原凭证号(OLD VOUCHER):" + t.getOldTrace(), t2_l).add(hasOLDTRACE);

        //退货
        p.setText("原参考号(OLD REFER):" + t.getOldReference(), t2_l).add(isREFUND);
        p.setText("原交易日期(OLD DATE):" + PosUtil.fromatMMDD(t.getOldDate()), t2_l).add(isREFUND);

        //原授权码（预授权完成、预授权完成撤销、预授权撤销）
        String oldAuthCode = t.getOldAuthCode();
        p.setText("原授权码(OLD AUTHNO):" + oldAuthCode, t2_l).add(hasOLDAUTHCODE && !"".equals(oldAuthCode));
        //ic
        if (isDebit) {
            p.setText("ARQC:" + PosUtil.print55(local55, "9F26"), t1_l).add(isIC);
            p.setText("TVR:" + PosUtil.print55(local55, "0095") + "    CSN:" + t.getCardSn(), t1_l).add(isIC);
            p.setText("AID:" + PosUtil.print55(local55, "0084"), t1_l).add(isIC);
            p.setText("ATC:" + PosUtil.print55(local55, "9F36") + "    TSI:" + PosUtil.print55(local55, "009B"), t1_l).add(isIC);
            p.setText("APP LABEL:" + PosUtil.print55(local55, "0050"), t1_l).add(isIC);
            p.setText("APN:" + PosUtil.print55(local55, "9F12"), t1_l).add(isIC);
            p.setText("UNPR NUM:" + PosUtil.print55(local55, "9F37"), t1_l).add(isIC);
            p.setText("AIP:" + PosUtil.print55(local55, "0082") + "    CVMR:" + PosUtil.print55(local55, "9F34"), t1_l).add(isIC);
            p.setText("IAD:" + PosUtil.print55(local55, "9F10"), t1_l).add(isIC);
            p.setText("Term Capa:" + PosUtil.print55(local55, "9F33"), t1_l).add(isIC);
        }

        //分期
        p.setText("分期付款期数(Installment Period):" + FlowControl.MapHelper.getInstallNO(), t2_l).add(isINSTALLMENT);
        if (isINSTALLMENT) {
            p.setText("分期付款首期金额(Initial Installment Payment):" + FlowControl.MapHelper.getFirstPayment(), t2_l).add(isINSTALLMENT);
            p.setText("分期付款还款币种:" + "人民币", t2_l).add(isINSTALLMENT);
            p.setText("持卡人手续费(Installment Charge):" + FlowControl.MapHelper.getHandlingCharge(), t2_l).add(isINSTALLMENT);
            p.setText("手续费支付方式:" + FlowControl.MapHelper.getHandlingType(), t2_l).add(isINSTALLMENT);
            p.setText("首期手续费:" + FlowControl.MapHelper.getFirstHandlingCharge(), t2_l).add(isINSTALLMENT);
            p.setText("每期手续费 :" + FlowControl.MapHelper.getEveryHandlingCharge(), t2_l).add(isINSTALLMENT);
        }

        if (action == ActionConstant.ACTION_ACCOUNT_LOAD_ACCOUNT) {
            p.setText("可用余额:" + FlowControl.MapHelper.getCanRechargeMoney(), t2_l).add();
            Card card = FlowControl.MapHelper.getCard();
            if (card != null) {
                p.setText("转入卡卡号:" + card.getPan(), t2_l).add(hasOLDAUTHCODE);
            }
        }

        //积分
        p.setText("商品代码:" + t.getProduct(), t2_l).add(isBONUS);
        p.setText("兑换积分数(Exchange Points)", t2_l).add(isBONUS);
        p.setText("积分余额数(Points Balance)", t2_l).add(isBONUS);
        p.setText("自付金额(Outstanding Amount)", t2_l).add(isBONUS);
        p.setText("转入卡卡号(Into Account)", t2_l).add(isBONUS);

        p.setText("重打印凭证/DUPLICATED", t2_l).add(isReprint);

        p.setText("持卡人签名(CARDHOLDER SIGNATURE):", t2_l).add();
        String signPath = t.getSignPath();
        if (!TextUtils.isEmpty(signPath) && new File(signPath).exists()) {
            TLog.l("图片路劲" + signPath);
            p.setImage(signPath, i1).add();
            p.setText("", t1_l).add();
        } else {
            p.setText("", t3_l).add();
            p.setText("", t3_l).add();
            p.setText("", t3_l).add();
            p.setText("", t3_l).add();
            p.setText("", t3_l).add();
            p.setText("", t3_l).add();
        }

        p.addTPrintDataBottom(bottom0);
        p.addTPrintDataBottom(bottom1).TSave();
        int printNum = Integer.parseInt(SystemParSettingAty.getPrintNumber());
        if (printNum > 1) {
            p.addTPrintDataBottom(bottom0);
            p.addTPrintDataBottom(bottom2).TSave();
        }
        return p;
    }

    /**
     * 打印商户参数
     *
     * @return
     */
    public static PrintClient PrintMerchantParam() throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("=======银联EMV参数=======", t2_l).add();
        p.setText("******商户参数******", t2_l).add();
        p.setText("商户名 :" + MerchantSetting.getMerchantName(), t2_l).add();
        p.setText("商户号 :" + MerchantSetting.getMerchantNo(), t2_l).add();
        p.setText("终端号 :" + MerchantSetting.getTerminalNo(), t2_l).add();
        return p;
    }

    public static PrintClient PrintTradeParam() throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("=======银联EMV参数=======", t2_l).add();
        p.setText("******交易控制参数******", t2_l).add();
        p.setText("消费是否支持 :" + TraditionalTypeSettingAty.isSale(), t2_l).add();
        p.setText("消费撤销是否支持 :" + TraditionalTypeSettingAty.isVoid(), t2_l).add();
        p.setText("退货是否支持 :" + TraditionalTypeSettingAty.isRefund(), t2_l).add();
        p.setText("余额查询是否支持 :" + TraditionalTypeSettingAty.isQueryBalance(), t2_l).add();
        p.setText("预授权是否支持 :" + TraditionalTypeSettingAty.isAuth(), t2_l).add();
        p.setText("预授权撤销是否支持 :" + TraditionalTypeSettingAty.isCancel(), t2_l).add();
        p.setText("预授权完成（请求） :" + TraditionalTypeSettingAty.isAuthComplete(), t2_l).add();
        p.setText("预授权完成（通知） :" + TraditionalTypeSettingAty.isAuthSettlement(), t2_l).add();
        p.setText("预授权完成撤销 :" + TraditionalTypeSettingAty.isCompleteVoid(), t2_l).add();
        p.setText("离线结算是否支持 :" + TraditionalTypeSettingAty.isOffline(), t2_l).add();
        p.setText("结算调整是否支持 :" + TraditionalTypeSettingAty.isAdjust(), t2_l).add();
        p.setText("接触式电子现金消费 :" + ECashTypeSettingAty.isECsale(), t2_l).add();
        p.setText("快速支付非接电子现金消费 :" + ECashTypeSettingAty.isECquickpass(), t2_l).add();
        p.setText("电子现金指定账户圈存 :" + ECashTypeSettingAty.isECloadAccount(), t2_l).add();
        p.setText("电子现金非指定账户圈存 :" + ECashTypeSettingAty.isECloadNonaccount(), t2_l).add();
        p.setText("电子现金现金充值 :" + ECashTypeSettingAty.isECloadCash(), t2_l).add();
        p.setText("电子现金充值撤销 :" + ECashTypeSettingAty.isECloadCashVoid(), t2_l).add();
        p.setText("电子现金脱机退货 :" + ECashTypeSettingAty.isECrefund(), t2_l).add();
        p.setText("分期付款消费 :" + InstallmentTypeSettingAty.isInstallment(), t2_l).add();
        p.setText("分期付款消费撤销 :" + InstallmentTypeSettingAty.isInstallmentVoid(), t2_l).add();
        p.setText("联盟积分消费 :" + BONUSTypeSettingAty.isCUPBONUS(), t2_l).add();
        p.setText("联盟积分消费撤销 :" + BONUSTypeSettingAty.isCUPBONUS_VOID(), t2_l).add();
        p.setText("发卡行积分消费 :" + BONUSTypeSettingAty.isBONUS(), t2_l).add();
        p.setText("发卡行积分消费撤销 :" + BONUSTypeSettingAty.isBONUS_VOID(), t2_l).add();
        p.setText("联盟积分查询是否支持 :" + BONUSTypeSettingAty.isCUPBONUS_QUERY(), t2_l).add();
        p.setText("联盟积分退货是否支持 :" + BONUSTypeSettingAty.isCUPBONUS_REFUND(), t2_l).add();
        p.setText("手机消费 :" + UpCardSettingAty.isUPCARD(), t2_l).add();
        p.setText("手机消费撤销 :" + UpCardSettingAty.isUPCARD_VOID(), t2_l).add();
        p.setText("手机退货 :" + UpCardSettingAty.isUPCARD_REFUND(), t2_l).add();
        p.setText("手机预授权 :" + UpCardSettingAty.isUPCARD_AUTH(), t2_l).add();
        p.setText("手机预授权撤销 :" + UpCardSettingAty.isUPCARD_CANCEL(), t2_l).add();
        p.setText("手机预授权完成（请求） :" + UpCardSettingAty.isUPCARD_AUTH_COMPLETE(), t2_l).add();
        p.setText("手机预授权完成（通知） :" + UpCardSettingAty.isUPCARD_AUTH_SETTLEMENT(), t2_l).add();
        p.setText("手机预授权完成撤销 :" + UpCardSettingAty.isUPCARD_COMPLETE_VOID(), t2_l).add();
        p.setText("手机余额查询 :" + UpCardSettingAty.isUPCARD_QUERY_BALANCE(), t2_l).add();
        p.setText("预约消费 :" + ReservationTypeSettingAty.isRESERVATION_SALE(), t2_l).add();
        p.setText("预约消费撤销 :" + ReservationTypeSettingAty.isRESERVATION_VOID(), t2_l).add();
        p.setText("订购消费 :" + MOTOTypeSettingAty.isMotoSale(), t2_l).add();
        p.setText("订购消费撤销 :" + MOTOTypeSettingAty.isMotoVoid(), t2_l).add();
        p.setText("订购退货 :" + MOTOTypeSettingAty.isMotoRefund(), t2_l).add();
        p.setText("订购预授权 :" + MOTOTypeSettingAty.isMotoAuth(), t2_l).add();
        p.setText("订购预授权撤销 :" + MOTOTypeSettingAty.isMotoCancel(), t2_l).add();
        p.setText("订购预授权完成（请求） :" + MOTOTypeSettingAty.isMotoAuthComplete(), t2_l).add();
        p.setText("订购预授权完成（通知） :" + MOTOTypeSettingAty.isMotoAuthSettlement(), t2_l).add();
        p.setText("订购预授权完成撤销 :" + MOTOTypeSettingAty.isMotoCompleteVoid(), t2_l).add();
        p.setText("订购持卡人身份验证 :" + MOTOTypeSettingAty.isMotoVerify(), t2_l).add();
        p.setText("磁条卡现金充值 :" + SPTools.get(OtherTypeSettingAty.KEY_ACCOUNT_LOAD_CASH, false), t2_l).add();
        p.setText("磁条卡账户充值 :" + SPTools.get(OtherTypeSettingAty.KEY_ACCOUNT_LOAD_ACCOUNT, false), t2_l).add();
        p.setText("结算是否自动签退 :" + TradeSettlementSettingAty.getNeedAutoSignout(), t2_l).add();
        p.setText("结算是否打印明细 :" + TradeSettlementSettingAty.getNeedPrintDetail(), t2_l).add();
        p.setText("结算是否打印失败明细 :" + TradeSettlementSettingAty.getNeedPrintFailure(), t2_l).add();
        p.setText("离线上送方式 :" + TradeOfflineSettingAty.getUploadType(), t2_l).add();
        p.setText("离线/消息重发次数 :" + TradeOfflineSettingAty.getUploadTimes(), t2_l).add();
        p.setText("是否输入主管密码 :" + TradeOtherSettingAty.isManagerPWD(), t2_l).add();
        p.setText("是否允许手输卡号 :" + TradeOtherSettingAty.getNeedGetcardManually(), t2_l).add();
        p.setText("预授权是否允许手输卡号 :" + TradeOtherSettingAty.getNeedGetcardManuallyAuth(), t2_l).add();
        p.setText("磁条卡账户充值 :" + TradeOtherSettingAty.getDefaultTrans(), t2_l).add();
        p.setText("退货限额 :" + TradeOtherSettingAty.getRefundLimit(), t2_l).add();
        p.setText("是否磁道加密 :" + TradeOtherSettingAty.getNeedTrackEncrypt(), t2_l).add();
        return p;
    }

    /**
     * 打印系统控制
     *
     * @return
     */
    public static PrintClient PrintSysParam() throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("=======银联EMV参数=======", t2_l).add();
        p.setText("******系统控制******", t2_l).add();
        p.setText("当前流水号 :" + SystemParSettingAty.getTrace(), t2_l).add();
        p.setText("当前批次号 :" + SystemParSettingAty.getBatch(), t2_l).add();
        p.setText("是否打印中文收单行 :" + SystemParSettingAty.getNeedPrintAcquirerName(), t2_l).add();
        p.setText("是否打印中文发卡行 :" + SystemParSettingAty.getNeedPrintIssuerName(), t2_l).add();
//        p.setText("套打签购单样式 :" + SPTools.get(SystemParSettingAty.KEY_SALES_SLIP_TYPE, "空白签购单"), t2_l).add();
        p.setText("热敏打印联数 :" + SystemParSettingAty.getPrintNumber(), t2_l).add();
        p.setText("签购单是否打英文 :" + SystemParSettingAty.getSlipHasEnglish(), t2_l).add();
        p.setText("冲正重发次数 :" + SystemParSettingAty.getReverseTimes(), t2_l).add();
        p.setText("最大交易笔数 :" + SystemParSettingAty.getMaxTradeNumber(), t2_l).add();
        p.setText("小费比例 :" + SystemParSettingAty.getFeeRate(), t2_l).add();
        return p;
    }

    /**
     * 通讯参数
     *
     * @return
     * @throws JSONException
     */
    public static PrintClient PrintCommuParam() throws JSONException {
        PrintClient p = new PrintClient();
        String commuType = CommuParmAty.getCommuType();
        p.setText("=======银联EMV参数=======", t2_l).add();
        p.setText("通讯方式 :" + commuType, t2_l).add();
        if ("无线".equals(commuType)) {
            p.setText("用户名 :" + CommuParmWireLessFragment.getUserNameSetting(), t2_l).add();
            p.setText("用户密码 :" + CommuParmWireLessFragment.getUserPwdSetting(), t2_l).add();
            p.setText("主机IP :" + CommuParmWireLessFragment.getSetHostIp(), t2_l).add();
            p.setText("主机端口 :" + CommuParmWireLessFragment.getSetHostPort(), t2_l).add();
            p.setText("备份主机IP :" + CommuParmWireLessFragment.getSetBackupIp(), t2_l).add();
            p.setText("备份主机端口 :" + CommuParmWireLessFragment.getSetBackupPort(), t2_l).add();
        } else if ("WiFi".equals(commuType)) {
            p.setText("主机IP :" + CommuParmWifiFragment.getHostIp(), t2_l).add();
            p.setText("主机端口 :" + CommuParmWifiFragment.getHostPost(), t2_l).add();
            p.setText("备份主机IP :" + CommuParmWifiFragment.getBackupIp(), t2_l).add();
            p.setText("备份主机端口 :" + CommuParmWifiFragment.getBackupPort(), t2_l).add();
        } else if ("以太网".equals(commuType)) {
            p.setText("主机IP :" + CommuParmEthernetFragment.getSetHostIp(), t2_l).add();
            p.setText("主机端口 :" + CommuParmEthernetFragment.getSetHostPort(), t2_l).add();
            p.setText("备份主机IP :" + CommuParmEthernetFragment.getSetBackupIp(), t2_l).add();
            p.setText("备份主机端口 :" + CommuParmEthernetFragment.getSetBackupPort(), t2_l).add();
        } else if ("拨号".equals(commuType)) {
            p.setText("外线号码 :" + CommuParmDialFragment.getOutsideCall(), t2_l).add();
            p.setText("主机电话1 :" + CommuParmDialFragment.getMainPhoneNum1(), t2_l).add();
            p.setText("主机电话2 :" + CommuParmDialFragment.getMainPhoneNum2(), t2_l).add();
            p.setText("主机电话3 :" + CommuParmDialFragment.getMainPhoneNum3(), t2_l).add();
            p.setText("拨号方式 :" + CommuParmDialFragment.getDialStyle(), t2_l).add();
        }
        return p;
    }

    /**
     * 其他参数
     *
     * @return
     */
    public static PrintClient PrintOtherParam() throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("=======银联EMV参数=======", t2_l).add();
        p.setText("******刷卡输密控制******", t2_l).add();
        p.setText("消费撤销是否刷卡 :" + TradeSwipCardSettingAty.isVOID(), t2_l).add();
        p.setText("预授权完成撤销是否刷卡 :" + TradeSwipCardSettingAty.isCOMPLETE_VOID(), t2_l).add();
        p.setText("消费撤销是否输密码 :" + TradeInputPwdSettingAty.isVoid(), t2_l).add();
        p.setText("分期付款撤销是否输密码 :" + TradeInputPwdSettingAty.isINSTALLMENT_VOID(), t2_l).add();
        p.setText("预授权撤销是否输密码 :" + TradeInputPwdSettingAty.isCANCEL(), t2_l).add();
        p.setText("预授权完成撤销是否输密码 :" + TradeInputPwdSettingAty.isCANCEL(), t2_l).add();
        p.setText("预授权完成联机是否输密码 :" + TradeInputPwdSettingAty.isAUTH_COMPLETE(), t2_l).add();
        return p;
    }

    public static PrintClient PrintVersion() throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("=======银联EMV参数=======", t2_l).add();
        p.setText("******版本号信息******", t2_l).add();
        p.setText("程序版本号 :" + AppConfig.POS_MODEL, t2_l).add();
        return p;
    }

    public static PrintClient PrintTxnList(List<TransactionData> tds) throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("交易明细/TXN LIST", t2_l).add();
        p.setText("-----------------------------------------", t2_l).add();
        p.setText("凭证号  类型  金额  卡号  授权码", t2_l).add();
        p.setText("VOUCHER   TYPE      AMT      CARD     AUTH", t1_l).add();
        for (TransactionData td : tds) {
            p.setText(td.getTrace() + " " + td.getName() + " " + PosUtil.changeAmout(td.getAmount()), t2_l).add();
            p.setText(TDevice.hiddenCardNum(td.getPan()) + " " + td.getAuthCode(), t2_l).add();
        }
        p.setText("-----------------------------------------", t2_l).add();
        p.addTPrintDataBottom(bottom1).add().TSave();
        return p;
    }

    public static PrintClient PrintFaileTxnList(List<TransactionData> tds) throws JSONException {
        PrintClient p = new PrintClient();
        p.setText("失败的交易明细/FAILE TXN LIST", t2_l).add();
        p.setText("-----------------------------------------", t2_l).add();
        p.setText("凭证号  类型  金额  卡号  授权码", t2_l).add();
        p.setText("VOUCHER   TYPE      AMT      CARD     AUTH", t1_l).add();
        if (tds != null && tds.size() > 0) {
            for (TransactionData td : tds) {
                if (td.getTrace() != null && td.getName() != null && td.getAmount() != null && td.getPan() != null && !"".equals(td.getPan()) && !"".equals(td.getAmount())) {
                    p.setText(td.getTrace() + " " + td.getName() + " " + PosUtil.changeAmout(td.getAmount()), t2_l).add();
                    p.setText(TDevice.hiddenCardNum(td.getPan()) + " " + td.getAuthCode(), t2_l).add();
                }
            }
        }
        p.setText("-----------------------------------------", t2_l).add();
        p.addTPrintDataBottom(bottom1).add().TSave();
        return p;
    }

    public static PrintClient PrintReverseError() throws Exception {
        PrintClient p = new PrintClient();
        Iso8583Manager iso = Reverse.getOldReverseIso();
        p.setText(AppContext.getInstance().getString(R.string.chongzhengguzhangdan), t3_c).add();
        p.setText("商户名(MERCHANT NAME):" + SettingConstant.getMERCHANT_NAME(), t2_l).add();
        p.setText("商户编号(MERCHANT NO):" + SettingConstant.getMERCHANT_NO(), t2_l).add();
        p.setText("终端号(TERMINAL NO):" + SettingConstant.getTERMINAL_NO(), t2_l).add();
        String bno = iso.getBit(60).substring(2, 8);
        p.setText("批次号(BATCH NO):" + bno, t2_l).add();
        p.setText("凭证号(VOUCHER NO):" + iso.getBit(11), t2_l).add();
        p.setText("交易类别(TRANS TYPE):" + ActionConstant.getAction(Reverse.getReverseAction(), true), t2_l).add();
        if (!TextUtils.isEmpty(iso.getBit(4))) {
            p.setText("交易金额(AMOUNT):", t2_l).add();
            p.setText("RMB：" + PosUtil.fenToYuan(iso.getBit(4)), t3_l).add();
        }
        p.setText("-----------------------------------------", t2_l).add();
        p.setText(AppContext.getInstance().getString(R.string.chongzhengbuchenggong), t2_l).add();
        p.setText(AppContext.getInstance().getString(R.string.cidanjuzhiweishoudanjigoufenxi), t2_l).add();
        return p;
    }
}
