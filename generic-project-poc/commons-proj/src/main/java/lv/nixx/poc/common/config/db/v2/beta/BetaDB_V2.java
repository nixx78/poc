package lv.nixx.poc.common.config.db.v2.beta;

import lv.nixx.poc.common.config.db.v2.NameHolder;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({BetaDBImportSelector.class})
public @interface BetaDB_V2 {

    String prefix = "beta";

    boolean jpaSupport() default false;
    String[] packagesToScan() default {};
    boolean showActuatorEndpoint() default false;

    String dataSource = prefix + NameHolder.DATASOURCE;
    String entityManagerFactory = prefix + NameHolder.ENTITY_MANAGER_FACTORY;
    String transactionManager = prefix + NameHolder.TRANSACTION_MANAGER;
    String healthIndicator = prefix + NameHolder.HEALTH_INDICATOR;
}
