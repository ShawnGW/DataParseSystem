package com.vtest.it.testItemRawdata;

import java.util.ArrayList;
import java.util.TreeMap;

public interface ItemRawdataDefine {
    public ArrayList<String> getTestItems();
    public TreeMap<String,String> getPrimaryTestValues();
    public TreeMap<String,String> getReTestValues();
}
