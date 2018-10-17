package com.vtest.it.rawdataInitGenerate;


import com.vtest.it.MappingParseTools.TskProberMappingParse;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.rawdataBean.rawdataInitParse;
import com.vtest.it.rawdataInit.InitRawdataProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TskMappingParse implements rawdataInitParse {

    private TskProberMappingParse tskProberMappingParse;
    private InitRawdataProperties initRawdataProperties;

    @Autowired
    public void setInitRawdataProperties(InitRawdataProperties initRawdataProperties) {
        this.initRawdataProperties = initRawdataProperties;
    }

    @Autowired
    public void setTskProberMappingParse(TskProberMappingParse tskProberMappingParse) {
        this.tskProberMappingParse = tskProberMappingParse;
    }
    public RawdataInitBean parse(File[] mapping,int gpibBin) {
        RawdataInitBean rawdataInitBean=new RawdataInitBean();
        rawdataInitBean.setProperties(initRawdataProperties.getProperties());
        try {
            tskProberMappingParse.Get(mapping[0],gpibBin,rawdataInitBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawdataInitBean;
    }
}
