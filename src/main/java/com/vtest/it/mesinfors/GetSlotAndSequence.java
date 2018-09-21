package com.vtest.it.mesinfors;

import com.vtest.it.mestools.GetLotConfigFromMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GetSlotAndSequence {

    private GetLotConfigFromMes getLotConfigFromMes;
    private GetMesInformations getMesInformations;

    @Autowired
    public void setGetLotConfigFromMes(GetLotConfigFromMes getLotConfigFromMes) {
        this.getLotConfigFromMes = getLotConfigFromMes;
    }

    @Autowired
    public void setGetMesInformations(GetMesInformations getMesInformations) {
        this.getMesInformations = getMesInformations;
    }

    public HashMap<String, String> get(String customerLot)
    {
        HashMap<String, String> config=new HashMap<>();
        config.put("readType", "OCR");
        config.put("sequence", "1-25");
        config.put("gpib", "0");
        getLotConfigFromMes.setLot(customerLot);
        HashMap<String, String> resultMap=getMesInformations.getInfor(getLotConfigFromMes, GetMesInformations.TYPE_CONFIG);
        config.put("sequence",resultMap.containsKey("[LOT:WaferSequence]")&&!resultMap.get("[LOT:WaferSequence]").equals("NA")?resultMap.get("[LOT:WaferSequence]"):(resultMap.containsKey("[FlexibleItem_ProcessSpecAttributes:Wafer_Sequence]")?resultMap.get("[FlexibleItem_ProcessSpecAttributes:Wafer_Sequence]").trim():"1-25"));
        config.put("readType",resultMap.containsKey("[FlexibleItem_ProcessSpecAttributes:WaferID_read]")?resultMap.get("[FlexibleItem_ProcessSpecAttributes:WaferID_read]").trim():"OCR");
        config.put("gpib",resultMap.containsKey("[FlexibleItem_ProcessSpecAttributes:GPIB_Bin]")?resultMap.get("[FlexibleItem_ProcessSpecAttributes:GPIB_Bin]").trim():"0");
        return config;
    }
}
