package com.mhdt.test;

import java.awt.Color;

import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class ProgressTest extends JWindow implements Runnable {
	private static final long serialVersionUID = 8288483511092760045L;
	private JProgressBar progress; // 进度条

    
    public ProgressTest(String str) {
        progress = new JProgressBar(1, 100); // 实例化进度条

        progress.setStringPainted(true);      // 描绘文字

     
        progress.setBackground(Color.PINK); // 设置背景色
        
        this.add(progress);
        this.setBounds(200, 300, 250, 50);
        this.setVisible(true);
    }

    public void run() {
        while(true) {
            for(int i=0; i<100; i++) {
                try {
                    progress.setValue(progress.getValue() + 1); // 随着线程进行，增加进度条值

                    progress.setString(progress.getValue() + "%");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progress.setValue(0);
            progress.setString(0+"%");
        }
    }
    
    public static void main(String[] args) {
        ProgressTest pb = new ProgressTest("Test JProcessBar");
        Thread t = new Thread(pb);
        t.start();
    }
}
