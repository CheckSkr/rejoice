package org.docstudio.rejoice.common.protocol;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * The Default Decoder: Use Java ObjectInputStream
 * @author Winston
 *
 */
public class DefaultDecoder implements Decoder {
	
	private static final int TYEPE = 1;

	public Object decode(String clazzName, byte[] bytes) throws Exception {
		ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
		Object obj = null;
		try{
			obj = objectIn.readObject();
		}finally{
			objectIn.close();
		}
		return obj;
	}


	public int getCoderType() {
		return TYEPE;
	}

}
