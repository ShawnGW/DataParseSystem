package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.mesinfors.GetSlotAndSequence;
import com.vtest.it.mestools.SlotModify;
import com.vtest.it.tools.Uncompress;
import com.vtest.it.vtestinterface.DatalogDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

@Service
public class DatalogDealParent implements DatalogDeal {
    public final String path="/server212/Datalog/TesterData/";
    private Uncompress uncompress;
    private SlotModify slotModify;
    private GetSlotAndSequence getSlotAndSequence;

    @Autowired
    public void setGetSlotAndSequence(GetSlotAndSequence getSlotAndSequence) {
        this.getSlotAndSequence = getSlotAndSequence;
    }

    @Autowired
    public void setSlotModify(SlotModify slotModify) {
        this.slotModify = slotModify;
    }

    @Autowired
    public void setUncompress(Uncompress uncompress) {
        this.uncompress = uncompress;
    }
    @Override
    public void datalogBackup(File datalog, HashMap<String, String> nameMap,boolean dealStdf) {
        try {
        String customerCode=nameMap.get("customerCode");
        String device=nameMap.get("device");
        String lot=nameMap.get("lot");
        String cp=nameMap.get("cp");
        String waferId=nameMap.get("waferId");

        HashMap<String,String> customerLotConfig=getSlotAndSequence.get(lot);
        String waferIdNew=slotModify.modify(customerLotConfig.get("sequence"),waferId);
        String unzipPath=path+customerCode+"/"+device+"/"+lot+"/"+cp+"/"+waferIdNew;
        File directory=new File(unzipPath);
        if (!directory.exists())
          {
              directory.mkdirs();
          }
          uncompress.Ucompress(datalog,unzipPath,waferId,waferIdNew);
          if (dealStdf&&(datalog.getName().endsWith(".std.zip")||datalog.getName().endsWith(".stdf.zip")))
          {
              String tempPath="/server212/Manmap/STDF_extract_TestTime";
              uncompress.Ucompress(datalog,tempPath,waferId,waferIdNew);
          }
          datalog.delete();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
