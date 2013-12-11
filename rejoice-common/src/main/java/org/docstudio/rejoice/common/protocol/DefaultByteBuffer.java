package org.docstudio.rejoice.common.protocol;

import java.nio.ByteBuffer;


/***
 * the default implementation  of RejoiceByteBuffer
 * use java nio ByteBuffer
 * @author Winston
 *
 */
public class DefaultByteBuffer implements RejoiceByteBuffer {
	
	private ByteBuffer byteBuffer = null;
	
	public DefaultByteBuffer(){
	}

	public RejoiceByteBuffer get(int capacity) {
		byteBuffer = ByteBuffer.allocate(capacity);
		return this;
	}

	public void writeByte(int index, byte data) {
		byteBuffer.put(index, data);

	}

	public void writeByte(byte data) {
		byteBuffer.put(data);
	}

	public byte readByte() {
		return byteBuffer.get();
	}

	public void writeInt(int data) {
		byteBuffer.putInt(data);
	}

	public void writeBytes(byte[] data) {
		byteBuffer.put(data);
	}

	public int readableBytes() {
		return byteBuffer.remaining();
	}

	public int readInt() {
		return byteBuffer.getInt();
	}

	public void readBytes(byte[] dst) {
		byteBuffer.get(dst);
	}

	public int readerIndex() {
		return byteBuffer.position();
	}

	public void setReaderIndex(int readerIndex) {
		byteBuffer.position(readerIndex);
	}

}
