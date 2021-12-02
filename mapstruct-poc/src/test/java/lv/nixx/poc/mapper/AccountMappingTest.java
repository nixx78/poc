package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.AccountDto;
import lv.nixx.poc.model.entity.AccountEntity;
import lv.nixx.poc.model.entity.TransactionEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

class AccountMappingTest {

    @Test
    void entityToDtoMappingTest() {

        AccountDto accountDto = AccountMapper.INSTANCE.accountToDto(new AccountEntity()
                .setId(1000L)
                .setTransactions(
                        List.of(
                                new TransactionEntity(1L, 100.00),
                                new TransactionEntity(2L, 200.00),
                                new TransactionEntity(3L, 300.00)
                        )
                )
        );

        System.out.println(accountDto);

//        assertAll(
//                () -> assertNotNull(customerDto),
//                () -> assertEquals(100L, customerDto.getId()),
//                () -> assertEquals("FirstName", customerDto.getName()),
//                () -> assertEquals("SecondName", customerDto.getSurname()),
//                () -> assertEquals(CustomerType.VIP, customerDto.getCustomerType())
//        );
    }
}
