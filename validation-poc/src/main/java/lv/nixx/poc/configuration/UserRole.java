package lv.nixx.poc.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
public class UserRole {
    private final String group;
    private final String role;

    @ConstructorBinding
    public UserRole(String group, String role) {
        this.group = group;
        this.role = role;
    }
}
