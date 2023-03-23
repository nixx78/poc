package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.PersonDto;
import lv.nixx.poc.model.entity.PersonEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto map(PersonEntity entity);

    @BeforeMapping
    default void beforeMapping(PersonEntity e, @MappingTarget PersonDto dto) {
        dto.setTimestamp(LocalDateTime.now().toString());
    }

    @AfterMapping
    default void afterMapping(PersonEntity e, @MappingTarget PersonDto dto) {
        dto.setValid(dto.getSurname() != null && dto.getName() != null);
    }
}
