package lv.nixx.poc;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig {

    @Bean
    public JetInstance jetInstance() {
        JetInstance jt = Jet.newJetInstance();

        PersonLoader pl = new PersonLoader();

        MapStoreConfig personStoreConfig = new MapStoreConfig();
        personStoreConfig .setWriteDelaySeconds(0);
        personStoreConfig .setEnabled(true);
        personStoreConfig .setImplementation(pl);
        personStoreConfig.setInitialLoadMode(MapStoreConfig.InitialLoadMode.EAGER);

        MapConfig mapConfig = new MapConfig();
        mapConfig .setMapStoreConfig(personStoreConfig );
        mapConfig .setName("person.map");
        mapConfig .setTimeToLiveSeconds(0);

        Config hazelcastConfig = jt.getConfig().getHazelcastConfig();
        hazelcastConfig.addMapConfig(mapConfig);


        return jt;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lv.nixx.poc"))
                .paths(PathSelectors.ant("/**/**"))
                .build();
    }




}
