package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.service.WriteSectService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.WriteSectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteSectServiceImpl implements WriteSectService {
    @Autowired
    private WriteSectDAO writeSectionDAO;
    @Autowired
    private MeterDAO meterDAO;

    @Override
    public List<WriteSectDomain> getWriteSect(WriteSectDomain writeSectionDomain) {
        // TODO Auto-generated method stub
        return writeSectionDAO.getWriteSect(writeSectionDomain);
    }

    @Override
    public List<WriteSectDomain> getWriteSectAndNum(WriteSectDomain writeSectionDomain) {
        //获取抄表段
        List<WriteSectDomain> writeSectDomainList = writeSectionDAO.getWriteSect(writeSectionDomain);
        //获取计量点
        MeterDomain tempMeter = new MeterDomain();
        tempMeter.setWriteSectionIds(writeSectionDomain.getWriteSectionIds());
        tempMeter.setMon(Integer.valueOf(writeSectionDomain.getMon()));
        List<MeterDomain> MeterDomainList = meterDAO.findMeterDomain(tempMeter);

        for (WriteSectDomain tw : writeSectDomainList) {
            Integer initedNum = 0;  //0
            Integer readedNum = 0;    //1
            Integer calcedNum = 0;    //2
            Integer calceExceptiondNum = 0;//-2
            Integer issuedNum = 0;//3
            Integer unknownNum = 0;
            Long WriteSectId = tw.getId();
            for (MeterDomain tm : MeterDomainList) {
                if (WriteSectId - tm.getWriteSectionId() != 0) {
                    continue;
                }
                if (tm.getStatus() - 0 == 0) {
                    initedNum++;
                } else if (tm.getStatus() - 1 == 0) {
                    readedNum++;
                } else if (tm.getStatus() - 2 == 0) {
                    calcedNum++;
                } else if (tm.getStatus() + 2 == 0) {
                    calceExceptiondNum++;
                } else if (tm.getStatus() - 3 == 0) {
                    issuedNum++;
                } else {
                    unknownNum++;
                }
            }

            tw.setInitedNum(initedNum);
            tw.setReadedNum(readedNum);
            tw.setCalcedNum(calcedNum);
            tw.setCalceExceptiondNum(calceExceptiondNum);
            tw.setIssuedNum(issuedNum);
            tw.setUnknownNum(unknownNum);
        }

        return writeSectDomainList;
    }

    @Override
    public List<WriteSectDomain> getMeterReadingSituation(WriteSectDomain writeSectionDomain) {
        // TODO Auto-generated method stub
        return writeSectionDAO.getMeterReadingSituation(writeSectionDomain);
    }

    @Override
    public List<WriteSectDomain> getMeterCalOrPublicSituation(WriteSectDomain writeSectionDomain,int status) {
        // TODO Auto-generated method stub
        return writeSectionDAO.getMeterCalOrPublicSituation(writeSectionDomain,status);
    }

    @Override
    public String getMeterInitSituation(WriteSectDomain writeSectionDomain) {
        // TODO Auto-generated method stub
        return writeSectionDAO.getMeterInitSituation(writeSectionDomain);
    }

    @Override
    public List<WriteSectDomain> getWriteSectDomain(WriteSectDomain writeSectDomain) {
        return writeSectionDAO.getWriteSectDomain(writeSectDomain);
    }


    @Override
    public List<WriteSectDomain> getWriteSectSortByWritorId(WriteSectDomain writeSectionDomain) {
        // TODO Auto-generated method stub
        return writeSectionDAO.getWriteSectSortByWritorId(writeSectionDomain);
    }
}
