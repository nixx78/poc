package lv.nixx.poc.second.config;

import lv.nixx.poc.second.repository.alpha.AlphaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = AlphaRepository.class,
        entityManagerFactoryRef = "alphaEntityManagerFactory",
        transactionManagerRef = "alphaTransactionManager"
)
public class AlphaRepoConfig {
}
