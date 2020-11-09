package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.BadArrearageDomain;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.entity.BadArrearageShowEntity;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.entity.UserInfoEntity;
import org.fms.billing.common.webapp.service.IArrearageService;
import org.fms.billing.common.webapp.service.IBadArrearageService;
import org.fms.billing.server.webapp.service.SysCommonConfigService;
import org.fms.billing.server.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("badArrearage")
public class BadArrearageController {
	@Autowired
	@Qualifier("badArrearageImpl")
	private IBadArrearageService badArrearageService;

	@Autowired
	@Qualifier("arrearageServiceImpl")
	private IArrearageService iArrearageService;

	@Autowired
	private TitanTemplate titanTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SysCommonConfigService sysCommonConfigService;

	@Autowired
	private UserService userService;
	@RequestMapping("fingByWhere")
	@ResponseBody
	public List<BadArrearageDomain> fingByWhere(@RequestBody(required = false) String badArrearageJson){
		BadArrearageDomain badArrearageDomain=
				JSONObject.parseObject(badArrearageJson,BadArrearageDomain.class);
		return badArrearageService.findByWhere(badArrearageDomain);
	}
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@RequestBody String badArrearageJson) {
		BadArrearageDomain badArrearageDomain=
				JSONObject.parseObject(badArrearageJson,BadArrearageDomain.class);
		return badArrearageService.insert(badArrearageDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@RequestBody String badArrearageJson) {
		BadArrearageDomain badArrearageDomain=
				JSONObject.parseObject(badArrearageJson,BadArrearageDomain.class);
		return badArrearageService.update(badArrearageDomain);
	}

	@RequestMapping("insertList")
	@ResponseBody
	public HttpResult insertList(@RequestBody String paramJson) {
		JSONObject jsonObject = JSONObject.parseObject(paramJson);

		String arrearageJson=jsonObject.getString("data");

		Integer isSettle=jsonObject.getInteger("isSettle");

		String remark=jsonObject.getString("remark");

		UserInfoEntity userInfoEntity =
				JSONObject.parseObject(jsonObject.getString("userInfo"),
						UserInfoEntity.class);

		List<ArrearageDomain> arrearageDomain=
				JSONObject.parseArray(arrearageJson,ArrearageDomain.class);

		List<ArrearageDomain> testArrearageDomain=
				arrearageDomain.stream().filter(t->t.getIsSettle().intValue()==isSettle.intValue()).collect(toList());

		if(testArrearageDomain!=null && testArrearageDomain.size()>0){
			return new HttpResult(HttpResult.ERROR,"保存失败:修改前后的结清标志不能相同");
		}

		List<BadArrearageDomain> badArrearageDomains=new ArrayList<>();

		arrearageDomain.forEach(t->{
			BadArrearageDomain badArrearageDomain=new BadArrearageDomain();
			badArrearageDomain.setArrearageId(t.getId());
			badArrearageDomain.setCreateDate(new Date());
			badArrearageDomain.setOperator(userInfoEntity.getId());
			badArrearageDomain.setOldIsSettle(t.getIsSettle());
			badArrearageDomain.setNewIsSettle(isSettle);
			badArrearageDomain.setRemark(remark);
			badArrearageDomains.add(badArrearageDomain);
			t.setIsSettle(isSettle);
		});
		//更新欠费标志
		iArrearageService.updateList(arrearageDomain);

		//生成记录
		int num=badArrearageService.insertList(badArrearageDomains);
		if(num>0){
			return new HttpResult(HttpResult.SUCCESS,"保存成功");
		}

		return new HttpResult(HttpResult.ERROR,"保存失败");
	}

	@RequestMapping("fingBadArrearage")
	@ResponseBody
	public HttpResultPagination fingBadArrearage(@RequestBody BadArrearageShowEntity paramarrearage) throws Exception {

		JSONObject jsonObject = new JSONObject();

		if (paramarrearage.getBusinessPlaceCode() != null) {

			jsonObject.put("id", paramarrearage.getBusinessPlaceCode());

			HttpResult<List<DeptDomain>> paramDeptHttpResult =
					titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, jsonObject,
							new TypeReference<HttpResult<List<DeptDomain>>>() {
							});

			List<DeptDomain> paramDeptList =
					paramDeptHttpResult.getResultData();

			if (paramDeptList.size() > 0) {
				paramarrearage.setBusinessPlaceCodes(paramDeptList.stream().map(DeptDomain::getId).collect(Collectors.toList()));
				paramarrearage.getBusinessPlaceCodes().add(paramarrearage.getBusinessPlaceCode());
			}
		}
		//结算户
		if(paramarrearage.getSettlementNo()!=null){{
			jsonObject.clear();
			jsonObject.put("settlementNo", paramarrearage.getSettlementNo());
			String settlementJson = restTemplate.postForObject(Global.getConfig("getSettlementByNo"), jsonObject, String.class);
			List<SettlementEntity> settementArray = JSONObject
					.parseArray(JSONObject.parseObject(settlementJson).getString("list"), SettlementEntity.class);
			if(settementArray==null || settementArray.size()<1){
				return new HttpResultPagination<ArrearageDomain>(paramarrearage, new ArrayList<>());
			}
			List<Long> settlementIds=
					settementArray.stream().map(SettlementEntity::getId).distinct().collect(toList());
			paramarrearage.setSettlementIds(settlementIds);
		}

		}
		//writesectionId转成writesectid 查询条件 -》基础数据
		List<BadArrearageShowEntity> badArrearageDomains =
				badArrearageService.findArrearageAndBadArrearage(paramarrearage);

		if (badArrearageDomains == null || badArrearageDomains.size() < 1) {
			return new HttpResultPagination<ArrearageDomain>(paramarrearage,
					new ArrayList<>());
		}

		//不为null 且不重复的结算户id
		List<Long> settlementIds =
				badArrearageDomains.stream().filter(t -> t.getSettlementId() != null)
						.map(t -> t.getSettlementId()).distinct().collect(toList());

		//根据结算户id 查询结算户信息
		jsonObject.clear();
		jsonObject.put("settleIds", settlementIds);
		HttpResult<List<SettlementEntity>> settleResult =
				titanTemplate.postJson("CIM-SERVER",
						"cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), jsonObject,
						new TypeReference<HttpResult<List<SettlementEntity>>>() {
						});

		List<SettlementEntity> settlementDomains = settleResult.getResultData();


		//处理成key（结算户id） value（结算户信息）显示
		Map<Long, SettlementEntity> settlementDomainMap =
				settlementDomains.stream()
						.collect(Collectors.toMap(SettlementEntity::getId, a -> a, (k1, k2) -> k2));

		//获取抄表区段集合
		List<Long> writeSectionIds = badArrearageDomains.stream().filter(t -> t.getWriteSectId() != null)
				.map(t -> t.getWriteSectId()).distinct().collect(toList());

		WriteSectDomain writeSectDomain = new WriteSectDomain();
		writeSectDomain.setPageSize(-1);
		writeSectDomain.setWriteSectionIds(writeSectionIds);
		String writeSectJson = restTemplate.postForObject(Global.getConfig("getWriteSectFindByWhere"),
				writeSectDomain, String.class);

		//抄表区段map
		Map<Long, WriteSectDomain> writeSectDomainMap = JSONUtil.readValue(writeSectJson,
				new TypeReference<List<WriteSectDomain>>() {
				}).stream().collect(Collectors.toMap(WriteSectDomain::getId, k -> k));

		//营业区域下拉
		DeptDomain deptDomain = new DeptDomain();

		jsonObject.clear();
		HttpResult<List<DeptDomain>> paramDeptHttpResult =
				titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, jsonObject,
						new TypeReference<HttpResult<List<DeptDomain>>>() {
						});

		List<DeptDomain> deptList =
				paramDeptHttpResult.getResultData();
		Map<Long, DeptDomain> deptIdMap =
				deptList.stream().collect(Collectors.toMap(DeptDomain::getId,a -> a, (k1, k2) -> k1));

		//获取下拉值
		Map<Long, String> chargeModeCommonConfigMap =
				sysCommonConfigService.getsystemCommonConfigMap("CHARGE_MODE");

		Map<Long, String> elecTypeConfigMap =
				sysCommonConfigService.getsystemCommonConfigMap("ELEC_TYPE");

		Map<Long, String> isSettleConfigMap =
				sysCommonConfigService.getsystemCommonConfigMap("PAID_FLAG");

		//批量获取计量点信息
		List<Long> meterIds=
				badArrearageDomains.stream().map(BadArrearageShowEntity::getMeterId).distinct().collect(toList());

		HttpResult httpResult = restTemplate.postForObject(Global.getConfig("getMeterByMeterIdsWithoutStatus"), meterIds,
				HttpResult.class);

		List<MeterDomain> meterDomains = JSONObject.parseArray(JSONArray.toJSONString(httpResult.getResultData()),
				MeterDomain.class);

		Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
				.collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));

		UserInfoEntity userDomain = new UserInfoEntity();
		List<MapEntity> listmap = userService.findMapByDomain(userDomain);
		Map<Long, String> userMap = FormatterUtil.ListMapEntityToMap(listmap);

		for (BadArrearageShowEntity badArrearageShowEntity : badArrearageDomains) {
			SettlementEntity settlementEntity =
					settlementDomainMap.get(badArrearageShowEntity.getSettlementId());
			MeterDomain meterDomain=
					meterDomainMap.get(badArrearageShowEntity.getMeterId());
			badArrearageShowEntity.setCreateDate(badArrearageShowEntity.getCreateDate());
			badArrearageShowEntity.setSettlementNo(settlementEntity.getSettlementNo());
			badArrearageShowEntity.setSettlementName(settlementEntity.getSettlementName());
			badArrearageShowEntity.setSetAddress(settlementEntity.getAddress());
			badArrearageShowEntity.setWriteSectName(writeSectDomainMap.get(badArrearageShowEntity.getWriteSectId()).getWriteSectName());
			badArrearageShowEntity.setBusinessPlaceName(deptIdMap.get(badArrearageShowEntity.getBusinessPlaceCode()).getDeptName());
			badArrearageShowEntity.setChargeModeName(chargeModeCommonConfigMap.get(Long.valueOf(settlementEntity.getChargeModeType())));
			badArrearageShowEntity.setMeterNo(meterDomain.getMeterNo());
			badArrearageShowEntity.setElecTypeCode(meterDomain.getElecTypeCode());
			badArrearageShowEntity.setElecTypeName(elecTypeConfigMap.get(Long.valueOf(meterDomain.getElecTypeCode())));
			badArrearageShowEntity.setNewIsSettleName(isSettleConfigMap.get(badArrearageShowEntity.getNewIsSettle()));
			badArrearageShowEntity.setOldIsSettleName(isSettleConfigMap.get(badArrearageShowEntity.getOldIsSettle()));
			badArrearageShowEntity.setOperatorName(userMap.get(Long.valueOf(badArrearageShowEntity.getOperator())));
			// arrearageDomain.setPunishMoney(paramArrearageDomain.getPunishMoney());
		}

		return new HttpResultPagination<BadArrearageShowEntity>(paramarrearage, badArrearageDomains);
	}

}
