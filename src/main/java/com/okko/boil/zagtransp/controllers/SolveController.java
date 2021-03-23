package com.okko.boil.zagtransp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SolveController {
    @RequestMapping(value = "solve", method = RequestMethod.POST)
    @ResponseBody
    public String solve(){
        //System.out.print("solve");
        return "solve";
    }
}
