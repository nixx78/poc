package lv.nixx.poc.common.config.db.v1;

import jakarta.persistence.EntityManagerFactory;
import lv.nixx.poc.common.config.db.SQLScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

abstract class AbstractDBConfig {

    private static final Logger log = LoggerFactory.getLogger(AbstractDBConfig.class);

    private final String initFileName;
    private final String ddlAuto;
    private final String[] packagesToScan;
    private final ApplicationContext applicationContext;
    private final String prefix;

    private final String driverClassName;

    protected AbstractDBConfig(ApplicationContext applicationContext, String prefix) {
        Environment env = applicationContext.getEnvironment();

        this.prefix = prefix;
        this.applicationContext = applicationContext;
        this.initFileName = env.getProperty("db." + prefix + ".init-file");

        this.packagesToScan = env.getProperty("db." + prefix + ".packages.to.scan", String[].class);

        this.ddlAuto = env.getProperty("hibernate.hbm2ddl.auto", "validate");
        this.driverClassName = env.getProperty("driver-class-name", "com.mysql.cj.jdbc.Driver");
    }

    public DataSource createSource() {
        DataSourceProperties properties = applicationContext.getBean(prefix + "DataSourceProperties", DataSourceProperties.class);
        properties.setDriverClassName(this.driverClassName);

        DataSource dataSource = properties.initializeDataSourceBuilder().build();
        SQLScriptUtil.execute(dataSource, initFileName);
        return dataSource;
    }

    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
    ) {
        DataSource dataSource = applicationContext.getBean(prefix + "DataSource", DataSource.class);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        if (packagesToScan != null) {
            em.setPackagesToScan(packagesToScan);
            log.info("For data source [{}] scan packages {}", prefix, packagesToScan);
        }

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", ddlAuto);

        em.setJpaPropertyMap(properties);

        log.info("Entity manager factory for [{}] created", prefix);

        return em;
    }

    public PlatformTransactionManager createTransactionManager() {
        EntityManagerFactory f = applicationContext.getBean(prefix + "EntityManagerFactory", EntityManagerFactory.class);
        return new JpaTransactionManager(f);
    }
}
