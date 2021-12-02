package lv.nixx.poc.mapper;

import lv.nixx.poc.mapper.CustomerMapper;
import lv.nixx.poc.model.dto.CustomerDto;
import lv.nixx.poc.model.entity.CustomerEntity;
import lv.nixx.poc.model.dto.CustomerType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMappingTest {

    // TODO https://mapstruct.org/
    @Test
    void entityToDtoMappingTest() {

        CustomerDto customerDto = CustomerMapper.INSTANCE.customerToDto(new CustomerEntity()
                .setId(100L)
                .setFirstName("FirstName")
                .setSecondName("SecondName")
                .setType("VIP")
        );

        assertAll(
                () -> assertNotNull(customerDto),
                () -> assertEquals(100L, customerDto.getId()),
                () -> assertEquals("FirstName", customerDto.getName()),
                () -> assertEquals("SecondName", customerDto.getSurname()),
                () -> assertEquals(CustomerType.VIP, customerDto.getCustomerType())
        );
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

        assertThat(2, equalTo(customerDtos.size()));

        CustomerDto customerDto = customerDtos.get(0);

        assertAll(
                () -> assertNotNull(customerDto),
                () -> assertEquals(100L, customerDto.getId()),
                () -> assertEquals("FirstName1", customerDto.getName()),
                () -> assertEquals("SecondName1", customerDto.getSurname())
        );
    }
}
