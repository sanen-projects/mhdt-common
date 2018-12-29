package com.mhdt.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.mhdt.analyse.Validate;
import com.mhdt.exception.imageTransformException;
import com.mhdt.toolkit.FileUtility;
import com.mhdt.toolkit.ImageUtility;

/**
 * Image inPut/OutPut ,reprint from ImageUtility.
 * @author 懒得出风头<br>
 * Date: 2016/07/20<br>
 * Time： 14:08<br>
 * Email:282854237@qq.com
 * @see com.mhdt.toolkit.ImageUtility
 */
public class IMGIO {
	
	/**
	 * load BufferedImage.load BufferedImage.
	 * @param in
	 * @return
	 */
	public final static BufferedImage load(InputStream in){
		
		try {
			return  ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;   
	}
	
	/**
	 * load BufferedImage.
	 * @param bytes
	 * @return
	 */
	public final static BufferedImage load(byte[] bytes){
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);    
		return load(in);
		
	}
	
	public final static BufferedImage load(Class<?> cls,String path){
		try {
			return ImageIO.read(cls.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * load BufferedImage.
	 * @throws FileNotFoundException 
	 */
	public final static BufferedImage load(File file) throws FileNotFoundException {

			FileInputStream fis = new FileInputStream(file);
			return load(fis);
	}

	/**
	 * load ImageIcon
	 * 
	 * @param path
	 * @return ImageIcon
	 */
	public final static ImageIcon load(String path) {
		return new ImageIcon(path);
	}
	
	public final static boolean save(Image image, File outFile) {
		FileUtility.createFile(outFile);
		String format = FileUtility.getFormat(outFile);
		int type = 0;
		if (!Validate.isNullOrEmpty(format) && format.equals("png")) {
			type = BufferedImage.TYPE_INT_ARGB;
		} else {
			format = "jpg";
			type = BufferedImage.TYPE_USHORT_565_RGB;
		}
		try {
			return save(image, format, outFile, type);
		} catch (imageTransformException e) {
			e.printStackTrace();
		}
		return false;
	}

	public final static boolean save(Image image, String format, File outFile) {
		int type = 0;
		type = format.equals("png") ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_USHORT_565_RGB;
		try {
			return save(image, format, outFile, type);
		} catch (imageTransformException e) {
			e.printStackTrace();
		}
		return false;

	}

	public final static boolean save(Image image, File outFile, int bufferedType) {

		try {
			return save(image, FileUtility.getFormat(outFile), outFile, bufferedType);
		} catch (imageTransformException e) {
			e.printStackTrace();
		}

		return false;

	}

	public final static boolean save(Image image, String format, File outFile, int bufferedType)
			throws imageTransformException {

		try {
			FileUtility.createFile(outFile);
			BufferedImage bf = null;
			try {
				bf = ImageUtility.imageToBufferedImage(image, bufferedType);
			} catch (imageTransformException e) {
				e.printStackTrace();
				return false;
			}
			ImageIO.write(bf, format, outFile);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Save Images by List Image.
	 * <p>
	 * You also Can set Format is null,then save Image PNG by Default format .
	 */
	public final static void save(List<BufferedImage> list, File outFloder) {
		FileUtility.createFloder(outFloder);
		for (int i = 0; i < list.size(); i++) {
			save(list.get(i), "png", new File(outFloder + "/" + System.currentTimeMillis() + ".png"));
		}
	}

	/**
	 * Save Images by List Image.
	 * <p>
	 * You also Can set Format is null,then save Image PNG by Default format .
	 */
	public final static void save(List<BufferedImage> list, String format, File outFolder) {
		FileUtility.createFloder(outFolder);
		for (int i = 0; i < list.size(); i++) {
			BufferedImage bf = list.get(i);
			if(bf!=null)save(bf, "png", new File(outFolder + "/" + System.currentTimeMillis() + ".png"));
		}
	}

	/**
	 * Save Images by List Image.
	 * <p>
	 * You also Can set Format is null,then save Image PNG by Default format .
	 */
	public final static void save(List<BufferedImage> list, String format, String fileName, File outerFolder) {
		FileUtility.createFloder(outerFolder);
		for (int i = 0; i < list.size(); i++) {
			if (Validate.isNullOrEmpty(format))
				format = "png";
			save(list.get(i), format, new File(outerFolder + "/" + fileName + ".png"));
		}
	}

	/**
	 * Save Images By rows.
	 * You also Can set Format is null,then save Image PNG by Default format .
	 * @param list
	 * @param format
	 * @param outFloder
	 */
	public final static void saveByRows(List<List<BufferedImage>> list, String format, File outFloder) {
		FileUtility.createFloder(outFloder);
		for (int row = 0; row < list.size(); row++) {
			List<BufferedImage> temp = list.get(row);
			for (int col = 0; col < temp.size(); col++) {
				if (Validate.isNullOrEmpty(format))
					format = "png";
				save(temp.get(col), format, new File(outFloder + "/" + row + "-" + col + ".png"));
			}
		}
	}
	
	
	/**
	 * bufferdImage to byte Array
	 * @param image
	 * @param format
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] getBytes(BufferedImage image,String format) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		ImageIO.write(image, format, out);
		return out.toByteArray(); 
      
	}


}
