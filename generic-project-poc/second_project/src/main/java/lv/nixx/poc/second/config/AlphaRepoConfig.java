package lv.nixx.poc.second.config;

import lv.nixx.poc.common.config.db.AlphaDB;
import lv.nixx.poc.second.repository.alpha.AlphaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = AlphaRepository.class,
        entityManagerFactoryRef = AlphaDB.entityManagerFactory,
        transactionManagerRef = AlphaDB.transactionManager
)
public class AlphaRepoConfig {
}
