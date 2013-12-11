package org.docstudio.rejoice.container.spring;

import org.docstudio.rejoice.container.spring.services.HelloService;


public class JDKProxyTest {
	
	
	public static void main(String[] agrs){
		JDKProxy<HelloService> proxy  = new JDKProxy(); 
		HelloService helloService = proxy.createProxy(HelloService.class);
		String ss = helloService.sayHello("link");
		
		System.out.println("----------->"+ss);
	}
	
	
	
	public static interface Test{
		public String sayHello();
	}

	public static class TestImpl implements Test{

		public String sayHello() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
}
