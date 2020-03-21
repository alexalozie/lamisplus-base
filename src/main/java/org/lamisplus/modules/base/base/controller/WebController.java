package org.lamisplus.modules.base.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello LamisPlus from a RestController";
    }
}