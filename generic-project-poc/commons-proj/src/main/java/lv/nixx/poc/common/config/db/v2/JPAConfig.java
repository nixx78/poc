package lv.nixx.poc.common.config.db.v2;

public @interface JPAConfig {
    String[] packagesToScan() default "";
}
