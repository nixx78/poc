package lv.nixx.poc.perfmeeter.cache;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class BetaCache extends GenericCache {


    public BetaCache(MeterRegistry registry) {
        super(registry, "beta");
        put("b_key_1", "b1.value");
        put("b_key_2", "b2.value");
        put("b_key_3", "b3.value");
    }

    @Override
    public String get(String key) {
        return super.get(key);
    }

}
