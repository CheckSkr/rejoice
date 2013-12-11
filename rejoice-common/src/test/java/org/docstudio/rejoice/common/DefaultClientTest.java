package org.docstudio.rejoice.common;

import org.docstudio.rejoice.common.client.DefaultClient;
import org.junit.Test;

public class DefaultClientTest {
	
	@Test
	public void testClient() throws Exception{
		 int protocolType=1;
		 int requestId = 10;
		 int codecType=1;
		 byte[] interfaceName="org.docstudio.rejoice.common.services.HelloService".getBytes();
		 byte[] methodName="sayHello".getBytes();
		 int timeout=100;
			byte[][] argTypes = {"java.lang.String".getBytes()};
			Object[] requestObjects ={"link "};
		 Request req = new Request(protocolType,  requestId,  codecType,
					 interfaceName, methodName, timeout,
					 argTypes, requestObjects);
		 
		 byte[][] argTypes1 = { };
			Object[] requestObjects1 ={};
		 
		 Request req1 = new Request(protocolType,  11,  codecType,
				 interfaceName, methodName, timeout,
				 argTypes1, requestObjects1);
		 
		 
		 DefaultClient cleint = new DefaultClient("127.0.0.1", 12300, 100);
		 
		 
		 
		 try {
			cleint.sendRequest(req);
             //cleint.sendRequest(req1);
            
             
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
