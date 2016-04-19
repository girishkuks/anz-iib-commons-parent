/**
 * 
 */
package com.anz.common.compute.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.TransformType;
import com.anz.common.ioc.spring.MbNodefactory;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class CommonAssemblyTransformCompute extends CommonJavaCompute {
	
	private static final Logger logger = LogManager.getLogger();

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
		
		//MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = outAssembly.getMessage();
		
		/* 
		 * Set the compute node in the node factory so that 
		 * Transform classes can use the jdbc type4 connection datasource later
		 * @see #IIBJdbc4DataSource
		 * @see #AnzSpringIoCFactory
		 */
		MbNodefactory.getInstance().setMbNode(this);
		
		
		//String inputJson = ComputeUtils.getJsonDataFromBlob(inMessage);
		String outputJson = executeJsonToJsonTranform(inAssembly);
		if (outputJson != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceJsonDataToBlob(outMessage, outputJson);
		}
		
	}
	
	/**
	 * Write business logic here if you merely need to transform the message
	 * from JSON to JSON
	 * 
	 * @param inAssembly
	 *            input assembly
	 * @return output JSON Data to be placed in the message
	 */
	@SuppressWarnings("unchecked")
	public String executeJsonToJsonTranform(MbMessageAssembly inAssembly) throws Exception {
		String outJson = null;
		// Remove the subflow name if any from the Transformer class before com.anz.**
		String transformerClassName = getName().substring(getName().indexOf("com"));
		logger.info("Creating instance of {}", transformerClassName);
		try {
			ITransformer<MbMessageAssembly, String> jsonTransformer = (ITransformer<MbMessageAssembly, String>)Class.forName(transformerClassName).newInstance();
			outJson = jsonTransformer.execute(inAssembly);
		} catch(Exception e) {
			logger.throwing(e);
			throw e;
		}
		return outJson;
	}

	

}
