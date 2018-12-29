package com.mhdt.toolkit;

import java.io.File;
import java.net.URL;

import com.mhdt.analyse.Validate;

/**
 * Path util
 * @author 懒得出风头<p>
 * Date:2016/3/8<br>
 * Time:15:51<br>
 * Email: 282854237@qq.com
 */
public class PathUtil {
	
	private PathUtil(){
		
	}
	
	
	/**
	 * If current is <strong>Web-Project</strong> ，will get WebContent path  else return {@link #underCurrentProject()}
	 * @return
	 */
	public static String underWebContent(){
		String path = getClassPath();
		if(path.indexOf("WebContent")!=-1){
			path = path.substring(0,path.indexOf("WebContent")+"WebContent".length())+"/";
		}else{
			path = underCurrentProject();
		}
		return path;
	}
	/**
	 * under classPath folder
	 * @param menu
	 * @return
	 */
	public static String under(String menu){
		String path = getClassPath();
		if(path.indexOf(menu)!=-1){
			path = path.substring(0,path.indexOf(menu)+menu.length())+"/";
		}
		return path;
	}
	
	
	
	/**
	 * Under the SRC path add your custom path.Under the SRC path add your custom path.
	 * @param addTo
	 * @return
	 * @throws NullPointerException
	 */
	public static String underSrc(String addTo) throws NullPointerException{
		try{
			String path =PathUtil.class.getResource("/"+addTo).getPath();
			return path;
		}catch(NullPointerException e){
			System.out.println(PathUtil.class.getResource("/").getPath()+addTo);
			throw e;
		}
		
	}
	
	/**
	 * <pre>
	 * 获取类路径，默认bin下，在Runjar运行中得到的是类似:
	 * </pre>
	 * @return
	 */
	public static  String getClassPath(){
		URL url = PathUtil.class.getResource("/");
		if(url == null){
			url = PathUtil.class.getResource("");
		}
		String path =url.getPath();
		return path;
	}
	
	
	/**
	 * Get path of under prokect.
	 * @return
	 */
	public static String underCurrentProject() {
		
		String path = System.getProperty("user.dir");
		
		if(path.contains("jar!")){
			int index = path.indexOf("jar!");
			path = path.substring(0, index);
			index = path.lastIndexOf("/");
			path = path.substring(0, index+1);
			
		}if(path.contains(File.separator+"WebContent")){
			path = path.substring(0, path.indexOf("WebContent")-1);
		}else if(path.contains(File.separator+"WEB-INF")){
			path = path.substring(0, path.indexOf("WEB-INF")-1);
		} else if(path.contains(File.separator+"bin")){
			path = path.substring(0, path.indexOf("bin")-1);
		}else if(path.contains(File.separator+"target")){
			path = path.substring(0, path.indexOf("target")-1);
		}else if(path.contains(File.separator+"classes")){
			path = path.substring(0, path.indexOf("classes")-1);
		}else {
			path = new File("").getAbsolutePath();
		}
		
		if(!Validate.isNullOrEmpty(path) && path.startsWith("file:"))
			path = path.replaceFirst("file:", "");
		
		return path;
	}
	
	public static String getUserHome() {
		return System.getProperty("user.home") + File.separator;
	}
	
	
	
	
}
