package org.docstudio.rejoice.common.executor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;
import org.docstudio.rejoice.common.exception.ServiceNotFound;
import org.docstudio.rejoice.common.protocol.DefaultByteBuffer;
import org.docstudio.rejoice.common.protocol.RejoiceByteBuffer;
import org.docstudio.rejoice.common.protocol.RejoiceProtocol;
import org.docstudio.rejoice.common.server.Server;
import org.docstudio.rejoice.common.server.ServerHandler;

public class SocketWorker implements Runnable, ServerHandler {

	private Server server = null;
	
	private RejoiceProtocol protocol = new RejoiceProtocol();;

	public SocketWorker(Server server) {
		this.server = server;
	}

	public void run() {
		Socket socket = null;
		try {
			socket = SocketBlockingQueue.getQueue(server).takeSocket();
		} catch (InterruptedException e1) {
		}
		boolean running = true;
		while (running)
			try {
				DataInputStream inStream = new DataInputStream(
						socket.getInputStream());
				DataOutputStream outStream = new DataOutputStream(
						socket.getOutputStream());

				RejoiceByteBuffer bytebuffer = new DefaultByteBuffer();

				int length = inStream.readInt();
				bytebuffer = bytebuffer.get(length);

				byte[] revicedBytes = new byte[length];

				inStream.read(revicedBytes, 0, length);

				bytebuffer.writeBytes(revicedBytes);

				bytebuffer.setReaderIndex(0);

				Request req1 = null;
				try {
					req1 = (Request) protocol.decode(bytebuffer, null, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String interfaceNameStr = new String(req1.getInterfaceName());
				System.out.println(interfaceNameStr);
				String methodNameStr = new String(req1.getMethodName());
				System.out.println(methodNameStr);

				Response r = handleRequest(req1);
				
				RejoiceByteBuffer retBytebuffer = new DefaultByteBuffer();
				protocol.encode(r, retBytebuffer);
				retBytebuffer.setReaderIndex(0);
				byte[] retBytes = new byte[retBytebuffer.readableBytes()];
				retBytebuffer.readBytes(retBytes);
				outStream.writeInt(retBytes.length);
				outStream.write(retBytes);
				outStream.flush();

			} catch (IOException e) {
				running = false;
			} catch (Exception e) {
				running = false;
			}
	}

	public Response handleRequest(Request request) {
		Response r = new Response(request.getRequestId(),
				request.getCodecType(), request.getProtocolType());
		String interfaceName = new String(request.getInterfaceName());
		String methodName = new String(request.getMethodName());
		Object[] args = request.getRequestObjects();
		byte[][] argsTypes = request.getArgTypes();

		List<String> argsTypeList = new ArrayList<String>();

		for (byte[] argsType : argsTypes) {
			argsTypeList.add(new String(argsType));
		}
		Class[] typeClassArr = new Class[argsTypeList.size()];
		int i = 0;
		for (String argsTypeStr : argsTypeList) {
			try {
				typeClassArr[i++] = Class.forName(argsTypeStr);
			} catch (ClassNotFoundException e) {
				r.setException(new ServiceNotFound("can not find service:"
						+ interfaceName
						+ ",please confirm the service is registed or not"));
				return r;
			}
		}

		Object serviceInstance = getServer().findService(interfaceName);
		if (serviceInstance == null) {
			r.setException(new ServiceNotFound("can not find service:"
					+ interfaceName
					+ ",please confirm the service is registed or not"));
			return r;
		}
		try {
			Method method = null;
			if (typeClassArr.length == 0)
				method = serviceInstance.getClass().getMethod(methodName,
						new Class<?>[] {});
			else
				method = serviceInstance.getClass().getMethod(methodName,
						typeClassArr);
			Object obj = method.invoke(serviceInstance, args);
			r.setResponse(obj);
		} catch (NoSuchMethodException e) {
			r.setException(new ServiceNotFound("can not find method:"
					+ methodName + "in " + interfaceName
					+ ",please ensure you got the same interface"));
		} catch (SecurityException e) {
			r.setException(e);
		} catch (IllegalArgumentException e) {
			r.setException(e);
		} catch (IllegalAccessException e) {
			r.setException(e);
		} catch (InvocationTargetException e) {
			r.setException(e);
		}
		return r;
	}

	public Server getServer() {
		return server;
	}

}
