package com.mhdt.parse;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.mhdt.Print;

/**
 * 
 * <pre>
 *
 * @author LazyToShow
 * Date: 2017年3月9日
 * Time: 下午3:06:47
 * </pre>
 */
public class AppclassLoad {

	/**
	 * 加载目录下jar包
	 * 
	 * @param source
	 *            - lib folder
	 */
	public void loadJars(File source) {

		if (source == null || !source.exists() || source.isFile())
			return;

		File[] files = source.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar") || name.endsWith(".zip");
			}
		});

		for (File jar : files) {
			load(jar);
		}

	}

	/**
	 * ClassMenu
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	public List<String> loadClasses(File folder) throws Exception {
		if (folder == null)
			return null;

		int root_len = folder.getAbsolutePath().length();

		if (folder.exists() && folder.isDirectory()) {
			load(folder);
			return populateClassName(folder, root_len);
		}

		return null;
	}

	private List<String> populateClassName(File source, int root_len) {
		List<String> list = new ArrayList<String>();

		Stack<File> stack = new Stack<>();
		stack.push(source);

		/** 迭代Root 文件将类名返回 */
		while (!stack.isEmpty()) {
			File parent = stack.pop();

			for (File child : getChilds(parent)) {
				if (child.isDirectory()) {
					stack.push(child);
				} else {

					list.add(child.getAbsolutePath().substring(root_len + 1, child.getAbsolutePath().length() - 6)
							.replace("\\", "."));
				}
			}
		}
		return list;
	}

	private File[] getChilds(File parent) {
		return parent.listFiles(new FileFilter() {

			@Override
			public boolean accept(File child) {
				return child.isDirectory() || child.getName().endsWith(".class");
			}
		});
	}

	private void load(File folder) {
		try {
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			URL url = folder.toURI().toURL();
			Print.info(url);
			method.invoke(classLoader, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param packageName
	 * @param childPackage
	 * @return
	 * @throws IOException
	 */
	public static List<String> getClassName(String packageName, boolean childPackage) throws IOException {
		List<String> fileNames = new ArrayList<>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		Enumeration<URL> urls = loader.getResources(URLDecoder.decode(packagePath, "UTF-8"));
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if (url == null)
				continue;
			String type = url.getProtocol();
			if (type.equals("file")) {
				fileNames.addAll(getClassNameByFile(url.getPath(), childPackage));
			} else if (type.equals("jar")) {
				fileNames.addAll(getClassNameByJar(url.getPath(), childPackage));
			}
		}
		fileNames.addAll(getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage));
		return fileNames;
	}

	private static List<String> getClassNameByFile(String filePath, boolean childPackage)
			throws UnsupportedEncodingException {
		List<String> myClassName = new ArrayList<>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		if (childFiles == null)
			return myClassName;
		for (File childFile : childFiles) {
			
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClassName.addAll(getClassNameByFile(childFile.getPath(), childPackage));
				}
			} else {
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".class")) {
					childFilePath = childFilePath.substring(childFilePath.indexOf(File.separator+"classes"+File.separator) + 9,
							childFilePath.lastIndexOf("."));
					childFilePath = childFilePath.replace(File.separator, ".");
					myClassName.add(childFilePath);
				}
			}
		}
		return myClassName;
	}

	private static List<String> getClassNameByJar(String jarPath, boolean childPackage)
			throws UnsupportedEncodingException {
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try {
			
			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile(URLDecoder.decode(jarFilePath, "utf-8"));
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myClassName;
	}

	private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage)
			throws UnsupportedEncodingException {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				// 不必搜索classes文件夹
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!/" + packagePath;
				myClassName.addAll(getClassNameByJar(jarPath, childPackage));
			}
		}
		return myClassName;
	}

}
