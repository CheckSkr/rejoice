package org.docstudio.rejoice.common.server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;

public class DefaultServerHandler implements ServerHandler{
	
	private ExecutorService threadPool = null;
	private Server server = null;
	
	public DefaultServerHandler(Server server,ExecutorService threadPool){
		this.threadPool = threadPool;
		this.server = server;
	}
	

	public Response handleRequest(Request request) {
		return null;
	}
	
	
	public static class DefaultHandler implements Runnable{
		private Socket scoket = null;
		public  DefaultHandler(Socket scoket){
			this.scoket = scoket;
		}

		public void run() {
			
		}
		
	}


	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
