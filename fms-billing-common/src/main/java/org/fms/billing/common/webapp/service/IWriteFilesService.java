package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterAssetsDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.entity.CbReadingDownEntity;

public interface IWriteFilesService {

	public List<WriteFilesDomain> findByWhere(WriteFilesDomain writeFilesDomain);

	public List<WriteFilesDomain> mongoFind(WriteFilesDomain queryParam);
	List<WriteFilesDomain> findWriteFilesByWhere(WriteFilesDomain writeFilesDomain);
	public long mongoUpdate(List<WriteFilesDomain> writeFilesDomains);

	public List<WriteFilesDomain> getWriteFiles(WriteFilesDomain writeFilesDomain);
	
	public List<MeterAssetsDomain> getMeterAssetsByAssetsIds(List<Long> assetsIds) throws Exception;

	List<WriteFilesDomain> getWriteFilesDomain(WriteFilesDomain writeFilesDomain);

    List<WriteFilesDomain> writeFilesQuery(WriteFilesDomain writeFiles);

	List<WriteFilesDomain> zeroUserQuery(WriteFilesDomain writeFiles);

	List<WriteFilesDomain> mongoFindByWrite(WriteFilesDomain queryParam);

	List<WriteFilesDomain> mongoFindForReadingDown(WriteFilesDomain queryParam);

	List<WriteFilesDomain> findWriteFilesList(WriteFilesDomain writeFilesDomain) throws Exception;

	long mongoUpdateByCBQ(List<CbReadingDownEntity> cbReadingDownEntities);

	List<WriteFilesDomain> findWriteFilesByCustomer(CustomerDomain customerDomain);

    List<WriteFilesDomain> powerException(WriteFilesDomain writeFiles) throws Exception;
}
