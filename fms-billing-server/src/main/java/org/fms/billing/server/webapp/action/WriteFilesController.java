package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.CustomCollectors;
import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.util.MonUtils;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterAssetsDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.PriceExecutionDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.beakInterface.UserDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.domain.mongo.WriteSectMongoDomain;
import org.fms.billing.common.webapp.entity.CbReadingDownEntity;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.MeterWriteSnEntity;
import org.fms.billing.common.webapp.entity.SettlementDetailEntity;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IMeterMeterAssetsRelService;
import org.fms.billing.common.webapp.service.IMeterMoneyService;
import org.fms.billing.common.webapp.service.IMeterService;
import org.fms.billing.common.webapp.service.IPriceExecutionService;
import org.fms.billing.common.webapp.service.IUserMongoService;
import org.fms.billing.common.webapp.service.IWriteFilesService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.WriteSectMongoDAO;
import org.fms.billing.server.webapp.service.CimService;
import org.fms.billing.server.webapp.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("WriteFiles")
public class WriteFilesController {
    @Autowired
    @Qualifier("writeFilesServiceImpl")
    private IWriteFilesService iWriteFilesService;

    @Autowired
    @Qualifier("meterServiceImpl")
    private IMeterService iMeterService;

    @Autowired
    @Qualifier("userMongoServiceImpl")
    private IUserMongoService userMongoService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("priceExecutionServiceImpl")
    private IPriceExecutionService priceExecutionService;

    @Autowired
    private WriteSectMongoDAO writeSectMongoDAO;

    @Autowired
    private CimService cimService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private MeterDAO meterDAO;

    @Autowired
    @Qualifier("meterMoneyServiceImpl")
    private IMeterMoneyService meterMoneyService;

    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @Autowired
    @Qualifier("meterMeterAssetsRelServiceIpml")
    private IMeterMeterAssetsRelService meterMeterAssetsRelService;

    @RequestMapping("mongoFind")
    @ResponseBody
    public Object mongoFind(@RequestBody String json) throws Exception {
//		WriteFilesDomain body = JSONUtil.readValue(json, WriteFilesDomain.class);
        //手工表码修改剔除发行
        WriteFilesDomain body = GsonUtils.readValue(json, WriteFilesDomain.class);
        if (body.getMon() == null) {
            throw new Exception("电费月份为空.");
        }
        // 计量点取电价
        List<WriteFilesDomain> writeFilesDomains = iWriteFilesService.mongoFind(body);

        writeFilesDomains.stream().forEach(t -> {
            MeterDomain meterDomain = new MeterDomain();
            meterDomain.setId(t.getMeterId());
            meterDomain.setMon(t.getMon());
            List<MeterDomain> meterDomains = iMeterService.findMeterByWhere(meterDomain);
            if (null != meterDomains && meterDomains.size() > 0) {
                t.setPriceType(meterDomains.get(0).getPriceType());
                t.setStatus(meterDomains.get(0).getStatus());
                t.setWritorId(meterDomains.get(0).getWritorId());
            }
        });
        try {

            List<MeterAssetsDomain> meterAssetsDomains = iWriteFilesService.getMeterAssetsByAssetsIds(writeFilesDomains
                    .stream().map(WriteFilesDomain::getMeterAssetsId).filter(meterAssetsId -> meterAssetsId != null)
                    .distinct().collect(toList()));

            // madeNo可能是空，暂时用资产号显示 闫海祥
            writeFilesDomains.stream().filter(meterAssetsId -> meterAssetsId != null).forEach(w -> {
                try {

                    MeterAssetsDomain meterAssetsDomain = meterAssetsDomains.stream()
                            .filter(a -> a.getId().compareTo(w.getMeterAssetsId()) == 0).findFirst().orElse(null);
                    w.setMadeNo(meterAssetsDomain.getMeterAssetsNo());

                } catch (Exception e) {
                    e.initCause(new Exception("计量点编号:" + w.getMeterNo() + "资产有问题,请检查."));
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new HttpResultPagination<WriteFilesDomain>(body, writeFilesDomains);
    }

    @RequestMapping("mongoUpdate")
    @ResponseBody
    public long mongoUpdate(@RequestBody List<WriteFilesDomain> writeFilesDomains) {
        if(writeFilesDomains!=null && writeFilesDomains.size()>0){
            writeFilesDomains.forEach(t->{
                t.setStatus(null);
            });
        }
        return iWriteFilesService.mongoUpdate(writeFilesDomains);

    }

    @RequestMapping("findByWhere")
    @ResponseBody
    public List<WriteFilesDomain> findByWhere(@RequestBody(required = false) String writeFilesDomainJson) {
        WriteFilesDomain writeFilesDomain =
                JSONObject.parseObject(writeFilesDomainJson,
                        WriteFilesDomain.class);

        return iWriteFilesService.findByWhere(writeFilesDomain);

    }

    @RequestMapping("findWriteFilesByCustomer")
    @ResponseBody
    // 历史抄表记录查询
    public Object findWriteFilesByCustomer(@RequestBody String customer) {
        CustomerDomain customerDomain = GsonUtils.readValue(customer, CustomerDomain.class);
        List<WriteFilesDomain> writeFilesByCustomer = iWriteFilesService.findWriteFilesByCustomer(customerDomain);
        return new HttpResultPagination<>(new WriteFilesDomain(), writeFilesByCustomer);
    }

    @RequestMapping("getWriteFiles")
    @ResponseBody
    public List<WriteFilesDomain> getWriteFiles(@RequestBody WriteFilesDomain writeFilesDomain) {

        List<WriteFilesDomain> writeFilesDomains = iWriteFilesService.getWriteFiles(writeFilesDomain);

        return writeFilesDomains;
    }

    @RequestMapping("getWriteFilesDomain")
    @ResponseBody
    public Object getWriteFilesDomain(@RequestBody WriteFilesDomain writeFilesDomain) {

        List<WriteFilesDomain> list = iWriteFilesService.getWriteFilesDomain(writeFilesDomain);

        return list;
    }

    // 划零户查询
    @ResponseBody
    @RequestMapping("zeroUser")
    public HttpResultPagination zeroUser(@RequestBody String json) {
        WriteFilesDomain writeFiles = GsonUtils.readValue(json, WriteFilesDomain.class);
        writeFiles.setDiffNum(BigDecimal.ZERO);
        List<WriteFilesDomain> list =
                iWriteFilesService.zeroUserQuery(writeFiles);
        return new HttpResultPagination<>(writeFiles, list);

    }

    //	未抄表户查询
    @ResponseBody
    @RequestMapping("unwriteUser")
    public Object unwriteUser(@RequestBody String json) {
        WriteFilesDomain writeFiles = GsonUtils.readValue(json, WriteFilesDomain.class);
        writeFiles.setWriteFlag((byte) 0);
        List<WriteFilesDomain> list = iWriteFilesService.writeFilesQuery(writeFiles);
        return new HttpResultPagination<WriteFilesDomain>(writeFiles, list);
    }

    //	电量波动异常查询
    @ResponseBody
    @RequestMapping("powerException")
    public Object powerException(@RequestBody String json) throws Exception {
        WriteFilesDomain writeFiles = GsonUtils.readValue(json, WriteFilesDomain.class);
        List<WriteFilesDomain> list = iWriteFilesService.powerException(writeFiles);
        return new HttpResultPagination<WriteFilesDomain>(writeFiles, list);
    }


    @RequestMapping("readingDown")
    @ResponseBody
    public HttpResult readingDown(@RequestBody String json) {
        List<CbReadingDownEntity> cbReadingDownEntityList = new ArrayList<>();

        try {
            WriteFilesDomain body = GsonUtils.readValue(json, WriteFilesDomain.class);
            Integer[] writeSectionIds = body.getWriteSectionIds();
            //取抄表记录
            body.setPageSize(-1);
            List<WriteFilesDomain> writeFilesDomains = iWriteFilesService.mongoFindForReadingDown(body);

            if (writeFilesDomains == null || writeFilesDomains.size() < 1) {
                return new HttpResult(HttpResult.ERROR, "无需要下装的数据");
            }

            //取表计资产id
            List<Long> meterAssetsIdList =
                    writeFilesDomains.stream().filter(t -> t.getMeterAssetsId() != null)
                            .map(WriteFilesDomain::getMeterAssetsId).collect(Collectors.toList());

            List<Long> meterIds =
                    writeFilesDomains.stream().map(WriteFilesDomain::getMeterId).collect(Collectors.toList());

            // 本月抄表信息
            MeterDomain meterDomain = new MeterDomain();
            meterDomain.setMon(body.getMon());
            meterDomain.setIds(meterIds);
            meterDomain.setSn(body.getSn().intValue());
            List<MeterDomain> meterDomains = iMeterService.findMeterDomain(meterDomain);
            Map<String, MeterDomain> meterDomainMap =
                    meterDomains.stream().collect(Collectors.toMap(o -> o.getId() + "_" + o.getSn(), a -> a, (k1, k2) -> k1));

            //获取地址信息
            List<Long> userIds =
                    meterDomains.stream().map(MeterDomain::getUserId).distinct().collect(Collectors.toList());

            String userJson =
                    restTemplate.postForObject(Global.getConfig("getUserByIds"), userIds,
                            String.class);

            List<UserDomain> userDomainList =
                    JSONObject.parseArray(userJson, UserDomain.class);

            Map<Long, UserDomain> userMap = userDomainList.stream()
                    .collect(Collectors.toMap(UserDomain::getId, a -> a, (k1, k2) -> k1));

            // 上月抄表信息
            body.setMon(new Integer(MonUtils.getLastMon(body.getMon().toString())));
            body.setWriteSectionIds(writeSectionIds);
            List<WriteFilesDomain> lastMeterDomains =
                    iWriteFilesService.mongoFindForReadingDown(body);
            Map<String, WriteFilesDomain> lastWriteFilesMap =
                    lastMeterDomains.stream().collect(Collectors.toMap(o -> o.getMeterId() + "_" + o.getSn(), a -> a, (k1, k2) -> k1));

            //抄表序号
            JSONObject postParam = new JSONObject();
            postParam.put("writeSectionIds", writeSectionIds);
            postParam.put("pageSize", -1);
            //取抄表序号
            String returnMeterWriteSnJson =
                    restTemplate.postForObject(Global.getConfig(
                            "getAssetsRelByWriteSect"), postParam, String.class);
            List<MeterWriteSnEntity> meterWriteSnEntities =
                    JSONObject.parseArray(JSONObject.parseObject(returnMeterWriteSnJson).getString("list"),
                            MeterWriteSnEntity.class);
            Map<String, MeterWriteSnEntity> meterWriteSnEntityMap =
                    meterWriteSnEntities.stream()
                            .collect(Collectors.toMap(o -> o.getMeterId().toString() + "_" + o.getMeterAssetsId(), a -> a, (k1, k2) -> k1));

            //抄表段
            WriteSectMongoDomain writeSectMongoDomain = new WriteSectMongoDomain();
            writeSectMongoDomain.setIds(new ArrayList<>(Arrays.asList(writeSectionIds)));
            writeSectMongoDomain.setMon(body.getMon());
            writeSectMongoDomain.setPageSize(-1);
            List<WriteSectMongoDomain> writeSectMongoDomainList =
                    writeSectMongoDAO.findWriteSectMongoByWhere(writeSectMongoDomain);
            System.out.println("writeSectMongoDomainList=====" + writeSectMongoDomainList);
            Map<Long, WriteSectMongoDomain> writeSectMongoDomainMap =
                    writeSectMongoDomainList.stream().collect(Collectors.toMap(o -> o.getId(), a -> a, (k1, k2) -> k1));


            //电价
            PriceExecutionDomain priceExecutionDomain =
                    new PriceExecutionDomain();
            priceExecutionDomain.setPageSize(-1);
            List<PriceExecutionDomain> priceExecutionDomains =
                    priceExecutionService.findByWhere(priceExecutionDomain);
            Map<String, BigDecimal> priceExecutionMap =
                    priceExecutionDomains.stream().collect(Collectors.groupingBy(o -> o.getPriceTypeId() + "_" + o.getTimeSeg(), CustomCollectors.summingBigDecimal(PriceExecutionDomain::getPrice)));


            //获取表号
            List<MeterAssetsDomain> meterAssetsDomainList = null;

            meterAssetsDomainList = iWriteFilesService.getMeterAssetsByAssetsIds(meterAssetsIdList);


            Map<Long, MeterAssetsDomain> meterAssetsDomainMap = meterAssetsDomainList.stream()
                    .collect(Collectors.toMap(MeterAssetsDomain::getId, a -> a, (k1, k2) -> k1));


            //组装抄表器上装实体
            writeFilesDomains.forEach(t -> {
                CbReadingDownEntity cbReadingDownEntity = new CbReadingDownEntity();
                cbReadingDownEntity.setUserId(t.getUserId());
                cbReadingDownEntity.setUserName(t.getUserName());
                cbReadingDownEntity.setUserNo(t.getUserNo());
                cbReadingDownEntity.setStartNum(t.getStartNum());
                cbReadingDownEntity.setFactorNum(t.getFactorNum());
                cbReadingDownEntity.setFunctionCode(t.getFunctionCode());
                cbReadingDownEntity.setTimeSeg(t.getTimeSeg());
                cbReadingDownEntity.setMeterId(t.getMeterId());
                cbReadingDownEntity.setEndNum(t.getEndNum());
                cbReadingDownEntity.setMon(t.getMon());
                cbReadingDownEntity.setSn(t.getSn().intValue());
                cbReadingDownEntity.set_id(t.get_id());
                cbReadingDownEntity.setWriteSectionId(t.getWriteSectionId());
                cbReadingDownEntity.setMeterAssetsId(t.getMeterAssetsId());

                UserDomain userDomain = userMap.get(t.getUserId());
                if (userDomain != null) {
                    cbReadingDownEntity.setAddress(userDomain.getAddress());
                }


                if (writeSectMongoDomainMap.get(t.getWriteSectionId()) == null) {
                    cbReadingDownEntity.setWriteSectionNo(null);
                } else {
                    cbReadingDownEntity.setWriteSectionNo(writeSectMongoDomainMap.get(t.getWriteSectionId()).getWriteSectNo());
                }
                cbReadingDownEntityList.add(cbReadingDownEntity);
            });

            cbReadingDownEntityList.forEach(t -> {
                t.setPriceTypeId(meterDomainMap.get(t.getMeterId() + "_" + t.getSn()).getPriceType());
                if (meterAssetsDomainMap.get(t.getMeterAssetsId()) == null) {
                    t.setMeterAssetsNo("");
                } else {
                    t.setMeterAssetsNo(meterAssetsDomainMap.get(t.getMeterAssetsId()).getMadeNo());
                }
                t.setPrice(priceExecutionMap.get(t.getPriceTypeId() + "_" + t.getTimeSeg()));
                if (meterWriteSnEntityMap.get(t.getMeterId() + "_" + t.getMeterAssetsId()) != null) {
                    t.setMeterSn(meterWriteSnEntityMap.get(t.getMeterId() +
                            "_" + t.getMeterAssetsId()).getMeterSn());
                } else {
                    t.setMeterSn(null);
                }
                if (meterWriteSnEntityMap.get(t.getMeterId() + "_" + t.getMeterAssetsId()) != null) {
                    t.setWriteSn(meterWriteSnEntityMap.get(t.getMeterId() + "_" + t.getMeterAssetsId()).getWriteSn());
                } else {
                    t.setWriteSn(null);
                }
                if (lastWriteFilesMap.get(t.getMeterId() + "_" + t.getSn()) != null) {
                    t.setWritePower(lastWriteFilesMap.get(t.getMeterId() + "_" + t.getSn()).getWritePower());
                } else {
                    t.setWritePower(BigDecimal.ZERO);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "下装失败:" + e.getMessage());
        }

        return new HttpResult<>(HttpResult.SUCCESS, cbReadingDownEntityList);
    }

    @RequestMapping("readingUp")
    @ResponseBody
    public HttpResult readingUp(@RequestBody String json) {
        List<CbReadingDownEntity> cbReadingDownEntityList = new ArrayList<>();
        long account = 0;
        try {
            cbReadingDownEntityList =
                    JSONObject.parseArray(json, CbReadingDownEntity.class);
            //过滤掉没抄的数据
            cbReadingDownEntityList=
                    cbReadingDownEntityList.stream().filter(t->t.getEndNum()!=null).collect(toList());

            if(cbReadingDownEntityList==null || cbReadingDownEntityList.size()<1){
                return new HttpResult<>(HttpResult.ERROR, "上装失败:止码为空");
            }

            cbReadingDownEntityList.forEach(t->{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                if(t.getWriteTime()!=null && !"".equals(t.getWriteTime())){
                    try {
                        Date date=sdf.parse(t.getWriteTime());
                        t.setWriteDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    t.setWriteDate(new Date());
                }
                t.setMeterId(Long.valueOf(t.get_id().split("#")[0]));

            });

            List<Long> meterIds=
                    cbReadingDownEntityList.stream().map(CbReadingDownEntity::getMeterId).distinct().collect(toList());

            if(meterIds==null ||meterIds.size()<1){
                return new HttpResult<>(HttpResult.ERROR, "上装失败:没有要处理的计量点信息");
            }
            List<WriteFilesDomain> writeFilesDomains = new ArrayList<>();
            meterIds.forEach(t->{
                WriteFilesDomain writeFilesDomain=new WriteFilesDomain();
                writeFilesDomain.setMeterId(t);
                writeFilesDomain.setWriteFlag((byte)1);
                writeFilesDomains.add(writeFilesDomain);
            });

            List<MeterDomain> meterDomains =
                    iMeterService.getEffectiveComputeMeter(cbReadingDownEntityList.get(0).getMon(),writeFilesDomains, 3);

            List<Long> punishMeterIds=
                    meterDomains.stream().map(MeterDomain::getId).distinct().collect(toList());

            if(punishMeterIds!=null && punishMeterIds.size()>1){
                cbReadingDownEntityList=
                        cbReadingDownEntityList.stream().filter(t->!punishMeterIds.contains(t.getMeterId())).collect(toList());
            }

            if(cbReadingDownEntityList.size()<1){
                return new HttpResult<>(HttpResult.ERROR, "已发行用户,不允许上装表码");
            }

            account =
                    iWriteFilesService.mongoUpdateByCBQ(cbReadingDownEntityList);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "上装失败:" + e.getMessage());
        }

        return new HttpResult<>(HttpResult.SUCCESS, "上装成功，更新" + account + "条记录");
    }

    @RequestMapping("queryUnreadMeterUsers")
    @ResponseBody
    public Object queryUnreadMeterUsers(@RequestBody String writeFilesDomainJson) throws Exception {
        WriteFilesDomain writeFile = GsonUtils.readValue(writeFilesDomainJson, WriteFilesDomain.class);
        List<WriteFilesDomain> writeFilesList = iWriteFilesService.findWriteFilesList(writeFile);
        return new HttpResultPagination<>(writeFile, writeFilesList);
    }

    //用户电量电费清单
    @RequestMapping("queryEectricityBill")
    @ResponseBody
    public HttpResult queryEectricityBill(@RequestBody String paramSettlementJson) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(paramSettlementJson);
        Integer startMon = jsonObject.getInteger("startMon");
        Integer endMon = jsonObject.getInteger("endMon");
        String settlementName = jsonObject.getString("settlementName");
        String bankNo = jsonObject.getString("bankNo");
        Long writeSectionId = jsonObject.getLong("writeSectionId");

        List<Long> meterIds = new ArrayList<>();

        List<SettlementDomain> settlementDomains=new ArrayList<>();

        //根据计算户户名和银行账号计量点集合
        if ((bankNo != null && !"".equals(bankNo)) || (settlementName != null && !"".equals(settlementName))) {
            SettlementDomain paramSettlement = new SettlementDomain();
            paramSettlement.setBankNo(bankNo);
            paramSettlement.setSettlementName(settlementName);
            paramSettlement.setPageSize(-1);
            List<Long> settlementIds =
                    cimService.findSettlementIdByWhere(paramSettlement);

            if (settlementIds == null || settlementIds.size() < 1) {
                return new HttpResult<>(HttpResult.ERROR, "没有要查询的结算户信息");
            }

            settlementDomains=cimService.findSettlementByIds(settlementIds);

            if (settlementDomains == null || settlementDomains.size() < 1) {
                return new HttpResult<>(HttpResult.ERROR, "没有要查询的结算户信息");
            }
            List<SettlementMeterRelDomain> settlementMeterRelDomains =
                    cimService.getMeterIdsBySettlements(settlementDomains);
            if (settlementMeterRelDomains == null || settlementMeterRelDomains.size() < 1) {
                return new HttpResult<>(HttpResult.ERROR, "查询的结算户下没有计量点信息");
            }
            meterIds =
                    settlementMeterRelDomains.stream().map(SettlementMeterRelDomain::getMeterId).distinct().collect(Collectors.toList());

        }
        //根据抄表区段id 查询计量点id
        if (writeSectionId != null) {
            MeterDomain paramMeterDomain = new MeterDomain();
            paramMeterDomain.setMon(endMon);
            paramMeterDomain.setWriteSectionId(writeSectionId);
            List<MeterDomain> meterList = meterDAO.findMeterDomain(paramMeterDomain);
            if (meterList == null || meterList.size() < 1) {
                return new HttpResult<>(HttpResult.ERROR, endMon + "月，该抄表区段没有计量点");
            }

            meterIds =
                    meterList.stream().map(MeterDomain::getId).distinct().collect(Collectors.toList());

            String resultSettlements = restTemplate.postForObject
                    (Global.getConfig("getSettlementByMeterIds"), meterIds, String.class);

            List<Long> settlementIds = JSONObject.parseArray(resultSettlements, Long.class);

            String settlementJson = restTemplate.postForObject(Global.getConfig("findSettlementByIds"),
                    settlementIds, String.class);

            settlementDomains = JSONUtil
                    .readValue(settlementJson,
                            new TypeReference<HttpResult<List<SettlementDomain>>>() {
                    }).getResultData();


        }

        Map<Long, SettlementDomain> settlementDomainMap =
                settlementDomains.stream().collect(Collectors.toMap(SettlementDomain::getId, k -> k));

        List<Long> businessPlaceCodes=
                settlementDomains.stream().map(SettlementDomain::getBusinessPlaceCode).distinct().collect(toList());

        if (businessPlaceCodes==null || businessPlaceCodes.size()<1){
            return new HttpResult<>(HttpResult.ERROR, "该结算户没有营业区域");
        }

        DeptDomain paramDeptDomain=new DeptDomain();
        List<MapEntity> depts =
                deptService.findIdMapByDomain(paramDeptDomain);
        Map<Long, String> deptIdMap = FormatterUtil.ListMapEntityToMap(depts);
        //获取抄表记录
        WriteFilesDomain paramWriteFilesDoamin = new WriteFilesDomain();
        paramWriteFilesDoamin.setStartMon(startMon);
        paramWriteFilesDoamin.setEndMon(endMon);
        paramWriteFilesDoamin.setMeterIds(meterIds);
        paramWriteFilesDoamin.setPageSize(-1);
        List<WriteFilesDomain> writeFilesDomains =
                iWriteFilesService.findByWhere(paramWriteFilesDoamin);

        if (writeFilesDomains==null || writeFilesDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR, "该结算户没有抄表记录");
        }

        //获取电费记录
        MeterMoneyDomain paramMeterMoneyDomain = new MeterMoneyDomain();
        paramMeterMoneyDomain.setStartMon(startMon);
        paramMeterMoneyDomain.setEndMon(endMon);
        paramMeterMoneyDomain.setMeterIds(meterIds);
        paramMeterMoneyDomain.setPageSize(-1);
        List<MeterMoneyDomain> meterMoneyDomains =
                meterMoneyService.findByWhere(paramMeterMoneyDomain);
        Map<String, MeterMoneyDomain> meterMoneyDomainMap =
                meterMoneyDomains.stream().collect(Collectors.toMap(o -> o.getMon() + "_"+o.getSn()+ "_" + o.getMeterId(), a -> a, (k1, k2) -> k1));


        //表序号
        MeterMeterAssetsRelDomain meterMeterAssetsRelDomain =
                new MeterMeterAssetsRelDomain();
        List<Long> meterAssetsIds =
                writeFilesDomains.stream().filter(t -> t.getMeterAssetsId() != null).map(WriteFilesDomain::getMeterAssetsId).distinct().collect(Collectors.toList());
        meterMeterAssetsRelDomain.setMeterAssetsIds(meterAssetsIds);
        meterMeterAssetsRelDomain.setMon(endMon);
        List<MeterMeterAssetsRelDomain> meterMeterAssetsRelDomains =
                meterMeterAssetsRelService.mongoFind(meterMeterAssetsRelDomain);
        Map<Long, MeterMeterAssetsRelDomain> meterMeterAssetsRelDomainMap = meterMeterAssetsRelDomains.stream()
                .collect(Collectors.toMap(MeterMeterAssetsRelDomain::getMeterAssetsId, a -> a, (k1, k2) -> k1));

        //获取结算户与计量点关系
        List<SettlementMeterRelDomain> settlementMeterRelDomains =
                cimService.getSettlementMeterRelByMeterIds(meterIds);

        //获取计量点和结算户map
        Map<Long, SettlementDomain> meterSettlementRelMap =
                settlementMeterRelDomains.stream().filter(t -> t.getMeterId() != null).filter(t -> t.getSettlementId() != null)
                        .collect(Collectors.toMap(SettlementMeterRelDomain::getMeterId, a -> settlementDomainMap.get(a.getSettlementId()), (k1, k2) -> k1));


        //获取退预存电费
        ChargeDomain chargeInfoDomain = new ChargeDomain();
        chargeInfoDomain.setfChargeMode(6);
        chargeInfoDomain.setStartMon(startMon);
        chargeInfoDomain.setStartMon(endMon);
        chargeInfoDomain.setMeterIds(meterIds);
        List<ChargeDomain> chargeInfoDomains =
                iChargeService.findByWhere(chargeInfoDomain);
        //分组求和 记录
        Map<String, BigDecimal> chargeInfoDomainMap =
                chargeInfoDomains.stream().filter(t -> t.getFactTotal() != null)
                        .collect(Collectors.groupingBy(o -> o.getMon() + "_"+o.getSn()+ "_" + o.getMeterId(), CustomCollectors.summingBigDecimal(ChargeDomain::getFactTotal)));


        //赋值排序
        writeFilesDomains.stream().forEach(t -> {
            MeterMeterAssetsRelDomain meterMeterAssetsRelExp =
                    meterMeterAssetsRelDomainMap.get(t.getMeterAssetsId());
            if (meterMeterAssetsRelExp == null) {
                t.setMeterSn(1);
            } else {
                t.setMeterSn(meterMeterAssetsRelExp.getMeterSn() == null ? 1 :
                        meterMeterAssetsRelExp.getMeterSn());
            }
            SettlementDomain settleExp =
                    meterSettlementRelMap.get(t.getMeterId());
            if(settleExp==null){
                t.setSettlementId(null);
            }else{
                t.setSettlementId(settleExp.getId());
            }

        });

        List<Long> writeMeterIds=
                writeFilesDomains.stream().map(WriteFilesDomain::getMeterId).distinct().collect(toList());

        //赋值排序
        for(MeterMoneyDomain t:meterMoneyDomains){
            if(!writeMeterIds.contains(t.getMeterId())){
                WriteFilesDomain writeFilesDomain=new WriteFilesDomain();
                writeFilesDomain.setMeterId(t.getMeterId());
                writeFilesDomain.setMon(t.getMon());
                writeFilesDomain.setStartNum(BigDecimal.ZERO);
                writeFilesDomain.setEndNum(BigDecimal.ZERO);
                writeFilesDomain.setMeterSn(999);
                SettlementDomain settleExp =
                        meterSettlementRelMap.get(t.getMeterId());
                if(settleExp==null){
                    writeFilesDomain.setSettlementId(null);
                }else{
                    writeFilesDomain.setSettlementId(settleExp.getId());
                }
                writeFilesDomain.setFactorNum(BigDecimal.ONE);
                writeFilesDomain.setSn((byte)1);
                writeFilesDomain.setFunctionCode((byte)3);
                writeFilesDomains.add(writeFilesDomain);
            }

        };



        //过滤掉无效的
        writeFilesDomains=
                writeFilesDomains.stream().filter(t->t.getSettlementId()!=null).collect(toList());

        writeFilesDomains =
                writeFilesDomains.stream()
                        .sorted(Comparator.comparing(WriteFilesDomain::getSettlementId).thenComparing(WriteFilesDomain::getMon)
                                .thenComparing(WriteFilesDomain::getFunctionCode, Comparator.nullsLast(Byte::compareTo)).thenComparing(WriteFilesDomain::getMeterSn, Comparator.nullsLast(Integer::compareTo)))
                        .collect(Collectors.toList());

        List<SettlementDetailEntity> beanList = new ArrayList<>();

        List<SettlementDetailEntity> bankCollectionDetailEntities = new ArrayList<>();

        for (WriteFilesDomain writeFilesDomain : writeFilesDomains) {

            List<String> bankCollectionMeterIds =
                    beanList.stream().map(o->o.getMon()+"_"+o.getMeterId()).distinct().collect(Collectors.toList());

            SettlementDetailEntity settlementDetailEntity =
                    new SettlementDetailEntity();

            SettlementDomain settleExp =
                    meterSettlementRelMap.get(writeFilesDomain.getMeterId());
            if (settleExp == null) {
                continue;
            }
            settlementDetailEntity.setSettlementNo(settleExp.getSettlementNo());
            settlementDetailEntity.setSettlementName(settleExp.getSettlementName());
            settlementDetailEntity.setAddress(settleExp.getAddress());
            settlementDetailEntity.setBusinessPlaceCodeName(deptIdMap.get(settleExp.getBusinessPlaceCode()  ));
            settlementDetailEntity.setMon(writeFilesDomain.getMon());
            settlementDetailEntity.setStartNum(writeFilesDomain.getStartNum());
            settlementDetailEntity.setEndNum(writeFilesDomain.getEndNum());
            settlementDetailEntity.setFactorNum(writeFilesDomain.getFactorNum().intValue());
            settlementDetailEntity.setSn((int) writeFilesDomain.getSn());
            settlementDetailEntity.setMeterSn(writeFilesDomain.getMeterSn().intValue());
            String meterMoneyKey =
                    writeFilesDomain.getMon() + "_" + writeFilesDomain.getSn() + "_" + writeFilesDomain.getMeterId();
            MeterMoneyDomain meterMoneyExp =
                    meterMoneyDomainMap.get(meterMoneyKey);
            if (meterMoneyExp != null && writeFilesDomain.getFunctionCode() != Byte.valueOf("2") && !bankCollectionMeterIds.contains(writeFilesDomain.getMeterId())) {
                settlementDetailEntity.setAddPower(meterMoneyExp.getAddPower());
                settlementDetailEntity.setActiveTransformerLossPower(meterMoneyExp.getActiveTransformerLossPower());
                settlementDetailEntity.setTotalPower(meterMoneyExp.getTotalPower());
                settlementDetailEntity.setVolumeCharge(meterMoneyExp.getVolumeCharge());
                settlementDetailEntity.setBasicMoney(meterMoneyExp.getBasicMoney());
                settlementDetailEntity.setPowerRateMoney(meterMoneyExp.getPowerRateMoney());
                settlementDetailEntity.setActiveWritePower(meterMoneyExp.getActiveWritePower());
                settlementDetailEntity.setSurcharges(meterMoneyExp.getSurcharges());
                BigDecimal refundMoney=
                        chargeInfoDomainMap.get(writeFilesDomain.getMon() +
                                "_" + writeFilesDomain.getSn() + "_"+writeFilesDomain.getMeterId());
                if(refundMoney==null){
                    refundMoney=BigDecimal.ZERO;
                }
                settlementDetailEntity.setRefundMoney((meterMoneyExp.getRefundMoney().add(refundMoney)).negate());
                settlementDetailEntity.setTotalMoney(meterMoneyExp.getAmount().add(meterMoneyExp.getRefundMoney()));
                settlementDetailEntity.setShouldMoney(settlementDetailEntity.getTotalMoney().add(settlementDetailEntity.getRefundMoney()));
            } else {
                settlementDetailEntity.setPriceName("-");
                settlementDetailEntity.setAddPower(BigDecimal.ZERO);
                settlementDetailEntity.setActiveTransformerLossPower(BigDecimal.ZERO);
                settlementDetailEntity.setTotalPower(BigDecimal.ZERO);
                settlementDetailEntity.setVolumeCharge(BigDecimal.ZERO);
                settlementDetailEntity.setBasicMoney(BigDecimal.ZERO);
                settlementDetailEntity.setActiveWritePower(BigDecimal.ZERO);
                settlementDetailEntity.setPowerRateMoney(BigDecimal.ZERO);
                settlementDetailEntity.setRefundMoney(BigDecimal.ZERO);
                settlementDetailEntity.setTotalMoney(BigDecimal.ZERO);
                settlementDetailEntity.setShouldMoney(BigDecimal.ZERO);
            }
            settlementDetailEntity.setMeterId(writeFilesDomain.getMeterId());
            beanList.add(settlementDetailEntity);

            bankCollectionDetailEntities.add(settlementDetailEntity);
        }


        return new HttpResult<>(HttpResult.SUCCESS,"查询成功",bankCollectionDetailEntities);
    }
}
