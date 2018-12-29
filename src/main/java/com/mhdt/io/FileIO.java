package com.mhdt.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;

import com.mhdt.toolkit.FileUtility;

/**
 * File input/output .
 * 
 * @author 懒得出风头
 *         <p>
 *         Date: 2016/3/25<br>
 *         Time: 14:25<br>
 *         Email:282854237@qq.com
 * @see com.mhdt.toolkit.FileUtility
 */
public class FileIO {

	private FileIO() {

	}

	/** Acquire content from file And retain Each row's Newline. */
	public static String getContent(InputStream in) {
		return getContent(in, "utf-8");
	}

	public static String getContent(InputStream in, String encode) {
		InputStreamReader isr = null;
		BufferedReader bf = null;
		try {
			isr = new InputStreamReader(in, encode);
			bf = new BufferedReader(isr);
			return getContent(bf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";

	}

	/** Acquire content from file And retain Each row's Newline. */
	public static String getContent(File file) {
		return getContent(file, "utf-8");
	}

	/**
	 * Acquire content from file And retain Each row's NewlineAcquire content from
	 * file And retain Each row's Newline
	 * 
	 * @param file
	 * @param encode
	 * @return
	 */
	public static String getContent(File file, String encode) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader bf = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, encode);
			bf = new BufferedReader(isr);
			return getContent(bf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Acquire content from file And retain Each row's Newline. Warn The default
	 * file path under the SRC, Then you're using the different method,such -
	 * getContent(File file)
	 * 
	 * @throws FileNotFoundException
	 */
	public static String getContent(String path) throws FileNotFoundException {
		return getContent(path, "utf-8");
	}

	public static String getContent(String path, String encode) throws FileNotFoundException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader bf = null;
		try {
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis, encode);
			bf = new BufferedReader(isr);
			return getContent(bf);
		} catch (FileNotFoundException e1) {
			throw e1;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bf != null)
					bf.close();
				if (isr != null)
					isr.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private static String getContent(BufferedReader br) throws IOException {
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s = br.readLine()) != null) {
			sb.append(s + "\r\n");
		}
		return sb.length() > 0 ? sb.toString().substring(0, sb.toString().lastIndexOf("\r\n")) : "";

	}

	public static void write(File file, String conent, boolean isAppend) {
		write(file, conent, isAppend, "utf-8");
	}

	/**
	 * Write a string array into a file And add a newline By each row.
	 */
	public static void write(File file, Collection<String> content, boolean isAppend) {
		StringBuilder sb = new StringBuilder();
		for (String s : content) {
			sb.append(s + "\r\n");
		}
		write(file, sb.toString(), isAppend);
	}

	public static void write(File file, String conent, boolean isAppend, String encode) {
		FileUtility.createFile(file);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, isAppend), encode));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * write int[][] to file .
	 */
	public static void writeArray(File file, Object[][] array) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter write = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			write = new BufferedWriter(osw);
			for (int row = 0; row < array.length; row++) {
				for (int col = 0; col < array[row].length; col++) {
					write.write(array[row][col] + "");
				}
				write.write("\r\n");
			}
			write.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				osw.close();
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * read int [ ][ ] from file .
	 */
	public static int[][] readArray(File file) {
		int[][] array = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader read = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			read = new BufferedReader(isr);
			String s = null;
			ArrayList<String> list = new ArrayList<String>();
			while ((s = read.readLine()) != null) {
				list.add(s);
			}
			array = new int[list.size()][list.get(0).length()];
			for (int row = 0; row < list.size(); row++) {
				char[] chars = list.get(row).toCharArray();
				for (int col = 0; col < chars.length; col++) {
					array[row][col] = chars[col] - 0x30;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				isr.close();
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return array;

	}

	/**
	 * write Object implement serialize interface.
	 * 
	 * @param object
	 * @param outFile
	 */
	public static void writeObject(Object object, File outFile) {
		writeObject(object, outFile, false);
	}

	/**
	 * Save has been serialized object to the specified file .
	 * 
	 * @param object  -<br>
	 *                serialized object
	 * @param outFile -<br>
	 *                specified file
	 */
	public static void writeObject(Object object, File outFile, boolean append) {
		FileUtility.createFile(outFile);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(outFile, append);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * read has been serialized object from the specified file .
	 * 
	 * @param fromFile -<br>
	 *                 specified file
	 */
	public static Object readObject(File fromFile) {
		Object object = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fromFile);
			ois = new ObjectInputStream(fis);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	public static byte[] read(File file) {
		byte[] data = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			try {
				data = new byte[in.available()];
				in.read(data);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * write byte to file.
	 * 
	 * @param file
	 * @param bytes
	 * @param append
	 * @return result is success.
	 */
	public static boolean write(File file, byte[] bytes, boolean append) {
		FileUtility.createFile(file);
		if (bytes == null || bytes.length < 1)
			return false;

		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, append));
			try {
				out.write(bytes);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean write(File file, char[] cs, boolean isAppend) {

		FileUtility.createFile(file);

		if (cs == null || cs.length < 1)
			return false;

		try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, isAppend))) {
			out.write(cs, 0, cs.length);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
