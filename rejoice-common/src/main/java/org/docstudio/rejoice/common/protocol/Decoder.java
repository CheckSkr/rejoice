package org.docstudio.rejoice.common.protocol;

/**
 * decoder
 * @author Winston
 *
 */
public interface Decoder extends Codec {

	public Object decode(String clazzName,byte[] bytes) throws Exception;
	
}
