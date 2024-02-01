package lv.nixx.poc.first;

import lv.nixx.poc.common.config.amq.AMQConfig;
import lv.nixx.poc.common.config.db.AlphaDB;
import lv.nixx.poc.common.config.hazelcast.Hazelcast5Config;
import lv.nixx.poc.common.config.ws.WebSocketWithAMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@AlphaDB
@Hazelcast5Config
@WebSocketWithAMQConfig
@AMQConfig
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}