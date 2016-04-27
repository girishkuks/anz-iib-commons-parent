/**
 * 
 */
package com.anz.common.compute.impl;

import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 * 
 */
public class ComputeUtils {

	/**
	 * Converts JSON string to a message tree and assign to outMessage
	 * 
	 * @param outMessage
	 *            outMessage
	 * @param outputJson
	 *            JSON string
	 * @throws Exception
	 */
	public static void replaceJsonData(MbMessage outMessage, String outputJson)
			throws Exception {
		MbElement outMsgRootEl = outMessage.getRootElement();
		String parserName = MbJSON.PARSER_NAME;
		String messageType = "";
		String messageSet = "";
		String messageFormat = "";
		int encoding = 0;
		int ccsid = 0;
		int options = 0;
		outMsgRootEl.getFirstElementByPath("JSON").detach();
		outMsgRootEl.createElementAsLastChildFromBitstream(
				outputJson.getBytes("UTF-8"), parserName, messageType,
				messageSet, messageFormat, encoding, ccsid, options);

	}

	/**
	 * Converts a string to a message tree and assign to outMessage
	 * 
	 * @param outMessage
	 *            outMessage
	 * @param outputJson
	 *            a string
	 * @throws Exception
	 */
	public static void replaceStringAsBlob(MbMessage outMessage,
			String outputJson) throws Exception {
		MbElement outMsgRootEl = outMessage.getRootElement();
		String parserName = MbBLOB.PARSER_NAME;
		String messageType = "";
		String messageSet = "";
		String messageFormat = "";
		int encoding = 0;
		int ccsid = 0;
		int options = 0;

		MbElement blob = outMsgRootEl.getFirstElementByPath("/BLOB/BLOB");
		if (blob != null) {
			blob.detach();
		}
		blob = outMsgRootEl.getFirstElementByPath("BLOB");
		if (blob != null) {
			blob.detach();
		}
		outMsgRootEl.createElementAsLastChildFromBitstream(
				outputJson.getBytes(), parserName, messageType, messageSet,
				messageFormat, encoding, ccsid, options);

	}

	/**
	 * Get the JSON Data from the message
	 * 
	 * @param inMessage
	 *            Message
	 * @return JSON String
	 * @throws Exception
	 */
	public static String getJsonData(MbMessage inMessage) throws Exception {
		MbElement jsonElem = inMessage.getRootElement().getFirstElementByPath(
				"JSON/Data");
		if (jsonElem == null)
			return null;
		byte[] bs = jsonElem.toBitstream(null, null, null, 0, 1208, 0);
		if (bs == null)
			return null;
		String inputJson = new String(bs, "UTF-8");
		return inputJson;
	}

	/**
	 * Get the String from the BLOB in the message
	 * 
	 * @param inMessage
	 *            Message
	 * @return String
	 * @throws Exception
	 */
	public static String getStringFromBlob(MbMessage inMessage)
			throws Exception {
		MbElement blobElem = inMessage.getRootElement().getFirstElementByPath(
				"BLOB/BLOB");
		if (blobElem == null) {
			blobElem = inMessage.getRootElement().getFirstElementByPath("BLOB");
		}
		if (blobElem == null)
			return null;
		byte[] bs = (byte[]) blobElem.getValue();

		if (bs == null)
			return null;
		String inputStr = new String(bs);
		return inputStr;
	}

	/**
	 * Get the short exception text from the Exceptions Tree of the message
	 * @param outAssembly
	 * @return short exception text string delimited by :
	 * @throws MbException
	 */
	public static String getExceptionText(MbMessageAssembly outAssembly)
			throws MbException {
		// This is internal failure in transformation or logic
		MbElement exception = outAssembly.getExceptionList().getRootElement()
				.getFirstChild();
		String exceptionText = null;
		while (exception != null) {
			if (exceptionText == null) {
				exceptionText = "Error";
			}
			MbElement insert = exception.getFirstElementByPath("Insert");
			while (insert != null) {
				String text = (String) insert.getFirstElementByPath("Text")
						.getValue();
				if (text != null && !text.isEmpty()) {
					exceptionText = exceptionText + ": " + text;
				}
				insert = insert.getNextSibling();
			}
			if (exception.getNextSibling() != null) {
				exception = exception.getNextSibling();
			} else {
				exception = exception.getFirstChild();
			}
		}
		return exceptionText;
	}

	/**
	 * Set the reply status of the http response header.
	 * @param outAssembly
	 * @param statusCode e.g. 500
	 * @return the replystatuscode element Destination/HTTP/ReplyStatusCode
	 * @throws MbException
	 */
	public static MbElement setHttpReplyStatus(MbMessageAssembly outAssembly, String statusCode) throws MbException {
		// Create an HTTPResponseHeader with Error value to return for the
		// failure if it is missing
		MbElement localEnvironment = outAssembly.getLocalEnvironment().getRootElement();
		
		MbElement destination = localEnvironment.getFirstElementByPath("Destination");
		destination = destination == null ? 
				localEnvironment.createElementAsLastChild(MbElement.TYPE_NAME, "Destination",null) : 
					destination;
		
		MbElement http = destination.getFirstElementByPath("HTTP");
		http = http == null ? 
				destination.createElementAsLastChild(MbElement.TYPE_NAME, "HTTP", null) : 
					http;
		
		MbElement replyStatusCode = http.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ReplyStatusCode", statusCode);
		return replyStatusCode;
	}

}
