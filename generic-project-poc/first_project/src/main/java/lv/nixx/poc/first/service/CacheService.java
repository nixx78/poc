package lv.nixx.poc.first.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CacheService {

    @Autowired
    @Qualifier
    DataSource alphaDataSource;

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
