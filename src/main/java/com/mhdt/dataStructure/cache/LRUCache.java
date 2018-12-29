package com.mhdt.dataStructure.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *根据最近访问记录消除数据
 *Algorithm, according to a recent visit to record to eliminate  data<p>
 *its principle is that if the data was recently visited accessed several probability is relatively high in the future, 
 *the realization of the most common is the use of a linked list to save the cached data, detailed specific algorithm is as follows:
 *<blockquote>
 *1. The new data is inserted into the list head;<br>
 *2. When the cached data accuracy, the data will be moved to the list head;<br>
 *3. When the list is full, will be discarded data at the end of the list;
 *</blockquote>
 * @author 懒得出风头 <br>
 * Date: 2016/08/05<br>
 * Time: 15:07
 */
public class LRUCache<K,V> extends AbstractCache<K, V>{
	
	private boolean openExpire = false;
	
	
	 public LRUCache(int cacheSize){
		this(cacheSize, 0);
	}

	 /**
	  *Algorithm, according to a recent visit to record to eliminate  data<p>
	  *its principle is that if the data was recently visited accessed several probability is relatively high in the future, 
	  *the realization of the most common is the use of a linked list to save the cached data, detailed specific algorithm is as follows:
	  *<blockquote>
	  *1. The new data is inserted into the list head;<br>
	  *2. When the cached data accuracy, the data will be moved to the list head;<br>
	  *3. When the list is full, will be discarded data at the end of the list;
	  *</blockquote>
	  * @author 懒得出风头 <br>
	  * Date: 2016/08/05<br>
	  * Time: 15:07
	  */
	public LRUCache(int cacheSize, long defaultExpire) {
		super(cacheSize, defaultExpire);
		
				this.cacheMap = new LinkedHashMap<K, CacheObject<V>>( cacheSize +1 , 1f,true ) {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected boolean removeEldestEntry(
							Map.Entry<K, CacheObject<V>> eldest) {

						return LRUCache.this.removeEldestEntry(eldest);
					}

				};
	}

	private boolean removeEldestEntry(Map.Entry<K, CacheObject<V>> eldest) {
		if (this.cacheSize == 0)
			return false;
		return size() > cacheSize;
	}

	
	
	@Override
	public void put(K key, V value, long survival) {
		if(openExpire==false && survival>0)openExpire=true;
		super.put(key, value, survival);
	}

	@Override
	protected int eliminateCache() {

		if(!isOpenExpire())
			return 0;
		
		Iterator<CacheObject<V>> iterator = cacheMap.values().iterator();
		int count  = 0 ;
		while(iterator.hasNext()){
			CacheObject<V> cacheObject = iterator.next();
			
			if(cacheObject.isExpired() ){
				iterator.remove(); 
				count++ ;
			}
		}
		
		return count;
	}
	
	private boolean isOpenExpire(){
		return defaultExpira>0 || openExpire;
	}

}
