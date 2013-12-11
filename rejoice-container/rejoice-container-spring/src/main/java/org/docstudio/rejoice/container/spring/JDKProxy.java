package org.docstudio.rejoice.container.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;
import org.docstudio.rejoice.common.client.DefaultClient;

/**
 * @author Winston
 *
 * @param <T>
 */
public class JDKProxy<T> implements InvocationHandler{
	
	DefaultClient client = new DefaultClient("127.0.0.1", 12300, 100);
	
	private Class interfaceClass;
	
	public T createProxy(Class<T> interfaceClass){
		this.interfaceClass = interfaceClass;
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
	}

	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
		Response r = client.sendRequest(creatRequest( obj,  method,  args));
		return r.getResponse();
		 
	}
	
	private Request creatRequest(Object obj, Method method, Object[] args){
		 int protocolType=1;
		 int requestId = 10;
		 int codecType=1;
		 byte[] interfaceName=interfaceClass.getName().getBytes();
		 byte[] methodName=method.getName().getBytes();
		 int timeout=100;
		 
		 byte[][] argTypes  = null;
		 if(args.length > 0){
			 argTypes = new byte[args.length][];
			 int i=0;
		 for(Object arg: args){
			 argTypes[i++] = arg.getClass().getName().getBytes();
		 }
		 
		 }
			
		 Request req = new Request(protocolType,  requestId,  codecType,
					 interfaceName, methodName, timeout,
					 argTypes, args);
		 
		 return req;
		 
		 
	}
	
	
}
