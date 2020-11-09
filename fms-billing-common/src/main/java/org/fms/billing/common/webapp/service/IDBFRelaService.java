package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.WriteFilesDomain;

public interface IDBFRelaService {
	public List<WriteFilesDomain> writeFilter (List<WriteFilesDomain> originalData);

}
