package com.sz.controllers;

import com.sz.models.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 08.08.2017.
 */
@RestController()
public class ModelController {

    private int counter;
    private static final String template = "Hello, %s!";

    @RequestMapping("/api/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter++,
                String.format(template, name));
    }
}
