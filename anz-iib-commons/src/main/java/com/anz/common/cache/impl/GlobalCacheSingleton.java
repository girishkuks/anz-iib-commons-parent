package com.anz.common.cache.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalCacheSingleton {
	private static final Logger logger = LogManager.getLogger();
	private static GlobalCacheSingleton _inst = null;
	private static GlobalCacheSettings globalcacheSettings;

	private GlobalCacheSingleton() {
		if (globalcacheSettings == null) {
			try {
				JAXBContext jaxbContext = JAXBContext
						.newInstance(GlobalCacheSettings.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				
				URL resource = null;
				
				String path =  System.getenv("CACHE_CONFIG");
				logger.info("System property CACHE_CONFIG={}",path);
				File configFile = new File(path + "/" + "global-cache.xml");
				if(configFile.exists()) {
					try {
						resource =  configFile.toURI().toURL();
					} catch (MalformedURLException e) {
						logger.throwing(e);
					}
				} else {
				
					resource = GlobalCacheSingleton.class
						.getResource("global-cache.xml");
				}
				
				if(resource != null) {
					globalcacheSettings = (GlobalCacheSettings) unmarshaller
						.unmarshal(resource);
				}
			} catch (JAXBException e) {
				logger.throwing(e);
			}
		}
	}

	public static GlobalCacheSingleton getInstance() {
		if (_inst == null) {
			_inst = new GlobalCacheSingleton();
		}

		return _inst;
	}

	public Cache getCacheSetting(String cacheName) {

		if (globalcacheSettings != null) {
			for (Cache cacheSetting : globalcacheSettings.getCache()) {
				if (cacheName.equalsIgnoreCase(cacheSetting.getName())) {
					logger.info("Cache setting found for the cache {}", cacheName);
					return cacheSetting;
				}
			}
		}
		return null;

	}

}
