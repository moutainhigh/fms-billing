package org.fms.billing.server.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.BillDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDetailsDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMoneyMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;
import org.fms.billing.common.webapp.service.IBillService;
import org.fms.billing.server.webapp.dao.BillDAO;
import org.fms.billing.server.webapp.dao.MeterMeterAssetsRelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@TransactionService
public class BillServiceImpl implements IBillService {
	@TransactionDAO
	private BillDAO billDAO;

	@Autowired
	private MeterMeterAssetsRelDAO meterMeterAssetsRelDAO;

	@Autowired
	private TitanTemplate titanTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<BillDomain> findByWhere(BillDomain billDomain) {
		return billDAO.findByWhere(billDomain);
	}

	@Override
	public int insert(BillDomain billDomain) {
		return billDAO.insert(billDomain);
	}

	@Override
	public int update(BillDomain billDomain) {
		return billDAO.update(billDomain);
	}

	@Override
	public List<MeterMoneyDetailsDomain> getMeterMoneyBySettlement(String settlementNo, String mon) {

		if(settlementNo==null || "".equals(settlementNo)){
			return new ArrayList<>(0);
		}

		Map<String, Object> params = new HashMap<>();
		params.put("settlementNo", settlementNo);

		try {
			String meterIdsJsonString = restTemplate.postForObject(Global.getConfig(
					"getMeterIdsBySettlementInfo"), params, String.class);

			List<Long> meterIds = JSONObject.parseArray(meterIdsJsonString, Long.class);


			if(meterIds==null || meterIds.size()<1){
				return new ArrayList<>(0);
			}

			HttpResultPagination<SettlementDomain> settlementDomains = titanTemplate.postJson("CIM-SERVER",
					"cimServer/settlement?method=findClearSettlementByWhere", null, params, new TypeReference<HttpResultPagination<SettlementDomain>>() {
					});

			params.clear();
			params.put("meterIds", meterIds);
			String meterDomainListString = restTemplate.postForObject(Global.getConfig("getMeterAndUserByIds"), params, String.class);
			List<MeterDomain> meterDomains =
					JSONObject.parseArray(meterDomainListString, MeterDomain.class);


			List<MeterMoneyMongoDomain> meterMoneyMongoDomains = billDAO.getMeterMoneyByMeterIds(meterIds, mon);
			List<MeterMongoDomain> meterMongoDomains = billDAO.getMeterByMeterIds(meterIds, mon);

			Map<Long,MeterMoneyMongoDomain> meterMoneyMongoDomainMap = meterMoneyMongoDomains.stream().collect(Collectors.toMap(MeterMoneyMongoDomain::getMeterId,k->k));
			Map<Long,MeterMongoDomain> meterMongoDomainMap = meterMongoDomains.stream().collect(Collectors.toMap(MeterMongoDomain::getId, k->k));

			MeterMeterAssetsRelDomain rel = new MeterMeterAssetsRelDomain();
			rel.setMon(Integer.valueOf(mon));
			rel.setMeterIds(meterIds);
			List<MeterMeterAssetsRelDomain> relList = meterMeterAssetsRelDAO.mongoFind(rel);

			Map<String, MeterMeterAssetsRelDomain> relListMap =
					relList.stream().collect(Collectors.toMap(o -> o.getMeterId() + "_" + o.getFunctionCode()+"_" +o.getPhaseSeq(), a -> a, (k1, k2) -> k1));
//			meterType,priceType,tsType,cosType,baseMoneyFlag,needIndex,transLostType

//			meterDomains.forEach(m->{
//				m.setMeterMoneyMoneyDomain(meterMoneyMongoDomainMap.get(m.getId()));
//			});

			meterMoneyMongoDomains.forEach(t->{
				t.setMeterNo(meterMongoDomainMap.get(t.getMeterId()).getMeterNo());

				t.getMeterDataInfo().forEach(m->{
					MeterMeterAssetsRelDomain meterMeterAssetsRelDomain=relListMap.get(t.getMeterId()+ "_" +m.getFunctionCode() + "_" + m.getPhaseSeq() );
				if(meterMeterAssetsRelDomain!=null) {
					m.setWriteSn(meterMeterAssetsRelDomain.getWriteSn());
					m.setMeterOrder(meterMeterAssetsRelDomain.getMeterOrder());
				}
				});

				t.setAddMoney1(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "2" + "#" + "0"));
                t.setAddMoney2(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "3" + "#" + "0"));
                t.setAddMoney3(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "4" + "#" + "0"));
                t.setAddMoney4(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "5" + "#" + "0"));
                t.setAddMoney5(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "6" + "#" + "0"));
                t.setAddMoney6(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "7" + "#" + "0"));
                t.setAddMoney7(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "8" + "#" + "0"));
                t.setAddMoney8(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "9" + "#" + "0"));
                t.setAddMoney9(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "10" + "#" + "0"));
                t.setAddMoney10(t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "11" + "#" + "0"));
                t.setMeterDomains(meterDomains);
			});

			MeterMoneyDetailsDomain meterMoneyDetails = new MeterMoneyDetailsDomain();
			meterMoneyDetails.setMon(meterMoneyMongoDomainMap.get(meterMoneyMongoDomains.get(0).getMeterId()).getMon());
			meterMoneyDetails.setSn(meterMongoDomainMap.get(meterMoneyMongoDomains.get(0).getMeterId()).getCountTimes());
			meterMoneyDetails.setMeterMoneyMongoDomains(meterMoneyMongoDomains);
//			if(settlementDomains.getList().isEmpty())
//				return;
			meterMoneyDetails.setSettlementNo(settlementDomains.getList().get(0).getSettlementNo());
			meterMoneyDetails.setSettlementName(settlementDomains.getList().get(0).getSettlementName());
			meterMoneyDetails.setAddress(settlementDomains.getList().get(0).getAddress());

			List<MeterMoneyDetailsDomain> meterMoneyDetailsList = new ArrayList<>();
			meterMoneyDetailsList.add(meterMoneyDetails);

			return meterMoneyDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
