package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//抄表单

@JsonIgnoreProperties(ignoreUnknown = true)
public class WriteFilesDomain extends ManagerParamEntity implements MybatisEntity {
    @TablePrimaryKey
    private String id;
    // 计量点ID
    private Long meterId;
    private Long meterAssetsId;// 电能表ID
    // 月份
    private Integer mon;
    // 初始化时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date initDate;// 初始化时间 INIT_DATE datetime FALSE FALSE FALSE
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date writeDate;// 抄表日期 WRITE_DATE datetime FALSE FALSE FALSE
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastWriteDate;// 上次抄表日期 LAST_WRITE_DATE datetime FALSE FALSE FALSE
    private Byte sn;// 本月次数 SN smallint FALSE FALSE FALSE
    private Byte writeFlag;// 抄表标志 WRITE_FLAG smallint FALSE FALSE FALSE
    private Byte writeMethod;// 抄表方式 WRITE_METHOD smallint FALSE FALSE FALSE
    private Byte timeSeg;// 时段 TIME_SEG smallint FALSE FALSE FALSE
    private Byte functionCode;// 功能代码
    private Byte powerDirection;// 功率方向
    private Byte phaseSeq;// 相序--（ABCD）
    private BigDecimal startNum;// 起码 START_NUM decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal endNum;// 止码 END_NUM decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal diffNum;// 度差 DIFF_NUM decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal writePower;// 抄见电量 WRITE_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal chgPower;// 换表电量 CHG_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal addPower;// 增减电量 ADD_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal factorNum;
    // 录入人
    private Long inputMan;
    //-------------------------------以下字段用于页面查询传值查询，不计入数据库---------------------------------
    private String userNo;
    private Long writeSectionId;// 所属抄表区段
    private Long tgId; // 所属台区 TG_ID bigint
    private Long lineId; // 所属线路 LINE_ID bigint
    private Long subsId; // 所属厂站 SUBS_ID bigint
    private Long userId; // 所属用户 USER_ID bigint
    private Long priceType;//电价
    private String meterNo;
    private String madeNo;// 电能表表号

    private String userName;
    private String address;
    private Integer[] writeSectionIds;
    private Long businessPlaceCode;

    private Long[] businessPlaceCodes;

    //抄表员
    private Long writorId;
    private String writorName;

    private String writeSectionNo;
    private Byte tsType; // 分时计费标准
    private Long meterOrder;// 表序号
    private BigDecimal lastEndNum;// 上月止码
    private BigDecimal lastDiffNum;// 上月度差
    private BigDecimal powerRate; // 电量波动率（本月度差-上月止码）/上月止码
    private Integer elecTypeCode; // 用电类别
    private List<Long> meterIds;
    private List<Integer> mons;
    private String _id;
    private String meterAssetsNo;
    private BigDecimal writeSn;
    private Integer startMon;
    private Integer endMon;
    private String meterType; // 计量点类别
//	private List<WriteFilesDomain> rel;
	//表序号
    private Integer meterSn;
    private String meterName;
    private Integer unWriteFlag;

    private String tsMeterFlagName;

    private String functionCodeName;

    private String powerDirectionName;

    private String businessPlaceCodeName;

    private BigDecimal lastWritePower;

    private Long settlementId;

    private Integer status;

    public Long getInputMan() {
        return inputMan;
    }

    public void setInputMan(Long inputMan) {
        this.inputMan = inputMan;
    }

    public BigDecimal getWriteSn() {
        return writeSn;
    }

    public void setWriteSn(BigDecimal writeSn) {
        this.writeSn = writeSn;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public BigDecimal getLastWritePower() {
        return lastWritePower;
    }

    public void setLastWritePower(BigDecimal lastWritePower) {
        this.lastWritePower = lastWritePower;
    }

    public String getBusinessPlaceCodeName() {
        return businessPlaceCodeName;
    }

    public void setBusinessPlaceCodeName(String businessPlaceCodeName) {
        this.businessPlaceCodeName = businessPlaceCodeName;
    }

    public String getFunctionCodeName() {
        return functionCodeName;
    }

    public void setFunctionCodeName(String functionCodeName) {
        this.functionCodeName = functionCodeName;
    }

    public String getTsMeterFlagName() {
        return tsMeterFlagName;
    }

    public void setTsMeterFlagName(String tsMeterFlagName) {
        this.tsMeterFlagName = tsMeterFlagName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Integer> getMons() {
        return mons;
    }

    public void setMons(List<Integer> mons) {
        this.mons = mons;
    }

    public List<Long> getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List<Long> meterIds) {
        this.meterIds = meterIds;
    }

    public BigDecimal getFactorNum() {
        return factorNum;
    }

    public void setFactorNum(BigDecimal factorNum) {
        this.factorNum = factorNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getLastWriteDate() {
        return lastWriteDate;
    }

    public void setLastWriteDate(Date lastWriteDate) {
        this.lastWriteDate = lastWriteDate;
    }

    public Byte getSn() {
        return sn;
    }

    public void setSn(Byte sn) {
        this.sn = sn;
    }

    public Byte getWriteFlag() {
        return writeFlag;
    }

    public void setWriteFlag(Byte writeFlag) {
        this.writeFlag = writeFlag;
    }

    public Byte getWriteMethod() {
        return writeMethod;
    }

    public void setWriteMethod(Byte writeMethod) {
        this.writeMethod = writeMethod;
    }

    public Byte getTimeSeg() {
        return timeSeg;
    }

    public void setTimeSeg(Byte timeSeg) {
        this.timeSeg = timeSeg;
    }

    public BigDecimal getStartNum() {
        return startNum;
    }

    public void setStartNum(BigDecimal startNum) {
        this.startNum = startNum;
    }

    public BigDecimal getEndNum() {
        return endNum;
    }

    public void setEndNum(BigDecimal endNum) {
        this.endNum = endNum;
    }

    public BigDecimal getDiffNum() {
        return diffNum;
    }

    public void setDiffNum(BigDecimal diffNum) {
        this.diffNum = diffNum;
    }

    public BigDecimal getWritePower() {
        return writePower;
    }

    public void setWritePower(BigDecimal writePower) {
        this.writePower = writePower;
    }

    public BigDecimal getChgPower() {
        return chgPower;
    }

    public void setChgPower(BigDecimal chgPower) {
        this.chgPower = chgPower;
    }

    public BigDecimal getAddPower() {
        return addPower;
    }

    public void setAddPower(BigDecimal addPower) {
        this.addPower = addPower;
    }


    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Long getWriteSectionId() {
        return writeSectionId;
    }

    public void setWriteSectionId(Long writeSectionId) {
        this.writeSectionId = writeSectionId;
    }

    public Long getTgId() {
        return tgId;
    }

    public void setTgId(Long tgId) {
        this.tgId = tgId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer[] getWriteSectionIds() {
        return writeSectionIds;
    }

    public void setWriteSectionIds(Integer[] writeSectionIds) {
        this.writeSectionIds = writeSectionIds;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getMadeNo() {
        return madeNo;
    }

    public void setMadeNo(String madeNo) {
        this.madeNo = madeNo;
    }

    public Byte getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(Byte functionCode) {
        this.functionCode = functionCode;
    }

    public Byte getPowerDirection() {
        return powerDirection;
    }

    public void setPowerDirection(Byte powerDirection) {
        this.powerDirection = powerDirection;
    }

    public Long getMeterAssetsId() {
        return meterAssetsId;
    }

    public void setMeterAssetsId(Long meterAssetsId) {
        this.meterAssetsId = meterAssetsId;
    }

    public Byte getPhaseSeq() {
        return phaseSeq;
    }

    public void setPhaseSeq(Byte phaseSeq) {
        this.phaseSeq = phaseSeq;
    }

    public Long getWritorId() {
        return writorId;
    }

    public void setWritorId(Long writorId) {
        this.writorId = writorId;
    }

    public Long getPriceType() {
        return priceType;
    }

    public void setPriceType(Long priceType) {
        this.priceType = priceType;
    }

    public Long getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(Long businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public Long[] getBusinessPlaceCodes() {
        return businessPlaceCodes;
    }

    public void setBusinessPlaceCodes(Long[] businessPlaceCodes) {
        this.businessPlaceCodes = businessPlaceCodes;
    }

    public String getWriteSectionNo() {
        return writeSectionNo;
    }

    public void setWriteSectionNo(String writeSectionNo) {
        this.writeSectionNo = writeSectionNo;
    }

    public Byte getTsType() {
        return tsType;
    }

    public void setTsType(Byte tsType) {
        this.tsType = tsType;
    }

    public Long getMeterOrder() {
        return meterOrder;
    }

    public void setMeterOrder(Long meterOrder) {
        this.meterOrder = meterOrder;
    }

    public BigDecimal getLastEndNum() {
        return lastEndNum;
    }

    public void setLastEndNum(BigDecimal lastEndNum) {
        this.lastEndNum = lastEndNum;
    }

    public BigDecimal getLastDiffNum() {
        return lastDiffNum;
    }

    public void setLastDiffNum(BigDecimal lastDiffNum) {
        this.lastDiffNum = lastDiffNum;
    }

    public BigDecimal getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(BigDecimal powerRate) {
        this.powerRate = powerRate;
    }

    public Integer getElecTypeCode() {
        return elecTypeCode;
    }

    public void setElecTypeCode(Integer elecTypeCode) {
        this.elecTypeCode = elecTypeCode;
    }

    public String getMeterAssetsNo() {
        return meterAssetsNo;
    }

    public void setMeterAssetsNo(String meterAssetsNo) {
        this.meterAssetsNo = meterAssetsNo;
    }


    //
//	public List<WriteFilesDomain> getRel() {
//		return rel;
//	}
//
//	public void setRel(List<WriteFilesDomain> rel) {
//		this.rel = rel;
//	}


    public Integer getStartMon() {
        return startMon;
    }

    public void setStartMon(Integer startMon) {
        this.startMon = startMon;
    }

    public Integer getEndMon() {
        return endMon;
    }

    public void setEndMon(Integer endMon) {
        this.endMon = endMon;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getWritorName() {
        return writorName;
    }

    public void setWritorName(String writorName) {
        this.writorName = writorName;
    }

	public Integer getMeterSn() {
		return meterSn;
	}

	public void setMeterSn(Integer meterSn) {
		this.meterSn = meterSn;
	}

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public Integer getUnWriteFlag() {
        return unWriteFlag;
    }

    public void setUnWriteFlag(Integer unWriteFlag) {
        this.unWriteFlag = unWriteFlag;
    }

    public String getPowerDirectionName() {
        return powerDirectionName;
    }

    public void setPowerDirectionName(String powerDirectionName) {
        this.powerDirectionName = powerDirectionName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
