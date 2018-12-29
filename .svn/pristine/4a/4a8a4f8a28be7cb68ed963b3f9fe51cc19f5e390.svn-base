package com.mhdt.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JProgressBar;
import javax.swing.JWindow;

import com.mhdt.system.WindowClosing;

public class LoadWindow extends JWindow implements Runnable,WindowClosing {
	
	private static final long serialVersionUID = 1L;
	private JProgressBar progress; 

	    public LoadWindow(int width,int height) {
	    	
	    	setSize(width, height);
	        progress = new JProgressBar(1, 100);
	        progress.setStringPainted(true);     
	        progress.setBackground(Color.WHITE); 
	        progress.setForeground(Color.GRAY);
	    //    progress.setIndeterminate(true);
	        this.add(progress);
	        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((d.width-getWidth())/2, (d.height-getHeight())/2);
	        this.setVisible(true);
	    }

	    public void run() {
	        while(true) {
	            for(int i=1; i<101; i++) {
	                try {
	                    progress.setValue(i); 
	                    progress.setString("loading    "+progress.getValue());
	                    Thread.sleep(100);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	            progress.setString("正在初始化游戏  ...");
	            try {
					Thread.sleep(50000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            onWindowClosing();
	        }
	    }
	    
	    public static void main(String[] args) {
	    	UImanager.setStyle(UImanager.WINDOW);
	        LoadWindow pb = new LoadWindow(320,13);
	        Thread t = new Thread(pb);
	        t.start();
	    }

		@Override
		public void onWindowClosing() {
			this.dispose();
		}

}
