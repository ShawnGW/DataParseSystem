package com.vtest.it.rawdataParse;

import com.vtest.it.pojo.mvcDieBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
@Service
public class ParseRawdataNew {
    public ArrayList<mvcDieBean> getMap(File file)
    {
        ArrayList<mvcDieBean> resultMap=new ArrayList<>();
        try {
            FileReader in=new FileReader(file);
            BufferedReader reader=new BufferedReader(in);
            String content;
            boolean flag=false;
            while ((content=reader.readLine())!=null)
            {
                if (content.trim().equals("RawData"))
                {
                    flag=true;
                    continue;
                }
                if (content.trim().equals("DataEnd"))
                {
                    break;
                }
                if (flag)
                {
                    resultMap.add(getBean(content));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    public ArrayList<mvcDieBean> getPrimaryMap(File file)
    {
        ArrayList<mvcDieBean> resultMap=new ArrayList<>();
        try {
            FileReader in=new FileReader(file);
            BufferedReader reader=new BufferedReader(in);
            String content;
            boolean flag=false;
            while ((content=reader.readLine())!=null)
            {
                if (content.trim().equals("PrimaryMap"))
                {
                    flag=true;
                    continue;
                }
                if (content.trim().equals("PrimaryMapEnd"))
                {
                    break;
                }
                if (flag)
                {
                    resultMap.add(getBean(content));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    public ArrayList<mvcDieBean> getRetestMap(File file)
    {
        ArrayList<mvcDieBean> resultMap=new ArrayList<>();
        try {
            FileReader in=new FileReader(file);
            BufferedReader reader=new BufferedReader(in);
            String content;
            boolean flag=false;
            while ((content=reader.readLine())!=null)
            {
                if (content.trim().equals("RetestMap"))
                {
                    flag=true;
                    continue;
                }
                if (content.trim().equals("RetestMapEnd"))
                {
                    break;
                }
                if (flag)
                {
                    resultMap.add(getBean(content));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    private mvcDieBean getBean(String content)
    {
        mvcDieBean mvcDieBean=new mvcDieBean();
        mvcDieBean.setX(Integer.valueOf(content.substring(0,4).trim()));
        mvcDieBean.setY(Integer.valueOf(content.substring(4,8).trim()));
        mvcDieBean.setS(Integer.valueOf(content.substring(12,16).trim()));
        mvcDieBean.setT(Integer.valueOf(content.substring(16,20).trim()));
        mvcDieBean.setH(Integer.valueOf(content.substring(8,12).trim()));
        mvcDieBean.setN("Bin"+Integer.valueOf(content.substring(12,16).trim()));
        mvcDieBean.setZ(0);
        return mvcDieBean;
    }
}
