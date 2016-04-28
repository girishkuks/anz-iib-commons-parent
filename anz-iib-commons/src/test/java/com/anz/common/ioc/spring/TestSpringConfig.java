package com.anz.common.ioc.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.anz.common.dataaccess.daos.ILookupDao;
import com.anz.common.dataaccess.daos.iib.LookupDao;

@Configuration
@EnableJpaRepositories(basePackages={"com.anz.common.dataaccess.daos.iib.repos"})
public class TestSpringConfig extends AbstractSpringConfig {

	@Bean(name="dataSource")
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL).build();
	}
	
	@Bean
	public ILookupDao lookupDao() {
		return new LookupDao();
	}
}
