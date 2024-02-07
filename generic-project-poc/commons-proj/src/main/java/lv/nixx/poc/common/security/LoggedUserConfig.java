package lv.nixx.poc.common.security;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Configuration
class LoggedUserConfig {

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getLoggedUserInfo", summary = "Return logged user info", tags = { "Get logged User info" }))
    RouterFunction<ServerResponse> getLoggedUserInfoRoute() {
        return RouterFunctions.route(GET("/user").and(accept(MediaType.APPLICATION_JSON)),
                req -> ok().body(this.getLoggedUserInfo()));
    }

    private Map<String, Object> getLoggedUserInfo() {
        return Map.of(
                "userId", "userId.Value",
                "roles", List.of("ROLE_A", "ROLE_2")
        );
    }

}
