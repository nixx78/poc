package lv.nixx.poc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class TransactionEntity {
    private final long id;
    private final Double amount;
    private Date date;

}
