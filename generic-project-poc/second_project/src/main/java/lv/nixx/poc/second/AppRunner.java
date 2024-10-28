package lv.nixx.poc.second;

import lv.nixx.poc.common.config.db.AlphaDB;
import lv.nixx.poc.common.config.db.BetaDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@AlphaDB
@BetaDB
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}