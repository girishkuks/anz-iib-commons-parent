package com.anz.common.ioc.spring;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import com.anz.common.dataaccess.daos.ILookupDao;
import com.anz.common.ioc.IIoCFactory;

public class AnzSpringIoCFactoryTest {

	//@Test
	public void testGetBean() throws Exception {
		IIoCFactory factory = AnzSpringIoCFactory.getInstance(null);
		
		DataSource ds = factory.getBean("dataSource", DataSource.class);
		
		assertNotNull(ds);

		// get repository
		
		ILookupDao lookupDao = factory.getBean(ILookupDao.class);
		assertNotNull(lookupDao.getRepository());
		assertNotNull(lookupDao.getEntityManager());
		
		//assertNotNull(lookupDao.);
	}

}
