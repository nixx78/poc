package lv.nixx.poc.common.config.db;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
class BetaDBConfig extends AbstractDBConfig {

    public BetaDBConfig(ApplicationContext context) {
        super(context, BetaDB.prefix);
    }

    @Bean(name = BetaDB.prefix + "DataSourceProperties")
    @ConfigurationProperties("db." + BetaDB.prefix)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = BetaDB.dataSource)
    public DataSource createSource() {
        return super.createSource();
    }

    @Bean(name = BetaDB.entityManagerFactory)
    @Override
    @Conditional(BetaDBCondition.class)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Bean(name = BetaDB.transactionManager)
    @Override
    @Conditional(BetaDBCondition.class)
    public PlatformTransactionManager transactionManager() {
        return super.transactionManager();
    }

    static class BetaDBCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return Utils.loadJPABeans(context.getBeanFactory(), BetaDB.class);
        }
    }

}
