package lv.nixx.poc.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Setter
public class CrudCacheOperationsImpl<K, V> implements CrudCacheOperations<K, V> {

    protected HazelcastInstance hazelcastInstance;

    @NonNull
    private String mapName;

    public CrudCacheOperationsImpl(HazelcastInstance hazelcastInstance, @NonNull String mapName) {
        this.hazelcastInstance = hazelcastInstance;
        this.mapName = mapName;
    }

    protected IMap<K, V> getMap() {
        return hazelcastInstance.getMap(mapName);
    }

    @Override
    public V get(K key) {
        return getMap().get(key);
    }

    @Override
    public void add(K key, V value) {
        getMap().put(key, value);
    }

    @Override
    public V update(K key, V value) {
        return getMap().replace(key, value);
    }

    @Override
    public V remove(K key) {
        return getMap().remove(key);
    }

    @Override
    public int size() {
        return getMap().size();
    }

    @Override
    public Collection<V> getValues(Predicate<?, ?> p) {
        return getMap().entrySet(p)
                .stream()
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }


}
