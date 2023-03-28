package lv.nixx.poc.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MapMapperTest {

    private final MapMapper mapper = Mappers.getMapper(MapMapper.class);

    @Test
    void test() {
        Map<String, String> map = mapper.map(
                Map.of(
                        "s1", "s1.value",
                        "s2", "s2.value",
                        "s3", "s3.value"
                )
        );

        assertThat(map).containsAllEntriesOf(Map.of(
                "s1", "s1.value",
                "s2", "s2.value",
                "s3", "s3.value"
        ));
    }

}
