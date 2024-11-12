package lv.nixx.poc.second.config;

import lv.nixx.poc.common.config.db.v2.beta.BetaDB_V2;
import lv.nixx.poc.second.repository.beta.BetaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = BetaRepository.class,
        entityManagerFactoryRef = BetaDB_V2.entityManagerFactory,
        transactionManagerRef = BetaDB_V2.transactionManager
)
public class BetaRepoConfig {
}
