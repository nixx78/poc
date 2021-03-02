package lv.nixx.poc.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
public class CacheConfig {
    private final String url;
    private final String name;

    @ConstructorBinding
    public CacheConfig(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
