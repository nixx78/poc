package lv.nixx.poc.common.config.hazelcast.v2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Collection;

public class HazelcastImporter implements ImportSelector {

    private static final Logger log = LoggerFactory.getLogger(HazelcastImporter.class);

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(Hazelcast5.class.getName())
        );

        if (attributes != null) {
            AnnotationAttributes[] instances = (AnnotationAttributes[]) attributes.get("instances");

            Collection<HazelcastConfiguration.HazelcastInstanceConfigDTO> instanceConfigs = Arrays.stream(instances)
                    .map(t -> new HazelcastConfiguration.HazelcastInstanceConfigDTO(String.valueOf(t.get("beanName")), String.valueOf(t.get("propertyPrefix"))))
                    .toList();

            HazelcastConfiguration.setInstances(instanceConfigs);
            log.info("Set Hazelcast instances [{}]", instanceConfigs);
        }

        return new String[]{HazelcastConfiguration.class.getName()};
    }
}
