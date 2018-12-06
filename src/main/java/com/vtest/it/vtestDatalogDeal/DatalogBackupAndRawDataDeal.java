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
    public String datalogBackup(String path, File mapping, HashMap<String,String> resultMap)
    {
        String finPath=null;
        try {
            String customCode=resultMap.get("customCode");
            String device=resultMap.get("device");
            String lot=resultMap.get("lot");
            String cp=resultMap.get("cpStep");

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
            File releaseDirectory=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp);
            if (!releaseDirectory.exists())
            {
                releaseDirectory.mkdirs();
            }
            File destFile=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp+"/"+mapping.getName()+"_"+simpleDateFormat.format(new Date()));
            FileUtils.copyFile(mapping,destFile);
            finPath=destFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finPath;
    }
    public void rawdataDeal(String path, File rawdata,HashMap<String,String> resultMap)
    {
        try {
            String customCode=resultMap.get("customCode");
            String device=resultMap.get("device");
            String lot=resultMap.get("lot");
            String cp=resultMap.get("cpStep");
            String waferId=resultMap.get("waferId");

            File releaseDirectory=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp);
            if (!releaseDirectory.exists())
            {
                releaseDirectory.mkdirs();
            }
            File destFile=new File(path+"/"+customCode+"/"+device+"/"+lot+"/"+cp+"/"+waferId+".raw");
            FileUtils.copyFile(rawdata,destFile);
            FileUtils.forceDelete(rawdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
