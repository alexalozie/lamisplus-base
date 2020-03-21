package org.lamisplus.modules.base.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
    @GetMapping("/applicationKey")
    @ResponseBody
    public String applicationKey() {
        return "The application key is: DEMO";
    }
}