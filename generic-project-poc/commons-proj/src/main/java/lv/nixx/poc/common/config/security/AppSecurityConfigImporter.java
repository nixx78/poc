package lv.nixx.poc.common.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

class AppSecurityConfigImporter implements ImportSelector {

    private static final Logger log = LoggerFactory.getLogger(AppSecurityConfigImporter.class);

    @Override
    public String[] selectImports(@NonNull AnnotationMetadata metadata) {

        String canonicalName = AppSecurityConfig.class.getCanonicalName();
        MultiValueMap<String, Object> allAnnotationAttributes = metadata.getAllAnnotationAttributes(canonicalName);

        List<String> configurationToLoad = new ArrayList<>();

        if (allAnnotationAttributes != null) {
            List<Object> loadControllerAttrib = allAnnotationAttributes.get("loadUserInfoController");

            boolean loadUserInfoController = !CollectionUtils.isEmpty(loadControllerAttrib) && (Boolean) loadControllerAttrib.get(0);

            if (loadUserInfoController) {
                configurationToLoad.add(LoggedUserConfig.class.getCanonicalName());
            }
        }

        configurationToLoad.add(SecurityPropertyConfig.class.getCanonicalName());

        if (log.isInfoEnabled()) {
            log.info("Application security related configuration: \n\t\t {}", String.join("\n\t\t", configurationToLoad));
        }

        return configurationToLoad.toArray(new String[0]);
    }

}
