package com.vtest.it.rawdatafterdeal;

import com.vtest.it.pojo.equipment.EquipmentBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class GenerateEquipmentInforBean {
    public void generate(RawdataInitBean rawdataInitBean, EquipmentBean equipmentBean)
    {
        LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
        equipmentBean.setCustomerCode(properties.get("Customer Code"));
        equipmentBean.setDevice(properties.get("Device Name"));
        equipmentBean.setLotId(properties.get("Lot ID"));
        equipmentBean.setCpStep(properties.get("CP Process"));
        equipmentBean.setWaferNo(properties.get("Wafer ID"));
        equipmentBean.setTesterId(properties.get("Tester ID"));
        equipmentBean.setProberId(properties.get("Prober ID"));
        equipmentBean.setCardId(properties.get("Prober Card ID"));
        equipmentBean.setFirstTouchDown(rawdataInitBean.getPrimaryTouchDownTimes());
        equipmentBean.setFirstTimeRange(rawdataInitBean.getPrimaryTouchDownDuringTime());
        equipmentBean.setFirstTestRate(rawdataInitBean.getPrimaryRate());
        equipmentBean.setRetestTouchDown(rawdataInitBean.getReTestTouchDownTimes());
        equipmentBean.setRetestTimeRange(rawdataInitBean.getReTestTouchDownDuringTime());
        equipmentBean.setRetestRate(rawdataInitBean.getReTestRate());
        equipmentBean.setSingleTestTime(rawdataInitBean.getSingleTouchDownDuringTime());
        equipmentBean.setTestTime(rawdataInitBean.getTestDuringTime());
    }
}
