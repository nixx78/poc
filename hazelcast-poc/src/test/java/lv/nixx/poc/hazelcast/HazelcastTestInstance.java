package lv.nixx.poc.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import lv.nixx.poc.hazelcast.model.PersonDTO;
import lv.nixx.poc.hazelcast.model.serializer.PersonDTOSerializer;

public class HazelcastTestInstance {

    public static HazelcastInstance get() {
        return get(new Config());
    }

    public static HazelcastInstance get(Config config) {
        SerializerConfig personDtoConf = new SerializerConfig();
        personDtoConf.setImplementation(new PersonDTOSerializer()).setTypeClass(PersonDTO.class);

        config.getSerializationConfig().addSerializerConfig(personDtoConf);

        return new TestHazelcastInstanceFactory().newHazelcastInstance(config);
    }

}
