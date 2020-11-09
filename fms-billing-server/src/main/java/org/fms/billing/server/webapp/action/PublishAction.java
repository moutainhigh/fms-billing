/**
 * Author : czy
 * Date : 2019年7月4日 下午3:36:21
 * Title : com.riozenc.billing.webapp.controller.ComputeAction.java
 **/
package org.fms.billing.server.webapp.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.entity.PulishEntity;
import org.fms.billing.common.webapp.entity.UserInfoEntity;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IMeterService;
import org.fms.billing.common.webapp.service.INoteInfoService;
import org.fms.billing.common.webapp.service.IPreChargeService;
import org.fms.billing.common.webapp.service.IPublishService;
import org.fms.billing.common.webapp.service.IWriteFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.client.TitanTemplate.TitanCallback;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@ControllerAdvice
@RequestMapping("publish")
public class PublishAction {

    @Autowired
    @Qualifier("writeFilesServiceImpl")
    private IWriteFilesService writeFilesService;

    @Autowired
    @Qualifier("publishServiceImpl")
    private IPublishService iPublishService;

    @Autowired
    @Qualifier("meterServiceImpl")
    private IMeterService iMeterService;

    @Autowired
    @Qualifier("preChargeServiceImpl")
    private IPreChargeService iPreChargeService;

    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    @Qualifier("noteInfoServiceImpl")
    private INoteInfoService iNoteInfoService;

    @RequestMapping(params = "method=publishByMeter")
    @ResponseBody
    public Object publishByMeter(@RequestBody String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);
        WriteFilesDomain writeFilesDomain = GsonUtils.readValue(json, WriteFilesDomain.class);
        List<MeterDomain> meterDomains = GsonUtils.readValueToList(jsonObject.get("meters").toString(),
                MeterDomain.class);

        UserInfoEntity userInfoEntity =
                GsonUtils.readValue(jsonObject.get("userInfo").toString(), UserInfoEntity.class);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // 推送余额进mongodb
        /*
         * List<String> meterIds = new ArrayList<>(); meterIds =
         * meterDomains.stream().map(t -> { return t.getId().toString();
         * }).collect(Collectors.toList());
         */

        HttpResult<?> httpResult = null;

        Map<String, Object> params = new HashMap<>();
        // params.put("date", 202001);
        params.put("date", writeFilesDomain.getMon());
        params.put("meterDomains", meterDomains);
        params.put("sn", writeFilesDomain.getSn());
        params.put("managerId", userInfoEntity.getId());
        params.put("callback", "billingServer/publish?method=publishCallback");
        try {
            TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS", "cfs/billingData?method=issue",
                    httpHeaders, params, HttpResult.class);
            httpResult = callback.call();
            return httpResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "发行失败." + e.toString());
        }
    }

    @RequestMapping(params = "method=publishByWriteSect")
    @ResponseBody
    public Object publishByWriteSect(@RequestBody String json) {

        WriteFilesDomain writeFilesDomain = JSONObject.parseObject(json, WriteFilesDomain.class);

        JSONObject jsonObject = JSONObject.parseObject(json);

        UserInfoEntity userInfoEntity =
                GsonUtils.readValue(jsonObject.get("userInfo").toString(), UserInfoEntity.class);


        List<MeterDomain> meterDomains = iMeterService.getMeterByWriteSectionIds(writeFilesDomain.getMon(),
                writeFilesDomain.getWriteSectionIds(), 2);

        if (meterDomains == null || meterDomains.size() < 1) {
            return new HttpResult<>(HttpResult.ERROR, "所选记录已全部发行,请勿重复发行");
        }

        List<PreChargeDomain> preChargeDomains = new ArrayList<>();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpResult<?> httpResult = null;

        Map<String, Object> params = new HashMap<>();
        params.put("date", writeFilesDomain.getMon());
        params.put("meterDomains", meterDomains);
        params.put("sn", writeFilesDomain.getSn());
        params.put("preChargeDomains", preChargeDomains);
        params.put("managerId", userInfoEntity.getId());
        params.put("callback", "billingServer/publish?method=publishCallback");
        try {

            TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS", "cfs/billingData?method=issue",
                    httpHeaders, params, HttpResult.class);
            httpResult = callback.call();
            return httpResult;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "发行失败" + e.toString());
        }

    }

    @RequestMapping(params = "method=publishCallback")
    @ResponseBody
    @Transactional
    public HttpResult<?> publishCallback(@RequestBody String meterMoneyBody)throws Exception {
        System.out.println("cfs服务回调成功================" + new Date());
        JSONObject jsonObject = JSONObject.parseObject(meterMoneyBody);
        String managerId = jsonObject.getString("managerId");
        PulishEntity pulishEntity = GsonUtils.readValue(meterMoneyBody, PulishEntity.class);

        HttpResult<?> httpResult = iPublishService.publishCallback(pulishEntity, managerId);
        if (httpResult.getStatusCode() == HttpResult.ERROR) {
            return httpResult;
        }

        List<PreChargeDomain> lastPreCharges =
                (List<PreChargeDomain>) httpResult.getResultData();

        Map<Long, BigDecimal> lastPreChargeMap =
                lastPreCharges.stream().collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a.getBalance(), (k1, k2) -> k1));


        //更新id
        System.out.println("余额冲抵成功================" + new Date());
        httpResult = iPublishService.updateMeterMoneyId();
        if (httpResult.getStatusCode() == HttpResult.ERROR) {
            return httpResult;
        }
        System.out.println("更新id成功================" + new Date());

        //生成发票
        List<Long> meterMoneyIds =
                pulishEntity.getMeterMoneyDomains().stream().map(MeterMoneyDomain::getId).collect(Collectors.toList());

        List<ChargeDomain> chargeDomains =
                iChargeService.findChargeByMeterMoneyIds(meterMoneyIds);

        if (chargeDomains != null && chargeDomains.size() > 0) {
            httpResult = iNoteInfoService.createNoteInfo(chargeDomains);
            //赋值本次和上次余额
            List<NoteInfoDomain> noteInfoDomains = (List<NoteInfoDomain>) httpResult.getResultData();

            if (noteInfoDomains != null && noteInfoDomains.size() > 0) {
                List<Long> settlementIds =
                        noteInfoDomains.stream().filter(t -> t.getSettlementId() != null).map(NoteInfoDomain::getSettlementId).distinct().collect(Collectors.toList());

                List<PreChargeDomain> thisPreCharges =
                        iPreChargeService.findPreChargeBySettleIds(settlementIds);

                Map<Long, BigDecimal> thisPreChargeMap =
                        thisPreCharges.stream().collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a.getBalance(), (k1, k2) -> k1));


                Map<Long, List<NoteInfoDomain>> noteInfoMaps =
                        noteInfoDomains.stream().collect(Collectors.groupingBy(NoteInfoDomain::getSettlementId));

                for (Long key : noteInfoMaps.keySet()) {
                    List<NoteInfoDomain> noteInfoDomainList =
                            noteInfoMaps.get(key);
                    BigDecimal lastPre = lastPreChargeMap.get(key);
                    BigDecimal thisPre = thisPreChargeMap.get(key);

                    noteInfoDomainList.get(0).setLastBalance(lastPre == null ? BigDecimal.ZERO : lastPre);
                    noteInfoDomainList.get(noteInfoDomainList.size() - 1).setThisBalance(thisPre == null ? BigDecimal.ZERO : lastPre);

                }

                iNoteInfoService.insertList(noteInfoDomains.stream().filter(t -> t.getMeterItem() != null).filter(t -> !"".equals(t.getMeterItem())).collect(Collectors.toList()));
            }

        }

        System.out.println("生成发票成功================" + new Date());
        return httpResult;
    }

    @RequestMapping(params = "method=backPublishByMeter")
    @ResponseBody
    @Transactional
    public Object backPublishByMeter(@RequestBody String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);

        List<MeterDomain> receiveMeterDomains =
                GsonUtils.readValueToList(jsonObject.get("meters").toString(),
                        MeterDomain.class);

        List<WriteFilesDomain> writeFilesDomains = new ArrayList<>();
        receiveMeterDomains.forEach(t -> {
            WriteFilesDomain writeFilesDomain = new WriteFilesDomain();
            writeFilesDomain.setMeterId(t.getId());
            writeFilesDomain.setWriteFlag((byte) 1);
            writeFilesDomains.add(writeFilesDomain);
        });

        List<MeterDomain> meterDomains =
                iMeterService.getEffectiveComputeMeter(jsonObject.getInteger("mon"), writeFilesDomains, 3);

        List<Long> meterIds = meterDomains.stream().map(MeterDomain::getId).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("mon", jsonObject.getInteger("mon"));
        // Long类型集合转字符串
        map.put("meterIds", meterIds);
        map.put("sn", jsonObject.getInteger("sn"));
        // 关联收费表 若收费无法回退
        List<ChargeDomain> chargeDomains = iChargeService.findBackPulishByMeterIds(map);

        List<Long> chargeMeterIds = chargeDomains.stream().map(ChargeDomain::getMeterId).collect(Collectors.toList());

        //回退的所有计量点 去掉已收费的计量点
        List<Long> backMeterIds = new ArrayList<>();
        meterDomains.stream().forEach(t -> {
            if (!chargeMeterIds.contains(t.getId())) {
                backMeterIds.add(t.getId());
            }
        });

        if (backMeterIds.size() < 1 || backMeterIds == null) {
            return new HttpResult<>(HttpResult.ERROR, "回退失败:欠费记录已收费无法回退！");
        }

		/*调用cfs 回退mongo 暂直接调用mysql
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpResult<?> httpResult = null;

		Map<String, Object> params = new HashMap<>();
		params.put("date", writeFilesDomain.getMon());
		params.put("meterDomains", backMeterDomains);
		params.put("sn", writeFilesDomain.getSn());
		params.put("callback", "billingServer/publish?method=backPublishCallback");
		try {

			TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS", "cfs/billingData?method=issue",
					httpHeaders, params, HttpResult.class);
			httpResult = callback.call();
			return httpResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HttpResult<>(HttpResult.ERROR, "发行失败" + e.toString());
		}*/
        PulishEntity pulishEntity = new PulishEntity();
        pulishEntity.setMon(jsonObject.getInteger("mon"));
        pulishEntity.setMeterIds(backMeterIds);
        pulishEntity.setManagerId(jsonObject.getLong("managerId"));
        pulishEntity.setSn(jsonObject.getInteger("sn"));
        return backPublishCallback(JSONObject.toJSONString(pulishEntity));

    }

    @RequestMapping(params = "method=backPublishByWriteSect")
    @ResponseBody
    @Transactional
    public Object backPublishByWriteSect(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        WriteFilesDomain writeFilesDomain = JSONObject.parseObject(json, WriteFilesDomain.class);

		/*writeFilesDomain.setPageSize(-1);

		List<WriteFilesDomain> writeFilesDomains = writeFilesService.mongoFind(writeFilesDomain);*/

        List<MeterDomain> meterDomains = iMeterService.getMeterByWriteSectionIds(writeFilesDomain.getMon(),
                writeFilesDomain.getWriteSectionIds(), 3);

        if (meterDomains == null || meterDomains.size() < 1) {
            return new HttpResult<>(HttpResult.ERROR, "回退失败:无已发行数据！");
        }

        List<Long> meterIds = meterDomains.stream().map(MeterDomain::getId).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("mon", writeFilesDomain.getMon());
        map.put("sn", writeFilesDomain.getSn());
        // Long类型集合转字符串
        map.put("meterIds", meterIds);
        // 关联收费表 若收费无法回退
        List<ChargeDomain> chargeDomains = iChargeService.findBackPulishByMeterIds(map);

        List<Long> chargeMeterIds = chargeDomains.stream().map(ChargeDomain::getMeterId).collect(Collectors.toList());

        //回退的所有计量点 去掉已收费的计量点
        List<Long> backMeterIds = new ArrayList<>();
        meterDomains.stream().forEach(t -> {
            if (!chargeMeterIds.contains(t.getId())) {
                backMeterIds.add(t.getId());
            }
        });
        if (backMeterIds.size() < 1 || backMeterIds == null) {
            return new HttpResult<>(HttpResult.ERROR, "回退失败:欠费记录已收费无法回退！");
        }
		/*调用cfs 回退mongo 暂直接调用mysql
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpResult<?> httpResult = null;

		Map<String, Object> params = new HashMap<>();
		params.put("date", writeFilesDomain.getMon());
		params.put("meterDomains", backMeterDomains);
		params.put("sn", writeFilesDomain.getSn());
		params.put("callback", "billingServer/publish?method=backPublishCallback");
		try {

			TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS", "cfs/billingData?method=issue",
					httpHeaders, params, HttpResult.class);
			httpResult = callback.call();
			return httpResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HttpResult<>(HttpResult.ERROR, "发行失败" + e.toString());
		}*/
        PulishEntity pulishEntity = new PulishEntity();
        pulishEntity.setMon(writeFilesDomain.getMon());
        pulishEntity.setMeterIds(backMeterIds);
        pulishEntity.setManagerId(jsonObject.getLong("managerId"));
        pulishEntity.setSn(jsonObject.getInteger("sn"));
        return backPublishCallback(JSONObject.toJSONString(pulishEntity));
    }

    @RequestMapping(params = "method=backPublishCallback")
    @ResponseBody
    @Transactional
    public HttpResult<?> backPublishCallback(@RequestBody String meterMoneyBody) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(meterMoneyBody);
            String managerId = jsonObject.getString("managerId");
            PulishEntity pulishEntity = GsonUtils.readValue(meterMoneyBody, PulishEntity.class);
            HttpResult<?> httpResult = iPublishService.backPublishCallback(pulishEntity, managerId);
            return httpResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "回退失败" + e.toString());
        }
    }


    @RequestMapping(params = "method=publishByMeterByPostMan")
    @ResponseBody
    public Object publishByMeterByPostMan(@RequestBody String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);
        WriteFilesDomain writeFilesDomain = GsonUtils.readValue(json, WriteFilesDomain.class);

        String aa = "14120,27336,32018,42556,53023,53323,55868,56063,56689," +
                "57199,57200,58223,58224,62597,69258,69259,69359,69552,69607,69710,69828,69904,70005,70161,70292,70759,70796,71308,71661,72038,72051,72154,72278,72343,72378,72504,72728,73329,73388,73773,73869,74076,74121,74131,74173,74231,74319,74411,74463,74552,74646,74781,75135,75280,75317,75634,76175,76381,76430,76453,76574,76576,76727,77119,77222,77247,77255,77274,77453,77539,77761,77952,79351,79786,80320,80413,80731,80907,80917,80923,81056,82392,82564,83267,83366,83559,83696,83818,84459,84516,84518,86077,86391,87536,87778,87779,88126,88733,90841,91098,91099,91426,91606,92469,94607,94670,95645,96126,96557,99069,99246,99267,102464,102935,103629,105588,105644,106172,106456,106701,106770,107395,108737,110180,111887,111891,113061,113063,113593,113921,114350,116993,119349,120182,120683,121523,122449,124923,126287,137874,139841,139843,140135,140409,141240,141280,141593,141714,142141,142371,142429,142877,143399,143987,144458,144465,145224,145760,145761,145769,150965";

        List<Long> result =
                Arrays.asList(aa.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());

        MeterDomain meterDomain = new MeterDomain();
        meterDomain.setMon(202007);
        meterDomain.setIds(result);
        List<MeterDomain> meterDomains =
                iMeterService.findMeterDomain(meterDomain);
/*
		List<MeterDomain> meterDomains = GsonUtils.readValueToList(jsonObject.get("meters").toString(),
				MeterDomain.class);*/

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // 推送余额进mongodb
        /*
         * List<String> meterIds = new ArrayList<>(); meterIds =
         * meterDomains.stream().map(t -> { return t.getId().toString();
         * }).collect(Collectors.toList());
         */

        HttpResult<?> httpResult = null;

        Map<String, Object> params = new HashMap<>();
        // params.put("date", 202001);
        params.put("date", writeFilesDomain.getMon());
        params.put("meterDomains", meterDomains);
        params.put("sn", writeFilesDomain.getSn());
//        params.put("preChargeDomains", preChargeDomains);
        params.put("callback", "billingServer/publish?method=publishCallback");
        try {
            TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS", "cfs/billingData?method=issue",
                    httpHeaders, params, HttpResult.class);
            httpResult = callback.call();
            return httpResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "发行失败." + e.toString());
        }
    }


    @RequestMapping(params = "method=backPublishByMeterByPostMan")
    @ResponseBody
    public Object backPublishByMeterByPostMan(@RequestBody String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);

		/*List<MeterDomain> receiveMeterDomains =
				GsonUtils.readValueToList(jsonObject.get("meters").toString(),
				MeterDomain.class);*/
        String aa = "206,948,5450,5451,5563,5687,5834,5835,6827,11691,11724," +
                "13122,13253,16851,18820,18822,18845,19057,19164,19314,19458,19987,20096,20330,20421,21082,21150,21449,21461,25817,26380,27024,32587,38485,42477,43909,44035,44463,45608,46655,46699,47151,47188,47250,47307,47353,47436,47473,47639,47688,47710,47712,47725,47759,47771,47774,47776,47816,47817,48042,48214,48215,48431,48636,48660,48781,49051,49073,49254,49333,49371,49467,49762,50016,50113,50258,50318,50333,50663,50720,51017,51239,51388,57320,57321,59599,61670,64926,64945,66365,66432,66470,66519,66551,66673,66679,66687,66899,66953,67120,67440,67529,67924,68010,68153,70291,70876,82787,83108,83226,83377";
        List<Long> result =
                Arrays.asList(aa.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());


        List<WriteFilesDomain> writeFilesDomains = new ArrayList<>();
        result.forEach(t -> {
            WriteFilesDomain writeFilesDomain = new WriteFilesDomain();
            writeFilesDomain.setMeterId(t);
            writeFilesDomain.setWriteFlag((byte) 1);
            writeFilesDomains.add(writeFilesDomain);
        });

        List<MeterDomain> meterDomains =
                iMeterService.getEffectiveComputeMeter(jsonObject.getInteger("mon"), writeFilesDomains, 3);

        List<Long> meterIds = meterDomains.stream().map(MeterDomain::getId).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("mon", jsonObject.getInteger("mon"));
        // Long类型集合转字符串
        map.put("meterIds", meterIds);
        map.put("sn", jsonObject.getInteger("sn"));
        // 关联收费表 若收费无法回退
        List<ChargeDomain> chargeDomains = iChargeService.findBackPulishByMeterIds(map);

        List<Long> chargeMeterIds = chargeDomains.stream().map(ChargeDomain::getMeterId).collect(Collectors.toList());

        //回退的所有计量点 去掉已收费的计量点
        List<Long> backMeterIds = new ArrayList<>();
        meterDomains.stream().forEach(t -> {
            if (!chargeMeterIds.contains(t.getId())) {
                backMeterIds.add(t.getId());
            }
        });

        if (backMeterIds.size() < 1 || backMeterIds == null) {
            return new HttpResult<>(HttpResult.ERROR, "回退失败:欠费记录已收费无法回退！");
        }

		/*调用cfs 回退mongo 暂直接调用mysql
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpResult<?> httpResult = null;

		Map<String, Object> params = new HashMap<>();
		params.put("date", writeFilesDomain.getMon());
		params.put("meterDomains", backMeterDomains);
		params.put("sn", writeFilesDomain.getSn());
		params.put("callback", "billingServer/publish?method=backPublishCallback");
		try {

			TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS", "cfs/billingData?method=issue",
					httpHeaders, params, HttpResult.class);
			httpResult = callback.call();
			return httpResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HttpResult<>(HttpResult.ERROR, "发行失败" + e.toString());
		}*/
        PulishEntity pulishEntity = new PulishEntity();
        pulishEntity.setMon(jsonObject.getInteger("mon"));
        pulishEntity.setMeterIds(backMeterIds);
        pulishEntity.setManagerId(jsonObject.getLong("managerId"));
        pulishEntity.setSn(jsonObject.getInteger("sn"));
        return backPublishCallback(JSONObject.toJSONString(pulishEntity));

    }

}
