package com.vtest.it.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
@Service
public class RawdataModifyInitFile {
    private Uncompress uncompress;

    @Autowired
    public void setUncompress(Uncompress uncompress) {
        this.uncompress = uncompress;
    }
    public File[] initUploadFile(File file)
    {
        ArrayList<File> arrayList=new ArrayList<>();
        if (file.getName().endsWith(".zip"))
        {
            try {
                arrayList=uncompress.UncompressRawdataModify(file);
                file.delete();
            }catch (Exception e)
            { }
        }else {
            arrayList.add(file);
        }
        return  arrayList.toArray(new File[arrayList.size()]);
    }
}
