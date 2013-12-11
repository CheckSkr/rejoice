package org.docstudio.rejoice.common;

import org.docstudio.rejoice.common.server.DefaultServer;
import org.docstudio.rejoice.common.services.HelloService;
import org.docstudio.rejoice.common.services.HelloServiceImpl;
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
