package com.vtest.it.rawdatafterdeal;

import com.vtest.it.pojo.rawdataafterdeal.RawDataDealBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.vtestinterface.RawDataAfterDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawDataDeal {
   private RawDataDealBean rawDataDealBean;
   @Autowired
    public void setRawDataDealBean(RawDataDealBean rawDataDealBean) {
        this.rawDataDealBean = rawDataDealBean;
    }
    public void Deal(RawdataInitBean rawdataInitBean) {
        List<RawDataAfterDeal> list =rawDataDealBean.getDealList();
        for (RawDataAfterDeal deal:list ) {
            deal.deal(rawdataInitBean);
        }
    }
}
