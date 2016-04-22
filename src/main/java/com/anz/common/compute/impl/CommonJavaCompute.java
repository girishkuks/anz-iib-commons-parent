/**
 * 
 */
package com.anz.common.compute.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.ICommonJavaCompute;
import com.anz.common.compute.TransformType;
import com.ibm.broker.javacompute.MbJavaComputeNode;
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
	 * @see com.anz.common.compute.ICommonJavaCompute#getTransformationType()
	 */
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.ibm.broker.javacompute.MbJavaComputeNode#onInitialize()
	 */
	@Override
	public void onInitialize() throws MbException {
		// TODO Auto-generated method stub
		super.onInitialize();
		
		String logger_name = (String) getUserDefinedAttribute("LOGGER_NAME");
		if(logger_name!= null) {
			logger =  LogManager.getLogger(logger_name);
		}
		
		constructComputeInfo();
	}



	private void constructComputeInfo() {
		metaData = new ComputeInfo();
		try {
			metaData.setApplication(getMessageFlow().getApplicationName());
			metaData.setMessageFlow(getMessageFlow().getName());
			metaData.setBroker(getBroker().getName());
			metaData.setComputeName(getName());
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
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
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
