package lv.nixx.poc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class Request {

    @NotBlank(message = "id can't be blank")
    @Min(1)
    private String id;

    @NotNull(message = "Name parameter can't be null")
    private String name;

}
