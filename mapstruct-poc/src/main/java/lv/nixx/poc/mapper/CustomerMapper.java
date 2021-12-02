package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.CustomerDto;
import lv.nixx.poc.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "secondName", target = "surname")
    @Mapping(source = "type", target = "customerType")
    CustomerDto customerToDto(CustomerEntity c);

    @Mapping(source = "f1", target = "name")
    @Mapping(source = "f2", target = "surname")
    CustomerDto mapToCustomer(Map<String, String> map);

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "secondName", target = "surname")
    List<CustomerDto> customerToDto(Collection<CustomerEntity> c);

    //TODO https://mapstruct.org/documentation/stable/reference/html/#adding-custom-methods
}