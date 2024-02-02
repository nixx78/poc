package lv.nixx.poc.common.config.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.properties.ClientProperty;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class Hazelcast5Config {

    @Bean
    HazelcastInstance hazelcastInstance(@Value("${hazelcast5.hosts}") List<String> hazelcastHosts) {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setProperty(ClientProperty.INVOCATION_TIMEOUT_SECONDS.getName(), "30");
        clientConfig.setInstanceName("HazelcastPoc");

        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.setAddresses(hazelcastHosts);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }

}
