package org.docstudio.rejoice.common.client;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;


/**
 * 
 * @author Winston
 *
 */
public abstract class AbstractClient implements Client {
	
	public abstract Response sendRequest(Request request) throws Exception;
}
