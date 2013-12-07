package com.crazy.pss.common.cache;

import java.util.Collection;
import java.util.Set;

public interface Cache {

	public abstract <K,V> void put(K key, V entry);

	public abstract <K,V> V get(K key);

	public abstract <K> void remove(K key);

	public abstract <K> boolean containsKey(K key);

	public abstract <K> Set<K> keySet();

	public abstract <V> Collection<V> values();

	public abstract void clear();

	public abstract int size();
	
	public abstract Object getInternalCache();
}
