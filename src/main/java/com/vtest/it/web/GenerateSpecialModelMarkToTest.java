package com.vtest.it.web;

import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.marktopassmodel.MarkToPassModelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/DealMarkToTestDie")
public class GenerateSpecialModelMarkToTest {
    private GetDataSourceConfigDao getDataSourceConfigDao;
    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    @RequestMapping("/GenerateModel")
    public void  generate(@RequestParam("uploadFile") MultipartFile rawdata,@RequestParam("description") String description)
    {
        Set<String> testDieSet=new HashSet<>();
        try {
            File destDirectorySource=new File("/SpecialModel/sourceRawdata");
            File destDirectoryModel=new File("/SpecialModel/SourceModel");
            if(!destDirectorySource.exists())
            {
                destDirectorySource.mkdirs();
            }
            if(!destDirectoryModel.exists())
            {
                destDirectoryModel.mkdirs();
            }
            File destFile=new File("/SpecialModel/sourceRawdata/"+rawdata.getOriginalFilename());
            rawdata.transferTo(destFile);
            FileReader in=new FileReader(destFile);
            BufferedReader reader=new BufferedReader(in);
            String content=null;
            boolean flag=false;
            String customerCode=null;
            String device=null;
            String lot=null;
            String cp=null;
            String waferId=null;
            while((content=reader.readLine())!=null)
            {
                if (content.contains("Customer Code"))
                {
                    customerCode=content.split(":")[1].trim();
                    continue;
                }
                if (content.contains("Device Name"))
                {
                    device=content.split(":")[1].trim();
                    continue;
                }
                if (content.contains("Lot ID"))
                {
                    lot=content.split(":")[1].trim();
                    continue;
                }
                if (content.contains("CP Process"))
                {
                    cp=content.split(":")[1].trim();
                    continue;
                }
                if (content.contains("Wafer ID"))
                {
                    waferId=content.split(":")[1].trim();
                    continue;
                }
                if (content.equals("RawData"))
                {
                    flag=true;
                    continue;
                }
                if (content.equals("DataEnd"))
                {
                    break;
                }
                if (flag)
                {
                    testDieSet.add(content.substring(0,4)+content.substring(4,8));
                }
            }
            File modelFile=new File(destDirectoryModel.getPath()+"/"+customerCode+"_"+device+"_"+lot+"_"+cp+"_"+waferId+".mol");
            FileWriter out=new FileWriter(modelFile);
            PrintWriter print=new PrintWriter(out);
            for (String coordinate : testDieSet) {
                print.print(coordinate+"\r\n");
            }
            print.flush();
            print.close();
            MarkToPassModelBean markToPassModelBean=new MarkToPassModelBean();
            markToPassModelBean.setDescription(description);
            markToPassModelBean.setModelId(customerCode+"_"+device+"_"+lot+"_"+cp+"_"+waferId);
            markToPassModelBean.setModelPath(modelFile.getPath());
            markToPassModelBean.setSourceFile("/SpecialModel/sourceRawdata/"+rawdata.getOriginalFilename());
            getDataSourceConfigDao.addNewModel(markToPassModelBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
