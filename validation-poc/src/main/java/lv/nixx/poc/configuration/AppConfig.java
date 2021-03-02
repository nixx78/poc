package lv.nixx.poc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Validated
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfig {

    @Min(1)
    @Max(200)
    private int pageSize;

    @Max(10)
    @NotNull
    @Value("${total.record.count}")
    private Integer totalRecordCount;

}
