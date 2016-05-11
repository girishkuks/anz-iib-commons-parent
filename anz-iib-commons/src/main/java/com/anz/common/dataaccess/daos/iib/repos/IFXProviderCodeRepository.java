package com.anz.common.dataaccess.daos.iib.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anz.common.dataaccess.models.iib.IFXProviderCode;

public interface IFXProviderCodeRepository extends JpaRepository<IFXProviderCode, String> {
	
	public List<IFXProviderCode> findByProviderIdAndIfxCodeCode(String provider, String ifxCode);

}
