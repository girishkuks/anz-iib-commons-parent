package com.anz.common.ioc.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.anz.common.ioc.IIoCFactory;

public class AnzSpringIoCFactory implements IIoCFactory {

	private static final Logger logger = LogManager.getLogger();
	
	private static AnzSpringIoCFactory instance;
	
	private AnnotationConfigApplicationContext context;
	
	public AnzSpringIoCFactory() {
		
		// Load the Spring Context
		context = new AnnotationConfigApplicationContext();
		
		// register the Spring configuration
		context.register(TestSpringConfig.class);
		
		context.refresh();		
    }
	
	public <T> T getBean(String name, Class<T> clazz) {
		return context.getBean(name, clazz);
	}

	public <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static IIoCFactory getInstance() {
		
		if(instance == null) {
			instance = new AnzSpringIoCFactory();
		}
		
		return instance;
	}

}
