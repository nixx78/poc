package lv.nixx.poc.common.config.security;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

class LoggedUserConfigImporter implements ImportSelector {
    @Override
    public String[] selectImports(@NonNull AnnotationMetadata metadata) {
        return new String[]{
                "lv.nixx.poc.common.config.security.LoggedUserConfig"
        };
    }

}
