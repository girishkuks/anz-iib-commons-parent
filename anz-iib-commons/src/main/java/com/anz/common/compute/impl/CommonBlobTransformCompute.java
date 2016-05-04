/**
 * 
 */
package com.anz.common.compute.impl;

import com.anz.common.compute.TransformType;
import com.anz.common.ioc.spring.MbNodefactory;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public abstract class CommonBlobTransformCompute extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.ICommonComputeNode#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.HTTP_HHTP;
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
		
		
		String inputString = ComputeUtils.getStringFromBlob(inMessage);
		String outputString = executeBlobTranform(inputString);
		if (outputString != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceStringAsBlob(outMessage, outputString);
		}
		
	}
	
	/**
	 * Write business logic here if you merely need to transform the message
	 * from JSON to JSON
	 * 
	 * @param inputJson
	 *            input JSON Data
	 * @return output JSON Data to be placed in the message
	 */
	public String executeBlobTranform(String inputJson) throws Exception {
		String out = null;
		//String transformerClassName = getName().substring(getName().indexOf("com"));
		//logger.info("Creating instance of {}", transformerClassName);
		try {
			//ITransformer<String, String> jsonTransformer = (ITransformer<String, String>)Class.forName(transformerClassName).newInstance();
			ITransformer<String, String> stringTransformer = getTransformer();
			out = stringTransformer.execute(inputJson, appLogger, metaData);
		} catch(Exception e) {
			logger.throwing(e);
			throw e;
		}
		return out;
	}
	
	/**
	 * Get the external transformer class instance
	 * @return
	 */
	public abstract ITransformer<String, String> getTransformer();

	
	/**
	 * Save any user provided properties to local environment for this instance of message flow execution
	 * Such as Incident Area
	 * @param outAssembly
	 */
	public abstract void saveUserProvidedProperties(MbMessageAssembly outAssembly);

}
