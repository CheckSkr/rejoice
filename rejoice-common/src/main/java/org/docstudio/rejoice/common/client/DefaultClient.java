package org.docstudio.rejoice.common.client;

import java.io.*;
import java.net.Socket;

import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;
import org.docstudio.rejoice.common.protocol.DefaultByteBuffer;
import org.docstudio.rejoice.common.protocol.RejoiceByteBuffer;
import org.docstudio.rejoice.common.protocol.RejoiceProtocol;

public class DefaultClient extends AbstractClient {

	private String serverIP = null;

	private int serverPort;

	private int timeOut = 0;

	private Socket socket = null;

	private DataOutputStream outputStream = null;

	private DataInputStream inputStream = null;

	public DefaultClient(String serverIP, int serverPort, int timeOut) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.timeOut = timeOut;
		try {
			socket = new Socket(serverIP, serverPort);
			outputStream = new DataOutputStream(socket.getOutputStream());
			inputStream = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response sendRequest(Request request) throws Exception {

		RejoiceProtocol protocol = new RejoiceProtocol();
		RejoiceByteBuffer bytebuffer = new DefaultByteBuffer();

		bytebuffer = protocol.encode(request, bytebuffer);

		bytebuffer.setReaderIndex(0);
		byte[] dst = new byte[bytebuffer.readableBytes()];
		bytebuffer.readBytes(dst);
		outputStream.writeInt(dst.length);
		outputStream.write(dst);
		outputStream.flush();

		RejoiceByteBuffer bytebuffer1 = new DefaultByteBuffer();
	    int length = inputStream.readInt();
	    bytebuffer1 = bytebuffer1.get(length);
	    byte[] revicedBytes = new byte[length];
	    inputStream.read(revicedBytes, 0, length);
        bytebuffer1.writeBytes(revicedBytes);
	    bytebuffer1.setReaderIndex(0);

		Response req1 = (Response) 	protocol.decode(bytebuffer1, null, null);
		System.out.println(req1.getResponse());
		return req1;
	}

	public String getServerIP() {
		return serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public int getConnectTimeout() {
		return timeOut;
	}

}
