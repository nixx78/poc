package lv.nixx.poc.configuration;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) {
        Properties loadedProperties = this.loadYamlIntoProperties(resource.getResource());

        return new PropertiesPropertySource(name!=null && !name.isBlank() ? name : resource.getResource().getFilename(), loadedProperties);
    }

    private Properties loadYamlIntoProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}