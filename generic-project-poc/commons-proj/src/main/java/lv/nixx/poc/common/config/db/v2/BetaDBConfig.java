package lv.nixx.poc.common.config.db.v2;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
