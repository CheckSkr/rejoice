package org.docstudio.rejoice.common;

import java.util.concurrent.ConcurrentHashMap;

import org.docstudio.rejoice.common.protocol.Codec;
import org.docstudio.rejoice.common.protocol.Decoder;
import org.docstudio.rejoice.common.protocol.Encoder;
import org.docstudio.rejoice.common.protocol.DefaultDecoder;
import org.docstudio.rejoice.common.protocol.DefaultEncoder;

/**
 * the factory of Codec£º
 * 
 * @author Winston
 *
 */
public class CodecFactory {
	
	private static ConcurrentHashMap<String, Encoder> ENCODER_MAP = new ConcurrentHashMap<String, Encoder>();
	private static ConcurrentHashMap<String, Decoder> DECODER_MAP = new ConcurrentHashMap<String, Decoder>(); 
	private static DefaultEncoder javaEncoder = new DefaultEncoder();
	private static DefaultDecoder javaDecoder = new DefaultDecoder();
	static {
		ENCODER_MAP.put(String.valueOf(javaEncoder.getCoderType()), javaEncoder);
		DECODER_MAP.put(String.valueOf(javaDecoder.getCoderType()), javaDecoder);
	}
	
	
	public static Encoder getEncoder(int encoderType){
		if(!ENCODER_MAP.containsKey(String.valueOf(encoderType)))
			return javaEncoder;
		return ENCODER_MAP.get(String.valueOf(encoderType));
	}
	
	public static Decoder getDecoder(int decodeType){
		if(!(DECODER_MAP.contains( String.valueOf(decodeType))))
			return  javaDecoder;
		return DECODER_MAP.get(String.valueOf(decodeType));
	}
	
	public static void registEncoder(Encoder encoder){
		ENCODER_MAP.putIfAbsent(String.valueOf(encoder.getCoderType()), encoder);
	}
	
	public static void registDecoder(Decoder decoder){
		DECODER_MAP.putIfAbsent(String.valueOf(decoder.getCoderType()), decoder);
	}
	
	
	public static void registCoder(Codec coder){
		if(coder instanceof Encoder){
			registEncoder((Encoder) coder);
		}else if(coder instanceof Decoder){
			registDecoder((Decoder) coder);
		}else{
			//normally impossible get here
			throw new RuntimeException("can not regist coder,because it is not a instance of Encoder or Decoder");
		}
	}

}
