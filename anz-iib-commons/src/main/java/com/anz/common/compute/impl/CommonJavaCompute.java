/**
 * 
 */
package com.anz.common.compute.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.ICommonJavaCompute;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

/**
 * @author sanketsw
 * 
 */
public abstract class CommonJavaCompute extends MbJavaComputeNode implements
		ICommonJavaCompute {
	
	
	static Logger logger = LogManager.getLogger();
	static Logger appLogger = LogManager.getLogger();
	
	ComputeInfo metaData;
	

	/**
	 * @return the metaData
	 */
	public ComputeInfo getMetaData() {
		return metaData;
	}



	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(ComputeInfo metaData) {
		this.metaData = metaData;
	}



	public Logger getLogger() {
		return logger;
	}
	
	

	/* (non-Javadoc)
	 * @see com.ibm.broker.javacompute.MbJavaComputeNode#onInitialize()
	 */
	@Override
	public void onInitialize() throws MbException {
		// TODO Auto-generated method stub
		super.onInitialize();	
		
		String appLoggerName = (String) getUserDefinedAttribute("LOGGER_NAME");
		if(appLoggerName != null && ! appLoggerName.isEmpty()) {
			appLogger = LogManager.getLogger(appLoggerName);
			logger.info("appLogger is set to {}", appLoggerName);
		}
		
		constructComputeInfo();
	}



	private void constructComputeInfo() {
		metaData = new ComputeInfo();
		try {
			metaData.setMessageFlow(getMessageFlow());
			metaData.setBroker(getBroker());
			metaData.setComputeName(getName());
			logger.info("Metadata {}", getName());
		} catch (MbException e) {
			logger.error("Error accessing message flow and broker details from Compute {}", getName());
			logger.throwing(e);
		}
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.broker.javacompute.MbJavaComputeNode#evaluate(com.ibm.broker.
	 * plugin.MbMessageAssembly)
	 */
	@SuppressWarnings("unused")
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message 
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);			

			// copy input message headers to the new output
			// Since outMessage is copy of inMessage this is not required.
			// copyMessageHeaders(inMessage, outMessage);
			
			// ----------------------------------------------------------
			// Add user code below
			
			execute(inAssembly, outAssembly);
			

			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be
			// handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}


	protected void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();

		// iterate though the headers starting with the first child of the root
		// element and stopping before the last child (message body)
		MbElement header = inMessage.getRootElement().getFirstChild();
		while (header != null && header.getNextSibling() != null) {
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}


	/**
	 * Override business logic here if you need to use local environment or http
	 * header etc.
	 * 
	 * @param inAssembly
	 * @param outAssembly
	 * @throws Exception
	 */
	public abstract void  execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception;



}