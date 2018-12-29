package com.mhdt.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import com.mhdt.io.IMGIO;
import com.mhdt.toolkit.ImageUtility;

public class ImageSpilit {

	public static void main(String[] args) {
		
		BufferedImage bf = null;
		try {
			bf = IMGIO.load(new File("test/123.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		bf = ImageUtility.tranSparence(bf, 0.5);
		long t2 = System.currentTimeMillis();
		IMGIO.save(bf, "png", new File("test/trance.png"));
		System.out.println(t2-t1);
	}
}
