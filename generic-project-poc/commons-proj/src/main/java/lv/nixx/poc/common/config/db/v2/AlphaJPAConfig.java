package lv.nixx.poc.common.config.db.v2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

class AlphaJPAConfig {

    private static String prefix;
    private static String[] packagesToScan;

    public static void setPrefix(String prefix) {
        AlphaJPAConfig.prefix = prefix;
    }
    public static void setPackagesToScan(String[] packagesToScan) {
        AlphaJPAConfig.packagesToScan = packagesToScan;
    }

    private final ApplicationContext applicationContext;

    public AlphaJPAConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean(name = AlphaDB_V2.entityManagerFactory)
    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
    ) {
        return JPAConfigHelper.createEntityManagerFactory(applicationContext, prefix, packagesToScan);
    }

    @Bean(name = AlphaDB_V2.transactionManager)
    public PlatformTransactionManager createTransactionManager() {
        return JPAConfigHelper.createTransactionManager(applicationContext, prefix);
    }

}
