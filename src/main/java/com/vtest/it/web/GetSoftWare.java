package com.vtest.it.web;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/getSource")
public class GetSoftWare {
    @RequestMapping("/{SourceName:.+}")
    @ResponseBody
    public void getSource(@PathVariable("SourceName") String sourceName, OutputStream outputStream) {
        try {
            HttpHeaders headers=new HttpHeaders();
            File source = new File("/vtestSoftSource/"+sourceName);
            FileInputStream fileInputStream = new FileInputStream(source);
            FileCopyUtils.copy(fileInputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
