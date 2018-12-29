package com.mhdt.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import com.mhdt.system.WindowClosing;

/**
 * Common window.
 * @author  懒得出风头 <br>
 * Date： 2016/03/31 <br>
 * Time: 13:00
 */
public class Window extends JFrame implements WindowListener{
	private AtomicBoolean minimize = new AtomicBoolean();
	
	private static final long serialVersionUID = 9172229125891839930L;

	public Window(){
		setTitle("mhdt.com");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
	}
	
	public Window(int width,int height){
		setTitle("mhdt.com");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(width, height);
		this.addWindowListener(this);
	}
	

	public Window(String title){
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
	}
	
	public Window(String title,int width,int height){
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		setSize(width, height);
	}
	
	public final  void setCenterInDesktop(){
		if(getWidth()<1 || getHeight()<1)return;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width-getWidth())/2, (d.height-getHeight())/3);
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize){
		super.setPreferredSize(preferredSize);
		setSize(preferredSize);
		
	}
	
	/**
	 * 由主面板执行{@link windowClosing}操作
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		Window c =  (Window) e.getComponent();
		if(c.getContentPane() instanceof WindowClosing){
			WindowClosing action = (WindowClosing)c.getContentPane();
			action.onWindowClosing();
		}else{
			System.out.println("程序已关闭！");
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		minimize.compareAndSet(true, false);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		minimize.compareAndSet(false, true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	public boolean  isMinimize() {
		return minimize.get();
	}

	public void response(){
		if(isMinimize()){
			this.requestFocus();
		}
	}
	
	

}
