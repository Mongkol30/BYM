package com.bym.beeryyummymap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.service.annotation.GetExchange;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("appName", "Nong Ttoon");
        return "index";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }


}
