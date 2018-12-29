package com.mhdt.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class PlasmaDemo extends JComponent {

	private static final long serialVersionUID = -2236160343614397287L;
	private BufferedImage image = null;
	private int size = 256;

	public PlasmaDemo() {
		super();
		this.setOpaque(false);
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(getImage(), 5, 5, image.getWidth(), image.getHeight(), null);
	}

	private BufferedImage getImage() {
		if (image == null) {
			
			image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			int[] rgbData = new int[size * size];
			generateNoiseImage(rgbData);
			setRGB(image, 0, 0, size, size, rgbData);
			File outFile = new File("plasma.jpg");
			
			try {
				ImageIO.write(image, "png", outFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	public void generateNoiseImage(int[] rgbData) {
		int index = 0;
		int a = 255;
		int r = 0;
		int g = 0;
		int b = 0;

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				// set random color value for each pixel
				r = (int) (128.0 + (128.0 * Math.sin((row + col) / 8.0)));
				g = (int) (128.0 + (128.0 * Math.sin((row + col) / 8.0)));
				b = (int) (128.0 + (128.0 * Math.sin((row + col) / 8.0)));

				rgbData[index] = ((clamp(a) & 0xff) << 24) | ((clamp(r) & 0xff) << 16) | ((clamp(g) & 0xff) << 8)
						| ((clamp(b) & 0xff));
				index++;
			}
		}

	}

	private int clamp(int rgb) {
		if (rgb > 255)
			return 255;
		if (rgb < 0)
			return 0;
		return rgb;
	}

	public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			image.getRaster().setDataElements(x, y, width, height, pixels);
		else
			image.setRGB(x, y, width, height, pixels, 0, width);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Noise Art Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		// Display the window.
		frame.getContentPane().add(new PlasmaDemo(), BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(400 + 25, 450));
		frame.pack();
		frame.setVisible(true);
		
	}
}
