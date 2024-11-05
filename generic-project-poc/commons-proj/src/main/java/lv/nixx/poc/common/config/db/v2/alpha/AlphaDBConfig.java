package lv.nixx.poc.common.config.db.v2.alpha;

import lv.nixx.poc.common.config.db.v2.AbstractDBConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static lv.nixx.poc.common.config.db.v2.NameHolder.DATA_SOURCE_PROPERTIES;

@Configuration
class AlphaDBConfig extends AbstractDBConfig {

    public AlphaDBConfig(Environment environment) {
        super(environment, AlphaDB_V2.prefix);
    }

    @Bean(name = AlphaDB_V2.prefix + DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties("db." + AlphaDB_V2.prefix)   // Настройки для основного DataSource
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = AlphaDB_V2.dataSource)
    @ConfigurationProperties("db." + AlphaDB_V2.prefix + ".hikari")   // Настройки для Hikari properties
    public DataSource createSource(@Qualifier(AlphaDB_V2.prefix + DATA_SOURCE_PROPERTIES) DataSourceProperties dataSourceProperties) {
        return super.createSource(dataSourceProperties);
    }

}
