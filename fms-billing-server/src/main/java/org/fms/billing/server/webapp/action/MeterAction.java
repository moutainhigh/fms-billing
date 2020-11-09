/**
 * Author : czy
 * Date : 2019年7月8日 下午4:09:04
 * Title : com.riozenc.billing.webapp.controller.MeterAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.entity.MeterMoneyVerificationEntity;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IMeterService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@ControllerAdvice
@RequestMapping("meter")
public class MeterAction {

	@Autowired
	@Qualifier("meterServiceImpl")
	private IMeterService meterService;

	@Autowired
	@Qualifier("chargeServiceImpl")
	private IChargeService iChargeService;

	@Autowired
	private MeterDAO meterDAO;

	@RequestMapping(params = "method=findMeterByWhere")
	@ResponseBody
	public Object findMeterByWhere(@RequestBody MeterDomain meterDomain) {

		//meterDomain.setMon(201912);
		List<MeterDomain> list = meterService.findMeterByWhere(meterDomain);

		return new HttpResultPagination<MeterDomain>(meterDomain, list);
	}

	@RequestMapping(params = "method=findMeterByBackPublic")
	@ResponseBody
	public Object findMeterByBackPublic(@RequestBody MeterDomain meterDomain) {

		//meterDomain.setMon(201912);
		// 从mongo查计量点信息
		List<MeterDomain> list = meterService.findMeterByWhere(meterDomain);
		List<Long> meterIds=list.stream().map(MeterDomain::getId).collect(Collectors.toList());

		Map<String,Object> map=new HashMap<>();
		map.put("mon",meterDomain.getMon());
		//Long类型集合转字符串
		map.put("meterIds",meterIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
		//关联收费表 若收费无法回退
		List<ChargeDomain> chargeDomains=
				iChargeService.findBackPulishByMeterIds(map);

		List<Long> chargeMeterIds=
				chargeDomains.stream().map(ChargeDomain::getMeterId).collect(Collectors.toList());

		list.stream().forEach(t->{
			if(chargeMeterIds.contains(t.getId())){
				t.setIsCharge(0);
			}else{
				t.setIsCharge(1);
			}
		});
		return new HttpResultPagination<MeterDomain>(meterDomain, list);
	}

	@RequestMapping(params = "method=findMeterInfomation")
	@ResponseBody
	public Object findMeterInfomation(@RequestBody MeterDomain meterDomain) {

		List<MeterDomain> list = meterService.findMeterByWhere(meterDomain);

		return list;
	}

	@RequestMapping(params = "method=findMeterMoneyByMongodb")
	@ResponseBody
	public Object findMeterMoneyByMongodb(@RequestBody MeterDomain meterDomain) {

		List<MeterMoneyVerificationEntity> list = meterService.findMeterMoneyByMongodb(meterDomain);

		return list;
	}

	@RequestMapping("findMeterDomain")
	@ResponseBody
	public List<MeterDomain> findMeterDomain(@RequestBody String meterDomainJson) {
		MeterDomain meterDomain=JSONObject.parseObject(meterDomainJson,
				MeterDomain.class);
		List<MeterDomain> list = meterService.findMeterDomain(meterDomain);
		return list;
	}

	@RequestMapping("getMeterByWriteSectionIds")
	@ResponseBody
	public List<MeterDomain> getMeterByWriteSectionIds(@RequestBody String meterDomainJson) {

		MeterDomain meterDomain=JSONObject.parseObject(meterDomainJson,
				MeterDomain.class);

		List<MeterDomain> list =
				meterDAO.getMeterByWriteSectionIds(meterDomain.getMon(),
						(Integer[])meterDomain.getWriteSectionIds().toArray(),
						meterDomain.getStatus());

		return list;
	}

	@RequestMapping("getMeterByBusinessPlaceCode")
	@ResponseBody
	public List<MeterDomain> getMeterByBusinessPlaceCode(@RequestBody String meterDomainJson) {

		MeterDomain meterDomain=JSONObject.parseObject(meterDomainJson,
				MeterDomain.class);

		List<Integer> statuss=meterDomain.getStatuss();

		List<MeterDomain> list =
				meterDAO.getMeterByBusinessPlaceCode(meterDomain.getMon(),
						meterDomain.getBusinessPlaceCode(),
						statuss.toArray(new Integer[statuss.size()]));

		return list;
	}

}
