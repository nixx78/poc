package lv.nixx.poc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collection;
import java.util.Map;

@Configuration
@ConfigurationProperties
@PropertySource(value = "settings.yml", factory = YamlPropertySourceFactory.class)
@Setter
@Getter
public class YmlSettings {
    private String name;
    private Collection<String> aliases;
    private Collection<String> sequence;

    private CacheConfig accountCache;

    private Collection<CacheConfig> caches;

    private Map<String, UserRole> roles;

    private Map<Integer, String> map;

}
