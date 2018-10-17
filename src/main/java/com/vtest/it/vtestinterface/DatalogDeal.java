package com.vtest.it.vtestinterface;

import java.io.File;
import java.util.HashMap;

public interface DatalogDeal {
    public void datalogBackup(File datalog, HashMap<String,String> nameMap ,boolean dealStdf);
}
