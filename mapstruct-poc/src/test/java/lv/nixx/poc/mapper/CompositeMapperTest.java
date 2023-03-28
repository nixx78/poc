package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.CompositeDto;
import lv.nixx.poc.model.entity.AccountEntity;
import lv.nixx.poc.model.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CompositeMapperTest {

    private final CompositeMapper compositeMapper = Mappers.getMapper( CompositeMapper.class );

    @Test
    void mappingTest() {

        AccountEntity ae = new AccountEntity()
                .setId(1000L)
                .setAccountType("Type1");

        CustomerEntity ce = new CustomerEntity()
                .setFirstName("FirstName")
                .setSecondName("SecondName");

        CompositeDto expected = new CompositeDto()
                .setName("FirstName")
                .setSurname("SecondName")
                .setAccountId("1000");

        assertThat(compositeMapper.map(ae, ce))
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .isEqualTo(expected);


    }

}
