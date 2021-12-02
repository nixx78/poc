package lv.nixx.poc.model.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private long id;
    private String name;
    private String surname;
    private CustomerType customerType;
}
