package org.fms.billing.common.webapp.domain.beakInterface;

import java.util.Date;
//银行接口请求实体
public class BankInterfaceDomain {
    //户号
    private String cusCode;
    //电费
    private double payFee;
    //违约金
    private double punishMoney;
    //月份
    private int feeDate;
    //电费次数
    private int monSn;
    //
    private Date mtDatetime;
    //银行代码
    private String bankCode;
    //银行账号
    private String cusAccount;
    //户名
    private String cusName;

    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    public double getPayFee() {
        return payFee;
    }

    public void setPayFee(double payFee) {
        this.payFee = payFee;
    }

    public double getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(double punishMoney) {
        this.punishMoney = punishMoney;
    }

    public int getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(int feeDate) {
        this.feeDate = feeDate;
    }

    public int getMonSn() {
        return monSn;
    }

    public void setMonSn(int monSn) {
        this.monSn = monSn;
    }

    public Date getMtDatetime() {
        return mtDatetime;
    }

    public void setMtDatetime(Date mtDatetime) {
        this.mtDatetime = mtDatetime;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCusAccount() {
        return cusAccount;
    }

    public void setCusAccount(String cusAccount) {
        this.cusAccount = cusAccount;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
}
