package com.mhdt.toolkit;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.mhdt.io.IMGIO;

/**
 * 
 *
 * @author LazyToShow <br>
 *         Date: Dec 5, 2018 <br>
 *         Time: 10:00:24 AM
 */
public class Platform {

	/**
	 * Gets the current platform name
	 * 
	 * @return
	 */
	public static String getOSName() {
		return System.getProperties().getProperty("os.name");
	}

	/**
	 * Gets the local computer name and IP address
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public static InetAddress localHost() throws UnknownHostException {

		return InetAddress.getLocalHost();
	}

	public static String home() {
		return System.getenv().get("HOME");
	}

	public static String javaVersion() {
		return System.getProperty("java.version");
	}

	public static String language() {
		return System.getProperty("user.language");
	}

	public static String javaHome() {
		return System.getProperty("java.home");
	}

	public static String userName() {
		return System.getProperty("user.name");
	}

	public static String osVersion() {
		return System.getProperty("os.version");
	}

	public static String userCountry() {
		return System.getProperty("user.country");
	}

	public static String vmName() {
		return System.getProperty("java.vm.name");
	}

	/**
	 * Open a file or folder
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void openFile(File file) throws IOException {

		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
			Desktop.getDesktop().open(file);
	}

	/**
	 * Execute the specified command
	 * 
	 * @param command
	 */
	public static void exec(String command) {

		try {

			Assert.notNull(command, "Command is null");

			Process process = Runtime.getRuntime().exec(StringUtility.removeBlankChar(command));

			try (BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
					BufferedReader br = new BufferedReader(new InputStreamReader(bis))) {

				String line;
				while ((line = br.readLine()) != null)
					System.out.println(line);

				process.waitFor();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Take a screenshot of the current screen
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static Image captureScreen(File file) throws Exception {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		return robot.createScreenCapture(screenRectangle);
	}

	public static void openImage(Image image) throws IOException {
		Assert.notNull(image, "Image is null");
		File file = new File("temp/" + System.currentTimeMillis() + ".png");
		IMGIO.save(image, file);
		openFile(file);
	}

	public static SysClipboard getClipboard() {
		return SysClipboard.getInstance();
	}

	public static class SysClipboard {

		static SysClipboard sysClipboard;

		static SysClipboard getInstance() {
			if (sysClipboard == null)
				sysClipboard = new SysClipboard();

			return sysClipboard;
		}

		/**
		 * 
		 * @return
		 * @throws UnsupportedFlavorException
		 * @throws IOException
		 */
		public String getText() throws UnsupportedFlavorException, IOException {

			Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable clipTf = sysClip.getContents(null);

			if (clipTf != null && clipTf.isDataFlavorSupported(DataFlavor.stringFlavor))
				return (String) clipTf.getTransferData(DataFlavor.stringFlavor);

			return null;
		}

		/**
		 * 
		 * @return
		 * @throws Exception
		 */
		public Image getImage() throws Exception {

			Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable cc = sysc.getContents(null);

			if (cc != null && cc.isDataFlavorSupported(DataFlavor.imageFlavor))
				return (Image) cc.getTransferData(DataFlavor.imageFlavor);

			return null;
		}

		/**
		 * 
		 * @param text
		 */
		public void setText(String text) {
			Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable tText = new StringSelection(text);
			sysc.setContents(tText, null);
		}

		/**
		 * 
		 * @param image
		 * @throws Exception
		 */
		public void setImage(final Image image) throws Exception {

			Transferable trans = new Transferable() {

				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[] { DataFlavor.imageFlavor };
				}

				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return DataFlavor.imageFlavor.equals(flavor);
				}

				public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

					if (isDataFlavorSupported(flavor))
						return image;

					throw new UnsupportedFlavorException(flavor);
				}

			};

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
		}

	}

}
