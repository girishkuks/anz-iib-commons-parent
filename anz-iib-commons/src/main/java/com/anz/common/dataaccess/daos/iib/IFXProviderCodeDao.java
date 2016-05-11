package com.anz.common.dataaccess.daos.iib;

import java.util.List;

import com.anz.common.dataaccess.AbstractDao;
import com.anz.common.dataaccess.daos.IIFXProviderCodeDao;
import com.anz.common.dataaccess.daos.iib.repos.IFXProviderCodeRepository;
import com.anz.common.dataaccess.models.iib.IFXProviderCode;

public class IFXProviderCodeDao extends AbstractDao<IFXProviderCode, String, IFXProviderCodeRepository> implements IIFXProviderCodeDao {

	public List<IFXProviderCode> findByProviderIdAndIfxCodeCode(String provider,
			String ifxCode) {
		return getRepository().findByProviderIdAndIfxCodeCode(provider, ifxCode);
	}

}
