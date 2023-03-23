package lv.nixx.poc.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PersonDto {
    private String name;
    private String surname;

    private boolean valid;

    private String timestamp;

}
