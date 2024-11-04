package lv.nixx.poc.common.config.db.v1;

import lv.nixx.poc.common.config.db.Utils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

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
    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory() {
        return super.createEntityManagerFactory();
    }

    @Bean(name = BetaDB.transactionManager)
    @Override
    @Conditional(BetaDBCondition.class)
    public PlatformTransactionManager createTransactionManager() {
        return super.createTransactionManager();
    }

    static class BetaDBCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return Utils.loadJPABeans(context.getBeanFactory(), BetaDB.class);
        }
    }

}
