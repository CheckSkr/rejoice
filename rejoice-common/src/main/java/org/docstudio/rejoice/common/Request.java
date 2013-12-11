package org.docstudio.rejoice.common;

import java.util.concurrent.atomic.AtomicInteger;

public class Request {
	/***invoke Id add one **/
	private static final AtomicInteger INVOKE_ID = new AtomicInteger(0);
	
	/**protocol Type*/
	private int protocolType = 0;
	
	/**request Id*/
	private int requestId;
	
	/**codec type*/
	private int codecType;
	
	/**interface**/
	private byte[] interfaceName;
	
	/***method Name*/
	private byte[] methodName;
	
	/***request timeout*/
	private int timeout;
	
	/***request method args**/
	private byte[][] argTypes;

	/***request onjects*/
	private Object[] requestObjects = null;
	
	/***message  */
	private Object message = null;
	
	public Request(int protocolType, int codecType, byte[] interfaceName,
			byte[] methodName, int timeout, byte[][] argTypes,
			Object[] requestObjects, Object message) {
		this(protocolType, INVOKE_ID.incrementAndGet(),codecType,interfaceName,
				methodName, timeout, argTypes,
				requestObjects, message);
	}

	public Request(int protocolType, int requestId, int codecType,
			byte[] interfaceName, byte[] methodName, int timeout,
			byte[][] argTypes, Object[] requestObjects, Object message) {
		this.protocolType = protocolType;
		this.requestId = requestId;
		this.codecType = codecType;
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.timeout = timeout;
		this.argTypes = argTypes;
		this.requestObjects = requestObjects;
		this.message = message;
	}
	
	public Request(int protocolType, int requestId, int codecType,
			byte[] interfaceName, byte[] methodName, int timeout,
			byte[][] argTypes, Object[] requestObjects) {
		this.protocolType = protocolType;
		this.requestId = requestId;
		this.codecType = codecType;
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.timeout = timeout;
		this.argTypes = argTypes;
		this.requestObjects = requestObjects;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public  int getRequestId() {
		return requestId;
	}

	public static AtomicInteger getInvokeId() {
		return INVOKE_ID;
	}

	public int getCodecType() {
		return codecType;
	}

	public byte[] getInterfaceName() {
		return interfaceName;
	}

	public byte[] getMethodName() {
		return methodName;
	}

	public int getTimeout() {
		return timeout;
	}

	public byte[][] getArgTypes() {
		return argTypes;
	}

	public Object[] getRequestObjects() {
		return requestObjects;
	}

	public Object getMessage() {
		return message;
	}
	
}
