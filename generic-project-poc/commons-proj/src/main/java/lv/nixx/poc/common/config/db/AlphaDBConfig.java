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
class AlphaDBConfig extends AbstractDBConfig {

    public AlphaDBConfig(ApplicationContext context) {
        super(context, AlphaDB.prefix);
    }

    @Bean(name = AlphaDB.prefix + "DataSourceProperties")
    @ConfigurationProperties("db." + AlphaDB.prefix)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = AlphaDB.dataSource)
    public DataSource createSource() {
        return super.createSource();
    }

    @Bean(name = AlphaDB.entityManagerFactory)
    @Conditional(AlphaDBCondition.class)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Bean(name = AlphaDB.transactionManager)
    @Conditional(AlphaDBCondition.class)
    public PlatformTransactionManager transactionManager() {
        return super.transactionManager();
    }


    static class AlphaDBCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return Utils.loadJPABeans(context.getBeanFactory(), AlphaDB.class);
        }
    }

}
