/**
 * 
 */
package com.anz.common.compute.impl;

import com.anz.common.compute.TransformType;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public abstract class CommonErrorTransformCompute extends CommonJavaCompute {

	
	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJavaCompute#execute(com.ibm.broker.plugin.MbMessageAssembly, com.ibm.broker.plugin.MbMessageAssembly)
	 */
	@SuppressWarnings("unused")
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		try {
			MbMessage inMessage = inAssembly.getMessage();
			MbMessage outMessage = outAssembly.getMessage();
			
			/*
			 * Set the HTTPResponseHeader to Http 1.0/500 Internal Server Error
			 * Only for HTTP flows and when the error is not already set something else
			 * This block will be executed for internal errors in transformation such as NullPointerExceptions etc. 
			 */
			TransformType transformType = getTransformationType();
			if((transformType.equals(TransformType.HTTP_HHTP) || transformType.equals(TransformType.HTTP_MQ))
					&& outMessage.getRootElement().getFirstElementByPath("HTTPResponseHeader") == null) {

				ComputeUtils.setElementInTree("application/json", outMessage, "Properties", "ContentType");
				MbElement replyStatusCode = ComputeUtils.setHttpReplyStatus(outAssembly, "500");
				logger.info("Setting http reply status code: {} ", replyStatusCode);
			}
			
			
			String outputJson = executeTranformation(outAssembly);
			if (outputJson != null) {
				// Detach Original Exception Node from the response
				MbElement exception = outAssembly.getExceptionList().getRootElement().getFirstChild();
				if(exception != null)
					exception.detach();
				
				// Write this outputJson to outMessage
				ComputeUtils.replaceStringAsBlob(outMessage, outputJson);
			}
		} catch(Exception e) {
			logger.throwing(e);
			throw e;
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
			outputString = transformer.execute(outAssembly, appLogger, metaData);
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
