package lv.nixx.poc.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerEntity {
    private long id;
    private String firstName;
    private String secondName;
    private String type;
}
