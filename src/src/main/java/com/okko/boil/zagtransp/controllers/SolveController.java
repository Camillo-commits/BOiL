package com.okko.boil.zagtransp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SolveController {
    @RequestMapping("/solve")
    public String solve(){
        //System.out.print("solve");
        return "solve";
    }
}
