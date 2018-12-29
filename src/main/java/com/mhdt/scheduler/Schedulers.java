package com.mhdt.scheduler;

/**
 * <pre>
 * Scheduler factory.
 * @author 懒得出风头
 * Date: 2016/12/26
 * Time: 15:45
 * </pre>
 */
public class Schedulers {
	
	private Schedulers(){
		
	}
	
	/**
	 * Single thread tasks assigned.
	 * @return
	 */
	public static Scheduler singleThreadSchedule(){
		SingleThreadSchedule singleThreadSchedule =  new SingleThreadSchedule();
		return (Scheduler) singleThreadSchedule.scheduler(1);
	}
	
	/**
	 * Multi-threaded tasks assigned to realize more.The default number of threads associated with the current computer CPU core*2.
	 * @return
	 */
	public static Scheduler multiThreadedScheduler(){
		return new MultiThreadedScheduler();
	}
	
	/**
	 * Multi-threaded tasks assigned to realize more and Specifies the number of threads at the same time.
	 * @return
	 */
	public static Scheduler multiThreadedScheduler(int maxThreadCount){
		MultiThreadedScheduler multiThreadedScheduler = new MultiThreadedScheduler();
		return (Scheduler) multiThreadedScheduler.scheduler(maxThreadCount);
	}
	
	

}
