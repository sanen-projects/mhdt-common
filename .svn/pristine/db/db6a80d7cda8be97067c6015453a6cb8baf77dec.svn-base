package com.mhdt.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.mhdt.Print;
import com.mhdt.analyse.Validate;
import com.mhdt.toolkit.StringUtility;

/**
 * @author 懒得出风头
 *         <p>
 *         Date： 2016/3/25 <br>
 *         Time: 10:06<br>
 *         Email:282854237@qq.com
 */
public class FileUtility {

	public enum FileType {
		/** 文件 */
		File,
		/** 目录(文件夹) */
		Directory;
	}

	private FileUtility() {
	}

	/**
	 * Build a file based on the url, which can be <strong>classpath:</strong> or
	 * <strong>file:</strong> start, to represent the project root path or classpath
	 * 
	 * @param path
	 * @return
	 */
	public static File buider(String path) {

		if (path.startsWith("classpath:")) {
			try {
				String temp = path.substring(path.indexOf("classpath:") + 10);
				URI uri = FileUtility.class.getResource(File.separator + temp).toURI();
				return new File(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return null;
			}

		} else if (path.startsWith("file:")) {
			return new File(new File(PathUtil.underCurrentProject() + path.substring(path.indexOf("file:") + 5))
					.getAbsolutePath());
		} else {
			return new File(path);
		}

	}

	/** Random file In folder . */
	public static File randomFileName(File folder) {
		File[] files = folder.listFiles();
		Random r = new Random();
		return files[r.nextInt(files.length)];
	}

	public static void remove(String path) {
		remove(new File(path));
	}

	public static boolean remove(File file) {
		if (file.exists()) {

			if (file.isFile()) {
				return file.delete();
			} else if (file.isDirectory()) {

				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++)
					 remove(files[i]);

				return file.delete();
			}
		}
		
		return false;
	}

	public static void removeByPrefix(File folder, String startWith) {
		for (File file : folder.listFiles()) {
			if (file.getName().startsWith(startWith))
				remove(file);
		}
	}

	public static void removeBySuffix(File folder, String endwith) {
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(endwith))
				remove(file);
		}
	}

	/**
	 * Access to the file format
	 * 
	 * @param file
	 * @return
	 */
	public static String getFormat(File file) {

		Assert.notNull(file, "File is null");

		if (!file.exists() || file.getName().indexOf(".") == -1)
			return null;

		return file.getName().substring(file.getName().lastIndexOf(".") + 1);
	}

	public static String getFormat(String fileName) {

		Assert.notNullOrEmpty(fileName, "fileName is null or empty");
		Assert.state(fileName.contains("."), "Unformatted file, File name not included '.'");

		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * create floder and return result.
	 * 
	 * @param floder
	 * @return
	 */
	public static boolean createFloder(File floder) {
		boolean flag = true;
		if (!floder.exists()) {
			try {
				flag = floder.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;

	}

	/**
	 * create file and return result.
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createFile(File file) {

		Assert.notNull(file, "File is null");

		if (!file.exists()) {

			try {

				if (!Validate.isNullOrEmpty(file.getParent())) {
					File floder = new File(file.getParent());
					if (!floder.exists())
						createFloder(floder);
				}

				return file.createNewFile();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static String lastDirectory(File file) {
		Assert.notNull(file, "File is null");

		return file.getParent();
	}

	/**
	 * 文件重命名
	 * 
	 * @param file
	 * @return
	 */
	public static boolean rename(File file, String name) {

		Assert.notNull(file, "File is null");
		Assert.notNullOrEmpty(name, "Name is null or empty");
		Assert.state(file != null && file.exists(),
				"File does not exist " + file == null ? "" : file.getAbsolutePath());

		if (file.isDirectory())
			return file.renameTo(new File(lastDirectory(file) + File.separator + name));

		if (file.isFile()) {
			String format = getFormat(file);
			File newFile = null;

			if (Validate.isNullOrEmpty(format) || name.endsWith("." + format))
				newFile = new File(lastDirectory(file) + File.separator + name);
			else
				newFile = new File(lastDirectory(file) + File.separator + name + "." + format);

			return file.renameTo(newFile);
		}

		return false;
	}

	/**
	 * 文件(单个文件或文件夹)剪切
	 * 
	 * @param source       -源文件或文件夹
	 * @param dest         - 目标文件夹
	 * @param destFileType - 告之dest的类型,这是一个重要的参数以决定后续选择怎样的方式去处理<br>
	 */
	public static boolean shear(File source, File dest, FileType destFileType) {

		if (source == null || !source.exists())
			throw new NullPointerException("源文件[source]为 'Null' 或 不存在");

		if (source.isDirectory())
			return shearFolder(source, dest, destFileType);

		if (source.isFile())
			return shearFile(source, dest, destFileType);

		return false;
	}

	private static boolean shearFolder(File src, File dest, FileType destFileType) {
		if (destFileType != FileType.Directory) {
			Print.error("剪切文件夹: 目标文件[dest]也必须是文件夹");
			return false;
		}

		dest = new File(dest.getAbsoluteFile() + "/" + src.getName());
		createFloder(dest);
		File[] files = src.listFiles();

		if (files.length > 0) {
			for (File temp : files) {
				File n = new File(dest.getAbsolutePath() + "/" + temp.getName());
				if (copyFile(temp, n))
					if (temp.exists())
						temp.delete();
			}
		}
		src.delete();
		return true;
	}

	private static boolean shearFile(File src, File dest, FileType destFileType) {
		if (destFileType == FileType.File) {
			createFloder(new File(lastDirectory(dest)));
			if (copyFile(src, dest)) {
				src.delete();
				return true;
			}
			return false;
		}

		if (destFileType == FileType.Directory) {
			createFloder(dest);// 创建目标文件夹
			if (copyFile(src, new File(dest.getAbsolutePath() + "/" + src.getName()))) {
				src.delete();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 复制文件或者文件夹
	 * 
	 * @param src
	 * @param dest
	 * @param destFileType
	 * @return boolean
	 */
	public static boolean copy(File src, File dest, FileType destFileType) {

		if (src.isFile()) {

			if (destFileType == FileType.Directory)
				dest = new File(dest.getAbsoluteFile() + "/" + src.getName());
			createFile(dest);
			return copyFile(src, dest);
		}

		if (src.isDirectory()) {
			if (destFileType == FileType.File) {
				Print.error("复制文件夹-失败, 因为: 出于歧义的考虑(复制文件夹到文件 ??) - 请将文件夹作为输出项[dest]");
				return false;
			}
			return copyFolder(src, dest);
		}

		return false;
	}

	/**
	 * 复制文件夹到另一个文件夹
	 * 
	 * @param src
	 * @param dest
	 */
	private static boolean copyFolder(File src, File dest) {
		try {
			dest = new File(dest.getAbsoluteFile() + "/" + src.getName());
			createFloder(dest);
			File[] files = src.listFiles();
			for (File file : files) {
				copyFile(file, new File(dest.getAbsoluteFile() + "/" + file.getName()));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 复制文件到另一个文件
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	private static boolean copyFile(File src, File dest) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(dest);
			in = fis.getChannel();// 得到对应的文件通道
			out = fos.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fis.close();
				in.close();
				fos.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * get file pure name. such as file name is text.txt return 'text' if file is
	 * not exists or is null,throw NullPointException
	 * 
	 * @param file
	 * @return pureName
	 */
	public static String getPureName(File file) {
		if (file == null || !file.exists())
			throw new NullPointerException("文件为空:" + file.getAbsolutePath());
		String name = file.getName();
		if (name.indexOf(".") != -1) {
			name = name.substring(0, name.lastIndexOf("."));
		}
		return name;
	}

	public static String getPureName(String fileName) {

		Assert.notNullOrEmpty(fileName, "fileName is null or empty");
		if (fileName.contains("."))
			return fileName.substring(0, fileName.lastIndexOf("."));
		else
			return fileName;
	}
	
	public static List<String> childies(File folder){
		return childies(folder, null);
	}

	/**
	 * Return all The file absolute path under Folder. if folder is null or not
	 * exists return empty
	 * 
	 * @param folder
	 * @return
	 */
	public static List<String> childies(File folder,String $suffix) {
		
		if (folder == null || !folder.exists() || !folder.isDirectory())
			return new ArrayList<String>();
		
		List<String> list = new ArrayList<String>();
		List<File> fs = Arrays.asList(folder.listFiles());
		
		Collections.sort(fs, new Comparator<File>() {

			public int compare(File o1, File o2) {
				try {
					String name1 = o1.getName();
					String name2 = o2.getName();
					return StringUtility.extractInteger(name1) - StringUtility.extractInteger(name2);
				} catch (Exception e) {
					return 0;
				}

			}
		});

		for (File f : fs) {
			if(!Validate.isNullOrEmpty($suffix) && f.getAbsolutePath().endsWith($suffix))
			list.add(f.getAbsolutePath());
		}
		return list;
	}

	/**
	 * <pre>
	 * 根据文件名前缀查找文件
	 * 注意此方法只索引文件(非文件夹)同时方法使用了递归,在复杂树下查找注意系统资源消耗
	 * &#64;param folder - 文件夹
	 * &#64;param prefix - 前缀
	 * &#64;return 返回匹配文件数组
	 * </pre>
	 */
	public static File[] findByPrefix(File folder, Object prefix) {
		List<File> list = new ArrayList<File>();
		String preName = prefix.toString();

		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(Arrays.asList(findByPrefix(file, prefix)));
			} else if (file.isFile() && file.getName().startsWith(preName)) {
				list.add(file);

			}
		}
		return list.toArray(new File[1]);
	}

	/**
	 * 
	 * Add the folder and filename to a new file.
	 * 
	 * @param foleder
	 * @param fileName
	 * @return
	 */
	public static File join(File foleder, String fileName) {
		return new File(foleder.getAbsolutePath() + File.separator + fileName);
	}

}
