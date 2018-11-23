package com.vtest.it.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Navigation")
public class NavigationMain {
    @RequestMapping("/{page}")
    public String navigator(@PathVariable("page")String pageName)
    {
        return pageName;
    }
}
