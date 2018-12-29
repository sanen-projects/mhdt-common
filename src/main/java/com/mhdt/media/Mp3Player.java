package com.mhdt.media;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.processing.FilerException;

import com.mhdt.Print;

import javazoom.jl.player.Player;

/**
 * <p>
 * Music play : single play, loop play and change music . 
 * </p>
 * 
 * @author 懒得出风头
 *         <p>
 *         Date: 2016/3/4<br>
 *         Time: 17:34 <br>
 *         Email: 282854237@qq.com<br>
 *         lastUpdate: 2016/06/08 16:20
 */
public class Mp3Player {
	
	private Player player;
	private Thread thread;
	private Object source;
	private volatile boolean loop = true;	

	public Mp3Player(File file) throws FilerException {
		
		if(file.isFile() && file.exists()){
			source = file;
		}else {
			Print.error("文件不存在或不可用"); 
			throw new FilerException("");
		}
		
	}
	
	public Mp3Player(InputStream in) {
		source = in;
	}


	/**
	 * Single play .
	 */
	public final void play() {
		thread = new Thread(new Runnable() {
			public void run() {
				try {
					player = new Player(load(source));
					player.play();
				} catch (Exception e) {
					try {
						thread.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	/**
	 * Cycle to play.
	 */
	public final void loop() {
		loop = true;
		thread = new Thread(new Runnable() {
			public void run() {
				while (loop) {
					try {
						player = new Player(load(source));
						player.play();
					} catch (Exception e) {
						loop = false;
						try {
							thread.join();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}

			}
		});
		thread.start();
	}

	/**
	 * change MP3.
	 * @param file
	 */
	public final void change(File file) {
		
		source = file;
		
		if (loop) {
			stop();
			loop = true;
			loop();
		} else {
			stop();
			play();
		}
	}

	/**
	 * Stop not pause .
	 */
	public final void stop() {
		if (loop) {
			loop = false;
			player.close();
			
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				player.close();
			} catch (NullPointerException e1) {
				Print.error(" Unable to stop source is empty");
				throw e1;
			}
		}
	}

	private final BufferedInputStream load(Object obj) {
		try {
			BufferedInputStream bis = null;
			
			if(obj instanceof File){
				bis = new BufferedInputStream(new FileInputStream((File)obj));
			}else if(obj instanceof InputStream){
				bis = new BufferedInputStream((InputStream)obj);
			}else{
				Print.error("can't play the source .");
			}
			
			return bis;
			
		} catch (Exception e) {
			Print.error("The file can not find a file : \\" + obj.toString() + "\\");
		}
		return null;
	}
	
	
	

}
