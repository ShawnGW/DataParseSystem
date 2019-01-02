package com.vtest.it.rawdatafterdeal;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class GetModelFromConfig {
    public Set<String> getTestDieModel(File model)
    {
        Set<String> testDies=new HashSet<>();
        try {
            FileReader in=new FileReader(model);
            BufferedReader reader=new BufferedReader(in);
            String content= null;
            while ((content=reader.readLine())!=null)
            {
                testDies.add(content);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testDies;
    }
}
