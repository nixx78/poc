package lv.nixx.poc.perfmeeter.service;

import io.micrometer.core.annotation.Timed;
import lv.nixx.poc.perfmeeter.cache.AlphaCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlphaService {

    private final AlphaCache cache;

    @Autowired
    public AlphaService(AlphaCache cache) {
        this.cache = cache;
    }

    @Timed(value = "service.alpha.process", description = "Time taken by AlphaService.process")
    public String process(String key) {
        return "Processed:" + System.currentTimeMillis() + "." + cache.get(key);
    }

}
