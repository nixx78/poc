package lv.nixx.poc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class Request {

    @NotBlank(message = "id can't be blank")
    @Min(value = 1, message = "Minimum allowed value is '1'")
    private String id;

    @NotNull(message = "Name parameter can't be null")
    @Size(min = 3, message = "Minimum allowed character count is '3'")
    private String name;

}
