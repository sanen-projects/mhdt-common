package com.mhdt.net;

import java.util.concurrent.atomic.AtomicBoolean;

import com.mhdt.net.Urls.DownInfo;
import com.mhdt.net.Urls.Download;
import com.mhdt.scheduler.Scheduler;
import com.mhdt.scheduler.Schedulers;
import com.mhdt.toolkit.MathUtility;

/**
 * 默认实现间隔每秒打印下载进度 <br>
 * 重写{@link SimpleDownload#todo(DownInfo)}方法自定义下载中操作<br>
 * 
 * @author LazyToShow<br>
 *	Date : 2018/02/21<br>
 *	Time:  1:00
 *
 */
public class SimpleDownload implements Download{
	
	static final Scheduler scheduler = Schedulers.singleThreadSchedule();
	AtomicBoolean flag = new AtomicBoolean(true);

	@Override
	public final void loading(final DownInfo downInfo) {
		scheduler.registed(new Runnable() {
			@Override
			public void run() {
				 do{
					try {
						todo(downInfo);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}while((flag.get() || downInfo.getCurrent()<downInfo.getLength()));
			}
		});
	}
	
	public void stop() {flag.set(false);}

	protected void todo(DownInfo downInfo) {
		double a = MathUtility.keepDecimalPoint(downInfo.getCurrent()/1024d, 2);
		double b = MathUtility.keepDecimalPoint(downInfo.getLength()/1024d, 2);;
		System.out.println(a+"kb/"+b+"kb");
	}


}
