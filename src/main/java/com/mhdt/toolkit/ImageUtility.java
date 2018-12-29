package com.mhdt.toolkit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mhdt.Print;
import com.mhdt.analyse.Statistics;
import com.mhdt.analyse.Validate;
import com.mhdt.exception.imageTransformException;
import com.mhdt.imageEditor.StackGaussianBlurFilter;
import com.mhdt.exception.SyntheticImagesException;
import com.mhdt.io.IMGIO;

/**
 * @author <strong>懒得出风头</strong>
 *         <p>
 *         Date: 2016/3/7<br>
 *         Time: 11:35<br>
 *         Email: 282854237@qq.com
 */
public class ImageUtility {

	private ImageUtility() {
	}

	/**
	 * apppend String.
	 */
	public final static BufferedImage appendString(BufferedImage bf, String content, Font font) {
		Graphics2D g = bf.createGraphics();
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.2f));
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(content, bf.getWidth() / 2 - (content.length() * 7), 12 + (bf.getHeight() - 14) / 2);
		g.dispose();
		return bf;

	}

	/**
	 * append String.
	 * 
	 * @throws FileNotFoundException
	 */
	public final static BufferedImage appendString(File file, String content, Font font) throws FileNotFoundException {
		BufferedImage bf = IMGIO.load(file);
		Graphics2D g = bf.createGraphics();
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(content, bf.getWidth() / 2 - (content.length() * 7), 12 + (bf.getHeight() - 14) / 2);
		g.dispose();
		return bf;

	}

	/**
	 * append String
	 * 
	 * @param list
	 * @param content
	 * @param font
	 * @param excursionX (字符串顶点偏移X)
	 * @param excursionY (字符串顶点偏移Y)
	 * @return
	 */
	public final static Collection<BufferedImage> appendString(Collection<BufferedImage> list, String content,
			Font font, int excursionX, int excursionY) {
		for (BufferedImage bf : list) {
			Graphics2D g = bf.createGraphics();
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString(content,
					(bf.getWidth()
							- (Statistics.chineseCount(content) * font.getSize() + Statistics.blankCount(content) * 7))
							/ 2 + excursionX,
					12 + excursionY + (bf.getHeight() - font.getSize()) / 2);
			g.dispose();
		}
		return list;
	}

	/**
	 * append String.
	 */
	public final static Collection<BufferedImage> appendString(Collection<BufferedImage> list, String content,
			Font font) {
		return appendString(list, content, font, 0, 0);

	}

	/**
	 * Image Vampix.
	 * 
	 * @param image
	 * @return
	 */
	public final static BufferedImage gray(BufferedImage image) {
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		BufferedImage newPic = op.filter(image, null);
		return newPic;
	}

	/**
	 * Change the picture transparency. Return a new BufferdImage.
	 * 
	 * @param image - inputBufferdImage
	 * @param alpha - (0 ~ 1.0)
	 * @return
	 */
	public final static BufferedImage tranSparence(BufferedImage image, double alpha) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		if (alpha < 0) {
			alpha = 0;
		} else if (alpha > 1) {
			alpha = 1;
		}

		alpha = 255 * alpha;
		int rgb = 0;
		Color color = null;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				rgb = image.getRGB(i, j);
				if (rgb != 0) {
					color = new Color(rgb);
					color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) alpha);
					newImage.setRGB(i, j, color.getRGB());
					continue;
				} else {
					newImage.setRGB(i, j, rgb);
				}

			}
		}

		return newImage;
	}

	/**
	 * append images.append images.append images.
	 * 
	 * @param srcFolder
	 */
	public final static void append(File srcFolder) {
		append(srcFolder, srcFolder.getName(), true, 0);
	}

	/**
	 * append images.
	 * 
	 * @param srcFolder
	 * @param cols
	 */
	public final static void append(File srcFolder, int cols) {

		append(srcFolder, srcFolder.getName(), true, cols);
	}

	public final static void append(File srcFolder, String outPutFileName, boolean isX, int cols) {
		List<BufferedImage> list = new ArrayList<BufferedImage>();
		File[] files = srcFolder.listFiles();
		for (File file : files) {
			if (file.getName().endsWith("png") || file.getName().endsWith("jpg"))
				try {
					list.add(IMGIO.load(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		}
		if (cols < 1)
			cols = list.size();
		int row = 1;
		if (list.size() % cols == 0) {
			row = list.size() / cols;
		} else {
			row = list.size() / cols + 1;
		}
		BufferedImage result = append(list, isX, cols);

		File file;
		if (outPutFileName.lastIndexOf(".") == -1
				|| Validate.isNullOrEmpty(outPutFileName.substring(outPutFileName.lastIndexOf(".")))) {
			file = new File(
					FileUtility.lastDirectory(srcFolder) + "/" + outPutFileName + "_" + row + "-" + cols + ".png");
		} else {
			file = new File(FileUtility.lastDirectory(srcFolder) + "/" + outPutFileName + "_" + row + "-" + cols);
		}

		if (result != null)
			IMGIO.save(result, file);
	}

	public final static BufferedImage append(List<BufferedImage> list, boolean isX, int cols) {
		if (list == null || list.isEmpty()) {
			Print.error("the image source is Null .");
			throw new NullPointerException();
		}
		try {
			boolean isFirstPng = true;
			BufferedImage outputImg = null;
			int outputImgW = 0;
			int outputImgH = 0;
			int currenW = 0;
			int currenH = 0;
			int row = 1;
			int i = 1;
			if (cols < 1)
				cols = list.size();

			if (list.size() % cols == 0) {
				row = list.size() / cols;
			} else {
				row = list.size() / cols + 1;
			}

			for (BufferedImage image : list) {
				if (isFirstPng) {
					isFirstPng = false;
					outputImg = image;
					outputImgW = outputImg.getWidth();
					outputImgH = outputImg.getHeight();
					i++;
				} else {
					BufferedImage appendImg = image;
					int appendImgW = appendImg.getWidth();
					int appendImgH = appendImg.getHeight();
					if (isX) {
						if (i <= cols) {
							outputImgW = outputImgW + appendImgW;
							outputImgH = outputImgH > appendImgH ? outputImgH : appendImgH;
						} else if (i > row * cols) {
							outputImgH = outputImgH + appendImgH;
						}

					} else {
						if (i <= cols) {
							outputImgW = outputImgW > appendImgW ? outputImgW : appendImgW;
							outputImgH = outputImgH + appendImgH;
						} else if (i > row * cols) {
							outputImgW = outputImgW + appendImgW;
						}

					}

					Graphics2D g2d = outputImg.createGraphics();
					BufferedImage newImage = g2d.getDeviceConfiguration().createCompatibleImage(outputImgW, outputImgH,
							Transparency.TRANSLUCENT);
					g2d.dispose();
					g2d = newImage.createGraphics();

					int oldImgW = outputImg.getWidth();
					int oldImgH = outputImg.getHeight();
					g2d.drawImage(outputImg, 0, 0, oldImgW, oldImgH, null);
					if (isX) {
						if (i <= cols) {
							g2d.drawImage(appendImg, oldImgW, 0, appendImgW, appendImgH, null);
							i++;
						} else if (i > row * cols) {
							g2d.drawImage(appendImg, 0, oldImgH, appendImgW, appendImgH, null);
							currenW = appendImgW;
							currenH = oldImgH;
							i++;
							row++;
						} else {
							g2d.drawImage(appendImg, currenW, currenH, appendImgW, appendImgH, null);
							currenW += appendImgW;
							i++;
						}
					} else {
						if (i <= cols) {
							g2d.drawImage(appendImg, 0, oldImgH, appendImgW, appendImgH, null);
							i++;
						} else if (i > row * cols) {
							g2d.drawImage(appendImg, oldImgW, 0, appendImgW, appendImgH, null);
							currenW = oldImgW;
							currenH = appendImgH;
							row++;
							i++;
						} else {
							g2d.drawImage(appendImg, currenW, currenH, appendImgW, appendImgH, null);
							currenH += appendImgH;
							i++;
						}
					}

					g2d.dispose();
					outputImg = newImage;
				}
			}
			return outputImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 切分地砖
	 * 
	 * @param src
	 * @param tileWidth
	 * @param tileHeight
	 * @return
	 */
	public final static BufferedImage[][] splitTiles(BufferedImage src, int tileWidth, int tileHeight) {
		int width = src.getWidth();
		int height = src.getHeight();
		int tile_rows = height / tileHeight == 0 ? height / tileHeight : height / tileHeight + 1;
		int tile_cols = width / tileWidth == 0 ? width / tileWidth : width / tileWidth + 1;

		BufferedImage[][] results = new BufferedImage[tile_rows][tile_cols];

		for (int row = 0; row < tile_rows; row++) {
			for (int col = 0; col < tile_cols; col++) {
				BufferedImage temp;
				int temp_width = tileWidth;
				int temp_height = tileHeight;
				if (row == tile_rows - 1)
					temp_height = height % tileHeight;
				if (col == tile_cols - 1)
					temp_width = width % tileWidth;
				temp = new BufferedImage(temp_width, temp_height, BufferedImage.TYPE_USHORT_565_RGB);
				Graphics2D g2d = temp.createGraphics();
				g2d.drawImage(src.getSubimage(col * tileWidth, row * tileHeight, temp_width, temp_height), 0, 0, null);
				// for(int i = 0;i<temp_height;i++){
				// for(int j=0;j<temp_width;j++){
				// int aa = col*tileWidth+i;
				// int bb = row*tileHeight+j;
				// System.out.println(aa+","+bb);
				// temp.setRGB(j, i, src.getRGB(aa,bb ));
				// }
				// }
				g2d.dispose();
				results[row][col] = temp;

			}
		}

		return results;
	}

	/**
	 * split Image By rows.
	 * 
	 * @throws SyntheticImagesException
	 * @throws FileNotFoundException
	 */
	public final static List<List<BufferedImage>> splitByRows(File file)
			throws SyntheticImagesException, FileNotFoundException {
		List<List<BufferedImage>> list = new ArrayList<List<BufferedImage>>();
		int[] array = getRowsAndCols(file.getName());
		int rows = array[0];
		int cols = array[1];
		BufferedImage image = IMGIO.load(file);
		int width = image.getWidth() / cols;
		int height = image.getHeight() / rows;
		for (int row = 0; row < rows; row++) {
			List<BufferedImage> temp = new ArrayList<BufferedImage>();
			for (int col = 0; col < cols; col++) {
				int rgb[] = new int[width * height];
				image.getRGB(col * width, row * height, width, height, rgb, 0, width);
				BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				newImage.setRGB(0, 0, width, height, rgb, 0, width);
				temp.add(newImage);
			}
			list.add(temp);
		}
		return list;
	}

	/**
	 * Split Image By one Row.
	 * 
	 * @throws SyntheticImagesException
	 * @throws FileNotFoundException
	 */
	public final static List<BufferedImage> split(File file) throws SyntheticImagesException, FileNotFoundException {
		List<BufferedImage> list = new ArrayList<BufferedImage>();
		int[] array = getRowsAndCols(file.getName());
		if (array == null) {
			list.add(IMGIO.load(file));
			return list;
		}
		int rows = array[0];
		int cols = array[1];
		BufferedImage image = IMGIO.load(file);
		int width = image.getWidth() / cols;
		int height = image.getHeight() / rows;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				int rgb[] = new int[width * height];
				image.getRGB(col * width, row * height, width, height, rgb, 0, width);
				BufferedImage newImage = null;
				try {
					newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				} catch (Exception e) {
					System.out.println(file.getName());
					e.printStackTrace();
				}

				newImage.setRGB(0, 0, width, height, rgb, 0, width);
				list.add(newImage);
			}
		}
		return list;
	}

	/**
	 * Get Image info - rows/cols From Image Name .
	 * 
	 * @param name
	 * @return
	 * @throws SyntheticImagesException
	 */
	private final static int[] getRowsAndCols(String name) throws SyntheticImagesException {
		if (name.indexOf("_") == -1 || name.indexOf("-") == -1) {
			throw new SyntheticImagesException(
					"【ERROR】 图片文件名不合法 " + name + "，请使用例如: test_row(数字代表行数)-col(数字代表列数).png 来命名你的图片合成文件");
		}
		int[] ary = new int[2];
		String[] array = name.substring(name.lastIndexOf("_") + 1).split("\\.")[0].split("-");
		try {
			ary[0] = Integer.parseInt(array[0]);
			ary[1] = Integer.parseInt(array[1]);
		} catch (java.lang.NumberFormatException e) {
			throw new SyntheticImagesException(
					"【ERROR】 图片文件名不合法 " + name + "，请使用例如: test_row(数字代表行数)-col(数字代表列数).png(及其它可解析文件后缀) 来命名你的图片合成文件");
		}
		return ary;
	}

	/**
	 * Image clip .<br>
	 * 
	 * @param bufferedImage
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 * @return - New BufferedImage clipped .
	 */
	public static BufferedImage clip(BufferedImage bufferedImage, int startX, int startY, int width, int height) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int[] rgb = new int[width * height];
		bufferedImage.getRGB(startX, startY, width, height, rgb, 0, width);
		newImage.setRGB(0, 0, width, height, rgb, 0, width);
		return newImage;
	}

	/**
	 * Change the picture size scale , default use <strong>SMOOTH</strong> algorithm
	 * .
	 * 
	 * @param src   - source picture
	 * @param scale - float type.
	 * @return - new Image
	 */
	public static Image scaleImage(BufferedImage src, float scale) {
		return scaleImage(src, (int) (src.getWidth() * scale), (int) (src.getHeight() * scale));
	}

	/**
	 * Scaling images to The specified pixel size , default use
	 * <strong>SMOOTH</strong> algorithm .
	 * 
	 * @param src       - SRC image.
	 * @param newWidth
	 * @param newHeight
	 * @return new Image
	 */
	public static Image scaleImage(BufferedImage src, int newWidth, int newHeight) {
		return scaleImage(src, newWidth, newHeight, Image.SCALE_SMOOTH);
	}

	/**
	 * 
	 * @param src       - BufferedImage
	 * @param newWidth
	 * @param newHeight
	 * @param           imageArithmetic-Image class algorithm.
	 * @return
	 */
	public static Image scaleImage(BufferedImage src, int newWidth, int newHeight, int imageArithmetic) {
		return src.getScaledInstance(newWidth, newHeight, imageArithmetic);
	}

	/**
	 * Image type conversion
	 * 
	 * @param src
	 * @param bufferedType
	 * @return
	 * @throws imageTransformException
	 */
	public static BufferedImage imageToBufferedImage(Image src, int bufferedType) throws imageTransformException {
		if (src instanceof BufferedImage)
			return (BufferedImage) src;

		int width = src.getWidth(null);
		int height = src.getHeight(null);

		if (!(src instanceof BufferedImage) && width == -1 && height == -1) {
			throw new imageTransformException("SRC-image cant cast to BufferedImage,\n"
					+ "because width=-1 and height==-1  try IMGIO.load image use ImageUtily.IMGIO.load(...)");
		}

		BufferedImage bf = new BufferedImage(width, height, bufferedType);
		Graphics g2d = bf.getGraphics();
		g2d.drawImage(src, 0, 0, width, height, null);
		g2d.dispose();
		return bf;
	}

	/**
	 * Image type conversionImage type conversionImage type conversion
	 * 
	 * @param src
	 * @return
	 */
	public static Image bufferedImageToImage(BufferedImage src) {
		return scaleImage(src, 1.0f);
	}

	/**
	 * highlight
	 * 
	 * @param image
	 */
	public static BufferedImage hightLight(BufferedImage image, int value) {
		BufferedImage bf = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// 获取到rgb的组合值
				int rgb = image.getRGB(x, y);
				if (rgb == 0 || rgb == 4494294)
					continue;
				Color color = new Color(rgb);
				int r = color.getRed() + value;
				int g = color.getGreen() + value;
				int b = color.getBlue() + value;
				if (r > 255) {
					r = 255;
				} else if (r < 0) {
					r = 0;
				}

				if (g > 255) {
					g = 255;
				} else if (g < 0) {
					g = 0;
				}

				if (b > 255) {
					b = 255;
				} else if (b < 0) {
					b = 0;
				}

				color = new Color(r, g, b);
				bf.setRGB(x, y, color.getRGB());
			}
		}
		return bf;
	}

	/**
	 * 旋转图片
	 * 
	 * @param src   - 源图片
	 * @param angel - 角度
	 * @return
	 */
	public static BufferedImage rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		// 计算新图片的大小
		Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage destImage = null;
		destImage = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = destImage.createGraphics();
		// 变换
		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
		g2.drawImage(src, null, null);
		return destImage;
	}

	private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		// 如果角度大于90度，我们需要进行一些转换
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}

	/**
	 * <p>
	 * Returns an array of pixels, stored as integers, from a
	 * <code>BufferedImage</code>. The pixels are grabbed from a rectangular area
	 * defined by a location and two dimensions. Calling this method on an image of
	 * type different from <code>BufferedImage.TYPE_INT_ARGB</code> and
	 * <code>BufferedImage.TYPE_INT_RGB</code> will unmanage the image.
	 * </p>
	 *
	 * @param img    the source image
	 * @param x      the x location at which to start grabbing pixels
	 * @param y      the y location at which to start grabbing pixels
	 * @param w      the width of the rectangle of pixels to grab
	 * @param h      the height of the rectangle of pixels to grab
	 * @param pixels a pre-allocated array of pixels of size w*h; can be null
	 * @return <code>pixels</code> if non-null, a new array of integers otherwise
	 * @throws IllegalArgumentException is <code>pixels</code> is non-null and of
	 *                                  length &lt; w*h
	 */
	public static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
		if (w == 0 || h == 0) {
			return new int[0];
		}

		if (pixels == null) {
			pixels = new int[w * h];
		} else if (pixels.length < w * h) {
			throw new IllegalArgumentException("pixels array must have a length" + " >= w*h");
		}

		int imageType = img.getType();
		if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB) {
			Raster raster = img.getRaster();
			return (int[]) raster.getDataElements(x, y, w, h, pixels);
		}

		// Unmanages the image
		return img.getRGB(x, y, w, h, pixels, 0, w);
	}

	/**
	 * <p>
	 * Writes a rectangular area of pixels in the destination
	 * <code>BufferedImage</code>. Calling this method on an image of type different
	 * from <code>BufferedImage.TYPE_INT_ARGB</code> and
	 * <code>BufferedImage.TYPE_INT_RGB</code> will unmanage the image.
	 * </p>
	 *
	 * @param img    the destination image
	 * @param x      the x location at which to start storing pixels
	 * @param y      the y location at which to start storing pixels
	 * @param w      the width of the rectangle of pixels to store
	 * @param h      the height of the rectangle of pixels to store
	 * @param pixels an array of pixels, stored as integers
	 * @throws IllegalArgumentException is <code>pixels</code> is non-null and of
	 *                                  length &lt; w*h
	 */
	public static void setPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
		if (pixels == null || w == 0 || h == 0) {
			return;
		} else if (pixels.length < w * h) {
			throw new IllegalArgumentException("pixels array must have a length" + " >= w*h");
		}

		int imageType = img.getType();
		if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB) {
			WritableRaster raster = img.getRaster();
			raster.setDataElements(x, y, w, h, pixels);
		} else {
			// Unmanages the image
			img.setRGB(x, y, w, h, pixels, 0, w);
		}
	}
	
	
	public static BufferedImage gaussianBlur(BufferedImage image,int radius) {
		return new StackGaussianBlurFilter(radius).filter(image, null);
	}

}
