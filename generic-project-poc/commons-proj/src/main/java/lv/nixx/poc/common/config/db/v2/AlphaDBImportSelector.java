package lv.nixx.poc.common.config.db.v2;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlphaDBImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(AlphaDB_V2.class.getName());

        if (attributes == null) {
            throw new IllegalStateException("Can't load configuration with null attributes");
        }

        boolean jpaSupport = (boolean) attributes.get("jpaSupport");
        String[] packagesToScan = (String[]) attributes.get("packagesToScan");

        List<String> configsToLoad = new ArrayList<>();
        configsToLoad.add(AlphaDBConfig.class.getName());

        if (jpaSupport) {
            AlphaJPAConfig.setPackagesToScan(packagesToScan);
            AlphaJPAConfig.setPrefix(AlphaDB_V2.prefix);

            configsToLoad.add(AlphaJPAConfig.class.getName());
        }

        return configsToLoad.toArray(new String[]{});
    }
}
