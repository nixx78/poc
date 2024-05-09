package lv.nixx.poc.perfmeeter.cache;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;


@Service
public class AlphaCache extends GenericCache {

    public AlphaCache() {
        super();
        put("a_key_1", "a1.value");
        put("a_key_2", "a2.value");
        put("a_key_3", "a3.value");
    }

    @Override
    @Timed("cache.alpha")
    public String get(String key) {
        return super.get(key);
    }


}
