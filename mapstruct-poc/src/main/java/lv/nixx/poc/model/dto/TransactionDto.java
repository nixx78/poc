package lv.nixx.poc.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransactionDto {
    private long id;
    private BigDecimal amount;
}
