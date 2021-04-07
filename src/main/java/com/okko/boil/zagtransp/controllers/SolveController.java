package com.okko.boil.zagtransp.controllers;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class SolveController {
    List<Integer> tab=new ArrayList<>();
    @RequestMapping(value = "solve", method = RequestMethod.POST)
    @ResponseBody
    public String solve() throws IOException {
        Connection connect = Jsoup.connect("http://localhost:8080"); //to nie działa bo po wcisnieciu solve wszystkie dane sie wymazuja
        Document document=connect.get();
        Elements elements = document.select("td");
        for(Element el: elements){
            System.out.println(el.text());
        }
        return "solve";
    }

    @RequestMapping(value="solve/{s}",method=RequestMethod.POST)
    @ResponseBody
    public Integer solving(@PathVariable(value="s")Integer s){  // to teoretycznie miało zapisywać te wszystkie dane do listy, ale cos poszło nie tak
        tab.add(s);
        return s;
    }



}
