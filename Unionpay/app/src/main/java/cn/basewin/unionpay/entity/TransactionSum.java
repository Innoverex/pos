package cn.basewin.unionpay.entity;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

/**
 * 作者：lhc<br>
 * 创建时间：2016/7/19 10:45<br>
 * 描述：交易总计
 */
public class TransactionSum implements Serializable {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "engName")
    private String engName;
    @Column(name = "amount")
    private String amount;
    @Column(name = "num")
    private String num;
    @Column(name = "cardType")
    private String cardType;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
