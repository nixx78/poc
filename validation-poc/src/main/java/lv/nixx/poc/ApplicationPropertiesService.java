package lv.nixx.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ApplicationPropertiesService {

    private final Environment environment;

    @Autowired
    public ApplicationPropertiesService(Environment environment) {
        this.environment = environment;
    }

    public String getPropertyForProfile() {
        String v = null;
        String[] activeProfiles = environment.getActiveProfiles();

        if (activeProfiles.length == 0) {
            return environment.getProperty("property.for.profile");
        }

        for (String p : activeProfiles) {
            v = environment.getProperty("property.for.profile-" + p);
            if (v != null) {
                break;
            }
        }
        return v;
    }


}
