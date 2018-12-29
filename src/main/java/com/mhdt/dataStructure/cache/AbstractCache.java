package com.mhdt.dataStructure.cache;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.mhdt.toolkit.DateUtility;

/**
 * Abstract cache Object .
 * DATE： 2016/08/05<br>
 * Time: 10:24
 * @author 懒得出风头
 */
public abstract class AbstractCache<K,V> implements Cache<K, V>{
	
	private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
	private final Lock readLock = cacheLock.readLock();
	private final Lock writeLock = cacheLock.writeLock();
	
	/** cache list */
	protected Map<K,CacheObject<V>> cacheMap;
	
	/** default 0, unlimited */		
	protected int cacheSize; 
	
	/** default value=0  Never expire */	
	protected long defaultExpira;   
	
	/**
	 * @param cacheSize 
	 * @param defaultExpirationTime - if the param is 0 , will never expire.
	 */
	public AbstractCache(int cacheSize ,long defaultExpirationTime){
		this.cacheSize  = cacheSize ;
		this.defaultExpira  = defaultExpirationTime ;
	}
	

	@Override
	public void put(K key, V value) {
		put(key, value, defaultExpira );
	}

	@Override
	public void put(K key, V value, long survival) {
		writeLock.lock();

		try {
			CacheObject<V> co = new CacheObject<V>(value, survival);
			
			if (isFull()) 
				eliminate() ;
			
			cacheMap.put(key, co);
		}
		finally {
			writeLock.unlock();
		}
	}

	@Override
	public V get(K key) {
		readLock.lock();

		try {
			CacheObject<V> entry = cacheMap.get(key);
			if (entry == null) {
				return null;
			}
			if (entry.isExpired()) {
				cacheMap.remove(key);
				return null;
			}

			return entry.getObject();
		}
		finally {
			readLock.unlock();
		}
	}

	@Override
	public int eliminate() {
		writeLock.lock();
		try {
			return eliminateCache();
		}
		finally {
			writeLock.unlock();
		}
	}
	
	/**
	 * Eliminate object implementation
	 * @return
	 */
	protected abstract int eliminateCache(); 


	@Override
	public boolean isFull() {
		if (cacheSize == 0) {
			return false;
		}
		return cacheMap.size() >= cacheSize;
	}

	@Override
	public void remove(K key) {
		writeLock.lock();
		try {
			cacheMap.remove(key);
		}
		finally {
			writeLock.unlock();
		}
		
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			cacheMap.clear();
		}
		finally {
			writeLock.unlock();
		}
	}

	

	/**
	 * get default expire time.
	 * @return
	 */
	@Override
	public long getDefaultExpire() {
		return defaultExpira;
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	@Override
	public int getCacheSize() {
		return cacheSize;
	}
	
	@Override
	public int size() {
		return cacheMap.size();
	}
	
	@SuppressWarnings("hiding")
	class CacheObject<V> {
		
		final V value;
		long lastAccess;		/** last view time*/
		long accessCount;		/** view count */
		long expirationTime;				/** live time */
		
		CacheObject(V value, long ttl) {
			this.value = value;
			this.expirationTime = ttl;
			this.lastAccess = System.currentTimeMillis();
		}

		/** Is or not expires */
		boolean isExpired() {
			if (expirationTime == 0) {
				return false;
			}
			return lastAccess + expirationTime < System.currentTimeMillis();
		}
		
		V getObject() {
			lastAccess = System.currentTimeMillis();
			accessCount++;
			return value;
		}

		@Override
		public String toString() {
			return "value=" + value
					+ ", lastAccess=" + DateUtility.intToString((int)lastAccess/1000) + ", accessCount="
					+ accessCount + ", liveTime=" + expirationTime + "\n";
		}
		
		
    }
	
	@Override
	public String toString() {
		return "FIFOCache [cacheMap=" + cacheMap + " cacheSize=" + cacheSize
				+ " defaultExpire=" + defaultExpira+"]";
	}

}
