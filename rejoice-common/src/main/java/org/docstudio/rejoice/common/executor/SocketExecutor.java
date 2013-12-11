package org.docstudio.rejoice.common.executor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.docstudio.rejoice.common.server.Server;

public class SocketExecutor {
	
	private Server server = null;
	
	private static ConcurrentMap<Server, SocketExecutor> CACHED_EXECUTOR = new ConcurrentHashMap<Server, SocketExecutor>();
	
	private SocketExecutor(Server server){
		this.server = server;
	}
	
	public void start(int threadNum){
		for (int i = 0; i < threadNum; i++) {
			executorService.execute(new SocketWorker(server));
		}
		
	}
	
	
	public static SocketExecutor getExecutor(Server server){
		if(CACHED_EXECUTOR.containsKey(server))
			return CACHED_EXECUTOR.get(server);
		SocketExecutor exe = new SocketExecutor(server);
		CACHED_EXECUTOR.put(server, exe);
		return exe;
	}
	
	private static NamedThreadFactory threadFactory = new NamedThreadFactory("--socket worker--");
	
	private ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
	
	
	public void addSocketWorker(int num){
		for (int i=0; i< num ; i++) {
			executorService.execute(new SocketWorker(server));
		}
		
	}
	
	
	

}
