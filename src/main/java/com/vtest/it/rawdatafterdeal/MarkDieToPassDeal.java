package com.vtest.it.rawdatafterdeal;

import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.MarkAndSkipToPassBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.HTS8689MarkToPass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MarkDieToPassDeal extends AbstractRawDataAfterDeal{
    private HTS8689MarkToPass hts8689MarkToPass;
    private GetDataSourceConfigDao getDataSourceConfigDao;

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    @Autowired
    public void setHts8689MarkToPass(HTS8689MarkToPass hts8689MarkToPass) {
        this.hts8689MarkToPass = hts8689MarkToPass;
    }

    @Override
    public void deal(RawdataInitBean rawdataInitBean) {
        LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
        String customCode=properties.get("Customer Code");
        String device=properties.get("Device Name");
        ArrayList<MarkAndSkipToPassBean> markandSkipToPassBeans=getDataSourceConfigDao.getMarkAndSkipToPassConfig();
        for (MarkAndSkipToPassBean bean : markandSkipToPassBeans) {
            if (bean.getCustomCode().equals(customCode)&&bean.getDevice().equals(device))
            {
                hts8689MarkToPass.modifyMap(rawdataInitBean.getTestDieMap());
            }
        }
    }
}
