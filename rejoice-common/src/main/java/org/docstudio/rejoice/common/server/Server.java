package org.docstudio.rejoice.common.server;

import java.util.concurrent.ExecutorService;

import org.docstudio.rejoice.common.Endpoint;


/**
 * the server interface:
 * this Server has many implementions like:Mina or Netty
 * the default server is java nio SocketServer
 * @author Winston
 *
 */
public interface Server  extends Endpoint{
	
	/**
	 * start server
	 * @param port
	 * @param threadPool
	 * @throws Exception
	 */
	public void start(int port,ExecutorService threadPool) throws Exception;
	
	/**
	 * regist  service
	 * @param serviceName
	 * @param serviceInstacne
	 */
	public void registService(String serviceName,Object serviceInstacne);
	
	public Object findService(String serviceName);
	
	public void stop() throws Exception;

}
