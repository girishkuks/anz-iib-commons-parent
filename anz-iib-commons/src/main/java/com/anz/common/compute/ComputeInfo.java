/**
 * 
 */
package com.anz.common.compute;

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
	
			

}
