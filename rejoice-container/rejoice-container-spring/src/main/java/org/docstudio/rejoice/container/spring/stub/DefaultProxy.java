package org.docstudio.rejoice.container.spring.stub;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;
import org.docstudio.rejoice.common.client.DefaultClient;


/***
 * 
 * @author Winston
 *
 */
public class DefaultProxy implements Proxy<DefaultClient> {

	public DefaultClient getProxy() {
		return null;
	}
	
	public Object invoke(Request request) throws Exception{
		Response response = null;
		try {
			 response = getProxy().sendRequest(request);
		} catch (Exception e) {
			throw e;
		}
		if(response.isError()){
			throw new Exception(response.getException());
		}
		return response.getResponse();
	}

}
