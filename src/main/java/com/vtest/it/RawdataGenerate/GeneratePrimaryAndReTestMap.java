package com.vtest.it.RawdataGenerate;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

@Service
public class GeneratePrimaryAndReTestMap {
        final String path="/server212/RawData/RawdataDetails/";
        public void  generate(RawdataInitBean bean)throws IOException
        {
            LinkedHashMap<String, String> properties=bean.getProperties();
            String customer=properties.get("Customer Code");
            String device=properties.get("Device Name");
            String lot=properties.get("Lot ID");
            String cp=properties.get("CP Process");
            String wafer=properties.get("Wafer ID");
            File detRawdata=new File(path+"/"+customer+"/"+device+"/"+lot+"/"+cp+"/"+wafer+".dtl");
            if (!detRawdata.getParentFile().exists())
            {
                detRawdata.getParentFile().mkdirs();
            }
            PrintWriter printWriter=new PrintWriter(new FileWriter(detRawdata));
            generatePrimaryMap(bean.getPrimaryTestDieMap(),printWriter);
            generateReTestMap(bean.getReTestDieMap(),printWriter);
            printWriter.flush();
            printWriter.close();
        }
        private void generatePrimaryMap(HashMap<String,String> primaryTestDieMap,PrintWriter printWriter)
        {
            Set<String> coordinates=primaryTestDieMap.keySet();
            printWriter.print("PrimaryMap\r\n");
            for (String coordinate : coordinates) {
                printWriter.print(coordinate+primaryTestDieMap.get(coordinate)+"\r\n");
            }
            printWriter.print("PrimaryMapEnd\r\n");
        }
        private void generateReTestMap(HashMap<String,String> reTestDieMap,PrintWriter printWriter)
        {
            Set<String> coordinates=reTestDieMap.keySet();
            printWriter.print("RetestMap\r\n");
            for (String coordinate : coordinates) {
                printWriter.print(coordinate+reTestDieMap.get(coordinate)+"\r\n");
            }
            printWriter.print("RetestMapEnd\r\n");
        }
}
