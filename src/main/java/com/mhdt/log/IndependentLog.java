package com.mhdt.log;

import java.io.File;

import com.mhdt.analyse.Validate;
import com.mhdt.toolkit.DateUtility;
import com.mhdt.toolkit.FileUtility;
import com.mhdt.toolkit.StringUtility;

/**
 * 
 * @author LazyToShow <br>
 *         Date： 2018年8月29日 <br>
 *         Time: 上午10:18:45
 */
public class IndependentLog extends AbstractLog {

	String namespace;

	File outputFolder = new File(Log.DEFAULTOUTPATH);

	public IndependentLog(String namespace) {
		this.namespace = namespace;
		
	}

	@Override
	protected String generatingOutput(Level level, String methodName,Object message, Throwable throwable) {

		return  DateUtility.getNow("z M, y HH:mm:ss a")+" "+ namespace +" "+methodName+"\r\n"
				+level.sign + ":"  
				+ " " + (message == null ? "\tnull" : message.toString())
				+ " " + StringUtility.getStackMessage(throwable);
	
	}

	public IndependentLog(String namespace, File outputFolder) {

		if (outputFolder == null)
			throw new NullPointerException("输出文件夹为null");

		if (Validate.isNullOrEmpty(namespace))
			throw new NullPointerException("命名空间为空");

		if (!outputFolder.exists())
			FileUtility.createFloder(outputFolder);

		this.namespace = namespace;

	}

	@Override
	public File getLogFile() {

		File file = FileUtility.join(outputFolder, DateUtility.getNow("YYYYMMdd") + ".txt");
		if (!file.exists())
			FileUtility.createFile(file);

		return file;
	}

	@Override
	public File getErrorFile() {

		return getLogFile();
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public File getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

}
