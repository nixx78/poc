package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.PersonDto;
import lv.nixx.poc.model.entity.PersonEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Test
    void entityToDtoMappingTest() {

        PersonEntity e = new PersonEntity()
                .setSurname("Surname")
                .setName("Name");

        PersonDto dto = personMapper.map(e);

        PersonDto expected = new PersonDto()
                .setName("Name")
                .setSurname("Surname")
                .setValid(true);

        assertThat(dto)
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .isEqualTo(expected);
    }

}
