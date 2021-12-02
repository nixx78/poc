package lv.nixx.poc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class TransactionEntity {
    private final long id;
    private final Double amount;
}
