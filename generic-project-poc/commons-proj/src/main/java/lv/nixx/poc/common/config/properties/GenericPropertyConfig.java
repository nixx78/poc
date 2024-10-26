package lv.nixx.poc.common.config.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:generic.properties")
public class GenericPropertyConfig {
}
