package com.mhdt.toolkit;

/**
 * Frames to monitor
 * @author 懒得出风头<p>
 * Date: 2016/3/4<br>
 * Time: 11:39<br>
 * Email: 282854237@qq.com
 * By 《Java2D游戏编程》  autor:TimothyWright
 */

public class FrameRate {
	/** FPS: frameCount */				private String frameRate;
	/**	recoud last excecute time	*/	private long lastTime;
	/** Frames Per Secound */			private int frameCount;
	/** the time of  accumulation */	private long delta;
	/** the time of interval */			private long interval;
	
	/**
	 * Initialize the calculation Frames .
	 */
	public final void initialize(){
		frameRate = "FPS: 0";
		lastTime = System.currentTimeMillis();
	}
	
	/**
	 * Begin Caculate The Frames.
	 */
	public final void calculate(){
		long current = System.currentTimeMillis();
		//record interval between two trains
		delta += interval = current-lastTime;
		lastTime = current;
		frameCount++;
		if(delta>=1000){
			delta-=1000;
			frameRate = "FPS: "+frameCount;
			frameCount=0;
		}
	}
	/**
	 * The number of response one second executable program
	 * @return String
	 */
	public final String getFrameRate(){
		return frameRate;
	}
	
	/**
	 * Reflect the interval of two program execution
	 * @return second
	 */
	public final int getInterval(){
		return (int) interval;
	}
}
