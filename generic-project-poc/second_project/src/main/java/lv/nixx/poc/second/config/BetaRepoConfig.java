package lv.nixx.poc.second.config;

import lv.nixx.poc.common.config.db.v1.BetaDB;
import lv.nixx.poc.second.repository.beta.BetaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = BetaRepository.class,
        entityManagerFactoryRef = BetaDB.entityManagerFactory,
        transactionManagerRef = BetaDB.transactionManager
)
public class BetaRepoConfig {
}
