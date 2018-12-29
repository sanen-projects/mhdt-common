package com.mhdt.degist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.mhdt.analyse.Validate;
import com.mhdt.toolkit.Assert;

/**
 * Encryption Tool
 * 
 * @author 懒得出风头
 *         <p>
 *         Date: 2016/3/25<br>
 *         Time: 11:08<br>
 *         Email: 282854237@qq.com
 */
public class DegistTool {
	private DegistTool() {

	}

	/**
	 * Default hexadecimal encoding
	 * 
	 * @see DegistTool#md5(String, String, Encode)
	 * @param psw
	 * @return
	 */
	public static String md5(String psw) {
		return md5(psw, "", Encode.HEX);
	}

	/**
	 * Default hexadecimal encoding
	 * 
	 * @see DegistTool#md5(String, String, Encode)
	 * @param psw
	 * @param salt
	 * @return
	 */
	public static String md5(String psw, String salt) {
		return md5(psw, salt, Encode.HEX);
	}

	/**
	 * @see DegistTool#md5(String, String, Encode)
	 * @param psw
	 * @param encode
	 * @return
	 */
	public static String md5(String psw, Encode encode) {
		return md5(psw, "", encode);
	}

	/**
	 * MD(Message Digest algorithm ) Usually we don't directly use the MD
	 * encrypted.Usually will have a byte array to the MD, BASE to make the
	 * encryption, get the corresponding string
	 * 
	 * @param psw
	 * @param salt
	 * @param encode
	 * @return
	 * @throws NullPointerException
	 */
	public static String md5(String psw, String salt, Encode encode) {

		Assert.notNull(psw, "md5 source is Null.");

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest((salt + psw).getBytes());

			switch (encode) {
			case BINARY:
				return DatatypeConverter.printBase64Binary(bytes);
			case HEX:
				return byteToHexStr(bytes);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static enum Encode {
		BINARY, HEX;
	}

	/**
	 * 获取一个文件的md5值(可处理大文件)
	 * @param file
	 * @return
	 */
	public static String md5(File file) {
		FileInputStream fileInputStream = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];

			int len;

			while ((len = fileInputStream.read(buffer)) != -1)
				digest.update(buffer, 0, len);

			return DatatypeConverter.printBase64Binary(digest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 16进制编码
	 * 
	 * @param message
	 * @return
	 */
	public static String hexEncode(String message) {
		return DatatypeConverter.printHexBinary(message.getBytes());
	}

	/**
	 * 16进制解码
	 * 
	 * @param message
	 * @return
	 */
	public static String hexDecode(String message) {
		return new String(DatatypeConverter.parseHexBinary(message));
	}

	/**
	 * SHA(Secure Hash Algorithm），Important tool in cryptography applications such
	 * as digital signature， Is widely used in electronic commerce information
	 * security field.Although, SHA and MD by collision were cracked, But SHA is
	 * still recognized security encryption algorithm, is safer than the MD
	 *
	 * @param psw - Encrypted string
	 * 
	 * @return String password
	 */
	public static String sha(String psw) {
		if (Validate.isNullOrEmpty(psw))
			throw new NullPointerException("md5 source is Null.");
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");
			byte[] byts = messageDigest.digest((psw).getBytes());
			return DatatypeConverter.printBase64Binary(byts);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/** Two Way Encipherment By-Encode */
	public static String encode(String str) {
		return DatatypeConverter.printBase64Binary(str.getBytes());
	}

	/** Two Way Encipherment By-Decode */
	public static String decode(String str) {
		return new String(DatatypeConverter.parseBase64Binary(str));
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 */
	public static String byteToHexStr(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte bt : bytes) {
			sb.append(byteToHexStr(bt));
		}
		return sb.toString();
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param bt
	 * @return
	 */
	public static String byteToHexStr(byte bt) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		return new String(new char[] { Digit[(bt >>> 4) & 0X0F], Digit[bt & 0X0F] });
	}

	/**
	 * 16进制字符串转为字节数组
	 * 
	 * @param hexString the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (hexCharToByte(hexChars[pos]) << 4 | hexCharToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte hexCharToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

}
