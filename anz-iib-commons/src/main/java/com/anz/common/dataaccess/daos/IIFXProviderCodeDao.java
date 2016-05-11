package com.anz.common.dataaccess.daos;

import com.anz.common.dataaccess.IDao;
import com.anz.common.dataaccess.daos.iib.repos.IFXProviderCodeRepository;
import com.anz.common.dataaccess.models.iib.IFXProviderCode;

public interface IIFXProviderCodeDao extends IFXProviderCodeRepository, IDao<IFXProviderCode, String, IFXProviderCodeRepository> {

}
