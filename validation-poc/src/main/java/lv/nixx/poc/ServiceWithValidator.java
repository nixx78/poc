package lv.nixx.poc;

import lv.nixx.poc.configuration.AppConfig;
import lv.nixx.poc.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class ServiceWithValidator {

    private final AppConfig props;

    @Autowired
    public ServiceWithValidator(AppConfig props) {
        this.props = props;
    }

    public String process(@Valid Request request) {
        return "Success:FromService";
    }

    public AppConfig getAppConfig() {
        return props;
    }

}
