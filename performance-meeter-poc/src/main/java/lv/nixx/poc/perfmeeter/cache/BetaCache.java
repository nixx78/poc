package lv.nixx.poc.perfmeeter.cache;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;

@Service
public class BetaCache extends GenericCache {


    public BetaCache() {
        super();
        put("b_key_1", "b1.value");
        put("b_key_2", "b2.value");
        put("b_key_3", "b3.value");
    }

    @Override
    @Timed("cache.beta")
    public String get(String key) {
        return super.get(key);
    }

}
