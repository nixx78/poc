package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.AccountDto;
import lv.nixx.poc.model.dto.TransactionDto;
import lv.nixx.poc.model.entity.AccountEntity;
import lv.nixx.poc.model.entity.TransactionEntity;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    @Test
    void entityToDtoMappingTest() throws ParseException {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        AccountDto accountDto = AccountMapper.INSTANCE.accountToDto(new AccountEntity()
                .setId(1000L)
                .setAccountType("Type1")
                .setTransactions(
                        List.of(
                                new TransactionEntity(1L, 100.00, df.parse("03/14/2023 12:30:00"), LocalDateTime.parse("2023-03-04T13:11:30")),
                                new TransactionEntity(2L, 200.00, df.parse("03/15/2023 12:31:00"), LocalDateTime.parse("2023-03-04T13:11:31")),
                                new TransactionEntity(3L, 300.00, null, null)
                        )
                )
        );
        AccountDto expected = new AccountDto()
                .setId("1000")
                .setTransactionCount(3)
                .setAccountTypeMapped("Type1.mapped")
                .setTransactions(List.of(
                        new TransactionDto()
                                .setId(1)
                                .setAmount(BigDecimal.valueOf(100))
                                .setDate(1678789800000L)
                                .setDateTime("04.03.2023 13:11:30")
                        , new TransactionDto()
                                .setId(2)
                                .setAmount(BigDecimal.valueOf(200))
                                .setDate(1678876260000L)
                                .setDateTime("04.03.2023 13:11:31")
                        , new TransactionDto()
                                .setId(3)
                                .setAmount(BigDecimal.valueOf(300))
                ));

        assertThat(accountDto)
                .usingComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .usingRecursiveComparison()
                .isEqualTo(expected)
                .getWritableAssertionInfo();
    }
}
