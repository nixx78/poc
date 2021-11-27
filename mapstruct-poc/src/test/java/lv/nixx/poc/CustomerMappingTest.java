package lv.nixx.poc;

import lv.nixx.poc.model.CustomerDto;
import lv.nixx.poc.model.CustomerEntity;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMappingTest {

    // TODO https://mapstruct.org/
    @Test
    void entityToDtoMappingTest() {

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(100L);
        customerEntity.setFirstName("FirstName");
        customerEntity.setSecondName("SecondName");

        CustomerDto customerDto = CustomerMapper.INSTANCE.customerToDto(customerEntity);

        assertAll(
                () -> assertNotNull(customerDto),
                () -> assertEquals(100L, customerDto.getId()),
                () -> assertEquals("FirstName", customerDto.getName()),
                () -> assertEquals("SecondName", customerDto.getSurname())
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
}
