package lv.nixx.poc.second.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CacheService {

    private final ApplicationContext applicationContext;

    public CacheService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private IMap<String, String> getCache() {
        return applicationContext.getBean("alphaHazelcast", HazelcastInstance.class).getMap("test.map");
    }

    public void loadDataToCache() {
        IMap<String, String> cache = getCache();
        cache.clear();
        long st = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            cache.put("k_" + i, st + "_" + i);
        }
    }

    public Map<String, String> getCacheContent() {
        IMap<String, String> cache = getCache();

        return cache.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (t1, t2) -> t1, TreeMap::new)
                );
    }

}
