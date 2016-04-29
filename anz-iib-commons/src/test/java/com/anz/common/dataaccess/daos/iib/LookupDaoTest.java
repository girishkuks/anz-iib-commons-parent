package com.anz.common.dataaccess.daos.iib;

import static org.junit.Assert.assertNotNull;

import com.anz.common.dataaccess.daos.ILookupDao;


public class LookupDaoTest {//extends AbstractDaoTest {

	//@Autowired
	private ILookupDao lookupDao;
	
	//@Test
	public void testBeanDefinition() {
		assertNotNull(getLookupDao());
	}
	
	public ILookupDao getLookupDao() {
		return lookupDao;
	}
	
	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
}
