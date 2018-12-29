package com.mhdt.toolkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mhdt.analyse.Validate;

/**
 * @author 懒得出风头
 *         <p>
 *         Date: 2016-3-16<br>
 *         Time: 15:16<br>
 *         Email: 282854237@qq.com
 */
public class DateUtility {
	private DateUtility() {
	}

	private static Map<Character, Character> wordFigure = new HashMap<Character, Character>();

	static {
		wordFigure.put('Ο', '0');
		wordFigure.put('○', '0');
		wordFigure.put('Ｏ', '0');
		wordFigure.put('O', '0');
		wordFigure.put('０', '0');
		wordFigure.put('零', '0');
		wordFigure.put('〇', '0');
		wordFigure.put('�', '0');
		wordFigure.put('一', '1');
		wordFigure.put('壹', '1');
		wordFigure.put('二', '2');
		wordFigure.put('２', '2');
		wordFigure.put('贰', '2');
		wordFigure.put('三', '3');
		wordFigure.put('叁', '3');
		wordFigure.put('四', '4');
		wordFigure.put('４', '4');
		wordFigure.put('肆', '4');
		wordFigure.put('五', '5');
		wordFigure.put('伍', '5');
		wordFigure.put('六', '6');
		wordFigure.put('６', '6');
		wordFigure.put('陆', '6');
		wordFigure.put('七', '7');
		wordFigure.put('柒', '7');
		wordFigure.put('八', '8');
		wordFigure.put('捌', '8');
		wordFigure.put('九', '9');
		wordFigure.put('玖', '9');
		wordFigure.put('十', '0');
		wordFigure.put('拾', '0');
		wordFigure.put('廿', '2');

	}

	/**
	 * format your date by chinese to number<br>
	 * case: 二零一五年十月十三 to 2015年10月13
	 */
	public static String format(String date) {
		if (Validate.isNullOrEmpty(date))
			return date;
		date = date.replace("公元", "");
		char[] chardate = date.toCharArray();
		StringBuffer time = new StringBuffer();
		for (int i = 0; i < chardate.length; i++) {

			if (wordFigure.containsKey(chardate[i])) {
				if (chardate[i] == '十' || chardate[i] == '拾') {
					if (i > 0 && wordFigure.containsKey(chardate[i - 1]) && i < chardate.length - 1
							&& !wordFigure.containsKey(chardate[i + 1])) {
						time.append("0");
					} else if (i > 0 && wordFigure.containsKey(chardate[i - 1])) {

					} else if (i < chardate.length - 1 && !wordFigure.containsKey(chardate[i + 1])) {// 后面没有日期
						time.append("10");
					} else {
						time.append("1");
					}
					continue;
				}
				if (chardate[i] == '廿') {
					if (i == chardate.length - 1) {
						time.append("20");
					} else if (i < chardate.length - 1 && !wordFigure.containsKey(chardate[i + 1])) {
						time.append("20");
					} else {
						time.append("2");
					}
					continue;
				}

				time.append(wordFigure.get(chardate[i]));

			} else {

				time.append(chardate[i]);
			}

		}
		String result = time.toString();

		Matcher matcher = Pattern.compile("(?<![0-9]{2})([01][1-9]年)(?!(来|后))").matcher(result);
		while (matcher.find()) {
			String source = matcher.group();
			result = result.replaceFirst(source, "20" + source);
		}

		matcher = Pattern.compile("(?<![0-9]{2})([789][1-9]年)").matcher(result);
		while (matcher.find()) {
			String source = matcher.group();
			result = result.replaceFirst(source, "19" + source);
		}

		return result;
	}


	private static final List<String> yyyyMMdd = new ArrayList<String>() {
		private static final long serialVersionUID = -750877648575959375L;

		{
			add("yyyy-MM-dd");
			add("yyyy-MM-dd HH:mm:ss");
			add("yyyy-MM-dd HH:mm");
			add("yyyy/MM/dd");
			add("yyyy/MM/dd HH:mm:ss");
			add("yyyy/MM/dd HH:mm");
			add("yyyy年MM月dd");
			add("yyyy年MM月dd HH:mm:ss");
			add("yyyy年MM月dd HH:mm");
			add("yyyy.MM.dd");
			add("yyyy.MM.dd HH:mm:ss");
			add("yyyy.MM.dd HH:mm");
		}
	};

	/**
	 * parse String type to Int.
	 * <p>
	 * you can also try use the overload method to custom date format.<br>
	 * - stringToInt(String time,String format);
	 * @throws ParseException 
	 */
	public static int stringToInt(String time) throws ParseException {
		if (Validate.isNullOrEmpty(time))
			return 0;
		time = format(time);
		SimpleDateFormat sdf;
		long date;

		for (int i = 0; i < yyyyMMdd.size(); i++) {
			sdf = new SimpleDateFormat(yyyyMMdd.get(i));
			try {
				date = sdf.parse(time).getTime();
				return (int) (date / 1000);
			} catch (ParseException e) {
				continue;
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("^[1-2]\\d{3}", "yyyy");
		map.put("^[1-2]\\d{3}年$", "yyyy年");

		map.put("^[1-2]\\d{3}[\\.][0-9]{1,2}", "yyyy.MM");
		map.put("^[1-2]\\d{3}[\\-][0-9]{1,2}", "yyyy-MM");
		map.put("^[1-2]\\d{3}\\-[0-9]{1,2}月$", "yyyy-MM月");
		map.put("^[1-2]\\d{3}年[0-9]{1,2}$", "yyyy年MM");
		map.put("^[1-2]\\d{3}年[0-9]{1,2}月$", "yyyy年MM月");

		map.put("^[1-2]\\d{3}/[0-9]{1,2}/[0-9]{1,2}$", "yyyy/MM/dd");
		map.put("^[1-2]\\d{3}[0|1][0-9][0-3][0-9]$", "yyyyMMdd");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			Matcher m = Pattern.compile(key).matcher(time);
			if (m.matches()) {
				sdf = new SimpleDateFormat(entry.getValue());
				date = sdf.parse(time).getTime();
				return (int) (date / 1000);
			}
		}

		return 0;
	}

	/**
	 * use custom date format parse String type to Int.
	 * 
	 * @throws ParseException
	 */
	public static int stringToInt(String time, String format) throws ParseException {

		if (Validate.isNullOrEmpty(time))
			return 0;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(time);
		return (int) (date.getTime() / 1000L);
	}

	/**
	 * Parse your date to String by default format [yyyy-MM-dd HH:mm:ss].
	 * <p>
	 * you can also try use the overload method. <br>
	 * intToString(int time,String format);
	 */
	public static String intToString(int time) {
		
		long l = time * 1000L;
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = sf.format(new Date(l));
		
		if (result.endsWith("00:00:00")) {
			sf = new SimpleDateFormat("yyyy-MM-dd");
			result = sf.format(new Date(l));
		}
		
		return result;
	}

	/**
	 * Use custom date format parse your date to String.
	 */
	public static String intToString(int time, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(new Date(time * 1000L));
	}

	/**
	 * Get current time(String).
	 * <p>
	 * default format [yyyy-MM-dd HH:mm:ss]
	 */
	public static String getNow() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}

	/**
	 * Get current time(String).
	 * <p>
	 * custom format
	 */
	public static String getNow(String format) {
		if (Validate.isNullOrEmpty(format))
			format = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}

	/**
	 * Get current time(int).
	 * <p>
	 */
	public static int getNowForInt() {
		return (int) (new Date().getTime() / 1000l);
	}

	public static String today() {
		int now = getNowForInt();
		return intToString(now, "yyyy-MM-dd");
	}
	
	public static final int DAY_INT = 60 * 60 * 24;

	public static String yesterday() {
		int yesterday = getNowForInt() - DAY_INT;
		return intToString(yesterday, "yyyy-MM-dd");
	}

}
