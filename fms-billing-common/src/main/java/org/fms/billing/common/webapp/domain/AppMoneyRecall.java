package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AppMoneyRecall {
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
     * 客户编号
     */
    private Long userId;

    /**
     * 户号
     */
    private String userNo;

    /**
     * 户名
     */
    private String userName;

    /**
     * 计量点Id
     */
    private Long meterId;

    /**
     * 计量点号
     */
    private String meterNo;

    /**
     * 计量点名称
     */
    private String meterName;

    /**
     * 退补月份
     */
    private Integer rpMon;

    /**
     * 退补的是monsn
     */
    private Short rpMonSn;

    /**
     * 用电类别
     */
    private Integer elecTypeCode;

    /**
     * 分时标志
     */
    private Short tsFlag;

    /**
     * 基本电费计算方法:0不计算;1按最大需量计算;2按容量计算;8按容量计算(化肥)
     */
    private Short baseMoneyFlag;

    /**
     * 力率标准
     */
    private Short cosType;

    /**
     * 如果是走工作单，则申请人、审批人等信息不用再记录
     */
    private String applyPerson;

    /**
     * 申请日期
     */
    private Date applyDate;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 审批人
     */
    private String passPerson;

    /**
     * 审批日期
     */
    private Date passDate;

    /**
     * 抄表区段id
     */
    private Long writeSectId;

    /**
     * 抄表区段号
     */
    private String writeSectNo;

    /**
     * 抄表区段名称
     */
    private String writeSectName;

    /**
     * 生效月份
     */
    private Integer mon;

    /**
     * 生效月份次数
     */
    private Short monSn;

    /**
     * 总电量
     */
    private BigDecimal totalPower;

    /**
     * 总电费
     */
    private BigDecimal totalMoney;

    /**
     * 电度电费
     */
    private BigDecimal volumeCharge;

    /**
     * 基本电费
     */
    private BigDecimal basicMoney;

    /**
     * 力调电费
     */
    private BigDecimal powerRateMoney;

    /**
     * 退补峰电量
     */
    private BigDecimal activeWritePower1;

    /**
     * 退补平电量
     */
    private BigDecimal activeWritePower2;

    /**
     * 退补谷电量
     */
    private BigDecimal activeWritePower3;

    /**
     * 退补尖电量
     */
    private BigDecimal activeWritePower4;

    /**
     * 目录电价
     */
    private BigDecimal addMoney1;

    /**
     * 国家水利工程建设基金
     */
    private BigDecimal addMoney2;

    /**
     * 城市公用事业附加费
     */
    private BigDecimal addMoney3;

    /**
     * 大中型水库移民后期扶持资金
     */
    private BigDecimal addMoney4;

    /**
     * 地方水库移民后期扶持资金
     */
    private BigDecimal addMoney5;

    /**
     * 可再生能源电价附加
     */
    private BigDecimal addMoney6;

    /**
     * 农网还贷资金
     */
    private BigDecimal addMoney7;

    /**
     * 农村电网维护费
     */
    private BigDecimal addMoney8;

    /**
     * 价格调节基金
     */
    private BigDecimal addMoney9;

    private BigDecimal addMoney10;

    /**
     * 退补方式
     */
    private Integer rpMode;

    /**
     * 电价编码
     */
    private Short priceType;

    /**
     * 结清标志
     */
    private Integer paidFlag;

    /**
     * 审批结论
     */
    private Integer passResult;

    /**
     * 申请原因
     */
    private String passReason;

    private Long settlementId;

    private Integer recallType; //1是 单户退费 2是批量退费

    public Integer getRecallType() {
        return recallType;
    }

    public void setRecallType(Integer recallType) {
        this.recallType = recallType;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
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
     * 获取客户编号
     *
     * @return USER_ID - 客户编号
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置客户编号
     *
     * @param userId 客户编号
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取户号
     *
     * @return USER_NO - 户号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 设置户号
     *
     * @param userNo 户号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    /**
     * 获取户名
     *
     * @return USER_NAME - 户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置户名
     *
     * @param userName 户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取计量点Id
     *
     * @return METER_ID - 计量点Id
     */
    public Long getMeterId() {
        return meterId;
    }

    /**
     * 设置计量点Id
     *
     * @param meterId 计量点Id
     */
    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    /**
     * 获取计量点号
     *
     * @return METER_NO - 计量点号
     */
    public String getMeterNo() {
        return meterNo;
    }

    /**
     * 设置计量点号
     *
     * @param meterNo 计量点号
     */
    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo == null ? null : meterNo.trim();
    }

    /**
     * 获取计量点名称
     *
     * @return METER_NAME - 计量点名称
     */
    public String getMeterName() {
        return meterName;
    }

    /**
     * 设置计量点名称
     *
     * @param meterName 计量点名称
     */
    public void setMeterName(String meterName) {
        this.meterName = meterName == null ? null : meterName.trim();
    }

    /**
     * 获取退补月份
     *
     * @return RP_MON - 退补月份
     */
    public Integer getRpMon() {
        return rpMon;
    }

    /**
     * 设置退补月份
     *
     * @param rpMon 退补月份
     */
    public void setRpMon(Integer rpMon) {
        this.rpMon = rpMon;
    }

    /**
     * 获取退补的是monsn
     *
     * @return RP_MON_SN - 退补的是monsn
     */
    public Short getRpMonSn() {
        return rpMonSn;
    }

    /**
     * 设置退补的是monsn
     *
     * @param rpMonSn 退补的是monsn
     */
    public void setRpMonSn(Short rpMonSn) {
        this.rpMonSn = rpMonSn;
    }

    public Integer getElecTypeCode() {
        return elecTypeCode;
    }

    public void setElecTypeCode(Integer elecTypeCode) {
        this.elecTypeCode = elecTypeCode;
    }

    /**
     * 获取分时标志
     *
     * @return TS_FLAG - 分时标志
     */
    public Short getTsFlag() {
        return tsFlag;
    }

    /**
     * 设置分时标志
     *
     * @param tsFlag 分时标志
     */
    public void setTsFlag(Short tsFlag) {
        this.tsFlag = tsFlag;
    }

    /**
     * 获取基本电费计算方法:0不计算;1按最大需量计算;2按容量计算;8按容量计算(化肥)
     *
     * @return BASE_MONEY_FLAG - 基本电费计算方法:0不计算;1按最大需量计算;2按容量计算;8按容量计算(化肥)
     */
    public Short getBaseMoneyFlag() {
        return baseMoneyFlag;
    }

    /**
     * 设置基本电费计算方法:0不计算;1按最大需量计算;2按容量计算;8按容量计算(化肥)
     *
     * @param baseMoneyFlag 基本电费计算方法:0不计算;1按最大需量计算;2按容量计算;8按容量计算(化肥)
     */
    public void setBaseMoneyFlag(Short baseMoneyFlag) {
        this.baseMoneyFlag = baseMoneyFlag;
    }

    /**
     * 获取力率标准
     *
     * @return COS_TYPE - 力率标准
     */
    public Short getCosType() {
        return cosType;
    }

    /**
     * 设置力率标准
     *
     * @param cosType 力率标准
     */
    public void setCosType(Short cosType) {
        this.cosType = cosType;
    }

    /**
     * 获取如果是走工作单，则申请人、审批人等信息不用再记录
     *
     * @return APPLY_PERSON - 如果是走工作单，则申请人、审批人等信息不用再记录
     */
    public String getApplyPerson() {
        return applyPerson;
    }

    /**
     * 设置如果是走工作单，则申请人、审批人等信息不用再记录
     *
     * @param applyPerson 如果是走工作单，则申请人、审批人等信息不用再记录
     */
    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson == null ? null : applyPerson.trim();
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
     * 获取申请原因
     *
     * @return APPLY_REASON - 申请原因
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * 设置申请原因
     *
     * @param applyReason 申请原因
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    /**
     * 获取审批人
     *
     * @return PASS_PERSON - 审批人
     */
    public String getPassPerson() {
        return passPerson;
    }

    /**
     * 设置审批人
     *
     * @param passPerson 审批人
     */
    public void setPassPerson(String passPerson) {
        this.passPerson = passPerson == null ? null : passPerson.trim();
    }

    /**
     * 获取审批日期
     *
     * @return PASS_DATE - 审批日期
     */
    public Date getPassDate() {
        return passDate;
    }

    /**
     * 设置审批日期
     *
     * @param passDate 审批日期
     */
    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    /**
     * 获取抄表区段id
     *
     * @return WRITE_SECT_ID - 抄表区段id
     */
    public Long getWriteSectId() {
        return writeSectId;
    }

    /**
     * 设置抄表区段id
     *
     * @param writeSectId 抄表区段id
     */
    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
    }

    /**
     * 获取抄表区段号
     *
     * @return WRITE_SECT_NO - 抄表区段号
     */
    public String getWriteSectNo() {
        return writeSectNo;
    }

    /**
     * 设置抄表区段号
     *
     * @param writeSectNo 抄表区段号
     */
    public void setWriteSectNo(String writeSectNo) {
        this.writeSectNo = writeSectNo == null ? null : writeSectNo.trim();
    }

    /**
     * 获取抄表区段名称
     *
     * @return WRITE_SECT_NAME - 抄表区段名称
     */
    public String getWriteSectName() {
        return writeSectName;
    }

    /**
     * 设置抄表区段名称
     *
     * @param writeSectName 抄表区段名称
     */
    public void setWriteSectName(String writeSectName) {
        this.writeSectName = writeSectName == null ? null : writeSectName.trim();
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

    /**
     * 获取总电量
     *
     * @return TOTAL_POWER - 总电量
     */
    public BigDecimal getTotalPower() {
        return totalPower;
    }

    /**
     * 设置总电量
     *
     * @param totalPower 总电量
     */
    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
    }

    /**
     * 获取总电费
     *
     * @return TOTAL_MONEY - 总电费
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * 设置总电费
     *
     * @param totalMoney 总电费
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * 获取电度电费
     *
     * @return VOLUME_CHARGE - 电度电费
     */
    public BigDecimal getVolumeCharge() {
        return volumeCharge;
    }

    /**
     * 设置电度电费
     *
     * @param volumeCharge 电度电费
     */
    public void setVolumeCharge(BigDecimal volumeCharge) {
        this.volumeCharge = volumeCharge;
    }

    /**
     * 获取基本电费
     *
     * @return BASIC_MONEY - 基本电费
     */
    public BigDecimal getBasicMoney() {
        return basicMoney;
    }

    /**
     * 设置基本电费
     *
     * @param basicMoney 基本电费
     */
    public void setBasicMoney(BigDecimal basicMoney) {
        this.basicMoney = basicMoney;
    }

    /**
     * 获取力调电费
     *
     * @return POWER_RATE_MONEY - 力调电费
     */
    public BigDecimal getPowerRateMoney() {
        return powerRateMoney;
    }

    /**
     * 设置力调电费
     *
     * @param powerRateMoney 力调电费
     */
    public void setPowerRateMoney(BigDecimal powerRateMoney) {
        this.powerRateMoney = powerRateMoney;
    }

    /**
     * 获取退补峰电量
     *
     * @return ACTIVE_WRITE_POWER_1 - 退补峰电量
     */
    public BigDecimal getActiveWritePower1() {
        return activeWritePower1;
    }

    /**
     * 设置退补峰电量
     *
     * @param activeWritePower1 退补峰电量
     */
    public void setActiveWritePower1(BigDecimal activeWritePower1) {
        this.activeWritePower1 = activeWritePower1;
    }

    /**
     * 获取退补平电量
     *
     * @return ACTIVE_WRITE_POWER_2 - 退补平电量
     */
    public BigDecimal getActiveWritePower2() {
        return activeWritePower2;
    }

    /**
     * 设置退补平电量
     *
     * @param activeWritePower2 退补平电量
     */
    public void setActiveWritePower2(BigDecimal activeWritePower2) {
        this.activeWritePower2 = activeWritePower2;
    }

    /**
     * 获取退补谷电量
     *
     * @return ACTIVE_WRITE_POWER_3 - 退补谷电量
     */
    public BigDecimal getActiveWritePower3() {
        return activeWritePower3;
    }

    /**
     * 设置退补谷电量
     *
     * @param activeWritePower3 退补谷电量
     */
    public void setActiveWritePower3(BigDecimal activeWritePower3) {
        this.activeWritePower3 = activeWritePower3;
    }

    /**
     * 获取退补尖电量
     *
     * @return ACTIVE_WRITE_POWER_4 - 退补尖电量
     */
    public BigDecimal getActiveWritePower4() {
        return activeWritePower4;
    }

    /**
     * 设置退补尖电量
     *
     * @param activeWritePower4 退补尖电量
     */
    public void setActiveWritePower4(BigDecimal activeWritePower4) {
        this.activeWritePower4 = activeWritePower4;
    }

    /**
     * 获取目录电价
     *
     * @return ADD_MONEY1 - 目录电价
     */
    public BigDecimal getAddMoney1() {
        return addMoney1;
    }

    /**
     * 设置目录电价
     *
     * @param addMoney1 目录电价
     */
    public void setAddMoney1(BigDecimal addMoney1) {
        this.addMoney1 = addMoney1;
    }

    /**
     * 获取国家水利工程建设基金
     *
     * @return ADD_MONEY2 - 国家水利工程建设基金
     */
    public BigDecimal getAddMoney2() {
        return addMoney2;
    }

    /**
     * 设置国家水利工程建设基金
     *
     * @param addMoney2 国家水利工程建设基金
     */
    public void setAddMoney2(BigDecimal addMoney2) {
        this.addMoney2 = addMoney2;
    }

    /**
     * 获取城市公用事业附加费
     *
     * @return ADD_MONEY3 - 城市公用事业附加费
     */
    public BigDecimal getAddMoney3() {
        return addMoney3;
    }

    /**
     * 设置城市公用事业附加费
     *
     * @param addMoney3 城市公用事业附加费
     */
    public void setAddMoney3(BigDecimal addMoney3) {
        this.addMoney3 = addMoney3;
    }

    /**
     * 获取大中型水库移民后期扶持资金
     *
     * @return ADD_MONEY4 - 大中型水库移民后期扶持资金
     */
    public BigDecimal getAddMoney4() {
        return addMoney4;
    }

    /**
     * 设置大中型水库移民后期扶持资金
     *
     * @param addMoney4 大中型水库移民后期扶持资金
     */
    public void setAddMoney4(BigDecimal addMoney4) {
        this.addMoney4 = addMoney4;
    }

    /**
     * 获取地方水库移民后期扶持资金
     *
     * @return ADD_MONEY5 - 地方水库移民后期扶持资金
     */
    public BigDecimal getAddMoney5() {
        return addMoney5;
    }

    /**
     * 设置地方水库移民后期扶持资金
     *
     * @param addMoney5 地方水库移民后期扶持资金
     */
    public void setAddMoney5(BigDecimal addMoney5) {
        this.addMoney5 = addMoney5;
    }

    /**
     * 获取可再生能源电价附加
     *
     * @return ADD_MONEY6 - 可再生能源电价附加
     */
    public BigDecimal getAddMoney6() {
        return addMoney6;
    }

    /**
     * 设置可再生能源电价附加
     *
     * @param addMoney6 可再生能源电价附加
     */
    public void setAddMoney6(BigDecimal addMoney6) {
        this.addMoney6 = addMoney6;
    }

    /**
     * 获取农网还贷资金
     *
     * @return ADD_MONEY7 - 农网还贷资金
     */
    public BigDecimal getAddMoney7() {
        return addMoney7;
    }

    /**
     * 设置农网还贷资金
     *
     * @param addMoney7 农网还贷资金
     */
    public void setAddMoney7(BigDecimal addMoney7) {
        this.addMoney7 = addMoney7;
    }

    /**
     * 获取农村电网维护费
     *
     * @return ADD_MONEY8 - 农村电网维护费
     */
    public BigDecimal getAddMoney8() {
        return addMoney8;
    }

    /**
     * 设置农村电网维护费
     *
     * @param addMoney8 农村电网维护费
     */
    public void setAddMoney8(BigDecimal addMoney8) {
        this.addMoney8 = addMoney8;
    }

    /**
     * 获取价格调节基金
     *
     * @return ADD_MONEY9 - 价格调节基金
     */
    public BigDecimal getAddMoney9() {
        return addMoney9;
    }

    /**
     * 设置价格调节基金
     *
     * @param addMoney9 价格调节基金
     */
    public void setAddMoney9(BigDecimal addMoney9) {
        this.addMoney9 = addMoney9;
    }

    /**
     * @return ADD_MONEY10
     */
    public BigDecimal getAddMoney10() {
        return addMoney10;
    }

    /**
     * @param addMoney10
     */
    public void setAddMoney10(BigDecimal addMoney10) {
        this.addMoney10 = addMoney10;
    }

    /**
     * 获取退补方式
     *
     * @return RP_MODE - 退补方式
     */
    public Integer getRpMode() {
        return rpMode;
    }

    /**
     * 设置退补方式
     *
     * @param rpMode 退补方式
     */
    public void setRpMode(Integer rpMode) {
        this.rpMode = rpMode;
    }

    /**
     * 获取电价编码
     *
     * @return PRICE_TYPE - 电价编码
     */
    public Short getPriceType() {
        return priceType;
    }

    /**
     * 设置电价编码
     *
     * @param priceType 电价编码
     */
    public void setPriceType(Short priceType) {
        this.priceType = priceType;
    }

    /**
     * 获取结清标志
     *
     * @return PAID_FLAG - 结清标志
     */
    public Integer getPaidFlag() {
        return paidFlag;
    }

    /**
     * 设置结清标志
     *
     * @param paidFlag 结清标志
     */
    public void setPaidFlag(Integer paidFlag) {
        this.paidFlag = paidFlag;
    }

    /**
     * 获取审批结论
     *
     * @return PASS_RESULT - 审批结论
     */
    public Integer getPassResult() {
        return passResult;
    }

    /**
     * 设置审批结论
     *
     * @param passResult 审批结论
     */
    public void setPassResult(Integer passResult) {
        this.passResult = passResult;
    }

    /**
     * 获取申请原因
     *
     * @return PASS_REASON - 申请原因
     */
    public String getPassReason() {
        return passReason;
    }

    /**
     * 设置申请原因
     *
     * @param passReason 申请原因
     */
    public void setPassReason(String passReason) {
        this.passReason = passReason == null ? null : passReason.trim();
    }
}