package lv.nixx.poc.common.config.db.v2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class AlphaDBConfig extends AbstractDBConfig {

    public AlphaDBConfig(ApplicationContext context) {
        super(context, AlphaDB_V2.prefix);
    }

    @Bean(name = AlphaDB_V2.prefix + "DataSourceProperties")
    @ConfigurationProperties("db." + AlphaDB_V2.prefix)   // Настройки для основного DataSource
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = AlphaDB_V2.dataSource)
    @ConfigurationProperties("db." + AlphaDB_V2.prefix + ".hikari")   // Настройки для Hikari properties
    public DataSource createSource(@Qualifier(AlphaDB_V2.prefix + "DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return super.createSource(dataSourceProperties);
    }

}
