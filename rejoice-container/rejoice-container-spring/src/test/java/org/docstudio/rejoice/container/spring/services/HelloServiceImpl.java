package org.docstudio.rejoice.container.spring.services;

public class HelloServiceImpl implements HelloService {

	public String sayHello(String hello) {
		System.out.println("server exe "+hello);
		return " server"+hello;
	}

	public void sayHello() {
		System.out.println(" no return server exe ");
	}

}
