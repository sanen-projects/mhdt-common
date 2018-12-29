package com.mhdt.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.UIManager;

import com.mhdt.Print;

/**
 * Management of desktop application interface style<br>
 * @author LazyToShow <br>
 * Date: 2016/5/3 <br>
 * Time: 15:30
 */
public class UImanager {
	/** Song typeface,size 14 */
	public static final Font Font_14 = new Font("宋体", Font.PLAIN, 14);
	public static final Font Font_12 = new Font("宋体", Font.PLAIN, 12);
	
	public static Color red = new Color(255,106,106);
	public static Color green = new Color(0,129,64);
	public static Color yellow = new Color(255,215,0);
	public static Color blue = new Color(70,130,180);
	public static Color pink = new Color(238,180,180);
	public static Color gray = new Color(169,169,169);
	public static Color orange = new Color(255,140,0);
	public static Color purple = new Color(159,121,238);

	public static String WINDOW = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public static void setStyle(String style) {
		try {
			UIManager.setLookAndFeel(style);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * you can custom loader your font,
	 * premise you already have a file fomat by TTF
	 * @param ttfFile
	 * @return your custom font
	 */
	public static Font loadFont(File ttfFile) {
		if (!ttfFile.exists()) {
			System.out.println("[error] file not found");
			return null;
		}
		Font font = null;
		try {
			FileInputStream fi = new FileInputStream(ttfFile);
			BufferedInputStream fb = new BufferedInputStream(fi);
			font = Font.createFont(Font.TRUETYPE_FONT, fb);
			font = font.deriveFont(Font.BOLD, 14);
			Print.info("[info] font success load ： name= "
					+ font.getFontName() + " size:" + font.getSize());
		} catch (Exception e) {
			Print.error(e.getMessage());
		}

		return font;
	}
	
	/**
	 * Gets the system default font
	 * @return
	 */
	public static String[] getSystemDefaultFonts() {
		
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	}
	
}
