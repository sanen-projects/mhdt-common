package com.mhdt.dataStructure.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * First In First Out .<p>
 * Algorithm is based on the principle of the first in first out to eliminate the data, 
 * is one of the most simple implementation, specific algorithm is as follows:
 * <blockquote>
 * 1. New access data into FIFO queue tail, mobile data in a FIFO queue order<br>
 * 2. Eliminate the FIFO queue head of data
 * 
 * </blockquote>
 * @author 懒得出风头<br>
 * Date: 2016/08/05<br>
 * Time: 14:24
 */
public class FIFOCache<K,V> extends AbstractCache<K, V>{
	
	 FIFOCache(int cacheSize){
		this(cacheSize,0);
	}

	/**
	 * @param cacheSize 
	 * @param defaultExpire - if the param is 0 , will never expire.
	 */
	public FIFOCache(int cacheSize, long defaultExpire) {
		super(cacheSize, defaultExpire);
		cacheMap = new LinkedHashMap<K, CacheObject<V>>(cacheSize+1);
	}


	@Override
	protected int eliminateCache() {
		int count = 0;
		K firstKey = null;

		Iterator<K> iterator = cacheMap.keySet().iterator();
		while (iterator.hasNext()) {
			K key = iterator.next();
			CacheObject<V> cacheObject = cacheMap.get(key);

			if (cacheObject.isExpired()) {
				iterator.remove();
				count++;
			} else {
				if (firstKey == null)
					firstKey = key;
			}
		}

		//Delete expired objects or full, continue to delete the list first
		if (firstKey != null && isFull()) {
			cacheMap.remove(firstKey);
		}

		return count;
	}
	

}
