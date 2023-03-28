package lv.nixx.poc.mapper;


import lv.nixx.poc.model.SourceEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lv.nixx.poc.model.SourceEnum.*;
import static lv.nixx.poc.model.TargetEnum.*;
import static org.assertj.core.api.Assertions.assertThat;

public class EnumMapperTest {

    private final EnumMapper enumMapper = Mappers.getMapper(EnumMapper.class);

    @Test
    void enumMappingTest() {

        Collection<SourceEnum> source = new ArrayList<>(List.of(SE1, SE2, SE3));
        source.add(null);

        assertThat(enumMapper.map(source)).containsAll(List.of(TE1, TE2, SPECIAL, DEFAULT));
    }

}
