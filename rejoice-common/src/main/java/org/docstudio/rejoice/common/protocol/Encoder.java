package org.docstudio.rejoice.common.protocol;


/**
 * encoding
 * @author Winston
 */
public interface Encoder extends Codec {
	/**
	 * encode Object to byte array
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public byte[] encode(Object object) throws Exception;
}
