package lv.nixx.poc.common.config.hazelcast.v2;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.properties.ClientProperty;
import com.hazelcast.core.HazelcastInstance;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Collection;
import java.util.List;

public class HazelcastConfiguration {

    private final static Logger log = LoggerFactory.getLogger(HazelcastConfiguration.class);

    @Setter
    public static Collection<HazelcastInstanceConfigDTO> instances;

    public HazelcastConfiguration(GenericApplicationContext context) {

        ConfigurableEnvironment env = context.getEnvironment();

        for (HazelcastInstanceConfigDTO c : instances) {
            context.registerBean(c.beanName, HazelcastInstance.class, () -> createInstanceForPrefix(env, c.propertyPrefix));

            log.info("Register bean [{}]", c.beanName);
        }
    }

    private HazelcastInstance createInstanceForPrefix(Environment environment, String prefix) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setProperty(ClientProperty.INVOCATION_TIMEOUT_SECONDS.getName(), "30");
        clientConfig.setInstanceName(prefix + "_Instance");

        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();

        List<String> hazelcastHosts = environment.getProperty("hazelcast." + prefix + ".hosts", List.class);

        networkConfig.setAddresses(hazelcastHosts);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @AllArgsConstructor
    @ToString
    public static class HazelcastInstanceConfigDTO {
        String beanName;
        String propertyPrefix;
    }

}
