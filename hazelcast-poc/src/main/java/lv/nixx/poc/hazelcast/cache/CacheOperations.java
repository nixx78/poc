package lv.nixx.poc.hazelcast.cache;

import com.hazelcast.query.Predicate;

import java.util.Collection;

public interface CacheOperations<K, V> {
    V get(K key);

    void add(K key, V person);

    V update(K key, V person);

    V remove(K key);

    Collection<V> getValues(Predicate<?, ?> p);

    int size();

}
