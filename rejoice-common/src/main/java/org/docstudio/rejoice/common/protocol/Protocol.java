package org.docstudio.rejoice.common.protocol;

/**
 * The RPC Protocol
 * 
 * @author Winston
 *
 */
public interface Protocol {
	
	/**
	 *  the version of present protocol version
	 */
	public static final byte PROTOCOL_VERSION = (byte)1;
	
	public RejoiceByteBuffer encode(Object message,RejoiceByteBuffer bytebufferWrapper) throws Exception;
	
	public Object decode(RejoiceByteBuffer wrapper, Object errorObject,int...originPos) throws Exception;

}
