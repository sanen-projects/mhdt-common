package com.mhdt.parse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 *<pre>
 * class加载器
 * @author 懒得出风头
 * Date: 2017年3月9日
 * Time: 下午1:23:09
 *</pre>
 */
public class FileClassLoader extends ClassLoader{

    private File  clsFile; 

    public FileClassLoader(File clsFile) { 
    	this.clsFile = clsFile;
    } 

    @Override
    protected Class<?> findClass(String clsName) throws ClassNotFoundException { 
        byte[] classData = getClassData(); 
        
        if (classData == null) { 
            throw new ClassNotFoundException(); 
        } else { 
            return defineClass(clsName, classData, 0, classData.length); 
        } 
    } 

    private final byte[] getClassData() {
    	InputStream in = null;
    	
		try {
			in = new FileInputStream(clsFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		
		
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
            byte[] bytes = new byte[128]; 
            int readIndex = 0;
            
            while ((readIndex = in.read(bytes)) != -1) { 
                bos.write(bytes, 0, readIndex); 
            } 
            
            return bos.toByteArray(); 
        } catch (IOException e) {
            e.printStackTrace(); 
	    }finally{
	        	try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
        	return null; 
    } 

 }
