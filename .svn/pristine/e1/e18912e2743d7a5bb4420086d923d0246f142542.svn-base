package com.mhdt.dataStructure.cache;

/**
 * 缓存管理器
 * @author 懒得出风头 <p>
 * Date: 2016/08/18 <br>
 * Time: 17:21
 */
public class CacheManager {
	
	private CacheManager(){
		
	}
	
	/**
	 * LRU(Least Recently Used ，最近最少使用)<p>
	 * 缓存根据使用情况来淘汰元素,如果最近数据被访问过,那么将来被访问的可能性就越大.
	 */
	public static <K,V> LRUCache<K, V> LRUCache(int cacheSize){
		return LRUCache(cacheSize, 0);
	}
	
	/**
	 * LRU(Least Recently Used ，最近最少使用)<p>
	 * 缓存根据使用情况来淘汰元素,如果最近数据被访问过,那么将来被访问的可能性就越大.
	 * @param cacheSize - 缓存大小
	 * @param defaultExpire - 默认的过期时间,单位(毫秒)
	 * @return
	 */
	public static <K,V> LRUCache<K, V> LRUCache(int cacheSize, int defaultExpire){
		return new LRUCache<K, V>(cacheSize, defaultExpire);
	}
	
	/**
	 * LFU(Least Frequently Used，最不经常使用)<p>
	 * 缓存根据使用频率来淘汰元素,使用次数最高的总在缓存头部.<p>
	 * @param cacheSize - 缓存大小
	 * @return
	 */
	public static <K,V> LFUCache<K, V> LFUCache(int cacheSize){
		return LFUCache(cacheSize, 0);
	}
	
	/**
	 * LFU(Least Frequently Used，最不经常使用)<p>
	 * 缓存根据使用频率来淘汰元素,使用次数最高的总在缓存头部.
	 * @param cacheSize - 缓存大小
	 * @param defaultExpire - 默认的过期时间,单位(毫秒)
	 * @return
	 */
	public static <K,V> LFUCache<K, V> LFUCache(int cacheSize, int defaultExpire){
		return new LFUCache<K, V>(cacheSize, defaultExpire);
	}
	
	/**
	 * FIFO(First In First Out ，先进先出)<p>
	 * 根据时间顺序,先进来的元素优先淘汰.
	 * @param cacheSize  - 缓存大小
	 * @return
	 */
	public static <K,V> FIFOCache<K,V> FIFOCacheCache(int cacheSize){
		return FIFOCacheCache(cacheSize, 0);
	}
	
	/**
	 * FIFO(First In First Out ，先进先出)<p>
	 * 根据时间顺序,先进来的元素优先淘汰.
	 * @param cacheSize - 缓存大小
	 * @param defaultExpire - 默认的过期时间,单位(毫秒)
	 * @return
	 */
	public static <K,V> FIFOCache<K,V> FIFOCacheCache(int cacheSize, int defaultExpire){
		return new FIFOCache<K,V>(cacheSize, defaultExpire);
	}
	
	
}
