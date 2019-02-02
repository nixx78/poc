package lv.nixx.poc.hazelcast.service;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;

import lombok.Setter;

@Setter
public abstract class GenericEntityManager<K, V> {

	protected HazelcastInstance hazelcastInstance;
	
	abstract String getMapName();

	protected IMap<K, V> getMap() {
		return hazelcastInstance.getMap(getMapName());
	}
	
	public V get(K key) {
		return getMap().get(key);
	}

	public void add(K key, V person) {
		getMap().set(key, person);
	}
	
	public V update(K key, V person) {
		return getMap().replace(key, person);
	}
	
	public V remove(K key) {
		return getMap().remove(key);
	}
	
	protected Collection<V> collectAndMap(Predicate<?,?> p) {
		return getMap().entrySet(p)
				.stream()
				.map(Entry::getValue)
				.collect(Collectors.toList());
	}

}
