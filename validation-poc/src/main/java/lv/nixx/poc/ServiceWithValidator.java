package lv.nixx.poc;

import lv.nixx.poc.model.Request;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class ServiceWithValidator {

    public String process(@Valid Request request) {
        return "Success:FromService";
    }

}
