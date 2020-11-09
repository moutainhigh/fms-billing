package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Transient;

public class AppBulkRefund {
    private Long id;

    /**
     * 工作单号
     */
    private String appId;

    /**
     * 环节号
     */
    private Integer taskId;

    /**
     * 实例号
     */
    private Long processInstanceId;

    /**
     * 结算户id
     */
    private Long settlementId;

    /**
     * 结算户NO
     */
    private String settlementNo;

    /**
     * 结算户名字
     */
    private String settlementName;

    /**
     * 营业区域号
     */
    private Integer businessPlaceCode;

    /**
     * 申请人id
     */
    private Integer applyMan;

    /**
     * 申请日期
     */
    private Date applyDate;

    /**
     * 审批人id
     */
    private Integer passMan;

    /**
     * 审批时间
     */
    private Date passDate;

    /**
     * 退费
     */
    private BigDecimal refundMoney;

    /**
     * 审批结论
     */
    private Integer passOption;

    /**
     * 生效月份
     */
    private Integer mon;

    /**
     * 生效月份次数
     */
    private Short monSn;

    @Transient
    private List<Long> settlementIds;

    public List<Long> getSettlementIds() {
        return settlementIds;
    }

    public void setSettlementIds(List<Long> settlementIds) {
        this.settlementIds = settlementIds;
    }

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取工作单号
     *
     * @return APP_ID - 工作单号
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置工作单号
     *
     * @param appId 工作单号
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 获取环节号
     *
     * @return TASK_ID - 环节号
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 设置环节号
     *
     * @param taskId 环节号
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取实例号
     *
     * @return PROCESS_INSTANCE_ID - 实例号
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * 设置实例号
     *
     * @param processInstanceId 实例号
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * 获取结算户id
     *
     * @return SETTLEMENT_ID - 结算户id
     */
    public Long getSettlementId() {
        return settlementId;
    }

    /**
     * 设置结算户id
     *
     * @param settlementId 结算户id
     */
    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    /**
     * 获取结算户NO
     *
     * @return SETTLEMENT_NO - 结算户NO
     */
    public String getSettlementNo() {
        return settlementNo;
    }

    /**
     * 设置结算户NO
     *
     * @param settlementNo 结算户NO
     */
    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo == null ? null : settlementNo.trim();
    }

    /**
     * 获取结算户名字
     *
     * @return SETTLEMENT_NAME - 结算户名字
     */
    public String getSettlementName() {
        return settlementName;
    }

    /**
     * 设置结算户名字
     *
     * @param settlementName 结算户名字
     */
    public void setSettlementName(String settlementName) {
        this.settlementName = settlementName == null ? null : settlementName.trim();
    }

    /**
     * 获取营业区域号
     *
     * @return BUSINESS_PLACE_CODE - 营业区域号
     */
    public Integer getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    /**
     * 设置营业区域号
     *
     * @param businessPlaceCode 营业区域号
     */
    public void setBusinessPlaceCode(Integer businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    /**
     * 获取申请人id
     *
     * @return APPLY_MAN - 申请人id
     */
    public Integer getApplyMan() {
        return applyMan;
    }

    /**
     * 设置申请人id
     *
     * @param applyMan 申请人id
     */
    public void setApplyMan(Integer applyMan) {
        this.applyMan = applyMan;
    }

    /**
     * 获取申请日期
     *
     * @return APPLY_DATE - 申请日期
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * 设置申请日期
     *
     * @param applyDate 申请日期
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * 获取审批人id
     *
     * @return PASS_MAN - 审批人id
     */
    public Integer getPassMan() {
        return passMan;
    }

    /**
     * 设置审批人id
     *
     * @param passMan 审批人id
     */
    public void setPassMan(Integer passMan) {
        this.passMan = passMan;
    }

    /**
     * 获取审批时间
     *
     * @return PASS_DATE - 审批时间
     */
    public Date getPassDate() {
        return passDate;
    }

    /**
     * 设置审批时间
     *
     * @param passDate 审批时间
     */
    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    /**
     * 获取退费
     *
     * @return REFUND_MONEY - 退费
     */
    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    /**
     * 设置退费
     *
     * @param refundMoney 退费
     */
    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    /**
     * 获取审批结论
     *
     * @return PASS_OPTION - 审批结论
     */
    public Integer getPassOption() {
        return passOption;
    }

    /**
     * 设置审批结论
     *
     * @param passOption 审批结论
     */
    public void setPassOption(Integer passOption) {
        this.passOption = passOption;
    }

    /**
     * 获取生效月份
     *
     * @return MON - 生效月份
     */
    public Integer getMon() {
        return mon;
    }

    /**
     * 设置生效月份
     *
     * @param mon 生效月份
     */
    public void setMon(Integer mon) {
        this.mon = mon;
    }

    /**
     * 获取生效月份次数
     *
     * @return MON_SN - 生效月份次数
     */
    public Short getMonSn() {
        return monSn;
    }

    /**
     * 设置生效月份次数
     *
     * @param monSn 生效月份次数
     */
    public void setMonSn(Short monSn) {
        this.monSn = monSn;
    }
}