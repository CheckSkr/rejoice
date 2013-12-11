package org.docstudio.rejoice.common.spi;

import org.docstudio.rejoice.common.protocol.Decoder;
import org.docstudio.rejoice.common.protocol.Encoder;


/**
 * extension of Codec
 * @author Winston
 *
 */
public interface CodecExtension  extends Extension {
	
	public Encoder getEncoder();
	
	public Decoder getDecoder();

}
