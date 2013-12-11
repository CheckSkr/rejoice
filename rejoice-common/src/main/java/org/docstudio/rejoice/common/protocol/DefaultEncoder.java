package org.docstudio.rejoice.common.protocol;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Winston
 * 
 */
public class DefaultEncoder implements Encoder {
	
	private static final int TYPE = 1;

	public byte[] encode(Object object) throws Exception {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(byteArray);
		try {
			output.writeObject(object);
			output.flush();
		} finally {
			output.close();
		}
		return byteArray.toByteArray();
	}

	public int getCoderType() {
		return TYPE;
	}
}
