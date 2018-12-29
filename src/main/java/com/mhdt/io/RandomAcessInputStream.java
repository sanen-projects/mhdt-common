package com.mhdt.io;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.mhdt.toolkit.ByteUtilly;

/**
 * 
 * @author 懒得出风头 <p>
 * Date: 2016-5-17<br>
 * Time： 17:51
 * 
 */
public class RandomAcessInputStream extends ByteArrayInputStream {

	public RandomAcessInputStream(byte[] buf) {
		super(buf);
	}

	public RandomAcessInputStream(byte[] buf, int offset, int length) {
		super(buf, offset, length);
	}

	public RandomAcessInputStream(File file) throws FileNotFoundException,
			IOException {
		this(new FileInputStream(file));
	}

	public RandomAcessInputStream(InputStream in) throws IOException {
		this(ByteUtilly.inputStreamToByte(in));
	}

	public void seek(int position) {
		if (position < 0 || position > this.count)
			throw new IndexOutOfBoundsException(position + ":" + this.count);
		this.pos = position;
	}

	public long getPosition() {
		return this.pos;
	}

	public final void close() {
		this.buf = null;
		this.count = 0;
		System.gc();
	}

	public int readInt() {

		int byte1 = read();
		int byte2 = read();
		int byte3 = read();
		int byte4 = read();

		return (byte1 + (byte2 << 8) + (byte3 << 16) + (byte4 << 24));

	}

	public int readUnsignedInt() throws EOFException {

		int ch1 = this.read();
		int ch2 = this.read();
		int ch3 = this.read();
		int ch4 = this.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));

	}

	public short readUnsignedShort() {
		int byte1 = read();
		int byte2 = read();

		return (short) ((byte2 << 8) + byte1);
	}

	public boolean readFull(byte[] buff) throws IOException {

		read(buff);
		return true;
	}

	public byte[] read(int counts) {
		
		byte[] bytes = null;
		
		if (counts > available())
			bytes = new byte[available()];
		else
			bytes = new byte[counts];

		for (int i = 0; i < bytes.length && available() > 0; i++) {
			bytes[i] = (byte) read();
		}
		return bytes;
	}

}
