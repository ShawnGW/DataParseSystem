package com.vtest.it.RawdataGenerate;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.GetRandomChar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

@Service
public class GenerateRawdata {
    private GetRandomChar getRandomChar;

    @Autowired
    public void setGetRandomChar(GetRandomChar getRandomChar) {
        this.getRandomChar = getRandomChar;
    }
    public File generate(RawdataInitBean rawdataInitBean) throws IOException
    {
        File tempRawdata =new File("/TempRawdata/"+ getRandomChar.getRandomChar(15)+".raw");
        PrintWriter printWriter=new PrintWriter(new FileWriter(tempRawdata));
        writeProperties(printWriter,rawdataInitBean.getProperties());
        writeTestDie(printWriter,rawdataInitBean.getTestDieMap());
        writeMarkAndSkipDie(printWriter,rawdataInitBean.getSkipAndMarkDieMap());
        writeBinSummary(printWriter,rawdataInitBean.getSiteBinSum());
        printWriter.flush();
        printWriter.close();
        return tempRawdata;
    }
    private void writeProperties(PrintWriter printWriter,LinkedHashMap<String, String> properties) {
        Set<String> propertieSet=properties.keySet();
        for (String proper : propertieSet) {
           printWriter.print(proper+":"+properties.get(proper)+"\r\n");
        }
    }
    private void writeTestDie(PrintWriter printWriter,HashMap<String, String> testDieMap) {
        printWriter.print("RawData\r\n");
        Set<String> propertieSet=testDieMap.keySet();
        for (String coordinate : propertieSet) {
            String binValueInfor=testDieMap.get(coordinate);
            printWriter.print(coordinate+binValueInfor+"\r\n");
        }
        printWriter.print("DataEnd\r\n");
    }
    private void writeMarkAndSkipDie(PrintWriter printWriter,HashMap<String, String> markAndSkipDieMap) {
        printWriter.print("SkipAndMarkStart\r\n");
        Set<String> propertieSet=markAndSkipDieMap.keySet();
        for (String coordinate : propertieSet) {
            printWriter.print(coordinate+markAndSkipDieMap.get(coordinate)+"\r\n");
        }
        printWriter.print("SkipAndMarkEnd\r\n");
    }
    private void writeBinSummary(PrintWriter printWriter,HashMap<Integer,HashMap<Integer,Integer>> siteBinSmmary) {
        printWriter.print("Bin Summary\r\n");
        TreeMap<Integer,Integer> binSummary=new TreeMap<>();
        Set<Integer> siteSet=siteBinSmmary.keySet();
        for (Integer site: siteSet) {
            HashMap<Integer,Integer> binMap=siteBinSmmary.get(site);
            Set<Integer> binSet=binMap.keySet();
            for (Integer bin: binSet) {
                if (binSummary.containsKey(bin))
                {
                    binSummary.put(bin,binSummary.get(bin)+binMap.get(bin));
                }
                else
                {
                    binSummary.put(bin,binMap.get(bin));
                }
            }
        }
        Set<Integer> binNumbers=binSummary.keySet();
        for (Integer bin : binNumbers) {
            printWriter.print("Bin"+bin+":"+binSummary.get(bin)+"\r\n");
        }
    }
}
