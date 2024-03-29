package lv.nixx.poc.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerDto {

    private String uuid;

    private long id;
    private String name;
    private String surname;
    private CustomerType customerType;
}
