package lv.nixx.poc.second;

import lv.nixx.poc.common.config.db.v1.BetaDB;
import lv.nixx.poc.common.config.db.v2.AlphaDB_V2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@AlphaDB_V2(packagesToScan = "lv.nixx.poc.second.orm.alpha")
@BetaDB
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}