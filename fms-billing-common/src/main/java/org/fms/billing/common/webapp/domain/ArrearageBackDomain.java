package org.fms.billing.common.webapp.domain;

import java.util.Date;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;
//抹账记录
public class ArrearageBackDomain extends ManagerParamEntity implements MybatisEntity {
    @TablePrimaryKey
    private Long id; //主键
    private String arrearageNo;//欠费凭证号
    private Long meterId;//计量点id
    private int sn; //算费次数
    private Double receivable;//chargeIdchargeId应收电费
    private int backMan;
    private Date backDate;
    private String backReson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArrearageNo() {
        return arrearageNo;
    }

    public void setArrearageNo(String arrearageNo) {
        this.arrearageNo = arrearageNo;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public Double getReceivable() {
        return receivable;
    }

    public void setReceivable(Double receivable) {
        this.receivable = receivable;
    }

    public int getBackMan() {
        return backMan;
    }

    public void setBackMan(int backMan) {
        this.backMan = backMan;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public String getBackReson() {
        return backReson;
    }

    public void setBackReson(String backReson) {
        this.backReson = backReson;
    }
}
