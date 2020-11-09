package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.PriceLadderRelaDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMoneySumMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;
import org.fms.billing.common.webapp.entity.LadderPowerQueryEntity;
import org.fms.billing.common.webapp.entity.SettlementDetailEntity;
import org.fms.billing.common.webapp.service.IMeterMoneyService;
import org.fms.billing.common.webapp.service.IPriceExecutionService;
import org.fms.billing.common.webapp.service.IPriceLadderRelaService;
import org.fms.billing.common.webapp.service.IPriceTypeService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.WriteSectDAO;
import org.fms.billing.server.webapp.service.CimService;
import org.fms.billing.server.webapp.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("MeterMoney")
public class MeterMoneyController {

    @Autowired
    @Qualifier("meterMoneyServiceImpl")
    private IMeterMoneyService meterMoneyService;

    @Autowired
    private WriteSectDAO writeSectDAO;

    @Autowired
    private CimService cimService;

    @Autowired
    private DeptService deptService;

    @Autowired
    @Qualifier("priceTypeServiceImpl")
    private IPriceTypeService iPriceTypeService;


    @Autowired
    private MeterDAO meterDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("priceExecutionServiceImpl")
    private IPriceExecutionService priceExecutionService;

    @Autowired
    @Qualifier("priceLadderRelaImpl")
    private IPriceLadderRelaService iPriceLadderRelaService;

    @RequestMapping("select")
    @ResponseBody
    public List<MeterMoneyDomain> findByWhere(@RequestBody String meterMoneyDomainJson) {
        MeterMoneyDomain meterMoneyDomain =
                JSONObject.parseObject(meterMoneyDomainJson,
                        MeterMoneyDomain.class);
        return meterMoneyService.findByWhere(meterMoneyDomain);
    }

    @RequestMapping("findMeterMoneyByIds")
    @ResponseBody
    public List<MeterMoneyDomain> findMeterMoneyByIds(@RequestBody String idsJson) {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        return meterMoneyService.findMeterMoneyByIds(ids);
    }

    @RequestMapping("meterMoneyDetailQuery")
    @ResponseBody
    //电量电费实时查询报表
    public List<MeterMoneyDomain> meterMoneyDetailQuery(@RequestBody String meterMoneyDomainJson) {
        MeterMoneyDomain meterMoneyDomain =
                JSONObject.parseObject(meterMoneyDomainJson,
                        MeterMoneyDomain.class);
        return meterMoneyService.meterMoneyDetailQuery(meterMoneyDomain);
    }

    @RequestMapping("getMeterMoney")
    @ResponseBody
    //客户电费明细查询
    public HttpResultPagination<MeterMongoDomain> getMeterMoney(@RequestBody String meter) {
        MeterDomain meterDomain = GsonUtils.readValue(meter, MeterDomain.class);
        List<MeterMongoDomain> meterMongoDomain = meterMoneyService.getMeterMoney(meterDomain);
        return new HttpResultPagination<>(meterDomain, meterMongoDomain);
    }

    @RequestMapping("queryHistoricalMeterMoney")
    @ResponseBody
    // 历史电费查询
    public JSONObject queryHistoricalMeterMoney(@RequestBody String meter) {
        MeterMoneyDomain meterMoneyDomain = GsonUtils.readValue(meter, MeterMoneyDomain.class);
        List<MeterMoneyDomain> meterMoneys = meterMoneyService.findHistoricalMeterMoney(meterMoneyDomain);
        JSONObject jsonObject=new JSONObject();
        BigDecimal sumTotalPower =BigDecimal.ZERO;
        BigDecimal sumAmount =BigDecimal.ZERO;
        BigDecimal sumRefundMoney =BigDecimal.ZERO;
        BigDecimal sumTotalMoney =BigDecimal.ZERO;
        if(meterMoneys!=null && meterMoneys.size()>0){
            sumTotalPower=
                    meterMoneys.stream().filter(t -> t.getTotalPower() !=null)
                    .map(MeterMoneyDomain::getTotalPower).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumAmount=
                    meterMoneys.stream().filter(t -> t.getAmount() !=null)
                            .map(MeterMoneyDomain::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumRefundMoney=
                    meterMoneys.stream().filter(t -> t.getRefundMoney() !=null)
                            .map(MeterMoneyDomain::getRefundMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumTotalMoney=
                    meterMoneys.stream().filter(t -> t.getTotalMoney() !=null)
                            .map(MeterMoneyDomain::getTotalMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        jsonObject.put("data",new HttpResultPagination<>(meterMoneyDomain,meterMoneys));
        jsonObject.put("sumTotalPower",sumTotalPower);
        jsonObject.put("sumRefundMoney",sumRefundMoney);
        jsonObject.put("sumTotalMoney",sumTotalMoney);
        jsonObject.put("sumAmount",sumAmount);
        return jsonObject;
    }

    @RequestMapping("insert")
    @ResponseBody
    public int insert(@ModelAttribute MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyService.insert(meterMoneyDomain);
    }

    @RequestMapping("update")
    @ResponseBody
    public int update(@ModelAttribute MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyService.update(meterMoneyDomain);
    }

    @RequestMapping("mongoFind")
    @ResponseBody
    public List<MeterMoneyDomain> mongoFind(@RequestBody MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyService.mongoFind(meterMoneyDomain);
    }

    @RequestMapping("getMeterMoneyList")
    @ResponseBody
    public HttpResultPagination<MeterMongoDomain> getMeterMoneyList(@RequestBody MeterDomain meterDomain) {

        List<MeterMongoDomain> r = meterMoneyService.getMeterMoneyByWhere(meterDomain);

        return new HttpResultPagination<MeterMongoDomain>(meterDomain, r);
    }

    @RequestMapping("getMeterMoneySum")
    @ResponseBody
    public HttpResultPagination<MeterMoneySumMongoDomain> getMeterMoneySum(@RequestBody MeterDomain meterDomain) {
        List<MeterMoneySumMongoDomain> list = meterMoneyService.getMeterMoneySum(meterDomain);

        return new HttpResultPagination<MeterMoneySumMongoDomain>(meterDomain, list);
    }

    /**
     * 数据确认
     *
     * @param json
     * @return
     */
    @RequestMapping("dataValidation")
    @ResponseBody
    public HttpResult<?> dataValidation(@RequestBody String json) {
        List<MeterDomain> meterDomains =
                GsonUtils.readValueToList(json, MeterDomain.class);
        List<MeterDomain> dataValidaMeterDomains = new ArrayList<>();

        meterDomains.forEach(t -> {
            MeterDomain meterDomain = new MeterDomain();
            meterDomain.setId(t.getId());
            meterDomain.setMon(t.getMon());
            meterDomain.setStatus(t.getStatus());
            dataValidaMeterDomains.add(meterDomain);
        });

        long modifiedCount =
                meterMoneyService.dataValidation(dataValidaMeterDomains);

        return new HttpResult<>(HttpResult.SUCCESS, "数据确认成功", modifiedCount);
    }

    @RequestMapping("findMeterMoney")
    @ResponseBody
    public Object findMeterMoneyBySettlement(@RequestBody String paramJson) {
        //解析参数
        JSONObject paramJsonObject=JSONObject.parseObject(paramJson);
        Integer mon=paramJsonObject.getInteger("mon");
        Long businessPlaceCode =paramJsonObject.getLong("businessPlaceCode");
        String settlementNo =paramJsonObject.getString("settlementNo");
       /* Long writeSectionId =paramJsonObject.getLong("writeSectionId");
        Long writorId =paramJsonObject.getLong("writorId");*/

        //获取结算户
        SettlementDomain paramSettlementDomain=new SettlementDomain();
        paramSettlementDomain.setPageSize(-1);
        paramSettlementDomain.setBusinessPlaceCode(businessPlaceCode);
        paramSettlementDomain.setSettlementNo(settlementNo);
        paramSettlementDomain.setInvoiceType(2);
        List<SettlementDomain> settlementDomains=
                cimService.findClearSettlementByWhere(paramSettlementDomain);
        if(settlementDomains==null ||settlementDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR,"无对应的结算户信息");
        }

        List<Long> settlementIds=
                settlementDomains.stream().filter(t->t.getId()!=null).map(SettlementDomain::getId).distinct().collect(toList());

        List<SettlementMeterRelDomain> settlementMeterRelDomains =
                cimService.getMeterIdsBySettlements(settlementDomains);

        if(settlementMeterRelDomains==null ||settlementMeterRelDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR,"无对应的结算户计量点关系");
        }

        //获取计量点
        List<Long> meterIds=
                settlementMeterRelDomains.stream().filter(t->t.getMeterId()!=null).map(SettlementMeterRelDomain::getMeterId).distinct().collect(toList());

        //根据抄表区段筛选电费记录
        MeterMoneyDomain paramMeterMoneyDomain=new MeterMoneyDomain();
        paramMeterMoneyDomain.setMon(mon);
        paramMeterMoneyDomain.setMeterIds(meterIds);
        paramMeterMoneyDomain.setPageSize(-1);
        List<MeterMoneyDomain> meterMoneyDomains=
                meterMoneyService.findByWhere(paramMeterMoneyDomain);
        if(meterMoneyDomains==null ||meterMoneyDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR,"无要查询的电费记录");
        }

        List<Long> writeSectionIds=
                meterMoneyDomains.stream().map(MeterMoneyDomain::getWriteSectId).distinct().collect(toList());
        //获取抄表区段id
        WriteSectDomain paramWriteSectDomain = new WriteSectDomain();
        paramWriteSectDomain.setWriteSectionIds(writeSectionIds);
        paramWriteSectDomain.setMon(mon.toString());
        List<WriteSectDomain> writeSectDomains = writeSectDAO.getWriteSectDomain(paramWriteSectDomain);

        if(writeSectDomains==null ||writeSectDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR,"无要查询的抄表区段");
        }


        Map<Long,WriteSectDomain> writeSectDomainMap=writeSectDomains.stream().collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));

        //查询营业区域
        if(businessPlaceCode==null){
            businessPlaceCode=
                    settlementDomains.stream().filter(t->t.getBusinessPlaceCode()!=null).collect(toList()).get(0).getBusinessPlaceCode();
        }
        DeptDomain deptDomain =
                deptService.getDept(businessPlaceCode);


        Map<Long,Long> meterIdSettlementIdMap=
                settlementMeterRelDomains.stream().collect(Collectors.toMap(SettlementMeterRelDomain::getMeterId, a -> a.getSettlementId(), (k1, k2) -> k1));

        //赋值结算户id
        meterMoneyDomains.stream().forEach(t->{
            Long settlementId=meterIdSettlementIdMap.get(t.getMeterId());
            if(settlementId!=null){
                t.setSettlementId(settlementId);
            }
        });


        Map<Long,SettlementDomain> settlementDomainMap=
                settlementDomains.stream().collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));


        //获取计量点信息
        MeterDomain paramMmeterDomain=new MeterDomain();
        paramMmeterDomain.setMon(mon);
        paramMmeterDomain.setIds(meterIds);
        List<MeterDomain> meterDomains=
                meterDAO.findMeterDomain(paramMmeterDomain);
        Map<Long,MeterDomain> meterDomainMap=meterDomains.stream().collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));
        //按计量点算费次数分组
        Map<String,List<MeterMoneyDomain>> meterMoneyList=
                meterMoneyDomains.stream().collect(Collectors.groupingBy(o -> o.getMeterId() + "_" + o.getSn() ));

        List<SettlementDetailEntity> settlementDetailEntities=new ArrayList<>();

        for(String k:meterMoneyList.keySet()){
            List<MeterMoneyDomain> v=meterMoneyList.get(k);
            MeterMoneyDomain meterMoneyDomain=v.get(0);
            SettlementDomain settlementDomain=
                    settlementDomainMap.get(meterMoneyDomain.getSettlementId());

            SettlementDetailEntity settlementDetailEntity= new SettlementDetailEntity();
            //结算户
            settlementDetailEntity.setSettlementNo(settlementDomain.getSettlementNo());
            //结算名
            settlementDetailEntity.setSettlementName(settlementDomain.getSettlementName());
            //结算地址
            settlementDetailEntity.setAddress(settlementDomain.getAddress());

            settlementDetailEntity.setBankNo(settlementDomain.getBankNo());
            //月份
            settlementDetailEntity.setMon(meterMoneyDomain.getMon());
            //算费次数
            settlementDetailEntity.setSn(Integer.valueOf(meterMoneyDomain.getSn()));
            //电量
            settlementDetailEntity.setTotalPower(v.stream().filter(t -> t.getTotalPower()!=null)
                    .map(MeterMoneyDomain::getTotalPower).reduce(BigDecimal.ZERO, BigDecimal::add));
            //价调基金
            settlementDetailEntity.setAddMoney8(v.stream().filter(t -> t.getAddMoney8()!=null)
                    .map(MeterMoneyDomain::getAddMoney8).reduce(BigDecimal.ZERO, BigDecimal::add));
            //退补电费
            BigDecimal refundMoney=v.stream().filter(t -> t.getRefundMoney()!=null)
                    .map(MeterMoneyDomain::getRefundMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal amount=v.stream().filter(t -> t.getAmount()!=null)
                    .map(MeterMoneyDomain::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            //应收
            settlementDetailEntity.setShouldMoney(refundMoney.add(amount).subtract(settlementDetailEntity.getAddMoney8()));
            //总电费
            settlementDetailEntity.setTotalMoney(refundMoney.add(amount));

            WriteSectDomain writeSectDomain=
                    writeSectDomainMap.get(meterMoneyDomain.getWriteSectId());
            if (writeSectDomain!=null){
                settlementDetailEntity.setWriteSectionNo(writeSectDomain.getWriteSectNo());
            }
            //营业区域名称
            settlementDetailEntity.setBusinessPlaceCodeName(deptDomain.getDeptName());
            //计量点号
            MeterDomain meterDomain=
                    meterDomainMap.get(meterMoneyDomain.getMeterId());
            if(meterDomain!=null){
                settlementDetailEntity.setMeterNo(meterDomain.getMeterNo());
            }

            settlementDetailEntities.add(settlementDetailEntity);
        };

        //合计电量
        BigDecimal sumTotalPower=
                settlementDetailEntities.stream().filter(t -> t.getTotalPower()!=null)
                .map(SettlementDetailEntity::getTotalPower).reduce(BigDecimal.ZERO, BigDecimal::add);
        //合计应收
        BigDecimal sumShoulwMoney=
                settlementDetailEntities.stream().filter(t -> t.getShouldMoney()!=null)
                        .map(SettlementDetailEntity::getShouldMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        //合计价调
        BigDecimal sumAddMoney8=
                settlementDetailEntities.stream().filter(t -> t.getAddMoney8()!=null)
                        .map(SettlementDetailEntity::getAddMoney8).reduce(BigDecimal.ZERO, BigDecimal::add);

        //合计总电费
        BigDecimal sumTotalMoney=
                settlementDetailEntities.stream().filter(t -> t.getTotalMoney()!=null)
                        .map(SettlementDetailEntity::getTotalMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        settlementDetailEntities=
                settlementDetailEntities.stream().filter(t-> t.getTotalMoney().compareTo(BigDecimal.ZERO)!=0).collect(toList());

        settlementDetailEntities=settlementDetailEntities.stream()
                .sorted(Comparator.comparing(SettlementDetailEntity::getWriteSectionNo,Comparator.nullsFirst(String::compareTo))
                        .thenComparing(SettlementDetailEntity::getSettlementNo, Comparator.nullsFirst(String::compareTo))
                        .thenComparing(SettlementDetailEntity::getMeterNo,Comparator.nullsFirst(String::compareTo))).collect(toList());


        JSONObject returnJSONObject=new JSONObject();
        returnJSONObject.put("sumTotalPower",sumTotalPower);
        returnJSONObject.put("sumShoulwMoney",sumShoulwMoney);
        returnJSONObject.put("sumAddMoney8",sumAddMoney8);
        returnJSONObject.put("sumTotalMoney",sumTotalMoney);
        returnJSONObject.put("settlementDetailEntities",settlementDetailEntities);

        return new HttpResult<>(HttpResult.SUCCESS,"查询成功",returnJSONObject);
    }

    /**
     * 居民阶梯用电查询
     *
     * @param json
     * @return
     */
    @RequestMapping("ladderPowerQuery")
    @ResponseBody
    public HttpResult<?> ladderPowerQuery(@RequestBody String json) {
        MeterMoneyDomain paramMeterMoneyDomain=JSONObject.parseObject(json,MeterMoneyDomain.class);

        //下级营业区域
        DeptDomain dept = new DeptDomain();
        List<DeptDomain> deptDomains=new ArrayList<>();
        try {
            dept =
                    deptService.getDept(Long.valueOf(paramMeterMoneyDomain.getBusinessPlaceCode()));
            deptDomains =
                    deptService.getDeptList(Long.valueOf(paramMeterMoneyDomain.getBusinessPlaceCode()));
            deptDomains.add(dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> businessPlaceCodeList =
                deptDomains.stream().filter(d -> d.getId() != null)
                .map(DeptDomain::getId).map(String::valueOf).collect(toList());

        deptDomains.forEach(t->{
            if(t.getDeptName()==null || "".equals(t.getDeptName())){
                t.setDeptName(t.getTitle());
            }
        });

        Map<Long,DeptDomain> deptDomainMap=
                deptDomains.stream().collect(Collectors.toMap(DeptDomain::getId,a -> a, (k1, k2) -> k1));


        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setPageSize(-1);
        writeSectDomain.setBusinessPlaceCodes(businessPlaceCodeList);
        String writeSectJson = restTemplate.postForObject(Global.getConfig("getWriteSectFindByWhere"),
                writeSectDomain, String.class);

        //抄表区段map
        List<WriteSectDomain> writeSectDomains=
                JSONObject.parseArray(writeSectJson,WriteSectDomain.class);
        Map<Long, Long> writeSectDomainMap = writeSectDomains.stream().collect(Collectors.toMap(WriteSectDomain::getId,a -> a.getBusinessPlaceCode(), (k1, k2) -> k1));

        paramMeterMoneyDomain.setBusinessPlaceCode(null);
        paramMeterMoneyDomain.setWriteSectIds(writeSectDomains.stream().map(WriteSectDomain::getId).distinct().collect(toList()));

        // 查询阶梯电价
        List<PriceLadderRelaDomain> priceLadderRelaDomains=
                iPriceLadderRelaService.findMongoPriceLadderRela(paramMeterMoneyDomain.getEndMon().toString());

        if(priceLadderRelaDomains==null || priceLadderRelaDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR, "所选月份无阶梯电价！");

        }

        Map<Long,String> priceLadderRelMap=
                priceLadderRelaDomains.stream().collect(Collectors.toMap(PriceLadderRelaDomain::getPriceExecutionId,a -> a.getPriceName(), (k1, k2) -> k1));


        List<Long> priceTypeIds=
                priceLadderRelaDomains.stream().map(PriceLadderRelaDomain::getPriceExecutionId).distinct().collect(toList());


        paramMeterMoneyDomain.setPriceTypeIds(priceTypeIds);


        List<MeterMoneyDomain> meterMoneyDomains=
                meterMoneyService.ladderPowerQuery(paramMeterMoneyDomain);


        if(meterMoneyDomains==null || meterMoneyDomains.size()<1){
            return new HttpResult<>(HttpResult.ERROR, "无阶梯电量数据");

        }

        meterMoneyDomains.forEach(t->{
            if(writeSectDomainMap.get(t.getWriteSectId())==null){
                t.setBusinessPlaceCode("102");
            }else{
                t.setBusinessPlaceCode(writeSectDomainMap.get(t.getWriteSectId()).toString());
            }
        });

        meterMoneyDomains=
                meterMoneyDomains.stream().sorted(Comparator.comparing(MeterMoneyDomain::getBusinessPlaceCode, Comparator.nullsFirst(String::compareTo))
                        .thenComparing(MeterMoneyDomain::getPriceTypeId,Comparator.nullsFirst(Long::compareTo))).collect(Collectors.toList());

        Map<String,List<MeterMoneyDomain>> meterMoneyMap=
                meterMoneyDomains.stream().collect(Collectors.groupingBy(o->o.getBusinessPlaceCode()+"_"+o.getPriceTypeId()));

        List<MeterMoneyDomain> meterMoneyByDeal=new ArrayList<>();
        for (String key:meterMoneyMap.keySet()){
            List<MeterMoneyDomain> meterMoneyByBusi= meterMoneyMap.get(key);
            MeterMoneyDomain meterMoneyDomain=new MeterMoneyDomain();
            meterMoneyDomain.setBusinessPlaceCode(meterMoneyByBusi.get(0).getBusinessPlaceCode());
            meterMoneyDomain.setPriceTypeId(meterMoneyByBusi.get(0).getPriceTypeId());
            meterMoneyDomain.setLadder1Power(meterMoneyByBusi.stream().filter(t -> t.getLadder1Power() !=null)
                    .map(MeterMoneyDomain::getLadder1Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            meterMoneyDomain.setLadder2Power(meterMoneyByBusi.stream().filter(t -> t.getLadder2Power() !=null)
                    .map(MeterMoneyDomain::getLadder2Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            meterMoneyDomain.setLadder3Power(meterMoneyByBusi.stream().filter(t -> t.getLadder3Power() !=null)
                    .map(MeterMoneyDomain::getLadder3Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            meterMoneyDomain.setLadder4Power(meterMoneyByBusi.stream().filter(t -> t.getLadder4Power() !=null)
                    .map(MeterMoneyDomain::getLadder4Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            meterMoneyByDeal.add(meterMoneyDomain);
        }

        List<LadderPowerQueryEntity> ladderPowerQueryEntities=new ArrayList<>();

        meterMoneyByDeal.forEach(t->{
            //一阶梯
            LadderPowerQueryEntity ladderPowerQueryEntity1=
                    new LadderPowerQueryEntity();
            DeptDomain paramDeptDomain=
                    deptDomainMap.get(Long.valueOf(t.getBusinessPlaceCode()));
            if(paramDeptDomain!=null)
            ladderPowerQueryEntity1.setBusinessPlaceCodeName(paramDeptDomain.getDeptName());

            ladderPowerQueryEntity1.setPriceName(priceLadderRelMap.get(t.getPriceTypeId())+"|一阶梯");
            ladderPowerQueryEntity1.setTotalPower(t.getLadder1Power());
            ladderPowerQueryEntities.add(ladderPowerQueryEntity1);

            //二阶梯
            LadderPowerQueryEntity ladderPowerQueryEntity2=
                    new LadderPowerQueryEntity();
            if(paramDeptDomain!=null)
                ladderPowerQueryEntity2.setBusinessPlaceCodeName(paramDeptDomain.getDeptName());

            ladderPowerQueryEntity2.setPriceName(priceLadderRelMap.get(t.getPriceTypeId())+"|二阶梯");
            ladderPowerQueryEntity2.setTotalPower(t.getLadder2Power());
            ladderPowerQueryEntities.add(ladderPowerQueryEntity2);

            //三阶梯
            LadderPowerQueryEntity ladderPowerQueryEntity3=
                    new LadderPowerQueryEntity();
            if(paramDeptDomain!=null)
                ladderPowerQueryEntity3.setBusinessPlaceCodeName(paramDeptDomain.getDeptName());

            ladderPowerQueryEntity3.setPriceName(priceLadderRelMap.get(t.getPriceTypeId())+"|三阶梯");
            ladderPowerQueryEntity3.setTotalPower(t.getLadder3Power());
            ladderPowerQueryEntities.add(ladderPowerQueryEntity3);

            //四阶梯
            LadderPowerQueryEntity ladderPowerQueryEntity4=
                    new LadderPowerQueryEntity();
            if(paramDeptDomain!=null)
                ladderPowerQueryEntity4.setBusinessPlaceCodeName(paramDeptDomain.getDeptName());

            ladderPowerQueryEntity4.setPriceName(priceLadderRelMap.get(t.getPriceTypeId())+"|四getFinishBackCharge阶梯");
            ladderPowerQueryEntity4.setTotalPower(t.getLadder4Power());
            ladderPowerQueryEntities.add(ladderPowerQueryEntity4);

        });

        return new HttpResult<>(HttpResult.SUCCESS, "查询成功", ladderPowerQueryEntities);
    }
}
