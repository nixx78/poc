package lv.nixx.poc.second;

import lv.nixx.poc.common.config.db.v2.alpha.AlphaDB_V2;
import lv.nixx.poc.common.config.db.v2.beta.BetaDB_V2;
import lv.nixx.poc.common.config.properties.EnableCommonProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonProperties

@AlphaDB_V2(showActuatorEndpoint = true, jpaSupport = true, packagesToScan = "lv.nixx.poc.second.orm.alpha")
@BetaDB_V2(showActuatorEndpoint = true, jpaSupport = true, packagesToScan = "lv.nixx.poc.second.orm.beta")

//@Hazelcast5
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}