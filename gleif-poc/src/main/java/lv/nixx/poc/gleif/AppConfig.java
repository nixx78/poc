package lv.nixx.poc.gleif;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.derby.jdbc.ClientDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories("lv.nixx.poc.gleif.data")
public class AppConfig { 
	
	@Value("${serverName}")
	private String serverName; 
	
	@Value("${databaseName}")
	private String databaseName;

	@Value("${user}")
	private String user;

	@Value("${password}")
	private String password;

	@Value("${portNumber}")
	private int portNumber;
 	@Bean
	public DataSource dataSource() {
		ClientDataSource ds = new ClientDataSource();
		ds.setDatabaseName(databaseName);
		ds.setServerName(serverName);
		ds.setUser(user);
		ds.setPassword(password);
		ds.setPortNumber(portNumber);
		
		return ds;
	}
  
	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform("org.hibernate.dialect.DerbyTenSevenDialect");
		jpaAdapter.setGenerateDdl(false);
		jpaAdapter.setShowSql(false);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("gleif.unit");
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(jpaAdapter);
		factory.setPackagesToScan("lv.nixx.poc.gleif.model");
		factory.afterPropertiesSet();

		return factory.getObject();
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}

}
