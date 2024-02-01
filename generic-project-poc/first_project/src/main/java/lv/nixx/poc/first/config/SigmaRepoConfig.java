package lv.nixx.poc.first.config;

import lv.nixx.poc.first.repository.sigma.SigmaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = SigmaRepository.class,
        entityManagerFactoryRef = "alphaEntityManagerFactory",
        transactionManagerRef = "alphaTransactionManager"
)
public class SigmaRepoConfig {
}
