/**
 * 
 */
package com.anz.common.transform;


public interface ITransformer<I,O> {
	O execute(I input) throws Exception;
}
