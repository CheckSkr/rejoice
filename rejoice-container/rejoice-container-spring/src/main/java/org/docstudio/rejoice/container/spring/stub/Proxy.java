package org.docstudio.rejoice.container.spring.stub;

import org.docstudio.rejoice.common.Request;

/**
 * @author Winston
 *
 */
public interface Proxy<T> {
	
	/**
	 * get local proxy
	 * @return
	 */
	public T getProxy();
	
	/***
	 * doInvoke
	 * @param request
	 * @return
	 */
	public Object invoke(Request request) throws Exception;
	
}
