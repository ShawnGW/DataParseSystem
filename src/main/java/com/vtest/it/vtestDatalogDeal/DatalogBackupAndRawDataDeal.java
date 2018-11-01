package com.vtest.it.vtestDatalogDeal;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class DatalogBackupAndRawDataDeal {
    public void datalogBackup(String path, File mapping, HashMap<String,String> resultMap)
    {
        try {
            String customCode=resultMap.get("customCode");
            String device=resultMap.get("device");
            String lot=resultMap.get("lot");
            String cp=resultMap.get("cp");

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
            File releaseDirectory=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp);
            if (!releaseDirectory.exists())
            {
                releaseDirectory.mkdirs();
            }
            File destFile=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp+"/"+mapping.getName()+"_"+simpleDateFormat.format(new Date()));
            FileUtils.copyFile(mapping,destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void rawdataDeal(String path, File rawdata,HashMap<String,String> resultMap)
    {
        try {
            String customCode=resultMap.get("customCode");
            String device=resultMap.get("device");
            String lot=resultMap.get("lot");
            String cp=resultMap.get("cp");
            String waferId=resultMap.get("waferId");

            File releaseDirectory=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp);
            if (!releaseDirectory.exists())
            {
                releaseDirectory.mkdirs();
            }
            File destFile=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp+"/"+waferId+".raw");
            FileUtils.copyFile(rawdata,destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
