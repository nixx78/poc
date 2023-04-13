package lv.nixx.poc.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class AccountDto {
    private String id;
    private int transactionCount;

    private BigDecimal averageAmount;

    private Collection<TransactionDto> transactions;

    private String accountTypeMapped;

    private String description;

}
