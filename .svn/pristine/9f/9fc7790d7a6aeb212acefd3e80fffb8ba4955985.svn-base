package com.mhdt.dataStructure.cache;

/**
 * Cache interface.
 * @author 懒得出风头<br>
 * DATE： 2016/08/05<br>
 * Time: 10:24
 * @param <K> -Key
 * @param <V> -Value
 */
public interface Cache<K,V> {
	/**
	 * return current Cache size.
	 * @return
	 */
	int size();
	
	/**
	 * Get the default survival time.
	 * @return
	 */
	 long getDefaultExpire();
	
	/**
	 * Add value to the cache object.
	 * @param key
	 * @param value
	 */
	void put(K key,V value);
	
	/**
	 * Add value to the cache object, and specified time to live.
	 * @param key
	 * @param value
	 * @param survival
	 */
	void put(K key,V value,long survival);
	
	
	/**
	 * Find by key.
	 * @param key
	 * @return
	 */
	V get(K key);
	
	
	/**
	 * Be deleted object size
	 * @return
	 */
	int eliminate();
	
	/**
	 * If the cache is full.
	 * @return
	 */
	boolean isFull();
	
	/**
	 * Remove by key.
	 * @param key
	 */
	void remove(K key);
	
	/**
	 * Empty cache .
	 */
	void clear();
	
	
	/**
	 * Return cache size.
	 * @return
	 */
	int getCacheSize();
	
	/**
	 * If the cache is Empty.
	 * @return
	 */
	boolean isEmpty();
	
}
