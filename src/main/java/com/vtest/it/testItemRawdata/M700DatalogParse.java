package com.vtest.it.testItemRawdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class M700DatalogParse implements ItemRawdataDefine {

    private ArrayList<String> TestItems;
    private TreeMap<String, String> primaryTestValues;
    private TreeMap<String, String> reTestValues;
    private File Datalog;
    @Override
    public ArrayList<String> getTestItems() {
        return this.TestItems;
    }
    @Override
    public TreeMap<String, String> getPrimaryTestValues() {
        return this.primaryTestValues;
    }
    @Override
    public TreeMap<String, String> getReTestValues() {
        return this.reTestValues;
    }
    public M700DatalogParse(File Datalog)
    {
        this.Datalog=Datalog;
    }
    private void  parseDatalog() throws IOException
    {
        FileReader in=new FileReader(Datalog);
        BufferedReader reader=new BufferedReader(in);
        String content;
        while((content=reader.readLine())!=null)
        {

        }
        reader.close();
    }
}
