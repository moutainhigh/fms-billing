package org.fms.billing.server.webapp.service;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.util.MonUtils;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterAssetsDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.UserDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.entity.CbReadingDownEntity;
import org.fms.billing.common.webapp.service.IWriteFilesService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.MeterMeterAssetsRelDAO;
import org.fms.billing.server.webapp.dao.WriteFilesDAO;
import org.fms.billing.server.webapp.dao.WriteFilesMongoDAO;
import org.fms.billing.server.webapp.dao.WriteSectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;

@Service
public class WriteFilesServiceImpl implements IWriteFilesService {
    private Log logger = LogFactory.getLog(WriteFilesServiceImpl.class);

    @Autowired
    private WriteFilesDAO writeFilesDAO;

    @Autowired
    private WriteFilesMongoDAO writeFilesMongoDAO;

    @Autowired
    private WriteSectDAO writeSectDAO;

    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MeterDAO meterDAO;

    @Autowired
    private MeterMeterAssetsRelDAO meterMeterAssetsRelDAO;

    @Autowired
    private CimService cimService;

    @Autowired
    private SysCommonConfigService sysCommonConfigService;

    @Autowired
    private DeptService deptService;

    @Override
    public List<WriteFilesDomain> findByWhere(WriteFilesDomain writeFilesDomain) {
        return writeFilesDAO.findByWhere(writeFilesDomain);
    }

    @Override
    public List<WriteFilesDomain> mongoFind(WriteFilesDomain queryParam) {

        List<Integer> writeSectId = new ArrayList<>();
        // 根据抄表员选抄表区段 mongodb
        if (null != queryParam.getWritorId()) {
            WriteSectDomain writeSectDomain = new WriteSectDomain();
            writeSectDomain.setMon(queryParam.getMon().toString());
            writeSectDomain.setWritorId(queryParam.getWritorId());
            List<WriteSectDomain> writeSectDomains = writeSectDAO.getWriteSect(writeSectDomain);
            writeSectDomains.stream().forEach(t -> {
                writeSectId.add(t.getId().intValue());
            });
        }
        if (null != writeSectId && writeSectId.size() > 1) {
            queryParam.setWriteSectionIds(writeSectId.toArray(new Integer[writeSectId.size()]));
        }

        List<WriteFilesDomain> writeFilesDomains = writeFilesDAO.mongoFindByWrite(queryParam);

        if (writeFilesDomains.size() == 0) {
            return writeFilesDomains;
        }

        return writeFilesDomains;
    }

    @Override
    public List<WriteFilesDomain> findWriteFilesByWhere(WriteFilesDomain writeFilesDomain) {
        return writeFilesMongoDAO.findWriteFilesByWhere(writeFilesDomain);
    }

    @Override
    public long mongoUpdate(List<WriteFilesDomain> writeFilesDomains) {
        // TODO Auto-generated method stub
        return writeFilesDAO.mongoUpdate(writeFilesDomains);
    }

    @Override
    public List<WriteFilesDomain> getWriteFiles(WriteFilesDomain writeFilesDomain) {
        // TODO Auto-generated method stub
        return writeFilesDAO.getWriteFiles(writeFilesDomain);
    }

    @Override
    public List<WriteFilesDomain> getWriteFilesDomain(WriteFilesDomain writeFilesDomain) {
        return writeFilesDAO.getWriteFilesDomain2(writeFilesDomain);
    }

    @Override
    public List<WriteFilesDomain> writeFilesQuery(WriteFilesDomain writeFiles) {
        List<WriteSectDomain> wsList = new ArrayList<>();
        if (writeFiles.getBusinessPlaceCode() != null) {
//            List<Long> businessPlaceCodeList = findAllByParentId(writeFiles.getBusinessPlaceCode());
            Map<String, Object> params = new HashMap<>();
            params.put("businessPlaceCode", writeFiles.getBusinessPlaceCode());
            List<DeptDomain> deptDomains = new ArrayList<>();
            try {
                deptDomains = titanTemplate.postJsonToList("TITAN-REPORT",
                        "report/Dept/queryDeptListByBusinessPlaceCode", null, params, DeptDomain.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Long> businessPlaceCodeList = deptDomains.stream().filter(d -> d.getId() != null)
                    .map(DeptDomain::getId).collect(toList());
            if (businessPlaceCodeList.size() > 1) {
                Long[] businessPlaceCodes = new Long[businessPlaceCodeList.size()];
                businessPlaceCodeList.toArray(businessPlaceCodes);
                writeFiles.setBusinessPlaceCodes(businessPlaceCodes);
            }
        }
        if (writeFiles.getWritorId() != null) {
            WriteSectDomain ws = new WriteSectDomain();
            ws.setMon(writeFiles.getMon().toString());
            ws.setWritorId(writeFiles.getWritorId());
            wsList = writeSectDAO.getWriteSectDomain(ws);
            List<Integer> writeSectionIdList = new ArrayList<>();
            wsList.stream().forEach(i -> {
                writeSectionIdList.add(i.getId().intValue());
            });
            Integer[] writeSectionIds = new Integer[writeSectionIdList.size()];
            writeSectionIdList.toArray(writeSectionIds);
            writeFiles.setWriteSectionIds(writeSectionIds);
        }

        List<WriteFilesDomain> list = writeFilesDAO.getWriteFilesDomain2(writeFiles);
        if (wsList.isEmpty()) {
            List<Long> writeSectionIdList = list.stream().filter(i -> i.getWriteSectionId() != null).map(WriteFilesDomain::getWriteSectionId).collect(toList());
            WriteSectDomain ws = new WriteSectDomain();
            ws.setMon(writeFiles.getMon().toString());
            ws.setWriteSectionIds(writeSectionIdList);
            wsList = writeSectDAO.getWriteSectDomain(ws);
        }
        MeterDomain m = new MeterDomain();
        m.setMon(writeFiles.getMon());
        List<Long> meterIds = list.stream().filter(i -> i.getMeterId() != null).map(WriteFilesDomain::getMeterId).collect(toList());
        m.setIds(meterIds);
        List<MeterDomain> meterList = meterDAO.findMeterDomain(m);

        MeterMeterAssetsRelDomain rel = new MeterMeterAssetsRelDomain();
        rel.setMon(writeFiles.getMon());
        rel.setMeterIds(meterIds);
        List<MeterMeterAssetsRelDomain> relList = meterMeterAssetsRelDAO.mongoFind(rel);

        WriteFilesDomain last = new WriteFilesDomain();
        last.setMon(Integer.valueOf(MonUtils.getLastMon(writeFiles.getMon().toString())));
        List<WriteFilesDomain> lastWriteFiles = writeFilesDAO.getWriteFilesDomain2(last);

        // 填充抄表区段、分时表标志、电价、用电类别、表序号、上月止码、上月度差、波动率
        for (WriteFilesDomain item : list) {
            wsList.forEach(ws -> {
                if (item.getWriteSectionId().equals(ws.getId())) {
                    // 填充抄表区段
                    item.setWriteSectionNo(ws.getWriteSectNo());
                }
            });
            meterList.forEach(meter -> {
                if (item.getMeterId().equals(meter.getId())) {
                    // 分时表标志
                    item.setTsType(meter.getTsType());
                    // 电价
                    item.setPriceType(meter.getPriceType());
                    // 用电类别
                    item.setElecTypeCode(meter.getElecTypeCode());
                }
            });
            relList.forEach(r -> {
                if (item.getMeterId().equals(r.getMeterId()) && item.getMeterAssetsId().equals(r.getMeterAssetsId())) {
                    // 表序号
                    item.setMeterOrder(r.getMeterOrder());
                }
            });
            lastWriteFiles.forEach(l -> {
                if (item.getFunctionCode().equals(l.getFunctionCode()) && item.getMeterId().equals(l.getMeterId())
                        && item.getPhaseSeq().equals(l.getPhaseSeq()) && item.getPowerDirection().equals(l.getPowerDirection())
                        && item.getTimeSeg().equals(l.getTimeSeg())) {
                    // 上月止码
                    item.setLastEndNum(l.getEndNum());
                    // 上月度差
                    item.setLastDiffNum(l.getDiffNum());
                    if (item.getLastEndNum() != null) {
                        // 波动率
                        item.setPowerRate((item.getDiffNum().subtract(item.getLastEndNum())).divide(item.getLastEndNum(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
                    }
                }
            });
        }
        if (writeFiles.getPowerRate() != null) {
            list = list.stream().filter(i -> i.getPowerRate() != null && i.getPowerRate().compareTo(writeFiles.getPowerRate()) > -1).collect(toList());
        }
        List<Long> meterIdList = list.stream().filter(l -> l.getMeterId() != null).map(WriteFilesDomain::getMeterId)
                .distinct().collect(toList());
        Map<String, Object> params2 = new HashMap<>();
        params2.put("meterIds", meterIdList);
        List<MeterMeterAssetsRelDomain> meterMeterAssetsRelDomains = new ArrayList<>();
        try {
            meterMeterAssetsRelDomains = titanTemplate.postJsonToList("CIM-SERVER",
                    "cimServer/meterMeterAssets?method=getMeterAssetsByMeterIds", null, params2,
                    MeterMeterAssetsRelDomain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<Long, MeterMeterAssetsRelDomain> meterMeterAssetsRelDomainMap = meterMeterAssetsRelDomains.stream()
                .collect(Collectors.toMap(MeterMeterAssetsRelDomain::getMeterAssetsId, v -> v, (v1, v2) -> v1));
        list.forEach(l -> {
            if (meterMeterAssetsRelDomainMap.get(l.getMeterAssetsId()) != null) {
                l.setMeterAssetsNo(meterMeterAssetsRelDomainMap.get(l.getMeterAssetsId()).getMeterAssetsNo());
            }
        });
        return list;
    }

    @Override
    public List<MeterAssetsDomain> getMeterAssetsByAssetsIds(List<Long> assetsIds) throws Exception {
        Map<String, List<Long>> params = new HashMap<>();
        params.put("ids", assetsIds);

        List<MeterAssetsDomain> assetsDomains = titanTemplate.postJsonToList("CIM-SERVER",
                "cimServer/meterAssets?method=getMeterAssetsByAssetsIds", null, params, MeterAssetsDomain.class);

        return assetsDomains;
    }

    public List<Long> findAllByParentId(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<Long> businessPlaceCodes = new ArrayList<>();
        try {
            businessPlaceCodes = titanTemplate.post("TITAN-REPORT",
                    "report/Dept/findAllByParentId", httpHeaders,
                    id,
                    new TypeReference<List<Long>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessPlaceCodes;
    }

    @Override
    public List<WriteFilesDomain> mongoFindByWrite(WriteFilesDomain queryParam) {
        return writeFilesDAO.mongoFindByWrite(queryParam);
    }

    @Override
    public List<WriteFilesDomain> mongoFindForReadingDown(WriteFilesDomain queryParam) {
        return writeFilesDAO.mongoFindForReadingDown(queryParam);
    }

    @Override
    public List<WriteFilesDomain> findWriteFilesList(WriteFilesDomain paramWriteFileDomain) throws Exception {

        //获取抄表区段
        WriteSectDomain paramWriteSectDomain = new WriteSectDomain();
        paramWriteSectDomain.setId(paramWriteFileDomain.getWriteSectionId());
        paramWriteSectDomain.setWritorId(paramWriteFileDomain.getWritorId());
        paramWriteSectDomain.setBusinessPlaceCode(paramWriteFileDomain.getBusinessPlaceCode());
        paramWriteSectDomain.setMon(paramWriteFileDomain.getMon().toString());
        List<WriteSectDomain> writeSectDomains = writeSectDAO.getWriteSectDomain(paramWriteSectDomain);

        if (writeSectDomains.isEmpty()) {
            return new ArrayList<>();
        }

        DeptDomain dept = deptService.getDept(writeSectDomains.get(0).getBusinessPlaceCode());

        Map<Long, WriteSectDomain> writeSectDomainMap =
                writeSectDomains.stream().collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));

        //抄表区段id集合
        List<Integer> writeSectionIds =
                writeSectDomains.stream().map(WriteSectDomain::getId).map(s -> s.intValue()).distinct().collect(Collectors.toList());

        //被引用太多暂不改
        Integer[] writeSectionIdsArray = new Integer[writeSectionIds.size()];
        writeSectionIds.toArray(writeSectionIdsArray);
        paramWriteFileDomain.setWriteSectionIds(writeSectionIdsArray);
        paramWriteFileDomain.setWriteSectionId(null);

        //获取未抄表户
        //paramWriteFileDomain.setWriteFlag((byte) 0);
        List<WriteFilesDomain> writeFilesDomains = writeFilesDAO.selectWriteFilesList(paramWriteFileDomain);

        writeFilesDomains.stream().forEach(t -> {
            WriteSectDomain writeSect =
                    writeSectDomainMap.get(t.getWriteSectionId());
            if (writeSect != null) {
                t.setWriteSectionNo(writeSect.getWriteSectNo());
            }
        });

        List<Long> meterIds = new ArrayList<>();
        meterIds =
                writeFilesDomains.stream().map(WriteFilesDomain::getMeterId).distinct().collect(toList());

        if (meterIds.isEmpty()) {
            return new ArrayList<>();
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("meterIds", meterIds);

        List<MeterMeterAssetsRelDomain> meterMeterAssetsRelDomains = new ArrayList<>();
        try {
            meterMeterAssetsRelDomains = titanTemplate.postJsonToList("CIM-SERVER",
                    "cimServer/meterMeterAssets?method=getMeterAssetsByMeterIds", null, params,
                    MeterMeterAssetsRelDomain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, MeterMeterAssetsRelDomain> meterMeterAssetsRelDomainMap =
                meterMeterAssetsRelDomains.stream().collect(Collectors.toMap(o -> o.getMeterId().toString() + "_" + o.getMeterAssetsId(), a -> a, (k1, k2) -> k1));

        //资产信息
        List<Long> meterAssetsIds =
                meterMeterAssetsRelDomains.stream().map(MeterMeterAssetsRelDomain::getMeterAssetsId).distinct().collect(toList());

        //获取表号
        List<MeterAssetsDomain> meterAssetsDomainList =
                getMeterAssetsByAssetsIds(meterAssetsIds);

        Map<Long, MeterAssetsDomain> meterAssetsDomainMap =
                meterAssetsDomainList.stream().collect(Collectors.toMap(MeterAssetsDomain::getId, k -> k));

        //获取计量点
        params.clear();
        params.put("meterIds", meterIds);
        List<MeterDomain> meterList = cimService.getMeterFindByWhere(params);
        Map<Long, MeterDomain> meterMap = meterList.stream().collect(Collectors.toMap(MeterDomain::getId, k -> k));

        //下拉
        Map<Long, String> functionConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("FUNCTION_CODE");

        Map<Long, String> timeSegConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("TS_METER_FLAG");
        logger.info("writeFilesDomains======"+writeFilesDomains.size());
        writeFilesDomains.stream().forEach(t -> {
            String key = t.getMeterId() + "_" + t.getMeterAssetsId();
            MeterMeterAssetsRelDomain paramMeterMeterAssetsRel =
                    meterMeterAssetsRelDomainMap.get(key);
            logger.info(key+"======"+paramMeterMeterAssetsRel);
            if (paramMeterMeterAssetsRel != null) {

                t.setMeterOrder(paramMeterMeterAssetsRel.getMeterOrder());
                t.setWriteSn(paramMeterMeterAssetsRel.getWriteSn());

            }

            t.setFunctionCodeName(functionConfigMap.get(Long.valueOf(t.getFunctionCode())));

            MeterDomain meterDomain = meterMap.get(t.getMeterId());
            if (meterDomain != null) {
                t.setTsMeterFlagName(timeSegConfigMap.get(Long.valueOf(meterDomain.getTsType())));
                t.setMadeNo(meterDomain.getMadeNo());
            }

            MeterAssetsDomain meterAssetsDomain =
                    meterAssetsDomainMap.get(t.getMeterAssetsId());
            if (meterAssetsDomain != null) {
                t.setMeterAssetsNo(meterAssetsDomain.getMeterAssetsNo());
                t.setMadeNo(meterAssetsDomain.getMadeNo());
            }

            t.setBusinessPlaceCodeName(dept.getDeptName());
            WriteSectDomain writeSectDomain =
                    writeSectDomainMap.get(t.getWriteSectionId());

            if (writeSectDomain != null) {
                t.setWriteSectionNo(writeSectDomain.getWriteSectNo());
            }
        });

        return writeFilesDomains;
    }

    @Override
    public long mongoUpdateByCBQ(List<CbReadingDownEntity> cbReadingDownEntities) {
        return writeFilesDAO.mongoUpdateByCBQ(cbReadingDownEntities);
    }

    //电表电量波动异常
    @Override
    public List<WriteFilesDomain> powerException(WriteFilesDomain paramWriteFileDomain) throws Exception {

        //获取抄表区段
        WriteSectDomain paramWriteSectDomain = new WriteSectDomain();
        paramWriteSectDomain.setId(paramWriteFileDomain.getWriteSectionId());
        paramWriteSectDomain.setWritorId(paramWriteFileDomain.getWritorId());
        paramWriteSectDomain.setBusinessPlaceCode(paramWriteFileDomain.getBusinessPlaceCode());
        paramWriteSectDomain.setMon(paramWriteFileDomain.getMon().toString());
        List<WriteSectDomain> writeSectDomains = writeSectDAO.getWriteSectDomain(paramWriteSectDomain);

        if (writeSectDomains.isEmpty()) {
            return new ArrayList<>();
        }

        DeptDomain dept = deptService.getDept(writeSectDomains.get(0).getBusinessPlaceCode());

        Map<Long, WriteSectDomain> writeSectDomainMap =
                writeSectDomains.stream().collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));

        //抄表区段id集合
        List<Integer> writeSectionIds =
                writeSectDomains.stream().map(WriteSectDomain::getId).map(s -> s.intValue()).distinct().collect(Collectors.toList());

        //被引用太多暂不改
        Integer[] writeSectionIdsArray = new Integer[writeSectionIds.size()];
        writeSectionIds.toArray(writeSectionIdsArray);
        paramWriteFileDomain.setWriteSectionIds(writeSectionIdsArray);
        paramWriteFileDomain.setWriteSectionId(null);

        //获取抄表区段内的数据
        paramWriteFileDomain.setWriteFlag((byte) 1);
        List<WriteFilesDomain> writeFilesDomains =
                writeFilesDAO.selectWriteFilesList(paramWriteFileDomain);

        if (writeFilesDomains == null || writeFilesDomains.size() < 1) {
            return new ArrayList<>();
        }

        //获取计量点
        MeterDomain m = new MeterDomain();
        m.setMon(paramWriteFileDomain.getMon());
        List<Long> meterIds =
                writeFilesDomains.stream().filter(i -> i.getMeterId() != null).map(WriteFilesDomain::getMeterId).distinct().collect(toList());
        m.setIds(meterIds);
        List<MeterDomain> meterList = meterDAO.findMeterDomain(m);
        Map<Long, MeterDomain> meterDomainMap =
                meterList.stream().collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));

        HashMap<String, Object> params = new HashMap<>();
        params.put("meterIds", meterIds);
        List<MeterMeterAssetsRelDomain> meterMeterAssetsRelDomains = new ArrayList<>();
        try {
            meterMeterAssetsRelDomains = titanTemplate.postJsonToList("CIM-SERVER",
                    "cimServer/meterMeterAssets?method=getMeterAssetsByMeterIds", null, params,
                    MeterMeterAssetsRelDomain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, MeterMeterAssetsRelDomain> meterMeterAssetsRelDomainMap =
                meterMeterAssetsRelDomains.stream().collect(Collectors.toMap(o -> o.getMeterId().toString() + "_" + o.getMeterAssetsId(), a -> a, (k1, k2) -> k1));


        //资产信息
        List<Long> meterAssetsIds =
                meterMeterAssetsRelDomains.stream().map(MeterMeterAssetsRelDomain::getMeterAssetsId).distinct().collect(toList());

        //获取表号
        List<MeterAssetsDomain> meterAssetsDomainList =
                getMeterAssetsByAssetsIds(meterAssetsIds);

        Map<Long, MeterAssetsDomain> meterAssetsDomainMap =
                meterAssetsDomainList.stream().collect(Collectors.toMap(MeterAssetsDomain::getId, k -> k));


        //上月电量
        paramWriteFileDomain.setMon(Integer.valueOf(MonUtils.getLastMon(paramWriteFileDomain.getMon().toString())));
        List<WriteFilesDomain> lastWriteFiles = writeFilesDAO.selectWriteFilesList(paramWriteFileDomain);
        Map<String, WriteFilesDomain> lastWriteFilesDomainMap =
                lastWriteFiles.stream().collect(Collectors.toMap(WriteFilesDomain::get_id, a -> a, (k1, k2) -> k1));

        //下拉
        Map<Long, String> functionConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("FUNCTION_CODE");

        Map<Long, String> timeSegConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("TS_METER_FLAG");

        //填充上月电量
        writeFilesDomains.forEach(t -> {

            String key = t.getMeterId() + "_" + t.getMeterAssetsId();

            MeterMeterAssetsRelDomain meterMeterAssetsRelDomain =
                    meterMeterAssetsRelDomainMap.get(key);

            if (meterMeterAssetsRelDomain != null) {
                t.setFactorNum(meterMeterAssetsRelDomain.getFactorNum());
            } else {
                t.setFactorNum(BigDecimal.ONE);
            }

            WriteFilesDomain lastWriteFilesDomain =
                    lastWriteFilesDomainMap.get(t.get_id());
            if (lastWriteFilesDomain != null) {
                t.setLastWritePower(FormatterUtil.calcDiff(lastWriteFilesDomain.getStartNum(), lastWriteFilesDomain.getEndNum()).multiply(t.getFactorNum()));
            } else {
                t.setLastWritePower(BigDecimal.ZERO);
            }

            t.setWritePower(FormatterUtil.calcDiff(t.getStartNum(), t.getEndNum()).multiply(t.getFactorNum()));

            BigDecimal powerRate = new BigDecimal(100);
            if (t.getLastWritePower() != null && t.getLastWritePower().compareTo(BigDecimal.ZERO) == 1) {
                powerRate =
                        (t.getWritePower().subtract(t.getLastWritePower())).divide(t.getLastWritePower(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            }

            if (t.getLastWritePower() != null && t.getLastWritePower().compareTo(t.getWritePower()) == 0) {
                powerRate = BigDecimal.ZERO;
            }
            t.setPowerRate(powerRate);
        });

        //过滤掉powerRate
        writeFilesDomains =
                writeFilesDomains.stream().filter(t -> t.getPowerRate().compareTo(paramWriteFileDomain.getPowerRate()) >= 0).collect(toList());

        // 填充抄表区段、分时表标志、电价、用电类别、表序号、上月止码、上月度差、波动率
        writeFilesDomains.forEach(t -> {

            t.setFunctionCodeName(functionConfigMap.get(Long.valueOf(t.getFunctionCode())));

            MeterDomain meterDomain = meterDomainMap.get(t.getMeterId());
            if (meterDomain != null) {
                t.setTsMeterFlagName(timeSegConfigMap.get(Long.valueOf(meterDomain.getTsType())));
            }

            String key = t.getMeterId() + "_" + t.getMeterAssetsId();
            MeterMeterAssetsRelDomain paramMeterMeterAssetsRel =
                    meterMeterAssetsRelDomainMap.get(key);
            if (paramMeterMeterAssetsRel != null) {
                t.setMeterOrder(paramMeterMeterAssetsRel.getMeterOrder());
                t.setWriteSn(paramMeterMeterAssetsRel.getWriteSn());

            }

            MeterAssetsDomain meterAssetsDomain =
                    meterAssetsDomainMap.get(t.getMeterAssetsId());
            if (meterAssetsDomain != null) {
                t.setMeterAssetsNo(meterAssetsDomain.getMeterAssetsNo());
                t.setMadeNo(meterAssetsDomain.getMadeNo());
            }

            t.setBusinessPlaceCodeName(dept.getDeptName());
            WriteSectDomain writeSectDomain =
                    writeSectDomainMap.get(t.getWriteSectionId());

            if (writeSectDomain != null) {
                t.setWriteSectionNo(writeSectDomain.getWriteSectNo());
            }

        });

        return writeFilesDomains;
    }

    @Override
    public List<WriteFilesDomain> findWriteFilesByCustomer(CustomerDomain customer) {
        Map<String, Object> params = new HashMap<>();
        MeterDomain meterDomain = new MeterDomain();
        meterDomain.setCustomerId(customer.getId());
        meterDomain.setPageSize(-1);
        params.put("meter", meterDomain);
        List<MeterDomain> meterDomains = new ArrayList<>();
        try {
            meterDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/meter?method=queryMetersByCustomer", new HttpHeaders(), params,
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (meterDomains.isEmpty()) {
            return new ArrayList<>();
        }
        // 将计量点list转化为map集合
        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));
        List<Long> writeSectIds = meterDomains.stream().filter(m -> m.getWriteSectionId() != null)
                .map(MeterDomain::getWriteSectionId).distinct().collect(toList());
        List<Long> meterIds = meterDomains.stream().filter(m -> m.getId() != null)
                .map(MeterDomain::getId).distinct().collect(toList());
        Map<String, Object> params2 = new HashMap<>();
        CustomerDomain customerDomain = new CustomerDomain();
        customerDomain.setId(customer.getId());
        params2.put("customer", customerDomain);
        List<UserDomain> userDomains = new ArrayList<>();
        try {
            userDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/user?method=queryUsersByCustomer", new HttpHeaders(), params2,
                    new TypeReference<List<UserDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDomains.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, UserDomain> userDomainMap = userDomains.stream()
                .collect(Collectors.toMap(UserDomain::getId, a -> a, (k1, k2) -> k1));
        Map<String, Object> params3 = new HashMap<>();
        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setWriteSectionIds(writeSectIds);
        params3.put("writeSect", writeSectDomain);
        List<WriteSectDomain> writeSectDomains = new ArrayList<>();
        try {
            writeSectDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/writeSect?method=queryByWhere", new HttpHeaders(), params3,
                    new TypeReference<List<WriteSectDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        WriteFilesDomain writeFilesDomain = new WriteFilesDomain();
        writeFilesDomain.setStartMon(customer.getStartMon());
        writeFilesDomain.setEndMon(customer.getEndMon());
        writeFilesDomain.setMeterIds(meterIds);
        List<WriteFilesDomain> writeFilesDomains = writeFilesDAO.findByWhere(writeFilesDomain);
        if (!writeSectDomains.isEmpty()) {
            Map<Long, WriteSectDomain> writeSectDomainMap = writeSectDomains.stream()
                    .collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));
            writeFilesDomains.forEach(w -> {
                w.setWriteSectionNo(writeSectDomainMap
                        .get(meterDomainMap.get(w.getMeterId()).getWriteSectionId()).getWriteSectNo());
                w.setWritorId(writeSectDomainMap
                        .get(meterDomainMap.get(w.getMeterId()).getWriteSectionId()).getWritorId());
            });
        }
        // 将计量点编号和类型加入到抄表记录中
        writeFilesDomains.forEach(w -> {
            if (meterDomainMap.get(w.getMeterId()) != null) {
                w.setMeterType(Byte.toString(meterDomainMap.get(w.getMeterId()).getMeterType()));
                w.setMeterNo(meterDomainMap.get(w.getMeterId()).getMeterNo());
            }
        });
        // 加入用户编号和名称
        writeFilesDomains.forEach(w -> {
            if (userDomainMap.get(meterDomainMap.get(w.getMeterId()).getUserId()) != null) {
                w.setUserNo(userDomainMap.get(meterDomainMap.get(w.getMeterId()).getUserId()).getUserNo());
                w.setUserName(userDomainMap.get(meterDomainMap.get(w.getMeterId()).getUserId()).getUserName());
            }
        });

        // 加入抄表员姓名
        writeFilesDomains.forEach(w -> {
            if (userDomainMap.get(w.getWritorId()) != null) {
                w.setWritorName(userDomainMap.get(w.getWritorId()).getUserName());
            }
        });
        Map<String, Object> params4 = new HashMap<>();
        params4.put("meterIds", meterIds);
        List<MeterDomain> meters = new ArrayList<>();
        try {
            meters = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/meter?method=findMeterAndMeterRelByMeterIds", new HttpHeaders(), params4,
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<Long, MeterDomain> metersMap = meters.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));
        writeFilesDomains.forEach(w -> {
            if (metersMap.get(w.getMeterId()) != null) {
                w.setMeterName(metersMap.get(w.getMeterId()).getMeterName());
                w.setMeterAssetsNo(metersMap.get(w.getMeterId()).getMeterAssetsNo());
                w.setMeterOrder(metersMap.get(w.getMeterId()).getMeterOrder());
                w.setMeterSn(metersMap.get(w.getMeterId()).getSn());
                w.setMeterType(metersMap.get(w.getMeterId()).getMeterType().toString());
                w.setMadeNo(metersMap.get(w.getMeterId()).getMadeNo());
            }
        });
        return writeFilesDomains;
    }

    @Override
    public List<WriteFilesDomain> zeroUserQuery(WriteFilesDomain writeFiles) {
        List<WriteSectDomain> wsList = new ArrayList<>();
        if (writeFiles.getBusinessPlaceCode() != null) {
            List<DeptDomain> deptDomains = new ArrayList<>();
            DeptDomain dept = new DeptDomain();
            try {
                dept = deptService.getDept(writeFiles.getBusinessPlaceCode());
                deptDomains = deptService.getDeptList(writeFiles.getBusinessPlaceCode());
                deptDomains.add(dept);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Long> businessPlaceCodeList = deptDomains.stream().filter(d -> d.getId() != null)
                    .map(DeptDomain::getId).collect(toList());
            if (businessPlaceCodeList.size() > 1) {
                writeFiles.setBusinessPlaceCode(null);
                Long[] businessPlaceCodes = new Long[businessPlaceCodeList.size()];
                businessPlaceCodeList.toArray(businessPlaceCodes);
                writeFiles.setBusinessPlaceCodes(businessPlaceCodes);
            }
        }
        if (writeFiles.getWritorId() != null) {
            WriteSectDomain ws = new WriteSectDomain();
            ws.setMon(writeFiles.getMon().toString());
            ws.setWritorId(writeFiles.getWritorId());
            wsList = writeSectDAO.getWriteSectDomain(ws);
            List<Integer> writeSectionIdList = new ArrayList<>();
            wsList.stream().forEach(i -> {
                writeSectionIdList.add(i.getId().intValue());
            });
            Integer[] writeSectionIds = new Integer[writeSectionIdList.size()];
            writeSectionIdList.toArray(writeSectionIds);
            writeFiles.setWriteSectionIds(writeSectionIds);
        }

        List<WriteFilesDomain> list = writeFilesDAO.getWriteFilesDomain2(writeFiles);
        if (wsList.isEmpty()) {
            List<Long> writeSectionIdList = list.stream().filter(i -> i.getWriteSectionId() != null).map(WriteFilesDomain::getWriteSectionId).collect(toList());
            WriteSectDomain ws = new WriteSectDomain();
            ws.setMon(writeFiles.getMon().toString());
            ws.setWriteSectionIds(writeSectionIdList);
            wsList = writeSectDAO.getWriteSectDomain(ws);
        }

        Map<Long, WriteSectDomain> writeSectDomainMap = wsList.stream().collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));

        MeterDomain m = new MeterDomain();
        m.setMon(writeFiles.getMon());
        List<Long> meterIds = list.stream().filter(i -> i.getMeterId() != null).map(WriteFilesDomain::getMeterId).collect(toList());
        m.setIds(meterIds);
        List<MeterDomain> meterList = meterDAO.findMeterDomain(m);
        Map<Long, MeterDomain> meterDomainMap =
                meterList.stream().collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));

        MeterMeterAssetsRelDomain rel = new MeterMeterAssetsRelDomain();
        rel.setMon(writeFiles.getMon());
        rel.setMeterIds(meterIds);
        List<MeterMeterAssetsRelDomain> relList = meterMeterAssetsRelDAO.mongoFind(rel);
        Map<String, MeterMeterAssetsRelDomain> meterMeterAssetsRelDomainMap =
                relList.stream().collect(Collectors.toMap(o -> o.getMeterId() +
                                "_" + o.getMeterAssetsId(), a -> a, (k1, k2) -> k1));

        List<Long> meterAssetsIds=
                relList.stream().filter(t->t.getMeterAssetsId()!=null).map(MeterMeterAssetsRelDomain::getMeterAssetsId).distinct().collect(toList());

        //获取表号
        List<MeterAssetsDomain> meterAssetsDomainList = null;
        try {
            meterAssetsDomainList = getMeterAssetsByAssetsIds(meterAssetsIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Long, MeterAssetsDomain> meterAssetsDomainMap =new HashMap<>();
        if(meterAssetsDomainList!=null){

            meterAssetsDomainMap=
                    meterAssetsDomainList.stream().collect(Collectors.toMap(MeterAssetsDomain::getId, k -> k));
        }

        Map<Long, String> functionCodeConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("FUNCTION_CODE");

        Map<Long, String> powerDirectionConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("POWER_DIRECTION");
        // 填充抄表区段、分时表标志、电价、用电类别、表序号、上月止码、上月度差、波动率
        for (WriteFilesDomain item : list) {
            if (writeSectDomainMap.get(item.getWriteSectionId()) != null) {

                item.setWriteSectionNo(writeSectDomainMap.get(item.getWriteSectionId()).getWriteSectNo());
            }

            MeterDomain meterDomain = meterDomainMap.get(item.getMeterId());
            if (meterDomain != null) {
                item.setTsType(meterDomain.getTsType());
            }

            String meterRelKey=item.getMeterId()+"_"+item.getMeterAssetsId();
            MeterMeterAssetsRelDomain meterMeterAssetsRelDomain=
                    meterMeterAssetsRelDomainMap.get(meterRelKey);
            if(meterMeterAssetsRelDomain!=null){
                item.setMeterOrder(meterMeterAssetsRelDomain.getMeterOrder());
            }

            if(meterAssetsDomainMap.get(item.getMeterAssetsId())!=null){
                item.setMeterAssetsNo(meterAssetsDomainMap.get(item.getMeterAssetsId()).getMeterAssetsNo());
            }

            if(functionCodeConfigMap.get(Long.valueOf(item.getFunctionCode()))!=null){
                item.setFunctionCodeName(functionCodeConfigMap.get(Long.valueOf(item.getFunctionCode())));
            }

            if(powerDirectionConfigMap.get(Long.valueOf(item.getPowerDirection()))!=null){
                item.setPowerDirectionName(powerDirectionConfigMap.get(Long.valueOf(item.getPowerDirection())));
            }
        }



        list=list.stream().sorted(Comparator.comparing(WriteFilesDomain::getBusinessPlaceCode,Comparator.nullsFirst(Long::compareTo))
                .thenComparing(WriteFilesDomain::getWriteSectionId,Comparator.nullsFirst(Long::compareTo))
                .thenComparing(WriteFilesDomain::getUserNo,Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        return list;
    }

}
