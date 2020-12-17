package lv.nixx.poc.freemarkerpoc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class DataModel {

    private BigDecimal totalAmount;
    private Collection<String> type = List.of("Type1", "Type2", "Type3", "Type4");

    private Collection<Customer> customers;

}
