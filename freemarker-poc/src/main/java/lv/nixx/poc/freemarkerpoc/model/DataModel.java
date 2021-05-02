package lv.nixx.poc.freemarkerpoc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class DataModel {

    private BigDecimal totalAmount;
    private BigDecimal tax;

    private Collection<String> type = List.of("Type1", "Type2", "Type3", "Type4");

    private Collection<Customer> customers;

    public BigDecimal calculateTotal() {
        return BigDecimal.valueOf(100.001);
    }

    public String convert(int n) {
        return n == 0 ? "ZERO" : "NON_ZERO";
    }

    public Collection<Customer> getVipCustomers() {
        return customers.stream()
                .filter(t -> t.getType().equals("VIP"))
                .collect(Collectors.toList());
    }


}
