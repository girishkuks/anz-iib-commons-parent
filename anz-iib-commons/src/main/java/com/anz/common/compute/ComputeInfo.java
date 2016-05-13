/**
 * 
 */
package com.anz.common.compute;

import java.util.HashMap;
import java.util.Map;

import com.ibm.broker.plugin.MbBroker;
import com.ibm.broker.plugin.MbMessageFlow;

/**
 * This object is passed over to the Transformer Classes and ExceptionMessage
 * 
 * @author sankestsw
 *
 */
public class ComputeInfo {
	
	MbMessageFlow messageFlow;
	
	MbBroker broker;
	
	String computeName;
	
	Map<String, String> userDefinedProperties = new HashMap<String, String>();
	
	/**
	 * @return the messageFlow
	 */
	public MbMessageFlow getMessageFlow() {
		return messageFlow;
	}

	/**
	 * @param messageFlow the messageFlow to set
	 */
	public void setMessageFlow(MbMessageFlow messageFlow) {
		this.messageFlow = messageFlow;
	}

	/**
	 * @return the broker
	 */
	public MbBroker getBroker() {
		return broker;
	}

	/**
	 * @param broker the broker to set
	 */
	public void setBroker(MbBroker broker) {
		this.broker = broker;
	}

	/**
	 * @return the computeName
	 */
	public String getComputeName() {
		return computeName;
	}

	/**
	 * @param computeName the computeName to set
	 */
	public void setComputeName(String computeName) {
		this.computeName = computeName;
	}

	/**
	 * @return the userDefinedProperties
	 */
	public Map<String, String> getUserDefinedProperties() {
		return userDefinedProperties;
	}

	/**
	 * @param userDefinedProperties the userDefinedProperties to set
	 */
	public void setUserDefinedProperties(Map<String, String> userDefinedProperties) {
		this.userDefinedProperties = userDefinedProperties;
	}

	/**
	 * Add userDefinedAttribute to the map
	 * @param userDefinedAttribute key (User Defined Property)
	 * @param value (Value of the User Defined Property)
	 */
	public void addUserDefinedProperty(String userDefinedAttribute, String value) {
		if(userDefinedProperties.get(userDefinedAttribute) != null) {
			userDefinedProperties.remove(userDefinedAttribute);
		}
		userDefinedProperties.put(userDefinedAttribute, value);
	}
	
	
			

}
