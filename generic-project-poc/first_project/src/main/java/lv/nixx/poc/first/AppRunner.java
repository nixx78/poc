package lv.nixx.poc.first;

import lv.nixx.poc.common.config.amq.AMQ;
import lv.nixx.poc.common.config.db.AlphaDB;
import lv.nixx.poc.common.config.hazelcast.Hazelcast5;
import lv.nixx.poc.common.config.ldap.LDAP;
import lv.nixx.poc.common.config.ws.WebSocketWithAMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@AlphaDB(jpaSupport = false)

@Hazelcast5
@WebSocketWithAMQ
@AMQ
@LDAP
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}