package com.mhdt.ui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SwingFactory {
	
	public static JFileChooser getJFileChoose(){
		JFileChooser fileChooser = new JFileChooser("C:\\Users\\Administrator\\Desktop\\");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
		        "图片文件(*.sas)", "sas");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
		        "图片文件(*.png)", "png");
		    fileChooser.setFileFilter(filter1);
		    fileChooser.setFileFilter(filter2);
		    return fileChooser;
	}
}
