package com.vtest.it.rawdatafterdeal;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.HTS8689MarkToPass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

public class HtsMarkDieToPassDeal extends AbstractRawDataAfterDeal{
    private HTS8689MarkToPass hts8689MarkToPass;

    @Autowired
    public void setHts8689MarkToPass(HTS8689MarkToPass hts8689MarkToPass) {
        this.hts8689MarkToPass = hts8689MarkToPass;
    }

    @Override
    public void deal(RawdataInitBean rawdataInitBean) {
        LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
        String customCode=properties.get("Customer Code");
        String device=properties.get("Device Name");
        if (customCode.equals("HTS")&&(device.equals("HS8689S1A2.CP")||device.equals("HS8916CMS1A1.CP")||device.equals("HS8916CMS1A3.CP")))
        {
            hts8689MarkToPass.modifyMap(rawdataInitBean.getTestDieMap());
        }
        perfectDeal(rawdataInitBean);
    }
}
