package lv.nixx.poc.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PersonEntity {
    private String name;
    private String surname;
}
