package lv.nixx.poc.common.config.db.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class GenericDBImportSelector implements ImportSelector {

    private final Logger log = LoggerFactory.getLogger(GenericDBImportSelector.class);

    private final String annotationClassName;
    private final String jpaConfigClassName;
    private final String actuatorConfigClassName;
    private final String dataSourceConfigClassName;

    public GenericDBImportSelector(@NonNull Class<?> annotationClass,
                                   @NonNull Class<?> jpaConfigClass,
                                   @NonNull Class<?> dataSourceConfigClass,
                                   @NonNull Class<?> actuatorConfigClass
    ) {
        this.annotationClassName = annotationClass.getName();
        this.jpaConfigClassName = jpaConfigClass.getName();
        this.actuatorConfigClassName = actuatorConfigClass.getName();
        dataSourceConfigClassName = dataSourceConfigClass.getName();
    }

    public abstract void setPackagesToConfig(String[] packagesToScan);

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(annotationClassName);

        if (attributes == null) {
            throw new IllegalStateException("Can't load configuration with null attributes");
        }

        boolean jpaSupport = (boolean) attributes.get("jpaSupport");
        String[] packagesToScan = (String[]) attributes.get("packagesToScan");

        List<String> configsToLoad = new ArrayList<>();
        configsToLoad.add(dataSourceConfigClassName);

        if (jpaSupport) {
            setPackagesToConfig(packagesToScan);
            configsToLoad.add(jpaConfigClassName);
        }

        boolean showActuatorEndpoint = (boolean) attributes.get("showActuatorEndpoint");
        if (showActuatorEndpoint) {
            configsToLoad.add(actuatorConfigClassName);
        }

        if (log.isInfoEnabled()) {
            log.info("For annotation [{}] load configuration:\n\t\t{}", annotationClassName, configsToLoad.stream().collect(Collectors.joining("\n\t\t")));
        }

        return configsToLoad.toArray(new String[]{});
    }

}
