package com.vtest.it.testerDatalogParse;

import com.vtest.it.vtestinterface.DatalogParse;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class V50DatalogParse implements DatalogParse {
    @Override
    public HashMap<String, String> parseName(String fileName) {
        HashMap<String,String> nameMap=new HashMap<>();
        Integer TesterID=6;
        boolean flag=true;
        String[] NameTokens=fileName.split("_");
        Integer Length=NameTokens.length;
        for (int j = 0; j < NameTokens.length; j++) {
            if (NameTokens[j].contains("TTK")||NameTokens[j].contains("TTT")||NameTokens[j].contains("TTV")) {
                if (flag) {
                    TesterID=j;
                    flag=false;
                }
            }
        }
        String CustomerCode=NameTokens[2];
        String Lot=NameTokens[TesterID-2];
        String waferId=NameTokens[TesterID-1];
        String CP=NameTokens[Length-4];
        StringBuffer Device_Buffer=new StringBuffer(NameTokens[3]);
        for (int j = 4; j< TesterID-2; j++) {
            Device_Buffer.append("_");
            Device_Buffer.append(NameTokens[j]);
        }
        String Device=Device_Buffer.toString();
        nameMap.put("customerCode",CustomerCode);
        nameMap.put("device",Device);
        nameMap.put("lot",Lot);
        nameMap.put("cp",CP);
        nameMap.put("waferId",waferId);
        return  nameMap;
    }
}
