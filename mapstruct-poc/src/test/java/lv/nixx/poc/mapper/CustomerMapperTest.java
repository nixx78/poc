package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.CustomerDto;
import lv.nixx.poc.model.dto.CustomerType;
import lv.nixx.poc.model.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void entityToDtoMappingTest() {

        CustomerDto expectedDto = new CustomerDto()
                .setId(100L)
                .setName("FirstName")
                .setSurname("SecondName")
                .setCustomerType(CustomerType.VIP)
                .setUuid("UUID.CONST");

        assertThat(customerMapper.customerToDto(new CustomerEntity()
                .setId(100L)
                .setFirstName("FirstName")
                .setSecondName("SecondName")
                .setType("VIP")
        )).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void entityToDtoWithDefaultTypeMappingTest() {

        CustomerDto expectedDto = new CustomerDto()
                .setId(100L)
                .setName("FirstName")
                .setSurname("SecondName")
                .setCustomerType(CustomerType.UNKNOWN)
                .setUuid("UUID.CONST");

        assertThat(customerMapper.customerToDto(new CustomerEntity()
                .setId(100L)
                .setFirstName("FirstName")
                .setSecondName("SecondName")
        )).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void mapToDtoTest() {

        CustomerDto customerDto = CustomerMapper.INSTANCE.mapToCustomer(Map.of(
                "id", "1000",
                "f1", "FirstName",
                "f2", "SecondName"
        ));

        assertAll(
                () -> assertNotNull(customerDto),
                () -> assertEquals(1000L, customerDto.getId()),
                () -> assertEquals("FirstName", customerDto.getName()),
                () -> assertEquals("SecondName", customerDto.getSurname())
        );
    }

    @Test
    void collectionMapTest() {

        List<CustomerDto> customerDtos = CustomerMapper.INSTANCE.customerToDto(
                List.of(
                        new CustomerEntity()
                                .setId(100L)
                                .setFirstName("FirstName1")
                                .setSecondName("SecondName1"),
                        new CustomerEntity()
                                .setId(200L)
                                .setFirstName("FirstName2")
                                .setSecondName("SecondName2")
                )
        );

        assertEquals(2, customerDtos.size());

        CustomerDto customerDto = customerDtos.get(0);

        assertAll(
                () -> assertNotNull(customerDto),
                () -> assertEquals(100L, customerDto.getId()),
                () -> assertEquals("FirstName1", customerDto.getName()),
                () -> assertEquals("SecondName1", customerDto.getSurname())
        );
    }
}
