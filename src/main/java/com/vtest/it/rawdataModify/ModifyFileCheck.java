package com.vtest.it.rawdataModify;

import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

@Service
public class ModifyFileCheck {
    public boolean check(File file)
    {
        boolean flag=true;
        try {
            ArrayList<String> contentList=new ArrayList<>();
            FileReader in=new FileReader(file);
            BufferedReader reader=new BufferedReader(in);
            String content;
            while ((content=reader.readLine())!=null)
            {
                contentList.add(content.trim());
            }
            reader.close();
            if (!contentList.get(0).contains("X,Y"))
            {
                flag=  false;
            }
            for (int i = 1; i < contentList.size(); i++) {
                if (contentList.get(i).contains("X,Y"))
                {
                    flag =  false;
                }
                try{
                    String[] tokens=contentList.get(i).split(",");
                    for (int j = 0; j < 2; j++) {
                        if (tokens[j].trim().length()>3)
                        {
                            flag=false;
                        }
                    }
                }catch (Exception e)
                {
                    flag =  false;
                }
            }
        }catch (Exception e)
        {
            return  false;
        }
        return  flag;
    }
}
