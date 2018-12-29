package com.mhdt.scheduler;

/**
 * <pre>
 * Abstract the scheduler interface
 * @author 懒得出风头 
 * Date: 2016/12/26
 * Time: 14:57
 * </pre>
 */
public interface Scheduler {
	
	/**
	 * Registed a new task.
	 * @param run
	 * @return
	 */
	public boolean registed(Runnable run);
	
	/**
	 * Registered tasks.
	 */
	public void execute();
	
	/**
	 * This method will block until the scheduler task is complete.
	 */
	public void waitAndShutDown();

}
