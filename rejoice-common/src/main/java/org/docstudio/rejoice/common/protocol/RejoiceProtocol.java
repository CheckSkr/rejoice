package org.docstudio.rejoice.common.protocol;

import java.util.ArrayList;

import org.docstudio.rejoice.common.CodecFactory;
import org.docstudio.rejoice.common.Request;
import org.docstudio.rejoice.common.Response;

/**
 * Rejoice Protocol implementation: Request Encode prorocol:
 * _________________________________________________________________________
 * |VERSION | TYPE | RequestProtocol | VERSION | TYPE | CODECTYPE | RequestID |
 * |_________________________________________________________________________
 * Protocol Header(Request): VERSION(1B): Protocol Version TYPE(1B): Protocol
 * Type Request Protocol VERSION(1B): TYPE(1B): request/response CODECTYPE(1B):
 * serialize/deserialize type KEEPED(1B): KEEPED(1B): KEEPED(1B): ID(4B):
 * request id TIMEOUT(4B): request timeout TARGETINSTANCELEN(4B): target service
 * name length METHODNAMELEN(4B): method name length ARGSCOUNT(4B): method args
 * count ARG1TYPELEN(4B): method arg1 type len ARG2TYPELEN(4B): method arg2 type
 * len ... ARG1LEN(4B): method arg1 len ARG2LEN(4B): method arg2 len ...
 * TARGETINSTANCENAME METHODNAME ARG1TYPENAME ARG2TYPENAME ... ARG1 ARG2 ...
 * 
 * @author Winston
 * 
 */
public class RejoiceProtocol implements Protocol {
	/**
	 * procotol type
	 */
	private static final int PROTOCOL_TYPE = 1;

	/**
	 * protocol version
	 */
	private static final byte VERSION = (byte) 1;
	/**
	 * the length of Request header is because of the:
	 */
	private static final int REQUEST_HEADER_LEN = 5 + 5 * 4;

	/**
	 * the length of Reponse header is because of the:
	 */
	private static final int RESPONE_HEADER_LEN = 5 + 3*4;

	private static final byte REQUEST = (byte) 0;

	private static final byte RESPONE = (byte) 1;

	public RejoiceByteBuffer encode(Object message, RejoiceByteBuffer bytebuffer)
			throws Exception {
		if (!(message instanceof Request) && !(message instanceof Response)) {
			throw new RuntimeException(
					"can not encode message because of only Request or Respone can be Encoded");
		}
		/**
		 * handle message encode
		 */
		if (message instanceof Request) {
			Request tempRequest = (Request) message;
			int requestArgsTypeLen = 0;
			int requestArgsLen = 0;
			ArrayList<byte[]> requestArgsTypeArr = new ArrayList<byte[]>();
			ArrayList<byte[]> requestArgsArr = new ArrayList<byte[]>();
			byte[][] argTypes = tempRequest.getArgTypes();
			for (byte[] argType : argTypes) {
				requestArgsTypeArr.add(argType);
				requestArgsTypeLen += argType.length;
			}

			Object[] requestObjects = tempRequest.getRequestObjects();
			int codecType = tempRequest.getCodecType();
			if (requestObjects != null)
				for (Object requestObject : requestObjects) {
					byte[] requestObjectBytes = CodecFactory.getEncoder(codecType).encode(requestObject);
					requestArgsArr.add(requestObjectBytes);
					requestArgsLen += requestObjectBytes.length;
				}

			byte[] interfaceName = tempRequest.getInterfaceName();
			byte[] methodName = tempRequest.getMethodName();
			int requestId = tempRequest.getRequestId();
			int timeout = tempRequest.getTimeout();
			int capacity = REQUEST_HEADER_LEN + requestArgsTypeArr.size() * 4
					+ requestArgsArr.size() * 4 + interfaceName.length
					+ methodName.length + requestArgsTypeLen + requestArgsLen;
			bytebuffer = bytebuffer.get(capacity);
			bytebuffer.writeByte(PROTOCOL_VERSION);// Protocol version
			bytebuffer.writeByte((byte) PROTOCOL_TYPE);// Protocol Type:Default
														// is RejoiceProtocol
			bytebuffer.writeByte(VERSION);// Current Version
			bytebuffer.writeByte(REQUEST);// this is Request Object
			bytebuffer.writeByte((byte) codecType);// CodeC Type
			bytebuffer.writeInt(requestId);// requestId
			bytebuffer.writeInt(timeout);// timeout
			bytebuffer.writeInt(interfaceName.length);// intefaceName length
			bytebuffer.writeInt(methodName.length);// method name length
			bytebuffer.writeInt(requestArgsArr.size());// request args length

			for (byte[] requestArgType : requestArgsTypeArr) {
				bytebuffer.writeInt(requestArgType.length);
			}

			for (byte[] requestArg : requestArgsArr) {
				bytebuffer.writeInt(requestArg.length);
			}

			bytebuffer.writeBytes(interfaceName);
			bytebuffer.writeBytes(methodName);

			for (byte[] requestArgType : requestArgsTypeArr) {
				bytebuffer.writeBytes(requestArgType);
			}

			for (byte[] requestArg : requestArgsArr) {
				bytebuffer.writeBytes(requestArg);
			}
			return bytebuffer;
		} else {
			Response tempRespone = (Response) message;

			byte[] body = new byte[0];
			byte[] className = new byte[0];

			if (tempRespone.getResponse() != null) {
				className = tempRespone.getResponse().getClass().getName()
						.getBytes();
				body = CodecFactory.getEncoder(tempRespone.getCodecType())
						.encode(tempRespone.getResponse());
			}
			if (tempRespone.isError()) {
				className = tempRespone.getException().getClass().getName()
						.getBytes();
				body = CodecFactory.getEncoder(tempRespone.getCodecType())
						.encode(tempRespone.getException());
			}
			int requestId = tempRespone.getRequestId();
			int codecType = tempRespone.getCodecType();
			int capacity = RESPONE_HEADER_LEN +className.length +body.length;
			bytebuffer = bytebuffer.get(capacity);
			bytebuffer.writeByte(PROTOCOL_VERSION);// Protocol version
			bytebuffer.writeByte((byte) PROTOCOL_TYPE);// Protocol Type:Default
														// is RejoiceProtocol
			bytebuffer.writeByte(VERSION);// Current Version
			bytebuffer.writeByte(RESPONE);// this is Respone Object
			bytebuffer.writeByte((byte) codecType);// CodeC Type
			bytebuffer.writeInt(requestId);
			bytebuffer.writeInt(className.length);
			bytebuffer.writeInt(body.length);
			bytebuffer.writeBytes(className);
			bytebuffer.writeBytes(body);
			return bytebuffer;
		}

	}

	public Object decode(RejoiceByteBuffer bytebuffer, Object errorObject,
			int... originPosArray) throws Exception {
		final int originPos;
		if(originPosArray!=null && originPosArray.length == 1){
			originPos = originPosArray[0];
		}
		else{
			originPos = bytebuffer.readerIndex();
		}
		if(bytebuffer.readableBytes() < 2){
			bytebuffer.setReaderIndex(originPos);
        	return errorObject;
        }
		/***
		 * bytebuffer.writeByte(PROTOCOL_VERSION);// Protocol version
			bytebuffer.writeByte((byte) PROTOCOL_TYPE);// Protocol Type:Default
														// is RejoiceProtocol
			bytebuffer.writeByte(VERSION);// Current Version
			bytebuffer.writeByte(REQUEST);// this is Request Object
		 */
        byte pversion = bytebuffer.readByte();
        if(pversion == (byte)PROTOCOL_VERSION){
        	byte ptype = bytebuffer.readByte();
        	if(ptype != PROTOCOL_TYPE){
        		throw new UnsupportedOperationException("protocol type : "+ptype+" is not supported!");
        	}
        	byte version = bytebuffer.readByte();
        	if(version != VERSION){
        		throw new UnsupportedOperationException("protocol type : "+VERSION+" is not supported!");
        	}
        	byte requestOrRespone = bytebuffer.readByte();
        	if(requestOrRespone == REQUEST){
        		if(bytebuffer.readableBytes() < REQUEST_HEADER_LEN -2){
        			bytebuffer.setReaderIndex(originPos);
        			return errorObject;
        		}
        		int codecType = bytebuffer.readByte();
        		int requestId = bytebuffer.readInt();
        		int timeout = bytebuffer.readInt();
        		int targetInstanceLen = bytebuffer.readInt();
        		int methodNameLen = bytebuffer.readInt();
        		int argsCount = bytebuffer.readInt();
        		int argInfosLen = argsCount * 4 * 2;
        		int expectedLenInfoLen = argInfosLen + targetInstanceLen + methodNameLen;
        		if(bytebuffer.readableBytes() < expectedLenInfoLen){
        			bytebuffer.setReaderIndex(originPos);
        			return errorObject;
        		}
        		int expectedLen = 0;
        		int[] argsTypeLen = new int[argsCount];
        		for (int i = 0; i < argsCount; i++) {
					argsTypeLen[i] = bytebuffer.readInt();
					expectedLen += argsTypeLen[i]; 
				}
        		int[] argsLen = new int[argsCount];
        		for (int i = 0; i < argsCount; i++) {
        			argsLen[i] = bytebuffer.readInt();
        			expectedLen += argsLen[i];
				}
        		byte[] interfaceName = new byte[targetInstanceLen];
        		bytebuffer.readBytes(interfaceName);
        		byte[] methodName= new byte[methodNameLen];
        		bytebuffer.readBytes(methodName);
        		if(bytebuffer.readableBytes() < expectedLen){
        			bytebuffer.setReaderIndex(originPos);
        			return errorObject;
        		}
        		byte[][] argTypes = new byte[argsCount][];
        		for (int i = 0; i < argsCount; i++) {
					byte[] argTypeByte = new byte[argsTypeLen[i]];
					bytebuffer.readBytes(argTypeByte);
					argTypes[i] = argTypeByte;
				}
        		Object[] args = new Object[argsCount];
        		for (int i = 0; i < argsCount; i++) {
					byte[] argByte = new byte[argsLen[i]];
					bytebuffer.readBytes(argByte);
					args[i] = CodecFactory.getDecoder(codecType).decode(new String(argTypes[i]), argByte);
				}
        		Request request = new Request(PROTOCOL_TYPE, requestId,  codecType,interfaceName,  methodName,  timeout, argTypes, args);
        		return request;
        	}
        	else if(requestOrRespone == RESPONE){
        		if(bytebuffer.readableBytes() < RESPONE_HEADER_LEN -2){
        			bytebuffer.setReaderIndex(originPos);
        			return errorObject;
        		}
        		int codecType = bytebuffer.readByte();
            	int requestId = bytebuffer.readInt();
            	int classNameLen = bytebuffer.readInt();
            	int bodyLen = bytebuffer.readInt();
            	if(bytebuffer.readableBytes() < classNameLen + bodyLen){
            		bytebuffer.setReaderIndex(originPos);
            		return errorObject;
            	}

            	byte[] classNameBytes = new byte[classNameLen];
            	byte[] bodyBytes = new byte[bodyLen];
            	bytebuffer.readBytes(classNameBytes);
            	bytebuffer.readBytes(bodyBytes);
            	Response responseWrapper = new Response(requestId,codecType,PROTOCOL_TYPE);
            	Object obj = CodecFactory.getDecoder(codecType).decode(new String(classNameBytes),bodyBytes);
            	responseWrapper.setResponse(obj);
            	responseWrapper.setResponseClassName(classNameBytes);
            	return responseWrapper;
        	}
        	else{
        		throw new UnsupportedOperationException("protocol type : "+requestOrRespone+" is not supported!");
        	}
        }
        else{
        	throw new UnsupportedOperationException("protocol version :"+pversion+" is not supported!");
        }
	}

}
