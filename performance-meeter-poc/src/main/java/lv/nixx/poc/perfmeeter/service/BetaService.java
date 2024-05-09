package lv.nixx.poc.perfmeeter.service;

import io.micrometer.core.annotation.Timed;
import lv.nixx.poc.perfmeeter.cache.BetaCache;
import org.springframework.stereotype.Service;

@Service
public class BetaService {

    private final BetaCache cache;

    public BetaService(BetaCache cache) {
        this.cache = cache;
    }

    @Timed(value = "service.beta.process", description = "Time taken by BetaService.process")
    public String process(String key) {
        return "Processed:" + System.currentTimeMillis() + "." + cache.get(key);
    }

}
