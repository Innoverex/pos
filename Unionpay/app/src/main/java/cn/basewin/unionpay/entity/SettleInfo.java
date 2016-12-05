package cn.basewin.unionpay.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/19 10:49<br>
 * 描述：
 */
@Table(name = "SettleInfo")
public class SettleInfo implements Serializable {
    @Column(name = "id", isId = true)
    private int id;
    /**
     * 商户名
     */
    @Column(name = "merchantName")
    private String merchantName;
    /**
     * 商户号
     */
    @Column(name = "merchantId")
    private String merchantId;
    /**
     * 终端号
     */
    @Column(name = "terminalId")
    private String terminalId;
    /**
     * 批次号
     */
    @Column(name = "batchNo")
    private String batchNo;
    /**
     * 交易日期
     */
    @Column(name = "date")
    private String date;
    /**
     * 交易时间
     */
    @Column(name = "time")
    private String time;
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
     * 内卡对帐状态('1'、'2')
     */
    @Column(name = "status_n")
    private String status_n;
    /**
     * 外卡对帐状态('1'、'2')
     */
    @Column(name = "status_w")
    private String status_w;

    /**
     * 内卡借记卡总金额
     */
    @Column(name = "totalmoney_debit_n")
    private String totalmoney_debit_n;

    /**
     * 内卡借记卡总笔数
     */
    @Column(name = "totalitem_debit_n")
    private String totalitem_debit_n;
    /**
     * 内卡贷记卡总金额
     */
    @Column(name = "totalmoney_credit_n")
    private String totalmoney_credit_n;
    /**
     * 内卡贷记卡总笔数
     */
    @Column(name = "totalitem_credit_n")
    private String totalitem_credit_n;
    /**
     * 外卡借记卡总金额
     */
    @Column(name = "totalmoney_debit_w")
    private String totalmoney_debit_w;
    /**
     * 外卡借记卡总笔数
     */
    @Column(name = "totalitem_debit_w")
    private String totalitem_debit_w;
    /**
     * 外卡贷记卡总金额
     */
    @Column(name = "totalmoney_credit_w")
    private String totalmoney_credit_w;
    /**
     * 外卡贷记卡总笔数
     */
    @Column(name = "totalitem_credit_w")
    private String totalitem_credit_w;

    public String getAcqId() {
        return acqId;
    }

    public void setAcqId(String acqId) {
        this.acqId = acqId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getStatus_n() {
        return status_n;
    }

    public void setStatus_n(String status_n) {
        this.status_n = status_n;
    }

    public String getStatus_w() {
        return status_w;
    }

    public void setStatus_w(String status_w) {
        this.status_w = status_w;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotalitem_credit_n() {
        return totalitem_credit_n;
    }

    public void setTotalitem_credit_n(String totalitem_credit_n) {
        this.totalitem_credit_n = totalitem_credit_n;
    }

    public String getTotalitem_credit_w() {
        return totalitem_credit_w;
    }

    public void setTotalitem_credit_w(String totalitem_credit_w) {
        this.totalitem_credit_w = totalitem_credit_w;
    }

    public String getTotalitem_debit_n() {
        return totalitem_debit_n;
    }

    public void setTotalitem_debit_n(String totalitem_debit_n) {
        this.totalitem_debit_n = totalitem_debit_n;
    }

    public String getTotalitem_debit_w() {
        return totalitem_debit_w;
    }

    public void setTotalitem_debit_w(String totalitem_debit_w) {
        this.totalitem_debit_w = totalitem_debit_w;
    }

    public String getTotalmoney_credit_n() {
        return totalmoney_credit_n;
    }

    public void setTotalmoney_credit_n(String totalmoney_credit_n) {
        this.totalmoney_credit_n = totalmoney_credit_n;
    }

    public String getTotalmoney_credit_w() {
        return totalmoney_credit_w;
    }

    public void setTotalmoney_credit_w(String totalmoney_credit_w) {
        this.totalmoney_credit_w = totalmoney_credit_w;
    }

    public String getTotalmoney_debit_n() {
        return totalmoney_debit_n;
    }

    public void setTotalmoney_debit_n(String totalmoney_debit_n) {
        this.totalmoney_debit_n = totalmoney_debit_n;
    }

    public String getTotalmoney_debit_w() {
        return totalmoney_debit_w;
    }

    public void setTotalmoney_debit_w(String totalmoney_debit_w) {
        this.totalmoney_debit_w = totalmoney_debit_w;
    }

    @Override
    public String toString() {
        return "SettleInfo{" +
                "id=" + id +
                ", merchantName='" + merchantName + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", issuerId='" + issuerId + '\'' +
                ", acqId='" + acqId + '\'' +
                ", status_n='" + status_n + '\'' +
                ", status_w='" + status_w + '\'' +
                ", totalmoney_debit_n='" + totalmoney_debit_n + '\'' +
                ", totalitem_debit_n='" + totalitem_debit_n + '\'' +
                ", totalmoney_credit_n='" + totalmoney_credit_n + '\'' +
                ", totalitem_credit_n='" + totalitem_credit_n + '\'' +
                ", totalmoney_debit_w='" + totalmoney_debit_w + '\'' +
                ", totalitem_debit_w='" + totalitem_debit_w + '\'' +
                ", totalmoney_credit_w='" + totalmoney_credit_w + '\'' +
                ", totalitem_credit_w='" + totalitem_credit_w + '\'' +
                '}';
    }
}
