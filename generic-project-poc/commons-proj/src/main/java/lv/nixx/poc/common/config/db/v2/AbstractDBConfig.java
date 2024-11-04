package lv.nixx.poc.common.config.db.v2;

import com.zaxxer.hikari.HikariDataSource;
import lv.nixx.poc.common.config.db.SQLScriptUtil;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

abstract class AbstractDBConfig {

    private final String initFileName;
    private final ApplicationContext applicationContext;
    private final String prefix;

    private final String driverClassName;

    protected AbstractDBConfig(ApplicationContext applicationContext, String prefix) {
        Environment env = applicationContext.getEnvironment();

        this.prefix = prefix;
        this.applicationContext = applicationContext;

        this.initFileName = env.getProperty("db." + prefix + ".init-file");
        this.driverClassName = env.getProperty("driver-class-name", "com.mysql.cj.jdbc.Driver");
    }

    public DataSource createSource(DataSourceProperties dataSourceProperties) {
        dataSourceProperties.setDriverClassName(this.driverClassName);

        HikariDataSource hikariDataSource = dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        SQLScriptUtil.execute(hikariDataSource, initFileName);

        return hikariDataSource;
    }


    public DataSource createSource() {
        DataSourceProperties properties = applicationContext.getBean(prefix + "DataSourceProperties", DataSourceProperties.class);
        properties.setDriverClassName(this.driverClassName);

        DataSource dataSource = properties.initializeDataSourceBuilder().build();
        SQLScriptUtil.execute(dataSource, initFileName);
        return dataSource;
    }

}
