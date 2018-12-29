package com.mhdt.scheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * <pre>
 * Multithreaded task scheduler.
 * @author 懒得出风头
 * Date: 2016/09/14
 * Time: 14:59
 * </pre>
 */
public abstract class CompositeScheduler implements Runnable{
	
	protected ExecutorService service ;
	
	protected static final int core_thread = Runtime.getRuntime().availableProcessors()*2;
	
	CompositeScheduler(){
		service = Executors.newFixedThreadPool(core_thread);
	}
	
	/**
	 * Set the maximum number of threads in the constructor
	 * @param maxThreadCount
	 */
	final CompositeScheduler scheduler(int maxThreadCount){
		this.maxThreadCount = maxThreadCount>0?maxThreadCount:1;
		service = Executors.newFixedThreadPool(this.maxThreadCount);
		return this;
	}
	
	private Queue<Runnable> queue = new ConcurrentLinkedQueue<Runnable>();
	
	/**
	 * To the registered task scheduler
	 * @param runnable
	 */
	public final boolean registed(Runnable runnable){
		
		if(runnable==null)
			return false;
		
		if (service.isTerminated())
			throw new RuntimeException("The moment can't registed - \" this scheduler already shutDown.\"");
		return queue.add(runnable);
	}
	
	private final Runnable getTask() {
		
		if(queue!=null && !queue.isEmpty()){
			return queue.poll();
		}
		
		return null;
	}
	
	
	/**
	 * Single thread task execution.
	 * @param task
	 */
	final void singleExecute(){
		checkService();
		service.execute(this);
	}
	
	protected int maxThreadCount;
	
	private final void checkService() {
		if(service==null || service.isTerminated()){
			service = Executors.newFixedThreadPool(maxThreadCount);
		}
	}

	/**
	 * Multithreaded task execution.
	 */
	final void multiExcute(){
		
		checkService();
		new Thread(new Runnable() {
				@Override
				public void run() {
					do{
						Runnable runnable = getTask();
						
							if(runnable!=null){
								service.execute(runnable);
							}else{
								sleep(100);
							}
						
						}while(!service.isTerminated());
				}
				
			}).start();
		
			atomicBoolean.compareAndSet(false, true);
	}
	
	@Override
	public void run() {
		
		do{
			
			Runnable runnable = getTask();
			if(runnable!=null){
				runnable.run();
			}else{
				sleep(1000);
			}
			
		}while(true);
		
	}
	
	private AtomicBoolean atomicBoolean = new AtomicBoolean();

	/**
	 * This method will enter the blocked until all tasks within the scheduler is complete.
	 */
	public void waitAndShutDown(){
		
			sleep(1000);
			boolean flag = atomicBoolean.get();
			if(!flag)return;
			atomicBoolean.compareAndSet(true, false);
			
			
			service.shutdown();
			while(true){
				if(service.isTerminated())break;
				sleep(2000);
			}
		
	}
	
	private final void sleep(long timelong){
		
		try {
			Thread.sleep(timelong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

}
