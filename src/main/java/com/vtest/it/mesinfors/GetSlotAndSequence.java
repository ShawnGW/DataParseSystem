package com.vtest.it.mesinfors;

import com.vtest.it.dao.vtmesdao.VtMesSlotAndSequenceDAO;
import com.vtest.it.pojo.SlotAndSequenceConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GetSlotAndSequence {

    private VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO;

    @Autowired
    public void setVtMesSlotAndSequenceDAO(VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO) {
        this.vtMesSlotAndSequenceDAO = vtMesSlotAndSequenceDAO;
    }

    public HashMap<String, String> get(String customerLot)
    {
        HashMap<String, String> config=new HashMap<>();
        config.put("readType", "OCR");
        config.put("sequence", "1-25");
        config.put("gpib", "0");
        try {
            SlotAndSequenceConfigBean slotAndSeqConfig = vtMesSlotAndSequenceDAO.getConfig(customerLot);
            config.put("sequence", slotAndSeqConfig.getSequence());
            config.put("readType", slotAndSeqConfig.getReadType());
            config.put("gpib", slotAndSeqConfig.getGpibBin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}
