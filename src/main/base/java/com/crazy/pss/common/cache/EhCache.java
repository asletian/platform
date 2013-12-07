package com.crazy.pss.common.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Element;

/**
 * 
 * @Description cache的实现
 * @see com.crazy.pss.common.cache.Cache
 * 
 * @author crazy/Y
 * @date 2013-7-19 下午9:55:51
 */
public class EhCache implements Cache{

	private net.sf.ehcache.Cache cache;
	
	@Override
	public <K, V> void put(K key, V entry) {
		cache.put(new Element(key, entry));
	}

	@Override
	public <K, V> V get(K key) {
		Element e = cache.get(key);
		return e == null ? null : (V)e.getValue();
	}

	@Override
	public <K> void remove(K key) {
		cache.remove(key);
	}

	@Override
	public <K> boolean containsKey(K key) {
		return cache.isKeyInCache(key);
	}

	@Override
	public <K> Set<K> keySet() {
		return new HashSet<K>(cache.getKeys());
	}

	@Override
	public <V> Collection<V> values() {
		Collection<String> keys = this.keySet();
		List<V> result = new ArrayList<V>();
		for(String key : keys)
			result.add((V)this.get(key));
		return result;
	}

	@Override
	public void clear() {
		cache.removeAll();
	}

	@Override
	public int size() {
		return this.cache.getSize();
	}

	@Override
	public Object getInternalCache() {
		return this.cache;
	}

}
