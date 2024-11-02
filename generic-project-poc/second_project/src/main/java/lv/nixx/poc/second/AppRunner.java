package lv.nixx.poc.second;

import lv.nixx.poc.common.config.db.v1.AlphaDB;
import lv.nixx.poc.common.config.db.v1.BetaDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

//TODO Process packages to scan from there...
@AlphaDB(packagesToScan = "lv.nixx.poc.second.orm.alpha")

@BetaDB
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}