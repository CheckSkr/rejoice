package org.docstudio.rejoice.common.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import org.docstudio.rejoice.common.executor.SocketBlockingQueue;
import org.docstudio.rejoice.common.executor.SocketExecutor;

public class DefaultServer implements Server {
	
	private ServerSocket server = null;
	
	private static int defaultThreadNum =10;
	
	
	private boolean stop = false;
	
	private ServerHandler serverhandler = null;
	
	private static final ConcurrentMap<String, Object> SERVICES = new ConcurrentHashMap<String, Object>();
	
	public DefaultServer(){
	}
	

	public void start(int port, ExecutorService threadPool) throws Exception {
	//	serverhandler =new DefaultServerHandler(this,threadPool);
		this.server = new ServerSocket(port);
		SocketExecutor.getExecutor(this).start(defaultThreadNum);
		while(!stop){
			Socket socket = server.accept();
			/***
			 * here we just offer socket to the blocking Queue.May be blocking here,we should tune
			 * the worker thread, if we get blocked here. Server handle ability will go down
			 */
			SocketBlockingQueue.getQueue(this).offerSocket(socket);
		}
		
	}

	public void registService(String serviceName, Object serviceInstacne) {
		if(SERVICES.containsKey(serviceName)){
			throw new IllegalStateException("can not regist service:"+serviceName+",cause: the service has already registed");
		}
		
		SERVICES.put(serviceName, serviceInstacne);
	}

	public void stop() throws Exception {
		stop = true;
	}


	public Object findService(String serviceName) {
		return SERVICES.get(serviceName);
	}

}
