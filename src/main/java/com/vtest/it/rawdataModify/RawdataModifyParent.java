package com.vtest.it.rawdataModify;

import java.io.File;
import java.util.HashMap;

public class RawdataModifyParent {
    public final File DataBase=new File("/server212/Manmap/ChangeRawdata");
    public final File DataBaseBackup=new File("/server212/Manmap/ChangeRawdataBackup");
    public final File RawdataProber=new File("/server212/RawData/ProberRawdatabackup");
    public final File RawdataTester=new File("/server212/RawData/TesterRawdatabackup");
    public HashMap<String, String> transferProperties=new HashMap<>();
    public  void initMap()
    {
        transferProperties.put("left", "MinX");
        transferProperties.put("up", "MinY");
        transferProperties.put("right", "MaxX");
        transferProperties.put("down", "MaxY");
        transferProperties.put("TestDieleft", "TestDieMinX");
        transferProperties.put("TestDieup", "TestDieMinY");
        transferProperties.put("TestDieright", "TestDieMaxX");
        transferProperties.put("TestDiedown", "TestDieMaxY");
    }
    public String modify(String property) {
        if (transferProperties.containsKey(property)) {
            return transferProperties.get(property);
        }else {
            return property;
        }
    }
}
