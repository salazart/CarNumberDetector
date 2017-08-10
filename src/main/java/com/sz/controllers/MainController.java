package com.sz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lenovo on 08.08.2017.
 */
@Controller
public class MainController{

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required = false, defaultValue = "World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }
}
