package com.mhdt.test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 引用测试
 * @author 懒得出风头
 * Date: 2016/08/02
 */
public class Quote_Test {
	
	public static void main(String[] args) {
		testSoftReference();
		//testWeakReference();
	}
	
	/**
	* 实现一个软引用
	*/
	public static  void testSoftReference(){
		//创建一个强引用Test
		String str = new String("Test");
		//创建一个引用队列
		ReferenceQueue<String> rq = new ReferenceQueue<String>();
		//实现一个软引用，将强引用类型str和是实例化的rq放到软引用实现里面
		SoftReference<String> srf = new SoftReference<String>(str,rq);
		str = null;
		//通过软引用get方法获取强引用中创建的内存空间Test值
		System.out.println(srf.get());
		//程序执行下gc现在jvm的内存空间还有很多所以gc不会回收str的对象
		System.gc();
		//所以这里执行get还是会打印Test的
		System.out.println(srf.get());
	}
	
	/**
	* 实现一个弱引用
	*/
		public static  void testWeakReference(){
			String str = new String("Test");
			ReferenceQueue<String> rq = new ReferenceQueue<String>();
			//实现一个弱引用，将强引用类型str和是实例化的rq放到弱引用实现里面
			WeakReference<String> wrf = new WeakReference<String>(str,rq);
			//给str给值为null。 然后再通过WeakReference弱引用的get()方法获得Test对象的引用
			str = null;
			//程序多执行gc，提高gc执行线程频率来回收对象。
			System.gc();
			//假如它还没有被垃圾回收，那么接下来在第执行wrf.get()方法会返回Test对象的引用，并且使得这个对象被str1强引用。
			//再接下来在行执行rq.poll()方法会返回null，因为此时引用队列中没有任何引用。
			String str1 = wrf.get(); 
			System.out.println(str1);
			//ReferenceQueue的poll()方法用于返回队列中的引用，如果没有则返回null。
			@SuppressWarnings("unchecked")
			Reference<String> ref= (Reference<String>) rq.poll();
			System.out.println("弱引用已消失"+ref);
			if(ref!=null){
				System.out.println(ref.get());
			}
	}
}
