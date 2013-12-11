package org.docstudio.rejoice.container.spring;

import org.docstudio.rejoice.common.server.DefaultServer;
import org.docstudio.rejoice.container.spring.services.HelloService;
import org.docstudio.rejoice.container.spring.services.HelloServiceImpl;
import org.junit.Test;

public class DefaultServerTest {

	@Test
	public void test(){
		DefaultServer server = new DefaultServer();
		HelloService service = new HelloServiceImpl();
		server.registService(HelloService.class.getName(), service);
		try {
			server.start(12300, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("-----------");
	}
}
