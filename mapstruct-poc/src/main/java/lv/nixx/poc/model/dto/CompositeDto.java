package lv.nixx.poc.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompositeDto {
    private String name;
    private String surname;
    private String accountId;
}
