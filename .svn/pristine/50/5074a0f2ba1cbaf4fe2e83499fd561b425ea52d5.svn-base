package com.mhdt.toolkit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.mhdt.toolkit.SimpleRandom;

/**
 * <pre>
 * 验证码测试
 * @author 懒得出风头
 * Date: 2017/09/26
 * Time: 16:53
 * </pre>
 */
public class AuthcodeUtil {
	
	SimpleRandom random = new SimpleRandom();
	
	/**
	 * 生成指定内容验证码
	 * @param content - 内容
	 * @param width - 图片宽
	 * @param height - 图片高
	 * @param foreColor - 前景色，默认值，可为null
	 * @param backgroundColor - 背景色， 默认值，可为null
	 * @param isDrawInterferLine - 是否绘制干扰线
	 * @param isDrawNoise - 是否绘制噪点
	 * @return 验证图片{@link BufferedImage}
	 */
	public BufferedImage createAuthcode(
			String content,int width,int height,
			Color foreColor,Color backgroundColor,
			boolean isDrawInterferLine,boolean isDrawNoise){
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		
		renderHint(g2d);//抗锯齿
		drawBackground(g2d,backgroundColor,width,height);//绘制背景
		if(isDrawInterferLine)drawInterferingLine(g2d, width, height);//绘制干扰线
		if(isDrawNoise)drawNoise(bufferedImage);//绘制噪点
		drawStr(content,width,height,g2d,foreColor);//绘制内容	
		return bufferedImage;
	}
	
	private void renderHint(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	}

	private void drawBackground(Graphics2D g2d,Color backgroundColor,int width,int height) {
		g2d.setColor(backgroundColor==null?new Color(237, 246, 255):backgroundColor);
		g2d.fillRect(0, 0, width, height);
	}

	private void drawStr(String str,int width,int height,Graphics2D g2d,Color foreColor) {
		
		g2d.setColor(foreColor==null?new Color(83, 134, 139):foreColor);
		int fontSize = height-4;
		Font font = new Font("Algerian", Font.ITALIC&Font.BOLD, fontSize);
	    g2d.setFont(font);
	    
	    char[] chars = str.toCharArray();
        for(int i = 0; i < str.length(); i++){
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(
            		Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
            		(width / str.length()) * i + fontSize/2, 
            		height/2);
            g2d.setTransform(affine);
            g2d.drawChars(chars, i, 1, 
            		((width-10) / str.length()) * i + 5,
            		height/2 + fontSize/3);
        }
         
        g2d.dispose();

	}

	/**
	 * 绘制干扰线
	 */
	private void drawInterferingLine(Graphics2D g2d,int width,int height) {
		//绘制干扰线
        for (int i = 0; i < height/4; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(width/2) + 1;
            int yl = y+random.nextInt(height/3) + 1;
            g2d.setColor(random.nextColor());
            g2d.drawLine(x, y, x + xl + 40, yl);
        }
	}
	
	/**
	 * 添加噪点
	 */
	private void drawNoise(BufferedImage bufferedImage) {
		 
		 float yawpRate = 0.03f;// 噪声率
	     int area = (int) (yawpRate * bufferedImage.getWidth() * bufferedImage.getHeight());
	     for (int i = 0; i < area; i++) {
	    	 int x = random.nextInt(bufferedImage.getWidth());
	         int y = random.nextInt(bufferedImage.getHeight());
	         int rgb =  random.nextColor().getRGB();
	         bufferedImage.setRGB(x, y, rgb);
	     }
	}
	
	
}
