package lv.nixx.poc.common.config.db.v1;

import lv.nixx.poc.common.config.db.Utils;
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
    @Conditional(AlphaDBJPACondition.class)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return super.createEntityManagerFactory();
    }

    @Bean(name = AlphaDB.transactionManager)
    @Conditional(AlphaDBJPACondition.class)
    public PlatformTransactionManager transactionManager() {
        return super.createTransactionManager();
    }


    static class AlphaDBJPACondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return Utils.loadJPABeans(context.getBeanFactory(), AlphaDB.class);
        }
    }

}
