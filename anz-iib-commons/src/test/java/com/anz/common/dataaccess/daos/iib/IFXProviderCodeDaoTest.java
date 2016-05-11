package com.anz.common.dataaccess.daos.iib;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.anz.common.dataaccess.daos.AbstractDaoTest;
import com.anz.common.dataaccess.daos.IIFXCodeDao;
import com.anz.common.dataaccess.daos.IIFXProviderCodeDao;
import com.anz.common.dataaccess.daos.IIFXProviderCodeDao;
import com.anz.common.dataaccess.daos.IProviderDao;
import com.anz.common.dataaccess.models.iib.IFXCode;
import com.anz.common.dataaccess.models.iib.IFXProviderCode;
import com.anz.common.dataaccess.models.iib.Provider;
import com.anz.common.ioc.IIoCFactory;
import com.anz.common.ioc.spring.AnzSpringIoCFactory;


public class IFXProviderCodeDaoTest extends AbstractDaoTest {

	@Autowired
	private IIFXProviderCodeDao ifxProviderCodeDao;
	
	@Autowired
	private IIFXCodeDao ifxCodeDao;
	
	@Autowired
	private ProviderDao providerDao;
	
	
	
	@Test
	public void testBeanDefinition() {
		assertNotNull(getIFXProviderCodeDao());
	}
	
	/**
	 * Just for the convenience 
	 * @throws Exception 
	 */
	public void populateIFXCodeDatabase() throws Exception {

		/*IIoCFactory factory = AnzSpringIoCFactory.getInstance();
		IIFXCodeDao ifxCodeDao = factory.getBean(IIFXCodeDao.class);
		IIFXProviderCodeDao ifxProviderCodeDao = factory.getBean(IIFXProviderCodeDao.class);
		IProviderDao providerDao = factory.getBean(IProviderDao.class);*/
		
		IFXCode o = new IFXCode();
		o.setCode("178");
		o.setDescr("Error conencting to the system. Please try again later.");
		o.setSeverity(IFXCode.SEV_CRITICAL);
		o.setStatus(IFXCode.STATUS_FAILURE);
		o = ifxCodeDao.saveAndFlush(o);		
		
		Provider p = new Provider();
		p.setId("CICS");
		p.setDescr("CICS provider");
		p = providerDao.saveAndFlush(p);
		
		IFXProviderCode i = new IFXProviderCode();
		i.setProvider(p);
		i.setIfxCode(o);
		i.setCode("15");
		i = ifxProviderCodeDao.saveAndFlush(i);
		
	}
	
	public IIFXProviderCodeDao getIFXProviderCodeDao() {
		return ifxProviderCodeDao;
	}
	
	public void setIFXProviderCodeDao(IIFXProviderCodeDao ifxProviderCodeDao) {
		this.ifxProviderCodeDao = ifxProviderCodeDao;
	}
	
	@Test
	public void testIFXProviderCode() throws Exception {
		populateIFXCodeDatabase();
		assertNotNull(ifxProviderCodeDao.findAll().get(0));
		List<IFXProviderCode> codes = ifxProviderCodeDao.findByProviderIdAndIfxCodeCode("CICS", "178");
		assertNotNull(codes);
		assertNotNull(codes.get(0));
		assertEquals("15", codes.get(0).getCode());
	}
}
