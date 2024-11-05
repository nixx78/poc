package lv.nixx.poc.common.config.db.v2;

import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

public class JPAConfigHelper {

    private final static Logger log = LoggerFactory.getLogger(JPAConfigHelper.class);

    public static LocalContainerEntityManagerFactoryBean createEntityManagerFactory(ApplicationContext applicationContext, String prefix, String[] packagesToScan) {
        DataSource dataSource = applicationContext.getBean(prefix + "DataSource", DataSource.class);

        Environment environment = applicationContext.getEnvironment();

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName(prefix + "PersistenceUnit");

        if (packagesToScan != null) {
            em.setPackagesToScan(packagesToScan);
            log.info("For data source [{}] scan packages {}", prefix, packagesToScan);
        }

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();

        String defaultHbmDll = environment.getProperty("hbm2ddl.auto.default", "XXX");
        String hbm2ddl = environment.getProperty("db." + prefix + ".hbm2ddl.auto", defaultHbmDll);

        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);

        em.setJpaPropertyMap(properties);

        log.info("Entity manager factory for [{}] created, persistence unit [{}] hbm2ddl [{}]", prefix, em.getPersistenceUnitName(), hbm2ddl);

        return em;
    }

    public static PlatformTransactionManager createTransactionManager(ApplicationContext applicationContext, String prefix) {
        EntityManagerFactory f = applicationContext.getBean(prefix + "EntityManagerFactory", EntityManagerFactory.class);

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(f);

        log.info("Transaction manager created, prefix [{}] persistenceUnitName  [{}]", prefix, jpaTransactionManager.getPersistenceUnitName());
        return jpaTransactionManager;
    }
}
