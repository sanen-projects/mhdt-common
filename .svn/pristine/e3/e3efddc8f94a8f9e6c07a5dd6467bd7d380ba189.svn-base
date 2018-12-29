package com.mhdt.test;

import java.io.ByteArrayOutputStream;

public class httpTool {

	// 16进制字符串转10进制转2进制函数
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	// 2进制转10进制转16进制字符串函数
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	// 将Unicode字符串转换成bool型数组
	public static boolean[] StrToBool(String input) {
		boolean[] output = Binstr16ToBool(BinstrToBinstr16(StrToBinstr(input)));
		return output;
	}

	// 将bool型数组转换成Unicode字符串
	public static String BoolToStr(boolean[] input) {
		String output = BinstrToStr(Binstr16ToBinstr(BoolToBinstr16(input)));
		return output;
	}

	// 将字符串转换成二进制字符串，以空格相隔
	private static String StrToBinstr(String str) {
		char[] strChar = str.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]) + " ";
		}
		return result;
	}

	// 将二进制字符串转换成Unicode字符串
	public static String BinstrToStr(String binStr) {
		String[] tempStr = StrToStrArray(binStr);
		char[] tempChar = new char[tempStr.length];
		for (int i = 0; i < tempStr.length; i++) {
			tempChar[i] = BinstrToChar(tempStr[i]);
		}
		return String.valueOf(tempChar);
	}

	// 将二进制字符串转换成Unicode字符串
	public static String BinstrToStrHelj(String binStr) {
		String[] tempStr = new String[binStr.length() / 6];
		for (int i = 0; i < (binStr.length() / 6); i++) {
			tempStr[i] = binStr.substring((i * 6), ((i + 1) * 6 - 1));
		}
		char[] tempChar = new char[tempStr.length];
		for (int i = 0; i < tempStr.length; i++) {
			tempChar[i] = BinstrToChar(tempStr[i]);
		}
		return String.valueOf(tempChar);
	}

	// 将二进制字符串格式化成全16位带空格的Binstr
	private static String BinstrToBinstr16(String input) {
		StringBuffer output = new StringBuffer();
		String[] tempStr = StrToStrArray(input);
		for (int i = 0; i < tempStr.length; i++) {
			for (int j = 16 - tempStr[i].length(); j > 0; j--)
				output.append('0');
			output.append(tempStr[i] + " ");
		}
		return output.toString();
	}

	// 将全16位带空格的Binstr转化成去0前缀的带空格Binstr
	private static String Binstr16ToBinstr(String input) {
		StringBuffer output = new StringBuffer();
		String[] tempStr = StrToStrArray(input);
		for (int i = 0; i < tempStr.length; i++) {
			for (int j = 0; j < 16; j++) {
				if (tempStr[i].charAt(j) == '1') {
					output.append(tempStr[i].substring(j) + " ");
					break;
				}
				if (j == 15 && tempStr[i].charAt(j) == '0')
					output.append("0" + " ");
			}
		}
		return output.toString();
	}

	// 二进制字串转化为boolean型数组 输入16位有空格的Binstr
	private static boolean[] Binstr16ToBool(String input) {
		String[] tempStr = StrToStrArray(input);
		boolean[] output = new boolean[tempStr.length * 16];
		for (int i = 0, j = 0; i < input.length(); i++, j++)
			if (input.charAt(i) == '1')
				output[j] = true;
			else if (input.charAt(i) == '0')
				output[j] = false;
			else
				j--;
		return output;
	}

	// boolean型数组转化为二进制字串 返回带0前缀16位有空格的Binstr
	private static String BoolToBinstr16(boolean[] input) {
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < input.length; i++) {
			if (input[i])
				output.append('1');
			else
				output.append('0');
			if ((i + 1) % 16 == 0)
				output.append(' ');
		}
		output.append(' ');
		return output.toString();
	}

	// 将二进制字符串转换为char
	private static char BinstrToChar(String binStr) {
		int[] temp = BinstrToIntArray(binStr);
		int sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum += temp[temp.length - 1 - i] << i;
		}
		return (char) sum;
	}

	// 将初始二进制字符串转换成字符串数组，以空格相隔
	private static String[] StrToStrArray(String str) {
		return str.split(" ");
	}

	// 将二进制字符串转换成int数组
	private static int[] BinstrToIntArray(String binStr) {
		char[] temp = binStr.toCharArray();
		int[] result = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = temp[i] - 48;
		}
		return result;
	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) throws Exception {
		bytes = bytes.toUpperCase();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray(), "GBK");
	}
}
