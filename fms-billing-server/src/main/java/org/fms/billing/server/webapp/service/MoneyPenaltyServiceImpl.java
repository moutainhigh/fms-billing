/**
 * Author : chizf
 * Date : 2020年6月5日 下午3:39:25
 * Title : com.riozenc.billing.webapp.service.impl.MoneyPenaltyServiceImpl.java
 *
**/
package org.fms.billing.server.webapp.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ArrearagePenaltyMoneyDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyPenaltyDataDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.service.IMoneyPenaltyService;
import org.fms.billing.server.webapp.dao.ArrearageDAO;
import org.fms.billing.server.webapp.dao.ArrearagePenaltyMoneyDAO;
import org.fms.billing.server.webapp.dao.MoneyPenaltyDAO;
import org.fms.billing.server.webapp.dao.WriteSectDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.common.date.DateUtil;

@TransactionService
public class MoneyPenaltyServiceImpl implements IMoneyPenaltyService {

	@Autowired
	private WriteSectDAO writeSectionDAO;
	@TransactionDAO
	private ArrearageDAO arrearageDAO;
	@TransactionDAO
	private MoneyPenaltyDAO moneyPenaltyDAO;

	@TransactionDAO
	private ArrearagePenaltyMoneyDAO arrearagePenaltyMoneyDAO;

	@Override
	public int insert(MeterMoneyPenaltyDataDomain t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(MeterMoneyPenaltyDataDomain t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(MeterMoneyPenaltyDataDomain t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MeterMoneyPenaltyDataDomain findByKey(MeterMoneyPenaltyDataDomain t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MeterMoneyPenaltyDataDomain> findByWhere(MeterMoneyPenaltyDataDomain t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generatePenaltyData(WriteSectDomain writeSectionDomain) {
		// 查询当月抄表区段
		List<WriteSectDomain> writeSectDomains = writeSectionDAO.getWriteSect(writeSectionDomain);
		writeSectDomains.forEach(w -> {
			ArrearageDomain arrearageDomain = new ArrearageDomain();
			arrearageDomain.setIsSettle(0);// 0=欠费
			List<ArrearageDomain> arrearageDomains = arrearageDAO.findByWhere(arrearageDomain);
			List<MeterMoneyPenaltyDataDomain> meterMoneyPenaltyDataDomains = arrearageDomains.stream().filter(a -> {
				Long days = DateUtil.between(a.getCreateDate(), new Date());

				if (days > w.getPunishDays()) {
					return true;
				} else {
					return false;
				}

			}).map(a -> {
				MeterMoneyPenaltyDataDomain meterMoneyPenaltyDataDomain = new MeterMoneyPenaltyDataDomain();

				meterMoneyPenaltyDataDomain.setWriteSectId(w.getId());
				meterMoneyPenaltyDataDomain.setArrearageId(a.getId());
				meterMoneyPenaltyDataDomain.setOweMoney(a.getOweMoney());
				meterMoneyPenaltyDataDomain.setCreateDate(new Date());
				meterMoneyPenaltyDataDomain.setUpdateDate(new Date());
				meterMoneyPenaltyDataDomain.setStatus((byte) 1);
				return meterMoneyPenaltyDataDomain;

			}).collect(Collectors.toList());

			// 根据营业区域删除数据，再新增数据
			MeterMoneyPenaltyDataDomain deleteDomain = new MeterMoneyPenaltyDataDomain();
			deleteDomain.setWriteSectId(w.getId());
			moneyPenaltyDAO.delete(deleteDomain);
			moneyPenaltyDAO.insertList(meterMoneyPenaltyDataDomains);
		});

	}

	@Override
	public int generatePenaltyMoney() {
		// 获取所有违约数据
		// 依据违约数据生成欠费记录
		List<MeterMoneyPenaltyDataDomain> meterMoneyPenaltyDataDomains = moneyPenaltyDAO
				.findByWhere(new MeterMoneyPenaltyDataDomain());
		List<ArrearagePenaltyMoneyDomain> arrearagePenaltyMoneyDomains = meterMoneyPenaltyDataDomains.parallelStream()
				.map(m -> {

					ArrearagePenaltyMoneyDomain arrearagePenaltyMoneyDomain = new ArrearagePenaltyMoneyDomain();

					arrearagePenaltyMoneyDomain.setArrearageId(m.getArrearageId());
					arrearagePenaltyMoneyDomain.setOweMoney(m.getOweMoney());
					arrearagePenaltyMoneyDomain.setCreateDate(new Date());
					arrearagePenaltyMoneyDomain.setPenaltyMoney(m.getOweMoney().divide(BigDecimal.valueOf(1000)));
					arrearagePenaltyMoneyDomain.setRemark(null);
					arrearagePenaltyMoneyDomain.setStatus((byte) 1);
					return arrearagePenaltyMoneyDomain;
				}).collect(Collectors.toList());

		// 入库

		return arrearagePenaltyMoneyDAO.insertList(arrearagePenaltyMoneyDomains);
	}

}
