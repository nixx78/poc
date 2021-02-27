package lv.nixx.poc;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("rest")
public class RestEndpoint {

    @PostMapping("/request1")
    public String simpleRequest(@RequestBody  @Valid Request request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "fail";
        }

        return "success";
    }

    @PostMapping("/request2")
    public String anotherRequest(@RequestBody @Valid Request request) {
        return "success";
    }

}