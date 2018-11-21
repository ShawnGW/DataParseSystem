package com.vtest.it.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tools")
public class SystemTools {
    @RequestMapping("/DegreeTrans")
    public String DegreeTrans()
    {
        return "RawdataDegreeTransform";
    }
}
