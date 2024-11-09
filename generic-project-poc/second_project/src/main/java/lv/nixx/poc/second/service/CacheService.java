package lv.nixx.poc.second.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

//@Service
public class CacheService {

    private final IMap<String, String> cache;

    public CacheService(HazelcastInstance hazelcastInstance) {
        this.cache = hazelcastInstance.getMap("test.map");
    }


    public void loadDataToCache() {
        cache.clear();
        long st = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            cache.put("k_" + i, st + "_" + i);
        }
    }

    public Map<String, String> getCacheContent() {
        return cache.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (t1, t2) -> t1, TreeMap::new)
                );
    }

}
