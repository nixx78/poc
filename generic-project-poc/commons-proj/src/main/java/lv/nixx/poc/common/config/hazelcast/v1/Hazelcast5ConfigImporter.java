package lv.nixx.poc.common.config.hazelcast.v1;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

class Hazelcast5ConfigImporter implements ImportSelector {
    @Override
    public String[] selectImports(@NonNull AnnotationMetadata metadata) {
        return new String[]{
                "lv.nixx.poc.common.config.hazelcast.v1.Hazelcast5Config"
        };
    }

}
