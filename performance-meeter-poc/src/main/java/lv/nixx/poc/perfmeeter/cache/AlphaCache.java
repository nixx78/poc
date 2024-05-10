package lv.nixx.poc.perfmeeter.cache;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;


@Service
public class AlphaCache extends GenericCache {

    public AlphaCache(MeterRegistry registry) {
        super(registry, "alpha");
        put("a_key_1", "a1.value");
        put("a_key_2", "a2.value");
        put("a_key_3", "a3.value");
    }

    @Override
    public String get(String key) {
        return super.get(key);
    }


}
