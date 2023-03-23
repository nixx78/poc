package lv.nixx.poc.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class AccountEntity {
    private long id;
    private Collection<TransactionEntity> transactions;

    private String accountType;

}
