package lv.nixx.poc.freemarkerpoc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class DataModel {

    private BigDecimal totalAmount;

    private Collection<Person> persons;

}
