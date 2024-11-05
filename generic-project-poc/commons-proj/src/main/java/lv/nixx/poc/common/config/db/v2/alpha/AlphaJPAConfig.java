package lv.nixx.poc.common.config.db.v2.alpha;

import lv.nixx.poc.common.config.db.v2.JPAConfigHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

class AlphaJPAConfig {

    private static String[] packagesToScan;

    public static void setAnnotationParameters(String[] packagesToScan) {
        AlphaJPAConfig.packagesToScan = packagesToScan;
    }

    private final ApplicationContext applicationContext;

    public AlphaJPAConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean(name = AlphaDB_V2.entityManagerFactory)
    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
    ) {
        return JPAConfigHelper.createEntityManagerFactory(applicationContext, AlphaDB_V2.prefix, packagesToScan);
    }

    @Bean(name = AlphaDB_V2.transactionManager)
    public PlatformTransactionManager createTransactionManager() {
        return JPAConfigHelper.createTransactionManager(applicationContext, AlphaDB_V2.prefix);
    }

}
