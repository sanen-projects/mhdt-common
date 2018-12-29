package com.mhdt.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import com.mhdt.analyse.Validate;
import com.mhdt.io.FileIO;
import com.mhdt.toolkit.Assert;
import com.mhdt.toolkit.FileUtility;
import com.mhdt.toolkit.FileUtility.FileType;

/**
 * Url tool
 *
 * @author LazyToShow <br>
 *         Date: March 27, 2017 <br>
 *         Time: 9:01:08 PM
 */
public class Urls {

	/**
	 * Open the link with your local browser
	 * 
	 * @param url
	 * @throws Exception
	 */
	public static void open(String url) throws Exception {
		String osName = System.getProperty("os.name", "");

		if (osName.startsWith("Mac OS")) {

			Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[] { String.class })
					.invoke(null, new Object[] { url });

			return;

		}

		if (osName.startsWith("Windows")) {

			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			return;

		}

		String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
		String browser = null;

		for (int count = 0; count < browsers.length && browser == null; count++)
			if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
				browser = browsers[count];

		if (browser == null)
			throw new Exception("Could not find web browser");
		else
			Runtime.getRuntime().exec(new String[] { browser, url });

	}

	/**
	 * 
	 * @param url
	 * @param args
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static byte[] post(String url, Map<String, Object> args) throws MalformedURLException, IOException {

		return request(url, RequestMethod.POST, args);
	}

	/**
	 * 
	 * @param url
	 * @param args
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static byte[] delete(String url, Map<String, Object> args) throws MalformedURLException, IOException {

		return request(url, RequestMethod.DELETE, args);
	}

	/**
	 * 
	 * @param url
	 * @param args
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static byte[] put(String url, Map<String, Object> args) throws MalformedURLException, IOException {

		return request(url, RequestMethod.PUT, args);
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static byte[] get(String url) throws MalformedURLException, IOException {

		return request(url, RequestMethod.GET, null);
	}

	/**
	 * Send a GET/POST request to the request address (Https supported) <br>
	 * 
	 * @param url           - It must start with HTTP/HTTPS and does not complete
	 *                      your url. Be aware of exceptions
	 * @param requestMethod {@link RequestMethod}
	 * @param args          - The parameters passed under the post request can be
	 *                      either {@link Map} in the form of key-value
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static byte[] request(String url, RequestMethod requestMethod, Object args)
			throws MalformedURLException, IOException {

		byte[] buf = new byte[1024];
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

		// https
		if (url.substring(0, 5).equals("https")) {
			SSLContext ctx = MyX509TrustManagerUtils();
			((HttpsURLConnection) conn).setSSLSocketFactory(ctx.getSocketFactory());
			((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {

				// During the handshake, if the hostname of the URL does not match the
				// identifying hostname of the server, the authentication mechanism can call
				// back the implementation of this interface to determine whether this
				// connection should be allowed.

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
		}

		initRequestProperties(conn, requestMethod);

		if (!Validate.isNullOrEmpty(args)) {
			conn.setDoOutput(true);
			conn.setDoInput(true);
			PrintWriter out = new PrintWriter(conn.getOutputStream());

			out.print(args instanceof Map ? procssData((Map<String, Object>) args) : args.toString());

			out.flush();
			out.close();
		}

		int size = -1;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());) {
			while ((size = bis.read(buf)) != -1)
				bos.write(buf, 0, size);

			bos.flush();
			return bos.toByteArray();
		}
	}

	/**
	 * HTTPS ignores certificate validation to prevent higher JDK versions from
	 * inheritting X509ExtendedTrustManager because the certificate algorithm does
	 * not meet the constraints
	 *
	 * @author LazyToShow <br>
	 *         Date: Nov 27, 2018 <br>
	 *         Time: 5:11:52 PM
	 */
	class MyX509TrustManager extends X509ExtendedTrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
				throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
				throws CertificateException {

		}

	}

	static SSLContext MyX509TrustManagerUtils() {

		TrustManager[] tm = { new Urls().new MyX509TrustManager() };
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctx;
	}

	public static void downLoad(String url, File dest, FileType type, ConfigureLink configureLink)
			throws MalformedURLException, IOException {

		downLoad(url, dest, type, configureLink, null);
	}

	public static void downLoad(String url, File dest, FileType type, Download downLoad)
			throws MalformedURLException, IOException {

		downLoad(url, dest, type, null, downLoad);
	}

	public static void downLoad(String url, File dest, FileType type) throws MalformedURLException, IOException {
		downLoad(url, dest, type, null, null);
	}

	public static void downLoad(String url, File destFile, FileType type, ConfigureLink configureLink,
			Download downLoad) throws MalformedURLException, IOException {

		byte[] buf = new byte[1024];
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		initRequestProperties(conn, RequestMethod.GET);

		if (configureLink != null)
			configureLink.configure(conn);

		int size = -1;

		try (BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

			DownInfo info = new DownInfo(conn.getContentLength(), getFileName(conn));
			if (downLoad != null)
				downLoad.loading(info);

			while ((size = bis.read(buf)) != -1) {
				bos.write(buf, 0, size);
				info.setCurrent(bos.size());
			}

			bos.flush();

			File dest = null;

			if (type.equals(FileType.Directory)) {
				dest = FileUtility.join(destFile, getFileName(conn));
			} else {
				dest = destFile;
			}

			FileIO.write(dest, bos.toByteArray(), false);
		}
	}

	public interface ConfigureLink {
		public void configure(HttpURLConnection conn);
	}

	/**
	 * 
	 * Additional actions for the program in download<br>
	 * Pay attention to {@link #loading(DownInfo)}The implementation of the method
	 * should be in a thread that you can refer to {@link SimpleDownload} Or inherit
	 * it to use
	 * 
	 * @author LazyToShow<br>
	 *         Date : 2018/02/21<br>
	 *         Time: 1:00
	 */
	public interface Download {

		public void loading(DownInfo downInfo);
	}

	public static class DownInfo {

		long current;

		long length;

		String fileName;

		public DownInfo(int length, String fileName) {
			this.length = length;
			this.fileName = fileName;

		}

		public long getCurrent() {
			return current;
		}

		public void setCurrent(long current) {
			this.current = current;
		}

		public long getLength() {
			return length;
		}

		public void setLength(long length) {
			this.length = length;
		}

		public void addLength(int add) {
			this.length += add;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	}

	/**
	 * According to IP return region, if provinces and cities, space segmentation.
	 * The method is to crawl http://www.ip138.com to obtain the region
	 * 
	 * @param ip
	 * @return Such as: hubei Yellowstone
	 */
	public static String getDistrictByIp(String ip) {
		String url = "http://www.ip138.com/ips138.asp?ip=" + ip + "&action=2";
		try {
			String str = new String(request(url, RequestMethod.GET, null), "gb2312");
			String result = str.substring(str.indexOf("本站数据：") + 5, str.indexOf(" ", str.indexOf("本站数据") + 4));
			if (result.contains("省")) {
				return result.replace("省", "省 ");
			} else if (result.contains("市")) {
				return result.replace("市", "市 ");
			} else {
				return result;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get host url from url if it had.
	 * 
	 * @param url
	 * @return
	 */
	public static String getHost(String url) {

		Assert.notNullOrEmpty(url, "Url is null or empty");

		try {
			return new URL(url).getHost();
		} catch (MalformedURLException e) {
			return url;
		}
	}

	static final String procssData(Map<String, Object> data) {

		if (data == null || data.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Object> entry : data.entrySet()) {
			try {
				sb.append(entry.getKey() + "=" + Urls.transferred(entry.getValue().toString()) + "&");
			} catch (NullPointerException e) {
				throw new IllegalArgumentException("The parameter value is null:" + entry.getKey(), e);
			}
		}

		return sb.substring(0, sb.length() - 1);
	}

	static final Map<String, String> words = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("+", "%2B");
			put("/", "%2F");
			put("?", "%3F");
			put("%", "%25");
			put("#", "%23");
			put("&", "%26");
			put("=", "%3D");
		}
	};

	/**
	 * Url character escaping
	 */
	public static String transferred(String str) {

		Assert.notNull(str, "Content is null");

		for (Map.Entry<String, String> entry : words.entrySet())
			if (str.contains(entry.getKey()))
				str = str.replace(entry.getKey(), entry.getValue());

		return str;
	}

	private static final void initRequestProperties(HttpURLConnection conn, RequestMethod requestMethod) {

		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");

		try {
			conn.setRequestMethod(requestMethod.toString());
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param conn
	 * @return
	 */
	private static String getFileName(URLConnection conn) {

		String filename = "";

		if (conn == null)
			return null;

		Map<String, List<String>> hf = conn.getHeaderFields();

		if (hf == null)
			return null;

		Set<String> key = hf.keySet();

		if (key == null)
			return null;

		out: for (String skey : key) {

			List<String> values = hf.get(skey);

			for (String value : values) {

				String result = new String(value.getBytes());

				int location = result.indexOf("filename");

				if (location >= 0) {
					result = result.substring(location + "filename".length());
					filename = result.substring(result.indexOf("=") + 1);
					break out;
				}

			}
		}

		if (Validate.isNullOrEmpty(filename)) {

			String url = conn.getURL().toString();
			filename = url.substring(url.lastIndexOf("/") + 1);
		}

		return filename;
	}

}
