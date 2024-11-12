package lv.nixx.poc.second.config;

import lv.nixx.poc.common.config.db.v2.alpha.AlphaDB_V2;
import lv.nixx.poc.second.repository.alpha.AlphaTableOneRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = AlphaTableOneRepository.class,
        entityManagerFactoryRef = AlphaDB_V2.entityManagerFactory,
        transactionManagerRef = AlphaDB_V2.transactionManager
)
public class AlphaRepoConfig {
}
