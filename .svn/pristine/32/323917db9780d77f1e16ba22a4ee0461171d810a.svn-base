package com.mhdt.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import com.mhdt.Print;
import com.mhdt.system.Monitor;
import com.mhdt.system.WindowClosing;
import com.mhdt.toolkit.FrameRate;
import com.mhdt.toolkit.RamUsage;

/**
 * My basic of canvas.
 * @author LazyToShow <br>
 *         Email: 282854237@qq.com <br>
 *		   Update: 2016/08/04 
 *			<blockquote>
 *				1.为了减少渲染线程不必要的计算和规范化更新线程,去除 render方法 {@link #render(Graphics2D g2d)}中 <strong>elapasedTime</strong>参数,
 *				更新到 update {@link #update(long elapsedTime)}方法中,以后涉及到计算(可忽略小量计算)统一到Update方法中进行操作.
 *			</blockquote>
 */
public abstract class Canvas extends JPanel
		implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Monitor, WindowClosing {

	private static final long serialVersionUID = -3576152091142892270L;

	/** date is or not updated end. */
	private volatile boolean isUpdated = false;

	private final Object UPDATE_LOCK = new Object();

	private Thread rendThread;
	private Thread updateThread;

	private BufferedImage bf;
	private VolatileImage vi;
	private Graphics2D g2d;
	private int FPS = 40;
	public boolean drawing = true;
	private boolean showFPS = true;
	private boolean showRAM = true;
	private long lastTime = 0;
	private boolean hardwareAcceleration = true;
	// FPS and RAM montinor .
	private final FrameRate frameRate = new FrameRate();
	private RamUsage ramMotitor = new RamUsage();

	public Canvas() {
		setBackground(Color.black);
		rendThread = new RendThread();
		updateThread = new UpdateThread();
		setIgnoreRepaint(true);
		setFocusable(true);
		requestFocus(true);

		setLayout(null);

		// 禁止tab键切换焦点
//		Set<AWTKeyStroke> keystrokes = new HashSet<AWTKeyStroke>(
//				getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//		keystrokes.remove(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
//		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keystrokes);

		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		frameRate.initialize();
	}

	private synchronized final void draw() {
		frameRate.calculate();
		Graphics2D g = (Graphics2D) this.getGraphics();
		if (g != null) {
			g2d.clearRect(0, 0, getWidth(), getHeight());
			this.render(g2d);
			
			if(vi!=null){
				g.drawImage(vi, 0, 0, this);
			}else{
				g.drawImage(bf,0,0,this);
			}
			
			g.finalize();
		}

	}

	private void createBackBuffer() {
		vi = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleVolatileImage(getWidth(), getHeight());
		int val = vi.validate(getGraphicsConfiguration());
		if( val == VolatileImage.IMAGE_INCOMPATIBLE || !hardwareAcceleration){
			Print.info("Init render: validate="+val+".(Does not support hardware acceleration)");
			vi = null;
			bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_USHORT_565_RGB);
			g2d = (Graphics2D) bf.getGraphics();
		}else{
			Print.info("Init render , val="+val+". Start the hardware acceleration");
			g2d = (Graphics2D) vi.getGraphics();
		}
		
		
	}

	/**
	 * Subclasses override to achieve animationSubclasses override to achieve animationSubclasses override to achieve animation
	 * @param g2d
	 */
	protected void render(Graphics2D g2d) {
		drawComponents(g2d);
		drawFPS(g2d);
		drawRAM(g2d);
	}

	protected abstract void update(long elapsedTime);

	private final class UpdateThread extends Thread {
		private UpdateThread() {
			this.setName("Caculate Thread");
			this.setDaemon(true);
		}

		@Override
		public void run() {
			long currentTime = System.currentTimeMillis();
			lastTime = currentTime;
			synchronized (UPDATE_LOCK) {
				while (drawing) {
					currentTime = System.currentTimeMillis();
					update(currentTime-lastTime);
					lastTime = currentTime;
					isUpdated = true;
					try {
						UPDATE_LOCK.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	private final class RendThread extends Thread {
		private RendThread() {
			this.setName("Render Thread");
			this.setDaemon(true);
		}

		@Override
		public void run() {
			long fpstime = (long) ((Double.valueOf(1000) / Double.valueOf(FPS)) * 1000000);
			long now = 0;// 渲染之前的时间
			long total;// 渲染一次的用时

			while (drawing) {
				now = System.nanoTime();
				if (isVisible() && isShowing()) {
					draw();
				}

				if (isUpdated) {
					synchronized (UPDATE_LOCK) {
						UPDATE_LOCK.notify();
						isUpdated = false;
					}

				}

				total = System.nanoTime() - now;

				if (total > fpstime)
					continue;

				try {
					Thread.sleep((fpstime - (System.nanoTime() - now)) / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				while ((System.nanoTime() - now) < fpstime) {
					System.nanoTime();
				}

			}
			synchronized (UPDATE_LOCK) {
				UPDATE_LOCK.notify();
			}

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_F:
				if (showFPS) {
					showFPS = false;
				} else {
					showFPS = true;
				}
				break;

			case KeyEvent.VK_R:
				if (showRAM) {
					showRAM = false;
				} else {
					showRAM = true;
				}
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public final int getFPS() {
		return FPS;
	}

	public final void setFPS(int fPS) {
		FPS = fPS;
	}

	@Override
	public void drawFPS(java.awt.Graphics g) {
		if (showFPS) {
			g.setColor(Color.BLACK);
			g.setFont(UImanager.Font_14);
			g.drawString(frameRate.getFrameRate(), 20 + 1, 20 + 1);

			g.setColor(Color.WHITE);
			g.drawString(frameRate.getFrameRate(), 20, 20);

		}

	}

	@Override
	public void drawRAM(java.awt.Graphics g) {
		if (showRAM) {
			g.setColor(Color.BLACK);
			g.setFont(UImanager.Font_14);
			g.drawString(ramMotitor.getRamUsage(30), 21, 41);

			g.setColor(Color.WHITE);
			g.setFont(UImanager.Font_14);
			g.drawString(ramMotitor.getRamUsage(30), 20, 40);
		}

	}

	
	protected final void drawComponents(Graphics g) {//渲染组件
		if(getComponents().length<1)return;
		Component[] comps =getComponents();
		for (int i = comps.length-1; i >= 0; i--) {
			Component c = comps[i];
			if(c.isVisible()){
				Graphics2D g2 = (Graphics2D) g.create(c.getX(), c.getY(), c.getWidth(), c.getHeight());
				
				try{
					c.paint(g2);
				}catch (Exception e) {
					
				}
				
				g2.dispose();
			}
		}
	}

	public final void setVisible(boolean flag) {

		drawing = flag;

		if (!rendThread.isAlive())
			rendThread.start();

		if (!updateThread.isAlive()) {
			updateThread.start();
		}

	}

	@Override
	public final void setSize(int width, int height) {
		super.setSize(width, height);
		createBackBuffer();
	}

	@Override
	public final void setSize(Dimension d) {
		super.setSize(d);
		createBackBuffer();
	}

	@Override
	public final void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		createBackBuffer();
	}
	
	public void clearCompoments(){
		this.removeAll();
	}

	@Override
	public void onWindowClosing() {
		drawing = false;
		try {
			Print.info("Closing RendThread...");
			drawing = false;
			rendThread.join();
			Print.info("RendThread closed.");
			updateThread.join();
			Print.info("UpdateThread closed.");
			Print.info("Application closed.");
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public FrameRate getFrameRate() {
		return frameRate;
	}

	public boolean isShowFPS() {
		return showFPS;
	}

	public void setShowFPS(boolean showFPS) {
		this.showFPS = showFPS;
	}

	public boolean isShowRAM() {
		return showRAM;
	}

	public void setShowRAM(boolean showRAM) {
		this.showRAM = showRAM;
	}

	public boolean isHardwareAcceleration() {
		return hardwareAcceleration;
	}

	public void setHardwareAcceleration(boolean hardwareAcceleration) {
		this.hardwareAcceleration = hardwareAcceleration;
	}
	
	
	
	

}
