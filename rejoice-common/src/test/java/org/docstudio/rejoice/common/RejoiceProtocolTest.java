package org.docstudio.rejoice.common;

import java.util.ArrayList;
import java.util.List;

import org.docstudio.rejoice.common.protocol.DefaultByteBuffer;
import org.docstudio.rejoice.common.protocol.RejoiceByteBuffer;
import org.docstudio.rejoice.common.protocol.RejoiceProtocol;
import org.junit.Test;

public class RejoiceProtocolTest {
	
	public void testRequest() throws Exception{
		RejoiceProtocol protocol = new RejoiceProtocol();
		 RejoiceByteBuffer bytebuffer = new DefaultByteBuffer();
		 int protocolType=1;
		 int requestId = 10;
		 int codecType=1;
		 String s = null;
		 byte[] interfaceName="org.docstudio.test.Demo".getBytes();
		 byte[] methodName="doInvoke".getBytes();
		 int timeout=100;
			byte[][] argTypes = {"java.lang.String".getBytes()};
			Object[] requestObjects ={"qq"};
		 Request req = new Request(protocolType,  requestId,  codecType,
					 interfaceName, methodName, timeout,
					 argTypes, requestObjects);
		 bytebuffer = protocol.encode(req, bytebuffer);
		 
		 bytebuffer.setReaderIndex(0);
		 
		 Request req1 = (Request) protocol.decode(bytebuffer, null, null);
		 
		 String interfaceNameStr = new String(req1.getInterfaceName());
		 System.out.println(interfaceNameStr);
		 String methodNameStr = new String(req1.getMethodName()); 
		 System.out.println(methodNameStr);


		 byte[] argType = req1.getArgTypes()[0];
		 
		 System.out.println(new String(argType));
		 
		 requestObjects = req1.getRequestObjects();
		 System.out.println("----------->"+requestObjects[0]);
		 
	}
	
	
	@Test
	public void testRespone() throws Exception{
		RejoiceProtocol protocol = new RejoiceProtocol();
		 RejoiceByteBuffer bytebuffer = new DefaultByteBuffer();
		 
		 Response  respone = new  Response(103, 1, 1); 
		 List<String> list = new ArrayList<String>();
		 list.add("sss");
		 list.add("ssddd");
		 
		 respone.setResponse(list);
		 
		 bytebuffer = protocol.encode(respone, bytebuffer);
		 bytebuffer.setReaderIndex(0);
		 
		 Response req1 = (Response) protocol.decode(bytebuffer, null, null);
		 
		 System.out.println(new String(req1.getResponseClassName()));
		 
		 System.out.println(req1.getResponse());
		 
	}

}
