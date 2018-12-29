package com.mhdt.analyse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mhdt.io.RandomAcessInputStream;
import com.mhdt.toolkit.Reflect;
import com.mhdt.toolkit.StringUtility;

/**
 * The commonly used validate util class.
 * <p>
 * Associated with Boolean value validation classes, There may be to use regular
 * expressions and the <strong>Internet</strong> support, for example
 * {@link Validate#isLink(String url)}
 * 
 * @author 懒得出风头
 *         <p>
 *         Date: 2016/2/22<br>
 *         Time: 11:13<br>
 *         Email: 282854237@qq.com
 */
public class Validate {

	private Validate() {
	}

	/**
	 * Judge Obj is or not null(empty).
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().equals("null"))
			return true;
		if (obj.toString().replaceAll("\\s", "").replaceAll("[ ]", "").equals(""))
			return true;
		return false;
	}

	/**
	 * 
	 * @param cc
	 * @return
	 */
	public static <T> boolean isNullOrEmpty(Collection<T> cc) {
		if (cc == null || cc.isEmpty())
			return true;

		return false;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static <k, v> boolean isNullOrEmpty(Map<k, v> map) {
		if (map == null || map.isEmpty())
			return true;

		return false;
	}

	public static final Pattern pattern_PHONE = Pattern.compile("1(3[0-9]|45|47|5[012356789]|7[0678]|8[0-9])[0-9]{8}");

	/** is or not aphone */
	public static boolean isPhone(String phone) {
		if (isNullOrEmpty(phone))
			return false;
		return pattern_PHONE.matcher(phone).matches();
	}

	public static final Pattern pattern_TELEPHONE = Pattern
			.compile("((0\\d{2,3}|852|853)[-—]?[2-8]\\d{6,7}|[2-8]\\d{6,7})");

	/** is or not a telephone */
	public static boolean isTelephone(String telephone) {
		if (isNullOrEmpty(telephone))
			return false;
		telephone = telephone.replaceAll("\\s", "").replaceAll("[-－—]{1,}", "-");
		return pattern_TELEPHONE.matcher(telephone).matches();
	}

	private static Pattern pattern_name = Pattern.compile("^([\u4e00-\u9fa5]{2,5}|[a-zA-Z\\.\\s]{1,20})$");

	/** is or not a chinese name */
	public static boolean isTrueName(String name) {
		if (isNullOrEmpty(name))
			return false;
		return pattern_name.matcher(name).matches();
	}

	private static Pattern pattern_url = Pattern.compile("(http://|www.)*[\\w\\.\\-\\_]{2,150}\\."
			+ "(guide|store|fish|house|party|city|info|vip|help|sexy|camp|fail|pink|video|lol|cool|host|hosting|loan|mom|biz|"
			+ "中文网|tax|hiphop|auto|cheap|xyz|gift|link|click|org|wang|net|space|date|pics|bike|life|wiki|site|limo|flowers|"
			+ "news|green|solar|tech|red|club|tv|ren|design|zone|tm|com|diet|mobi|download|tools|software|cars|care|fund|today|"
			+ "live|shoes|so|watch|pub|ink|co|marke|cn|company|la|cash|town|lawyer|ltd|media|cc|gov|car|trade|land|hk|online|"
			+ "farm|cab|name|asia|guru|rocks|bid|top|poker|audio|website|win|photo|ninja|blue|ac|pro|toys|pw|tips"
			+ ")");

	/** is or not a legal url */
	public static boolean isUrl(String url) {
		if (isNullOrEmpty(url))
			return false;
		Matcher m = pattern_url.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	/** is or not a email */
	public static boolean isEmail(String email) {
		if (isNullOrEmpty(email))
			return false;
		if (email.startsWith("www."))
			return false;
		String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * judge the char is a english or number
	 * 
	 * @return boolean
	 */
	public static boolean isEnglishOrNum(char ch) {
		if (isEnglish(ch))
			return true;
		if (isNumber(ch))
			return true;
		return false;
	}

	public static boolean isEnglishOrNum(Object content) {
		if (isNullOrEmpty(content)) {
			return false;
		}

		String str = content.toString();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!isEnglishOrNum(c)) {
				return false;
			}
		}

		return true;
	}

	/** judge the char is a english */
	public static boolean isEnglish(char ch) {
		int val = (int) ch;
		// a-z
		if (val > 64 && val < 91)
			return true;
		// A-Z
		if (val > 96 && val < 123)
			return true;
		return false;
	}

	/**
	 * 纯英文
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isEnglish(Object content) {
		if (Validate.isNullOrEmpty(content))
			return false;
		String str = content.toString();
		for (int i = 0; i < str.length(); i++) {
			if (!isEnglish(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/** judge the char is a number */
	public static boolean isNumber(char c) {

		int val = (int) c;

		// 0-9
		if (val > 47 && val < 58)
			return true;
		return false;
	}

	/** judge the string Whether is all number (int or double) */
	public static boolean isNumber(Object obj) {

		if (isNullOrEmpty(obj))
			return false;

		try {
			Double.parseDouble(String.valueOf(obj));
			return true;
		}catch (Exception e) {
			return false;
		}
		
		
	}

	public static boolean isNumber(Class<?> cls) {

		if (isNullOrEmpty(cls))
			return false;

		return cls == Integer.class || cls == int.class || cls == Double.class || cls == double.class
				|| cls == Float.class || cls == float.class || cls == Long.class || cls == long.class;
	}

	/** is or not blank */
	public static boolean isBlank(char c) {
		int val = c;
		if (val <= 32 || val == 9 || val == 160 || val == 12288)
			return true;
		return false;
	}

	/** is or not chinese */
	public static boolean isChinese(Character ch) {
		if (ch == null)
			return false;
		return Pattern.compile("[\u4e00-\u9fa5]+").matcher(String.valueOf(ch)).matches();
	}

	/**
	 * To determine whether there is a field in the object
	 * 
	 * @param obj       - Object
	 * @param fieldName
	 * @return is exists?true:false
	 */
	public static boolean hasField(Object obj, String fieldName) {
		if (obj == null)
			return false;
		try {
			Field field = Reflect.getField(obj, fieldName);
			if (field != null)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * To determine whether there is a field in the objectTo determine whether there
	 * is a field in the object
	 * 
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static boolean hasField(Class<?> cls, String fieldName) {
		if (cls == null)
			return false;
		try {
			Field field = Reflect.getField(cls, fieldName);
			if (field != null)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * To judge whether the object has a method
	 * 
	 * @param obj          - Object
	 * @param methodName
	 * @param paramClasses - params classes
	 * @return is exists?true:false
	 */
	@SuppressWarnings({ "rawtypes" })
	public static boolean hasMethod(Object obj, String methodName, Class... paramClasses) {
		try {
			obj.getClass().getMethod(methodName, paramClasses);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * To test whether the domain name can access.
	 * <p>
	 * <strong>This method need internate </strong> to validate. Validation way to
	 * crawl website to see if the content is <strong>empty</strong> Or whether to
	 * jump to <strong>114</strong> ? return false : return true .
	 * 
	 * @param url - A correct website domain name
	 * @return Can be accessed or inaccessible
	 */
	public static boolean isLink(String url) {
		if (url.startsWith("www."))
			url = "http://" + url;
		url = StringUtility.removeBlankChar(url);
		try {
			URL ur = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
			conn.setConnectTimeout(7000);
			conn.setReadTimeout(5000);
			int code = conn.getResponseCode();
			if (code == 400 || code == 404 || code == 503)
				return false;
			String text = StringUtility.toString(conn.getInputStream());
			if (Validate.isNullOrEmpty(text))
				return false;
			text = text.replaceAll("<script[^>]*>[\\s\\S]*</script>", "").replaceAll("<[^>]+>", " ").trim()
					.replaceAll("[\\s]+", " ");
			System.out.println(text);
			if (text.equals("114") || text.startsWith("Request Rejected The requested URL was rejected"))
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Link the url.
	 * <p>
	 * Will get three values ,one is whether can be connected ,two is
	 * http-response-code, three is analyse-code ,default 0, -1 representative
	 * http-code or exception Lead to inaccessible , -2 representative stream lead
	 * to inaccessible.
	 * 
	 * @param url
	 * @return Object[0] - <strong>boolean</strong> Whether can be connected <br>
	 *         Object[1] - <strong>int</strong> http-status code.<br>
	 *         Object[2] - <strong>int</strong> analyse-code. Object[3] -
	 *         <strong>String</strong> content
	 */
	public static Object[] link(String url) {
		int http_code = -1;
		boolean flag = true;
		int analyse_code = 0;
		String content = "";

		if (url.startsWith("www."))
			url = "http://" + url;
		url = StringUtility.removeBlankChar(url);
		try {
			URL ur = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
			conn.setConnectTimeout(7000);
			conn.setReadTimeout(5000);
			http_code = conn.getResponseCode();
			if (http_code == 400 || http_code == 404 || http_code == 503)
				return new Object[] { false, http_code, -1, content };
			RandomAcessInputStream stream = new RandomAcessInputStream(conn.getInputStream());
			stream.mark(0);
			String text = content = StringUtility.toString(stream);
			if (Validate.isNullOrEmpty(text))
				return new Object[] { false, http_code, -2, content };
			String chaset = getCharset(text);
			System.out.println("chaset--> " + chaset);
			if (!chaset.toLowerCase().equals("utf-8")) {
				stream.reset();
				text = content = StringUtility.toString(stream, chaset);
			}
			text = text.toLowerCase();

			String title = "";
			if (text.indexOf("<title>") != -1 && text.indexOf("</title>") != -1) {
				title = text.substring(text.indexOf("<title>") + "<title>".length(), text.indexOf("</title>"));
			}
			System.out.println("标题-->" + title);
			String body = "";
			text = text.replaceAll("<body[^\\>]+>", "<body>").replaceAll("<title[^\\>]+>", "<title>");
			if (text.indexOf("<body>") != -1 && text.indexOf("</body>") != -1) {
				body = text.substring(text.indexOf("<body>") + "<body>".length(), text.indexOf("</body>"));
			}
			System.out.println("正文-->" + !Validate.isNullOrEmpty(body));
			if (Validate.isNullOrEmpty(body))
				return new Object[] { false, http_code, -2, content };
			text = text.replaceAll("<script[^>]*>[\\s\\S]*</script>", "").replaceAll("<[^>]+>", " ").trim()
					.replaceAll("[\\s]+", " ");
			if (title.startsWith("114") || text.startsWith("Request Rejected The requested URL was rejected")) {
				flag = false;
				analyse_code = -2;
			}

		} catch (Exception e) {
			flag = false;
			analyse_code = -1;
		}
		return new Object[] { flag, http_code, analyse_code, content };

	}

	/**
	 * 获取html编码
	 * 
	 * @param text
	 * @return
	 */
	private static String getCharset(String text) {
		if (Validate.isNullOrEmpty(text))
			return text;
		String charset = text;
		if (text.indexOf("charset=") != -1) {
			charset = text.substring(text.indexOf("charset=") + "charset=".length());
			Matcher matcher = Pattern.compile("[0-9a-zA-Z]+").matcher(charset);
			while (matcher.find()) {
				charset = matcher.group();
				break;
			}
		}
		return charset;
	}

	/**
	 * 完全匹配正则表达式
	 * 
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean isPerfectMatch(String regex, String input) {
		return Pattern.matches(regex, input);
	}

	@SuppressWarnings("rawtypes")
	public static boolean equals(Collection c1, Collection c2) {

		if ((c1 == null && c2 != null) || (c1 != null && c2 == null) || c1.size() != c2.size())
			return false;

		Object[] array1 = c1.toArray();
		Object[] array2 = c2.toArray();

		for (int i = 0; i < array1.length; i++) {
			Object o1 = array1[i];
			Object o2 = array2[i];
			if (!o1.equals(o2))
				return false;
		}

		return true;

	}

	@SuppressWarnings("rawtypes")
	public static boolean equals(Map c1, Map c2) {

		if ((c1 == null && c2 != null) || (c1 != null && c2 == null) || c1.size() != c2.size())
			return false;

		if (equals(c1.keySet(), c2.keySet())) {
			return equals(c1.values(), c2.values());
		} else {
			return false;
		}

	}

	@SuppressWarnings("rawtypes")
	public static boolean equals(Object obj1, Object obj2) {

		if (obj1 == null && obj2 == null)
			return true;

		if ((obj1 == null && obj2 != null) || (obj1 != null && obj2 == null))
			return false;

		if (obj1 instanceof Collection)
			return equals((Collection) obj1, (Collection) obj2);

		if (obj1 instanceof Map)
			return equals((Map) obj1, (Map) obj2);

		return obj1.equals(obj2);
	}

	public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotation) {
		return field.isAnnotationPresent(annotation);
	}

	/**
	 * Object is or not InstanceOf class.
	 * 
	 * @param obj -Instance Objects
	 * @param cls -class
	 * @return boolean
	 */
	public static boolean isInstance(Object obj, Class<?> cls) {
		return cls.isInstance(obj);
	}

	public static boolean isStatic(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

	public static boolean isStatic(Method method) {
		return Modifier.isStatic(method.getModifiers());
	}

	public static boolean isArray(Object obj) {
		if (obj == null)
			return false;

		return obj.getClass().isArray();
	}

	public static boolean isCollection(Object obj) {
		return obj instanceof Collection;
	}

	public static boolean isString(Object obj) {
		return obj!=null && obj instanceof String;
	}

	public static boolean isEnum(Object value) {
		return value!=null && value.getClass().isEnum();
	}

}
