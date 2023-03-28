package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.CompositeDto;
import lv.nixx.poc.model.entity.AccountEntity;
import lv.nixx.poc.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CompositeMapper {
    @Mapping(target = "name", source = "customerEntity.firstName")
    @Mapping(target = "surname", source = "customerEntity.secondName")
    @Mapping(target = "accountId", source = "accountEntity.id")
    CompositeDto map(AccountEntity accountEntity, CustomerEntity customerEntity);
}
