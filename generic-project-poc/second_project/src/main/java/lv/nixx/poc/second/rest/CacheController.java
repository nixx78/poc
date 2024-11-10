package lv.nixx.poc.second.rest;

import lv.nixx.poc.second.service.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CacheController {

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/cache/load")
    void loadData() {
        cacheService.loadDataToCache();
    }

    @GetMapping ("/cache")
    Map<String, String> getContent() {
        return cacheService.getCacheContent();
    }
}
