package lv.nixx.poc;

import lv.nixx.poc.model.CustomerDto;
import lv.nixx.poc.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "secondName", target = "surname")
    CustomerDto customerToDto(CustomerEntity c);

    @Mapping(source = "f1", target = "name")
    @Mapping(source = "f2", target = "surname")
    CustomerDto mapToCustomer(Map<String, String> map);


}