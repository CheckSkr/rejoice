package org.docstudio.rejoice.common.executor;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.docstudio.rejoice.common.Event;
import org.docstudio.rejoice.common.server.Server;


/**
 * this blockingQueue holder all income socket;
 * and this is also a listener: while the queue size is too big,may cause
 * the server endpoint blocking, the new Thread need to start handle the socket
 * @author wj
 *
 */
public class SocketBlockingQueue {
	
	private static final int QUEUESIZE = 500;
	
	private  Server server = null;
	
	private static ConcurrentMap<Server, SocketBlockingQueue> CACHED_QUEUE = new ConcurrentHashMap<Server, SocketBlockingQueue>();
	
	private  SocketBlockingQueueEvent event = new SocketBlockingQueueEvent();
	
	public static  SocketBlockingQueue getQueue(Server server){
		if(CACHED_QUEUE.containsKey(server))
			return CACHED_QUEUE.get(server);
		SocketBlockingQueue q = new SocketBlockingQueue(server);
		CACHED_QUEUE.put(server, q);
		return q;
		
	}
	
	private SocketBlockingQueue(Server server){
		this.server = server;
	}
	
	
	private static BlockingQueue<Socket> socketQueue = new ArrayBlockingQueue<Socket>(QUEUESIZE);
	
	public  Socket takeSocket() throws InterruptedException{
		tigger(socketQueue.remainingCapacity());
		return socketQueue.take();
	}
	
	public  void offerSocket(Socket socket){
		tigger(socketQueue.remainingCapacity());
		 socketQueue.offer(socket);
	}
	
	public  void tigger(int num){
		float precent = (float)num/ QUEUESIZE;
		if(precent < 0.15){
			if(precent < 0.05)
				fireWarning(2);
			fireWarning(1);
		}else if(precent > 0.15){
			fireWarning(-1);
		}
	}
	
	public  void fireWarning(int level){
		if(level < 0){
			event.fireNegitiveEvent();
		}else{
			event.firePositiveEvent();
		}
	}
	
	private  class SocketBlockingQueueEvent implements Event{
		
		SocketBlockingQueueEvent(){
		}

		public void fireNegitiveEvent() {
			
		}

		public void firePositiveEvent() {
			SocketExecutor.getExecutor(server).addSocketWorker(2);
		}
		
	}
	
	
}
