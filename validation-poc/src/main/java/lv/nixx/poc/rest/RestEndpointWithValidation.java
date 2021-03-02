package lv.nixx.poc.rest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("rest")
@Validated
public class RestEndpointWithValidation {

    @GetMapping("/pathVariableValidation/{id}/{name}")
    public String pathVariableValidation(@PathVariable("id")    @Size(min = 2) String id,
                                         @PathVariable("name")  @Size(min = 5) String name
    ) {

        return "success:" + id + ":" + name;
    }

    @GetMapping("/requestParamsValidation")
    public String requestParamsValidation(@RequestParam("id")   @Size(min = 2) String id,
                                          @RequestParam("name") @Size(min = 5) String name
    ) {

        return "success:" + id + ":" + name;
    }



}