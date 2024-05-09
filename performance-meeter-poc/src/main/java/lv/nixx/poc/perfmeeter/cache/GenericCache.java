package lv.nixx.poc.perfmeeter.cache;

import java.util.HashMap;
import java.util.Map;

public class GenericCache {

    private final Map<String, String> map = new HashMap<>();

    public String get(String key) {
        return map.get(key);
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

}
