package com.vtest.it.pojo.rawdataafterdeal;

import com.vtest.it.vtestinterface.RawDataAfterDeal;

import java.io.Serializable;
import java.util.List;

public class RawDataDealBean implements Serializable {
    private List<RawDataAfterDeal> dealList;

    public void setDealList(List<RawDataAfterDeal> dealList) {
        this.dealList = dealList;
    }

    public List<RawDataAfterDeal> getDealList() {
        return dealList;
    }
}
