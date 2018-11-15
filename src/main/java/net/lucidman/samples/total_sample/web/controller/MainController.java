package net.lucidman.samples.total_sample.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value="/index")
    public String main() {
        return "index.html";
    }

}
