package com.mhdt.toolkit;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mhdt.analyse.Validate;
import com.mhdt.io.RandomAcessInputStream;

/**
 * @author 懒得出风头
 *         <p>
 *         Date: 2016/3/25<br>
 *         Time: 15:16<br>
 *         Email: 282854237@qq.com
 */
public class StringUtility {
	private StringUtility() {

	}

	public static String format(String content) {

		Assert.notNullOrEmpty(content, "Content is null or empty");

		StringBuilder sb = new StringBuilder();

		char last = '\0';
		char current = '\0';
		int indent = 0;

		boolean isInQuotationMarks = false;

		for (int i = 0; i < content.length(); i++) {

			last = current;
			current = content.charAt(i);

			switch (current) {

			case '"':
				if (last != '\\')
					isInQuotationMarks = !isInQuotationMarks;

				sb.append(current);

				break;
			case '{':

			case '[':

				sb.append(current);

				if (!isInQuotationMarks) {
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
				}

				break;

			case '}':

			case ']':

				if (!isInQuotationMarks) {
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
				}

				sb.append(current);
				break;

			case ',':

				sb.append(current);

				if (last != '\\' && !isInQuotationMarks) {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}

				break;

			default:
				sb.append(current);
			}

		}

		return sb.toString();
	}

	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++)
			sb.append('\t');
	}

	/**
	 * 扩展index of支持【正则】索引，如果索引到【正则】匹配到的字符则返回下标，否则返回-1；
	 * 
	 * @param content
	 * @param pattern - 正则表达式，如：[0-9]
	 * @return
	 */
	public static int indexOf(String content, String pattern) {
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (Pattern.matches(pattern, c + "")) {
				return i;
			}
		}

		return -1;
	}

	/** get string by assign encode. */
	public final static String encode(String str, String encode) {
		if (Validate.isNullOrEmpty(str))
			return null;
		try {
			return URLEncoder.encode(str, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** get string by assign decode. */
	public final static String decode(String str, String encode) {
		if (Validate.isNullOrEmpty(str))
			return null;
		try {
			return URLDecoder.decode(str, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Remove head and foot blank.
	 */
	public static String removeBlankChar(String txt) {
		
		if (txt == null || txt.trim().length() < 1)
			return txt;
		
		int len = txt.length();
		int st = 0;
		
		while (st < len) {
			char val = txt.charAt(st);
			if (Validate.isBlank(val)) {
				st++;
				continue;
			}
			break;
		}
		
		while (st < len) {
			
			char val = txt.charAt(len - 1);
			
			if (Validate.isBlank(val)) {
				len--;
				continue;
			}
			
			break;
		}
		
		if (st > 0 || len < txt.length())
			txt = txt.substring(st, len);
		
		return txt;
	}

	private static final String[][] brackets = new String[][] { new String[] { "(", ")" }, new String[] { "（", "）" },
			new String[] { "【", "】" }, new String[] { "[", "]" }, new String[] { "<", ">" }, };

	/** 检查括号 */
	public static String checkBrackets(String str) {

		if (Validate.isNullOrEmpty(str))
			return str;

		// abbr:巢告字[2013]7号(巢告字[2013]7号
		for (String[] bracket : brackets) {
			if (str.indexOf(bracket[0]) != -1 && str.indexOf(bracket[1]) == -1) {
				str = str.substring(str.indexOf(bracket[0]) + 1);
			}
		}

		// abbr:巢告字[2013]7号)巢告字[2013]8号(
		for (String[] bracket : brackets) {
			int start = 0;
			int end = 0;
			if ((start = str.indexOf(bracket[0])) != -1 && (end = str.indexOf(bracket[1])) != -1) {
				if (start > end)
					str = str.substring(0, end);
			}
		}

		// 影音汇（影音汇（北京）科技发展有限公司
		for (String[] bracket : brackets) {
			int index = str.indexOf(bracket[0]);
			if (index > -1 && str.indexOf(bracket[1]) > str.indexOf(bracket[0], index + 1)
					&& str.indexOf(bracket[1], str.indexOf(bracket[1]) + 1) == -1) {
				str = str.substring(index + 1);
			}
		}

		return str;
	}

	/**
	 * From a string of Numbers
	 * 
	 * @param content
	 * @return String
	 */
	public static String extractNumbers(String content) {
		if (Validate.isNullOrEmpty(content))
			return content;
		content = formatArabNumber(content);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < content.length(); i++) {

			char c = content.charAt(i);

			if (Validate.isNumber(c))

				sb.append(c);
		}

		return sb.length() > 0 ? sb.toString() : null;
	}

	/**
	 * From a string of Numbers
	 * 
	 * @param content
	 * @return int
	 */
	public static Integer extractInteger(String content) {
		if (Validate.isNullOrEmpty(content))
			return null;

		String temp = extractNumbers(content);

		if (!Validate.isNullOrEmpty(temp) && temp.startsWith("0"))
			temp.replaceFirst("0", "");

		if (!Validate.isNullOrEmpty(temp))
			return Integer.parseInt(temp);

		return null;
	}

	private static Map<Character, Integer> units = new HashMap<Character, Integer>();

	private static Map<Character, Character> values = new HashMap<Character, Character>();

	static {
		units.put('零', 0);
		units.put('百', 100);
		units.put('佰', 100);
		units.put('十', 10);
		units.put('拾', 10);
		units.put('千', 1000);
		units.put('仟', 1000);
		units.put('万', 10000);
		units.put('萬', 10000);
		units.put('亿', 100000000);

		values.put('块', '.');
		values.put('两', '2');
		values.put('俩', '2');
		values.put('一', '1');
		values.put('二', '2');
		values.put('三', '3');
		values.put('四', '4');
		values.put('五', '5');
		values.put('六', '6');
		values.put('七', '7');
		values.put('八', '8');
		values.put('九', '9');
		values.put('壹', '1');
		values.put('贰', '2');
		values.put('叁', '3');
		values.put('肆', '4');
		values.put('伍', '5');
		values.put('陆', '6');
		values.put('柒', '7');
		values.put('捌', '8');
		values.put('玖', '9');

	}

	/**
	 * Extract a phone number from a string.
	 * 
	 * @param content
	 * @return
	 */
	public static String extractPhone(String content) {
		if (content.length() < 11)
			return null;
		Matcher matcher = Validate.pattern_PHONE.matcher(content);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * Extract a telephone number from a string.
	 * 
	 * @param content
	 * @return
	 */
	public static String extractTelephone(String content) {
		if (content.length() < 7)
			return null;
		Matcher matcher = Pattern.compile("((0\\d{2,3}|852|853)[-—]?[2-8]\\d{6,7})").matcher(content);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	private static Map<Character, Character> arab_format = new HashMap<Character, Character>() {
		private static final long serialVersionUID = 1L;

		{
			String[] array = new String[] { "０１２３４５６７８９", "º¹²³⁴⁵⁶⁷⁸⁹", "₀₁₂₃₄₅₆₇₈₉" };
			String s = "0123456789";
			for (String temp : array) {
				for (int i = 0; i < 10; i++) {
					put(temp.charAt(i), s.charAt(i));
				}
			}
		}
	};

	/**
	 * Unified format specific Numbers but not case sensitive 0-9
	 * 
	 * @param number
	 * @return String
	 */
	public static String formatArabNumber(String number) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			if (arab_format.containsKey(c)) {
				sb.append(arab_format.get(c));
				continue;
			}
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Parse number string, United into Arabic numerals
	 * 
	 * @param content
	 * @return
	 */
	public static String parseNumber(String content) {
		StringBuilder str = new StringBuilder();
		StringBuilder number = new StringBuilder();

		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (Validate.isNumber(c) || units.containsKey(c) || values.containsKey(c)
					|| (c == '.' && number.length() > 0 && contains(number.toString(), '.') == 0)) {
				if (number.length() == 0 && (c == '0' | c == '零')) {
					if (content.length() > (i + 2) && content.charAt(i + 1) == '.') {

					} else {
						str.append(0);
						continue;
					}

				}
				number.append(c);

			} else {
				if (number.length() > 0) {
					boolean appPoint = false;

					if (number.toString().endsWith(".")) {
						number.replace(number.length() - 1, number.length(), "");
						appPoint = true;
					}

					if (number.length() > 15) {
						str.append(number.toString());
					} else {
						double d = parseNum(number.toString());
						str.append((d + "").endsWith(".0") ? new BigDecimal(d) : new BigDecimal((long) d));
						if (appPoint)
							str.append(".");
					}

					number.setLength(0);
				}
				str.append(c);
			}
		}

		if (number.length() > 0) {
			boolean appPoint = false;
			if (number.toString().endsWith(".")) {
				number.replace(number.length() - 1, number.length(), "");
				appPoint = true;
			}

			if (number.length() > 15) {
				str.append(number.toString());
			} else {
				double d = parseNum(number.toString());
				str.append((d + "").endsWith(".0") ? new BigDecimal(d) : new BigDecimal((long) d));
				if (appPoint)
					str.append(".");
			}
		}

		return str.toString();
	}

	/**
	 * The number in the string.
	 * 
	 * @param The String of need to format.
	 * @return result.
	 */
	private static double parseNum(String number) {
		StringBuilder num = new StringBuilder();
		/** 保留常数值余下单位做换算 */
		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			if (c == '零') {
				num.append(0);
				continue;
			}
			if (c == '十' || c == '拾') {
				if (i == 0) {
					if (number.length() > 1
							&& (Validate.isNumber(number.charAt(i + 1)) || values.containsKey(number.charAt(i + 1)))) {
						num.append("1");
						continue;
					} else {
						num.append("10");
						continue;
					}
				}

				if (i == number.length() - 1
						|| (i > 0 && i < number.length() - 1 && !values.containsKey(number.charAt(i + 1)))) {
					num.append("0");
					continue;
				}

				if (i > 0 && i < number.length() - 1 && values.containsKey(number.charAt(i + 1))
						&& !values.containsKey(number.charAt(i - 1))) {
					num.append("1");
					continue;
				}

				continue;
			}

			if (values.containsKey(c)) {

				num.append(values.get(c));

			} else {

				num.append(c);
			}
		}

		number = num.toString();
		num = new StringBuilder();

		char lastUnit = '零';
		double result = 0;
		double max = 0;

		for (int i = 0; i < number.length(); i++) {

			char c = number.charAt(i);

			if (Validate.isNumber(c) || (c == '.' && i > 0 && num.length() > 0)) {/** 是数字 */
				if (i == number.length() - 1 && num.length() < 1
						&& units.get(lastUnit) / 10 >= 10) {/** 是最后一位数字 (三百二) 需要综合判断上一个单位 */
					num.append(units.get(lastUnit) / 10 * Integer.parseInt(c + ""));
				} else {
					num.append(c);
				}

				if (i == number.length() - 1) {
					String figure = num.toString();
					if (contains(figure, '.') > 1)
						break;
					if (figure.endsWith("."))
						figure = figure.substring(0, figure.length() - 1);
					result += Double.parseDouble(figure);
					break;
				}

				continue;

			} else if (units.containsKey(c)) {
				/** 是单位且是第一个 */
				if (i == 0) {
					result = units.get(c);
					if (c == '亿') {
						max = result;
						result = 0;
						num.setLength(0);
						continue;
					}
					lastUnit = c;
				}

				/** 是单位且不是最后一个 */
				if (i > 0 && i < number.length() - 1) {

					if (num.length() == 0)
						num.append(0);
					result = units.get(c) < units.get(lastUnit)
							? result + Double.parseDouble(num.toString()) * units.get(c)
							: (result + Double.parseDouble(num.toString())) * units.get(c);

					if (c == '亿') {
						max = result;
						result = 0;
						num.setLength(0);
						continue;
					}
					lastUnit = c;
					num.setLength(0);

				} else if (i == number.length() - 1) {/** 是单位且是最后一个 */
					if (units.get(c) > units.get(lastUnit)) {
						if (num.length() > 0)
							result += Double.parseDouble(num.toString());

						result = result == 0 ? 1 * units.get(c) : result * units.get(c);
					} else if (units.containsKey(c) && (units.get(c) != units.get(lastUnit))) {
						if (num.length() > 0)
							result = result + Double.parseDouble(num.toString()) * units.get(c);
					}
				}

			}

		}
		return max + result;
	}

	/**
	 * Does it include matching character in the string and returns a number
	 * 
	 * @param source
	 * @param c
	 * @return
	 */
	public static int contains(String source, char c) {
		return contains(source, String.valueOf(c));
	}

	/**
	 * Does it include matching character in the string and returns a number
	 * 
	 * @param source
	 * @param matchStr
	 * @return
	 */
	public static int contains(String source, String matchStr) {
		if (source == null || matchStr == null)
			throw new NullPointerException(" Source or matchStr is null.");
		int index = -1;
		int count = 0;
		while ((index = source.indexOf(matchStr, index + 1)) != -1) {
			count++;
		}
		return count;
	}

	private static Map<Character, Character> english_format = new HashMap<Character, Character>();

	static {

		String[][] arrays = new String[][] { { "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ" },
				{ "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ", "abcdefghijklmnopqrstuvwxyz" } };

		for (String[] array : arrays) {
			for (int i = 0; i < 26; i++) {
				char c1 = array[0].charAt(i);
				char c2 = array[1].charAt(i);
				english_format.put(c1, c2);
			}
		}

	}

	/**
	 * Unified format strings in English
	 * 
	 * @return String
	 */
	public static String formatEnglish(String content) {

		Assert.notNullOrEmpty(content, "Content is null");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);

			if (english_format.containsKey(c)) {
				sb.append(english_format.get(c));
				continue;
			}
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * InputStream to String.
	 * 
	 * @param in
	 * @return
	 */
	public static String toString(InputStream in) {
		return toString(in, "utf-8");
	}

	/**
	 * InputStream to String.
	 * 
	 * @param in
	 * @return
	 */
	public static String toString(InputStream in, String charsetName) {
		try {

			Assert.notNull(in, "The input stream is null");

			RandomAcessInputStream ran = new RandomAcessInputStream(in);
			byte[] bytes = new byte[ran.available()];
			ran.read(bytes);
			ran.close();
			return new String(bytes, charsetName);

		} catch (Exception e) {

			return null;
		}
	}

	public static String doubleToString(double d) {
		return new DecimalFormat("#.00").format(d);
	}

	public static String firstToUpperCase(String content) {
		Assert.notNullOrEmpty(content, "The string is empty ：" + content);

		StringBuilder sb = new StringBuilder(content);
		return sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase()).toString();
	}

	public static String firstToLowerCase(String content) {
		Assert.notNullOrEmpty(content, "The string is empty ：" + content);

		StringBuilder sb = new StringBuilder(content);
		return sb.replace(0, 1, String.valueOf(sb.charAt(0)).toLowerCase()).toString();
	}

	public static List<String> splite(String regex, String content) {
		String[] array = content.split(regex);
		List<String> list = new ArrayList<>();

		for (String temp : array)
			if (!Validate.isNullOrEmpty(temp))
				list.add(temp);

		return list;
	}

	/**
	 * Gets the stack information for the exception
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackMessage(Throwable e) {

		if (e == null)
			return "";

		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));

		return result.toString();
	}

	public static boolean hasLength(String text) {
		return text != null && text.length() > 0;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str) {
		return (hasLength(str) && containsText(str));
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}

		int len = str.length();
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param obj
	 * @param connector
	 * @return
	 */
	public static String join(Object obj, String connector) {

		Assert.notNull(obj, "objs is null");

		Object[] array = null;

		if (Validate.isArray(obj))
			array = (Object[]) obj;
		else if (Validate.isCollection(obj))
			array = ((Collection<?>) obj).toArray();
		else {
			String content = String.valueOf(obj);
			array = new Object[content.length()];
			for (int i = 0; i < content.length(); i++)
				array[i] = content.charAt(i);
		}

		StringBuffer sb = new StringBuffer();

		for (Object o : array) {
			Assert.notNull(o, "obj is null " + obj);
			String item = String.valueOf(o);

			if (Validate.isNullOrEmpty(item))
				continue;

			sb.append(item + connector);
		}

		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
