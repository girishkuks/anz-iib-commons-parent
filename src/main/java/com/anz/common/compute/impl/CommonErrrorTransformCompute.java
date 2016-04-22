/**
 * 
 */
package com.anz.common.compute.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.TransformType;
import com.anz.common.ioc.spring.MbNodefactory;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public abstract class CommonErrrorTransformCompute extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.ICommonComputeNode#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.JSON_TO_JSON;
	}
	
	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJavaCompute#execute(com.ibm.broker.plugin.MbMessageAssembly, com.ibm.broker.plugin.MbMessageAssembly)
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = outAssembly.getMessage();
		
		/* 
		 * Set the compute node in the node factory so that 
		 * Transform classes can use the jdbc type4 connection datasource later
		 * @see #IIBJdbc4DataSource
		 * @see #AnzSpringIoCFactory
		 */
		MbNodefactory.getInstance().setMbNode(this);
		
		
		boolean fireAndForget = false;
		// TODO Get the FireAndForget user defined property
		
		
		// Create an HTTPResponseHeader with Error value to return for the failure if it is missing
		MbElement outRoot = outMessage.getRootElement();
		if(!fireAndForget && outRoot.getFirstElementByPath("HTTPResponseHeader") == null)
		{
			MbElement msgProps = inMessage.getRootElement().getFirstElementByPath("/Properties").copy();
			outRoot.addAsFirstChild(msgProps); 
			MbElement httpResHdr = outRoot.createElementAsLastChild(MbElement.TYPE_NAME, "HTTPResponseHeader", null); 
	        httpResHdr.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "X-Original-HTTP-Status-Line", "HTTP/1.1 500"); 
	        httpResHdr.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "X-Original-HTTP-Status-Code", 500); 
	        httpResHdr.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Content-Type", "text/html; charset=utf-8"); 
		}	
		
		
		//String inputJson = ComputeUtils.getJsonDataFromBlob(inMessage);
		String outputJson = executeTranformation(outAssembly);
		if (outputJson != null) {
			// Detach Original Exception Node from the response
			MbElement exception = outAssembly.getExceptionList().getRootElement();
			exception.detach();
			
			// Write this outputJson to outMessage
			ComputeUtils.replaceStringAsBlob(outMessage, outputJson);
		}
		
	}
	
	/**
	 * Write business logic here if you merely need to transform the message
	 * from JSON to JSON
	 * 
	 * @param outAssembly
	 *            output assembly copied from inAssembly
	 * @return output JSON Data to be placed in the message
	 */
	public String executeTranformation(MbMessageAssembly outAssembly) throws Exception {
		String outputString = null;
		// Remove the subflow name if any from the Transformer class before com.anz.**
		//String transformerClassName = getName().substring(getName().indexOf("com"));
		//logger.info("Creating instance of {}", transformerClassName);
		try {
			//ITransformer<MbMessageAssembly, String> jsonTransformer = (ITransformer<MbMessageAssembly, String>)Class.forName(transformerClassName).newInstance();
			ITransformer<MbMessageAssembly, String> transformer = getTransformer();
			outputString = transformer.execute(outAssembly, logger, metaData);
		} catch(Exception e) {
			logger.throwing(e);
			throw e;
		}
		return outputString;
	}
	
	/**
	 * Get the external transformer class instance
	 * @return transform
	 */
	public abstract ITransformer<MbMessageAssembly, String> getTransformer();

	

}
