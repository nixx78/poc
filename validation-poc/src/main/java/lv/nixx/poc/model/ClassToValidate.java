package lv.nixx.poc.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lv.nixx.poc.validate.AccountConstrains;
import lv.nixx.poc.validate.TypeConstrains;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class ClassToValidate {

    @NotBlank(message = "id can't be blank")
    @Min(1)
    private String id;

    @NotNull(message = "Name parameter can't be null")
    private String name;

    @AccountConstrains
    private Collection<String> accounts;

    @TypeConstrains
    private Collection<String> types;

}
