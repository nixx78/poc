package lv.nixx.poc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collection;

@Configuration
@ConfigurationProperties
@PropertySource(value = "settings.yml", factory = YamlPropertySourceFactory.class)
@Setter
@Getter
public class YmlSettings {
    private String name;
    private Collection<String> aliases;

    private CacheConfig accountCache;
    private CacheConfig customerCache;
}
