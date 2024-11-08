package lv.nixx.poc.common.config.db.v2;

import com.zaxxer.hikari.HikariDataSource;
import lv.nixx.poc.common.config.db.SQLScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@PropertySource("classpath:db.common.properties")
public abstract class AbstractDBConfig {

    private static final Logger log = LoggerFactory.getLogger(AbstractDBConfig.class);

    private final String initFileName;
    private final String prefix;

    private final String driverClassName;

    protected AbstractDBConfig(Environment env, String prefix) {
        this.prefix = prefix;

        this.initFileName = env.getProperty("db." + prefix + ".init-script");
        this.driverClassName = env.getProperty("driver-class-name.default");
    }

    public DataSource createSource(DataSourceProperties dataSourceProperties) {
        dataSourceProperties.setDriverClassName(this.driverClassName);

        HikariDataSource hikariDataSource = dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        SQLScriptUtil.execute(hikariDataSource, initFileName);

        log.info("DataSource for prefix [{}] created, driver class [{}] initialization filename [{}]", prefix, driverClassName, initFileName);


        return hikariDataSource;
    }
}
