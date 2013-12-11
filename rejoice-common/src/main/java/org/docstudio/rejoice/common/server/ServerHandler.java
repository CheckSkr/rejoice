package org.docstudio.rejoice.common.server;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;


/**
 * Server handler
 * @author Winston
 *
 */
public interface ServerHandler {
	
	public Response handleRequest(Request request);
	
	public Server getServer();

}
