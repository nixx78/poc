package lv.nixx.poc.first;

import lv.nixx.poc.common.config.amq.AMQ;
import lv.nixx.poc.common.config.db.v2.alpha.AlphaDB_V2;
import lv.nixx.poc.common.config.ldap.LDAP;
import lv.nixx.poc.common.config.properties.EnableCommonProperties;
import lv.nixx.poc.common.config.security.AppSecurityConfig;
import lv.nixx.poc.common.config.ws.WebSocketWithAMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@AlphaDB_V2()

@WebSocketWithAMQ
@AMQ
@LDAP

@AppSecurityConfig(loadUserInfoController = false)
@EnableCommonProperties

@SpringBootApplication
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}