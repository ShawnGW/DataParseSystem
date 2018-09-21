package com.vtest.it.rawdataBean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public interface RawdataFinalBean {
    public LinkedHashMap<String,String> getProperties();
    public HashMap<String,String> getTestDieMap();
    public HashMap<String,String> getSkipAndMarkDieMap();
    public TreeMap<Integer,Integer> getBinSummary();
}
