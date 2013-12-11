package org.docstudio.rejoice.common.client;

import org.docstudio.rejoice.common.Endpoint;

/**
 * 
 * @author Winston
 *
 */
public interface Client extends Endpoint{
	
	/**
	 * server address
	 * 
	 * @return String
	 */
	public String getServerIP();

	/**
	 * server port
	 * 
	 * @return int
	 */
	public int getServerPort();

	/**
	 * connect timeout
	 * 
	 * @return
	 */
	public int getConnectTimeout();

}
