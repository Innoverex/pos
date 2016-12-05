package cn.basewin.unionpay.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/19 10:16<br>
 * 描述：
 */
@Table(name = "TransactionData")
public class TransactionData implements Serializable {
    /**
     * ID
     */
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "action")
    private int action = 0;
    /**
     * 交易状态,'-'已撤销,'+'有效记录
     */
    @Column(name = "status")
    private String status;
    /**
     * 是否已调整  null未调整 '*'已调整
     */
    @Column(name = "isAdjust")
    private String isAdjust;
    /**
     * 脱线上送状态, '+'未上送,'*'已上送,'!'失败
     */
    @Column(name = "uploadStatus")
    private String uploadStatus;
    /**
     * 批上送上送状态, '+'未上送,'*'已上送,'!'失败
     */
    @Column(name = "batchStatus")
    private String batchStatus = "+";
    /**
     * 交易名称
     */
    @Column(name = "name")
    private String name = "";
    /**
     * 英文名称
     */
    @Column(name = "engName")
    private String engName = "";
    /**
     * 流水号
     */
    @Column(name = "trace")
    private String trace = "";
    /**
     * 批次号
     */
    @Column(name = "batch")
    private String batch = "";
    /**
     * 已发送的次数
     */
    @Column(name = "send")
    private String send = "";
    /**
     * 批上送时，已发送的次数
     */
    @Column(name = "batchSend")
    private String batchSend = "";
    /**
     * IC卡交易结果
     */
    @Column(name = "icResult")
    private String icResult = "";
    /**
     * IC条件码
     */
    @Column(name = "icCondition")
    private String icCondition = "";
    /**
     * 终端性能
     */
    @Column(name = "terminalCap")
    private String terminalCap = "";
    /**
     * 主帐号/转出帐号
     */
    @Column(name = "pan")
    private String pan = "";
    /**
     * 转入帐号
     */
    @Column(name = "pan_in")
    private String pan_in = "";
    /**
     * 过程码
     */
    @Column(name = "procCode")
    private String procCode = "";
    /**
     * 交易金额(单位：分)
     */
    @Column(name = "amount")
    private String amount = "";
    /**
     * 小费
     */
    @Column(name = "fee")
    private String fee = "";
    /**
     * 有小费时存在 amount-fee
     */
    @Column(name = "realAmount")
    private String realAmount;
    /**
     * 卡有效期
     */
    @Column(name = "expDate")
    private String expDate = "";
    /**
     * 结算日期
     */
    @Column(name = "settleDate")
    private String settleDate = "";
    /**
     * pan输入方式(卡号输入方式 2位)
     */
    @Column(name = "im_pan")
    private String im_pan = "";
    /**
     * pin输入方式
     */
    @Column(name = "im_pin")
    private String im_pin = "";
    /**
     * 卡序列号
     */
    @Column(name = "cardSn")
    private String cardSn = "";
    /**
     * 22域 服务点输入方式码  02 刷卡 05 插卡 07非接
     */
    @Column(name = "serviceCode")
    private String serviceCode = "";
    /**
     * pin最大长度(26域)
     */
    @Column(name = "maxPinLen")
    private String maxPinLen = "";
    /**
     * 货币代码(一般写死：156)
     */
    @Column(name = "currency")
    private String currency = "156";
    /**
     * 2磁道，脱机交易用
     */
    @Column(name = "track2")
    private String track2 = "";
    /**
     * 3磁道，脱机交易用
     */
    @Column(name = "track3")
    private String track3 = "";
    /**
     * PIN码，脱机交易用
     */
    @Column(name = "pin")
    private String pin = "";
    /**
     * 53域，脱机交易上送
     */
    @Column(name = "field53")
    private String field53 = "";
    /**
     * 55域，IC卡交易数据
     */
    @Column(name = "field55")
    private String field55 = "";
    /**
     * 61域，脱机交易用
     */
    @Column(name = "field61")
    private String field61 = "";
    /**
     * 63域，脱机交易用，escape表明当前字段保存需要对特殊字符转义
     */
    @Column(name = "field63")
    private String field63 = "";
    /**
     * 交易日期
     */
    @Column(name = "date")
    private String date = "";
    /**
     * 交易时间
     */
    @Column(name = "time")
    private String time = "";
    /**
     * 年份F
     */
    @Column(name = "year")
    private String year = "";
    /**
     * 参考号
     */
    @Column(name = "referenceNo")
    private String referenceNo = "";
    /**
     * 授权码
     */
    @Column(name = "authCode")
    private String authCode = "";
    /**
     * 原交易号
     */
    @Column(name = "oldTrace")
    private String oldTrace;
    /**
     * 原交易授权码
     */
    @Column(name = "oldAuthCode")
    private String oldAuthCode;
    /**
     * 卡机构
     */
    @Column(name = "cardId")
    private String cardId;
    /**
     * 发卡行保留信息
     */
    @Column(name = "issuerMsg")
    private String issuerMsg;
    /**
     * 银联保留信息
     */
    @Column(name = "cupMsg")
    private String cupMsg;
    /**
     * 受理机构保留信息
     */
    @Column(name = "aquMsg")
    private String aquMsg;
    /**
     * 发卡行ID
     */
    @Column(name = "issuerId")
    private String issuerId;
    /**
     * 收单行ID
     */
    @Column(name = "acqId")
    private String acqId;
    /**
     * 交易中心代码
     */
    @Column(name = "posCenter")
    private String posCenter;
    /**
     * 授权模式
     */
    @Column(name = "authMode")
    private String authMode;
    /**
     * 授权组织
     */
    @Column(name = "authOrgan")
    private String authOrgan;
    /**
     * 输入的过期时间
     */
    @Column(name = "expiredDateInput")
    private String expiredDateInput;
    // 分期
    /**
     * 期数
     */
    @Column(name = "installment_period")
    private String installment_period;
    /**
     * 币种
     */
    @Column(name = "installment_currency")
    private String installment_currency;
    /**
     * 还款方式(一次性支付、分期支付)
     */
    @Column(name = "installment_firstPa")
    private String installment_firstPa;
    /**
     * 还款金额
     */
    @Column(name = "installment_payWay")
    private String installment_payWay;
    /**
     * 一次性手续费/首期手续费
     */
    @Column(name = "installment_firstFee")
    private String installment_firstFee;
    /**
     * 每期手续费
     */
    @Column(name = "installment_eachFe")
    private String installment_eachFe;
    /**
     * 积分
     */
    @Column(name = "installment_bounds")
    private String installment_bounds;
    // 订购
    /**
     * 手机
     */
    @Column(name = "phone")
    private String phone;
    /**
     * 姓名
     */
    @Column(name = "persionName")
    private String persionName;
    /**
     * 身份证号
     */
    @Column(name = "persionId")
    private String persionId;
    // 积分
    /**
     * 商品代码
     */
    @Column(name = "product")
    private String product;
    /**
     * 兑换积分数
     */
    @Column(name = "bounds")
    private String bounds;
    /**
     * 积分金额
     */
    @Column(name = "bounds_balance")
    private String bounds_balance;
    /**
     * 自付金额
     */
    @Column(name = "pay_amount")
    private String pay_amount;
    // 其它
    /**
     * 操作员
     */
    @Column(name = "operator")
    private String operator;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 结算类型, 1借记、2贷记、其它都是结算无关
     */
    @Column(name = "settleType")
    private String settleType;
    /**
     * 卡类型, 1内卡、2外卡
     */
    @Column(name = "cardType")
    private String cardType = "1";
    /**
     * 结算属性
     */
    @Column(name = "settleAttr")
    private String settleAttr;
    /**
     * 余额
     */
    @Column(name = "balance")
    private String balance;
    // 电子签名数据
    /**
     * 电子签名数据上送状态, 'n'未上送,'s'已上送,'f'失败
     */
    @Column(name = "signDataStatus")
    private String signDataStatus;
    /**
     * 电子签名已发送的次数
     */
    @Column(name = "signDataSend")
    private String signDataSend;
    /**
     * 交易特征码
     */
    @Column(name = "specialCode")
    private String specialCode;
    /**
     * 电子签字数据
     */
    @Column(name = "signData")
    private byte[] signData;

    /**
     * 电子签字图片路径
     */
    @Column(name = "signPath")
    private String signPath;
    /**
     * 电子签字上送IC卡数据
     */
    @Column(name = "signICData")
    private String signICData;
    @Column(name = "sign_phone")
    private String sign_phone;
    @Column(name = "sign_balance")
    private String sign_balance;
    /**
     * 是否非接qps交易
     */
    @Column(name = "isQPSTrans")
    private String isQPSTrans;
    /**
     * 原参考号
     */
    @Column(name = "oldReference")
    private String oldReference;
    /**
     * 原交易日期
     */
    @Column(name = "oldDate")
    private String oldDate;

    /**
     * 是否已经打印
     */
    @Column(name = "needPrint")
    private boolean needPrint;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getOldAuthCode() {
        return oldAuthCode;
    }

    public void setOldAuthCode(String oldAuthCode) {
        this.oldAuthCode = oldAuthCode;
    }

    public String getAcqId() {
        return acqId;
    }

    public void setAcqId(String acqId) {
        this.acqId = acqId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAquMsg() {
        return aquMsg;
    }

    public void setAquMsg(String aquMsg) {
        this.aquMsg = aquMsg;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getAuthOrgan() {
        return authOrgan;
    }

    public void setAuthOrgan(String authOrgan) {
        this.authOrgan = authOrgan;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBatchSend() {
        return batchSend;
    }

    public void setBatchSend(String batchSend) {
        this.batchSend = batchSend;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getBounds() {
        return bounds;
    }

    public void setBounds(String bounds) {
        this.bounds = bounds;
    }

    public String getBounds_balance() {
        return bounds_balance;
    }

    public void setBounds_balance(String bounds_balance) {
        this.bounds_balance = bounds_balance;
    }

    public String getCarddId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCupMsg() {
        return cupMsg;
    }

    public void setCupMsg(String cupMsg) {
        this.cupMsg = cupMsg;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expiredDate) {
        this.expDate = expiredDate;
    }

    public String getExpiredDateInput() {
        return expiredDateInput;
    }

    public void setExpiredDateInput(String expiredDateInput) {
        this.expiredDateInput = expiredDateInput;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getField53() {
        return field53;
    }

    public void setField53(String field53) {
        this.field53 = field53;
    }

    public String getField55() {
        return field55;
    }

    public void setField55(String field55) {
        this.field55 = field55;
    }

    public String getField61() {
        return field61;
    }

    public void setField61(String field61) {
        this.field61 = field61;
    }

    public String getField63() {
        return field63;
    }

    public void setField63(String field63) {
        this.field63 = field63;
    }

    public String getIcCondition() {
        return icCondition;
    }

    public void setIcCondition(String icCondition) {
        this.icCondition = icCondition;
    }

    public String getIcResult() {
        return icResult;
    }

    public void setIcResult(String icResult) {
        this.icResult = icResult;
    }

    public String getIm_pan() {
        return im_pan;
    }

    public void setIm_pan(String im_pan) {
        this.im_pan = im_pan;
    }

    public String getIm_pin() {
        return im_pin;
    }

    public void setIm_pin(String im_pin) {
        this.im_pin = im_pin;
    }

    public String getInstallment_bounds() {
        return installment_bounds;
    }

    public void setInstallment_bounds(String installment_bounds) {
        this.installment_bounds = installment_bounds;
    }

    public String getInstallment_currency() {
        return installment_currency;
    }

    public void setInstallment_currency(String installment_currency) {
        this.installment_currency = installment_currency;
    }

    public String getInstallment_eachFe() {
        return installment_eachFe;
    }

    public void setInstallment_eachFe(String installment_eachFe) {
        this.installment_eachFe = installment_eachFe;
    }

    public String getInstallment_firstFee() {
        return installment_firstFee;
    }

    public void setInstallment_firstFee(String installment_firstFee) {
        this.installment_firstFee = installment_firstFee;
    }

    public String getInstallment_firstPa() {
        return installment_firstPa;
    }

    public void setInstallment_firstPa(String installment_firstPa) {
        this.installment_firstPa = installment_firstPa;
    }

    public String getInstallment_payWay() {
        return installment_payWay;
    }

    public void setInstallment_payWay(String installment_payWay) {
        this.installment_payWay = installment_payWay;
    }

    public String getInstallment_period() {
        return installment_period;
    }

    public void setInstallment_period(String installment_period) {
        this.installment_period = installment_period;
    }

    public String getIsAdjust() {
        return isAdjust;
    }

    public void setIsAdjust(String isAdjust) {
        this.isAdjust = isAdjust;
    }

    public String getIsQPSTrans() {
        return isQPSTrans;
    }

    public void setIsQPSTrans(String isQPSTrans) {
        this.isQPSTrans = isQPSTrans;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerMsg() {
        return issuerMsg;
    }

    public void setIssuerMsg(String issuerMsg) {
        this.issuerMsg = issuerMsg;
    }

    public String getMaxPinLen() {
        return maxPinLen;
    }

    public void setMaxPinLen(String maxPinLen) {
        this.maxPinLen = maxPinLen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldTrace() {
        return oldTrace;
    }

    public void setOldTrace(String oldTrace) {
        this.oldTrace = oldTrace;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPan_in() {
        return pan_in;
    }

    public void setPan_in(String pan_in) {
        this.pan_in = pan_in;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPersionId() {
        return persionId;
    }

    public void setPersionId(String persionId) {
        this.persionId = persionId;
    }

    public String getPersionName() {
        return persionName;
    }

    public void setPersionName(String persionName) {
        this.persionName = persionName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPosCenter() {
        return posCenter;
    }

    public void setPosCenter(String posCenter) {
        this.posCenter = posCenter;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public String getProduct() {
        return product == null ? "" : product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getSettleAttr() {
        return settleAttr;
    }

    public void setSettleAttr(String settleAttr) {
        this.settleAttr = settleAttr;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getSign_balance() {
        return sign_balance;
    }

    public void setSign_balance(String sign_balance) {
        this.sign_balance = sign_balance;
    }

    public String getSign_phone() {
        return sign_phone;
    }

    public void setSign_phone(String sign_phone) {
        this.sign_phone = sign_phone;
    }

    public byte[] getSignData() {
        return signData;
    }

    public void setSignData(byte[] signData) {
        this.signData = signData;
    }

    public String getSignDataSend() {
        return signDataSend;
    }

    public void setSignDataSend(String signDataSend) {
        this.signDataSend = signDataSend;
    }

    public String getSignDataStatus() {
        return signDataStatus;
    }

    public void setSignDataStatus(String signDataStatus) {
        this.signDataStatus = signDataStatus;
    }

    public String getSignICData() {
        return signICData;
    }

    public void setSignICData(String signICData) {
        this.signICData = signICData;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTerminalCap() {
        return terminalCap;
    }

    public void setTerminalCap(String terminalCap) {
        this.terminalCap = terminalCap;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) {
        this.track3 = track3;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getOldReference() {
        return oldReference;
    }

    public void setOldReference(String oldReference) {
        this.oldReference = oldReference;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public boolean isNeedPrint() {
        return needPrint;
    }

    public void setNeedPrint(boolean needPrint) {
        this.needPrint = needPrint;
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "id=" + id +
                ", action=" + action +
                ", status='" + status + '\'' +
                ", isAdjust='" + isAdjust + '\'' +
                ", uploadStatus='" + uploadStatus + '\'' +
                ", batchStatus='" + batchStatus + '\'' +
                ", name='" + name + '\'' +
                ", engName='" + engName + '\'' +
                ", trace='" + trace + '\'' +
                ", batch='" + batch + '\'' +
                ", send='" + send + '\'' +
                ", batchSend='" + batchSend + '\'' +
                ", icResult='" + icResult + '\'' +
                ", icCondition='" + icCondition + '\'' +
                ", terminalCap='" + terminalCap + '\'' +
                ", pan='" + pan + '\'' +
                ", pan_in='" + pan_in + '\'' +
                ", procCode='" + procCode + '\'' +
                ", amount='" + amount + '\'' +
                ", fee='" + fee + '\'' +
                ", realAmount='" + realAmount + '\'' +
                ", expDate='" + expDate + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", im_pan='" + im_pan + '\'' +
                ", im_pin='" + im_pin + '\'' +
                ", cardSn='" + cardSn + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", maxPinLen='" + maxPinLen + '\'' +
                ", currency='" + currency + '\'' +
                ", track2='" + track2 + '\'' +
                ", track3='" + track3 + '\'' +
                ", pin='" + pin + '\'' +
                ", field53='" + field53 + '\'' +
                ", field55='" + field55 + '\'' +
                ", field61='" + field61 + '\'' +
                ", field63='" + field63 + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", year='" + year + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", authCode='" + authCode + '\'' +
                ", oldTrace='" + oldTrace + '\'' +
                ", oldAuthCode='" + oldAuthCode + '\'' +
                ", cardId='" + cardId + '\'' +
                ", issuerMsg='" + issuerMsg + '\'' +
                ", cupMsg='" + cupMsg + '\'' +
                ", aquMsg='" + aquMsg + '\'' +
                ", issuerId='" + issuerId + '\'' +
                ", acqId='" + acqId + '\'' +
                ", posCenter='" + posCenter + '\'' +
                ", authMode='" + authMode + '\'' +
                ", authOrgan='" + authOrgan + '\'' +
                ", expiredDateInput='" + expiredDateInput + '\'' +
                ", installment_period='" + installment_period + '\'' +
                ", installment_currency='" + installment_currency + '\'' +
                ", installment_firstPa='" + installment_firstPa + '\'' +
                ", installment_payWay='" + installment_payWay + '\'' +
                ", installment_firstFee='" + installment_firstFee + '\'' +
                ", installment_eachFe='" + installment_eachFe + '\'' +
                ", installment_bounds='" + installment_bounds + '\'' +
                ", phone='" + phone + '\'' +
                ", persionName='" + persionName + '\'' +
                ", persionId='" + persionId + '\'' +
                ", product='" + product + '\'' +
                ", bounds='" + bounds + '\'' +
                ", bounds_balance='" + bounds_balance + '\'' +
                ", pay_amount='" + pay_amount + '\'' +
                ", operator='" + operator + '\'' +
                ", remark='" + remark + '\'' +
                ", settleType='" + settleType + '\'' +
                ", cardType='" + cardType + '\'' +
                ", settleAttr='" + settleAttr + '\'' +
                ", balance='" + balance + '\'' +
                ", signDataStatus='" + signDataStatus + '\'' +
                ", signDataSend='" + signDataSend + '\'' +
                ", specialCode='" + specialCode + '\'' +
                ", signData=" + Arrays.toString(signData) +
                ", signPath='" + signPath + '\'' +
                ", signICData='" + signICData + '\'' +
                ", sign_phone='" + sign_phone + '\'' +
                ", sign_balance='" + sign_balance + '\'' +
                ", isQPSTrans='" + isQPSTrans + '\'' +
                ", oldReference='" + oldReference + '\'' +
                ", oldDate='" + oldDate + '\'' +
                ", needPrint=" + needPrint +
                '}';
    }
}
