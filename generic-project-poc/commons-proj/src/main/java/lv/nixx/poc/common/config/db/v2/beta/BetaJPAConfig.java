package lv.nixx.poc.common.config.db.v2.beta;

import lv.nixx.poc.common.config.db.v2.JPAConfigHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

class BetaJPAConfig {

    private static String[] packagesToScan;

    public static void setAnnotationParameters(String[] packagesToScan) {
        BetaJPAConfig.packagesToScan = packagesToScan;
    }

    private final ApplicationContext applicationContext;

    public BetaJPAConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean(name = BetaDB_V2.entityManagerFactory)
    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
    ) {
        return JPAConfigHelper.createEntityManagerFactory(applicationContext, BetaDB_V2.prefix, packagesToScan);
    }

    @Bean(name = BetaDB_V2.transactionManager)
    public PlatformTransactionManager createTransactionManager() {
        return JPAConfigHelper.createTransactionManager(applicationContext, BetaDB_V2.prefix);
    }

}
