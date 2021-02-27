package lv.nixx.poc;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Request {

    @NotBlank(message = "id can't be blank")
    @Min(1)
    private String id;

    @NotNull(message = "Name parameter can't be null")
    private String name;

}
