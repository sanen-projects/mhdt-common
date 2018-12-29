package com.mhdt.dataStructure.cache;

import java.util.HashMap;
import java.util.Iterator;

/**
 * <pre>
 * 基于历史数据访问频率算法来消除数据。
 * Based on historical data access frequency algorithm to eliminate the data.
 * its principle is that if the past data access, the more to be accessed several probability is relatively
 * high in the future.LFU of each block of data has a reference count, all data blocks according to the reference count sorting,
 * data blocks with the same reference counting is sorted by time.
 * 1. The new data is inserted into the queue tail (because the reference count to 1);<br>
 * 2. The data in the queue is visit, reference counting, queue reorder;<br>
 * 3. When you need to eliminate the data, will be ordered has a list of the last block of data to delete;
 * @author 懒得出风头 
 * Date: 2016/08/05
 * Time: 15:59
 * </pre>
 */
public class LFUCache<K,V> extends AbstractCache<K, V>{
	
	 LFUCache(int cacheSize){
		this(cacheSize, 0);
	}
	
	 /**
	  * 
	  * @param cacheSize - 缓存数量
	  * @param defaultExpire - 默认到期时间
	  */
	 public LFUCache(int cacheSize, long defaultExpire) {
		super(cacheSize, defaultExpire);
		cacheMap = new HashMap<K, CacheObject<V>>(cacheSize+1) ;
	}

	@Override
	protected int eliminateCache() {
		Iterator<CacheObject<V>> iterator = cacheMap.values().iterator();
		int count  = 0 ;
		long minAccessCount = Long.MAX_VALUE  ;
		while(iterator.hasNext()){
			CacheObject<V> cacheObject = iterator.next();
			
			if(cacheObject.isExpired() ){
				iterator.remove(); 
				count++ ;
				continue ;
			}else{
				minAccessCount  = Math.min(cacheObject.accessCount , minAccessCount)  ;
			}
		}
		
		if(count > 0 ) return count ;
		
		if(minAccessCount != Long.MAX_VALUE ){
			
			iterator = cacheMap.values().iterator();
			
			while(iterator.hasNext()){
				CacheObject<V> cacheObject = iterator.next();
				
				if(cacheObject.accessCount <= minAccessCount ){
					iterator.remove();
					count++ ;
				}
				
			}
			
		}
		
		return count;
	}


}
