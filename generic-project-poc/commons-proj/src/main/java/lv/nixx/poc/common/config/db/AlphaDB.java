package lv.nixx.poc.common.config.db;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({AlphaDBConfig.class})
public @interface AlphaDB {

    String prefix = "alpha";

    boolean jpaSupport() default true;
    String[] packagesToScan() default "";

    String dataSource = prefix + "DataSource";
    String entityManagerFactory = prefix + "EntityManagerFactory";
    String transactionManager = prefix + "TransactionManager";

}
