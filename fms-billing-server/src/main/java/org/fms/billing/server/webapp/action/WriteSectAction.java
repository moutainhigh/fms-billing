package org.fms.billing.server.webapp.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.WriteSectMongoDomain;
import org.fms.billing.common.webapp.service.WriteSectService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.WriteSectMongoDAO;
import org.fms.billing.server.webapp.service.CimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("writeSect")
public class WriteSectAction {

    @Autowired
    @Qualifier("writeSectServiceImpl")
    private WriteSectService writeSectService;

    @Autowired
    private WriteSectMongoDAO writeSectMongoDAO;

    @Autowired
    private MeterDAO meterDAO;

    @Autowired
    private CimService cimService;

    @RequestMapping(params = "method=getWriteSect")
    @ResponseBody
    public Object getWriteSect(@RequestBody WriteSectDomain writeSectionDomain) {

        Integer findStatus = writeSectionDomain.getFindStatus();
        writeSectionDomain.setFindStatus(null);
        List<WriteSectDomain> domains = writeSectService.getWriteSect(writeSectionDomain);
        List<Long> writeSectionIds =
                domains.stream().filter(t -> t.getId() != null).map(WriteSectDomain::getId).collect(Collectors.toList());

        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setMon(writeSectionDomain.getMon());
        writeSectDomain.setWriteSectionIds(writeSectionIds);
        List<WriteSectDomain> completeDomains =
                writeSectService.getMeterReadingSituation(writeSectDomain);

        Map<Long, List<WriteSectDomain>> completeWriteSectMap =
                completeDomains.stream()
                        .collect(Collectors.groupingBy(WriteSectDomain::getId));
        //查询已抄未抄
        domains.stream().forEach(t -> {
            BigDecimal complete = BigDecimal.ZERO;
            if (completeWriteSectMap.get(t.getId()) != null) {
                complete =
                        completeWriteSectMap.get(t.getId()).stream().filter(f -> f.getComplete() != null).map(WriteSectDomain::getComplete).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            BigDecimal unComplete = BigDecimal.ZERO;
            if (completeWriteSectMap.get(t.getId()) != null) {
                unComplete =
                        completeWriteSectMap.get(t.getId()).stream().filter(f -> f.getUncomplete() != null).map(WriteSectDomain::getUncomplete).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            t.setComplete(complete);
            t.setUncomplete(unComplete);

        });

        if (findStatus != null) {

            //已算未算数量或者已发行未发行数量
            List<WriteSectDomain> calDomains =
                    writeSectService.getMeterCalOrPublicSituation(writeSectDomain, findStatus);

            Map<Long, List<WriteSectDomain>> calWriteSectMap = calDomains.stream()
                    .collect(Collectors.groupingBy(WriteSectDomain::getId));

            domains.stream().forEach(t -> {
                BigDecimal calComplete = BigDecimal.ZERO;
                if (calWriteSectMap.get(t.getId()) != null) {
                    calComplete =
                            calWriteSectMap.get(t.getId()).stream().filter(f -> f.getCalcComplete() != null).map(WriteSectDomain::getCalcComplete).reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                BigDecimal unCalComplete = BigDecimal.ZERO;
                if (calWriteSectMap.get(t.getId()) != null) {
                    unCalComplete =
                            calWriteSectMap.get(t.getId()).stream().filter(f -> f.getCalcUncomplete() != null).map(WriteSectDomain::getCalcUncomplete).reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                t.setCalcComplete(calComplete);
                t.setCalcUncomplete(unCalComplete);
            });
        }
        return new HttpResultPagination<WriteSectDomain>(writeSectionDomain, domains);
    }

    @RequestMapping(params = "method=getWriteSectAndNum")
    @ResponseBody
    public Object getWriteSectAndNum(@RequestBody WriteSectDomain writeSectionDomain) {

        List<WriteSectDomain> domains = writeSectService.getWriteSectAndNum(writeSectionDomain);
        return new HttpResultPagination<WriteSectDomain>(writeSectionDomain, domains);
    }

    /*@RequestMapping(params = "method=getMeterReadingSituation")
    @ResponseBody
    public Object getMeterReadingSituation(@RequestBody WriteSectDomain writeSectDomain) {
        String result = writeSectService.getMeterReadingSituation(writeSectDomain);
        return result;
    }
*/
    @RequestMapping(params = "method=getMeterInitSituation")
    @ResponseBody
    public Object getMeterInitSituation(@RequestBody WriteSectDomain writeSectDomain) {
        //long count = writeSectService.getMeterInitSituation(writeSectDomain);
        String result = writeSectService.getMeterInitSituation(writeSectDomain);

        HttpResult httpResult = new HttpResult();
        httpResult.setMessage(result);
        httpResult.setResultData(result);
        httpResult.setStatusCode(HttpResult.SUCCESS);
        return httpResult;
    }

    /*@RequestMapping(params = "method=getMeterCalSituation")
    @ResponseBody
    public Object getMeterCalSituation(@RequestBody WriteSectDomain writeSectDomain) {
        String result = writeSectService.getMeterCalSituation(writeSectDomain);
        return result;
    }*/

    @RequestMapping(params = "method=updateBatch")
    @ResponseBody
    public void updateBatch(@RequestBody Map<String, String> params) {

        System.out.println(GsonUtils.toJson(params));
    }

    @RequestMapping("getWriteSectDomain")
    @ResponseBody
    public List<WriteSectDomain> getWriteSectDomain(@RequestBody WriteSectDomain writeSectDomain) {

        List<WriteSectDomain> list = writeSectService.getWriteSectDomain(writeSectDomain);

        return list;
    }

    @RequestMapping("getMongoWriteSect")
    @ResponseBody
    public List<WriteSectMongoDomain> getMongoWriteSect(@RequestBody String writeSectMongoDomainJson) {

        WriteSectMongoDomain writeSectMongoDomain =
                JSONObject.parseObject(writeSectMongoDomainJson,
                        WriteSectMongoDomain.class);
        List<WriteSectMongoDomain> list = writeSectMongoDAO.findWriteSectMongoByWhere(writeSectMongoDomain);

        return list;
    }

    @RequestMapping(params = "method=getWriteSectSortByWritorId")
    @ResponseBody
    public Object getWriteSectSortByWritorId(@RequestBody WriteSectDomain writeSectionDomain) {

        Integer findStatus = writeSectionDomain.getFindStatus();
        writeSectionDomain.setFindStatus(null);
        List<WriteSectDomain> domains =
                writeSectService.getWriteSectSortByWritorId(writeSectionDomain);
        List<Long> writeSectionIds =
                domains.stream().filter(t -> t.getId() != null).map(WriteSectDomain::getId).collect(Collectors.toList());

        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setMon(writeSectionDomain.getMon());
        writeSectDomain.setWriteSectionIds(writeSectionIds);
        List<WriteSectDomain> completeDomains =
                writeSectService.getMeterReadingSituation(writeSectDomain);

        Map<Long, List<WriteSectDomain>> completeWriteSectMap =
                completeDomains.stream()
                        .collect(Collectors.groupingBy(WriteSectDomain::getId));
        //查询已抄未抄
        domains.stream().forEach(t -> {
            BigDecimal complete = BigDecimal.ZERO;
            if (completeWriteSectMap.get(t.getId()) != null) {
                complete =
                        completeWriteSectMap.get(t.getId()).stream().filter(f -> f.getComplete() != null).map(WriteSectDomain::getComplete).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            BigDecimal unComplete = BigDecimal.ZERO;
            if (completeWriteSectMap.get(t.getId()) != null) {
                unComplete =
                        completeWriteSectMap.get(t.getId()).stream().filter(f -> f.getUncomplete() != null).map(WriteSectDomain::getUncomplete).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            t.setComplete(complete);
            t.setUncomplete(unComplete);

        });

        if (findStatus != null) {

            //已算未算数量或者已发行未发行数量
            List<WriteSectDomain> calDomains =
                    writeSectService.getMeterCalOrPublicSituation(writeSectDomain, findStatus);

            Map<Long, List<WriteSectDomain>> calWriteSectMap = calDomains.stream()
                    .collect(Collectors.groupingBy(WriteSectDomain::getId));

            domains.stream().forEach(t -> {
                BigDecimal calComplete = BigDecimal.ZERO;
                if (calWriteSectMap.get(t.getId()) != null) {
                    calComplete =
                            calWriteSectMap.get(t.getId()).stream().filter(f -> f.getCalcComplete() != null).map(WriteSectDomain::getCalcComplete).reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                BigDecimal unCalComplete = BigDecimal.ZERO;
                if (calWriteSectMap.get(t.getId()) != null) {
                    unCalComplete =
                            calWriteSectMap.get(t.getId()).stream().filter(f -> f.getCalcUncomplete() != null).map(WriteSectDomain::getCalcUncomplete).reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                t.setCalcComplete(calComplete);
                t.setCalcUncomplete(unCalComplete);
            });
        }
        return new HttpResultPagination<WriteSectDomain>(writeSectionDomain, domains);
    }

    //根据抄表区段查结算户
    @RequestMapping(params = "method=getWriteSectSettlement")
    @ResponseBody
    public Object getWriteSectSettlement(@RequestBody WriteSectDomain writeSectionDomain) {

        List<WriteSectDomain> domains = writeSectService.getWriteSect(writeSectionDomain);
        List<Long> writeSectionIds =
                domains.stream().filter(t -> t.getId() != null).map(WriteSectDomain::getId).collect(Collectors.toList());

        MeterDomain paramMeterDomain=new MeterDomain();
        paramMeterDomain.setMon(Integer.valueOf(writeSectionDomain.getMon()));
        paramMeterDomain.setWriteSectNo(writeSectionDomain.getWriteSectNo());
        List<MeterDomain> meterDomainList=
                meterDAO.findMeterDomain(paramMeterDomain);

        if(meterDomainList==null || meterDomainList.size()<1){
            return new HttpResult<>(HttpResult.ERROR,"所选抄表区段无计量点信息");
        }

        List<Long> meterIds=meterDomainList.stream().map(MeterDomain::getId).distinct().collect(Collectors.toList());

        List<SettlementDomain> settlementDomains=
                cimService.getSettlementbyMeterIds(meterIds);

        return new HttpResult<>(HttpResult.SUCCESS,"查询成功",settlementDomains);
    }
}
