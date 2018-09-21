package com.vtest.it.tools;

import org.springframework.stereotype.Service;

@Service
public class RemoveDoubleSpace {
    public  String remove(String fileName) {
        fileName =fileName.replaceAll("__","_");
        return fileName;
    }
}
