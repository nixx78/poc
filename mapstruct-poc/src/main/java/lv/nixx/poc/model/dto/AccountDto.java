package lv.nixx.poc.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class AccountDto {
    private String id;
    private int transactionCount;
    private Collection<TransactionDto> transactions;
}
