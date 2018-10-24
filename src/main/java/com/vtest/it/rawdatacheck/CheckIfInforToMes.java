package com.vtest.it.rawdatacheck;

import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.datainfortomes.DataInforToMesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckIfInforToMes {
    private GetDataSourceConfigDao getDataSourceConfigDao;

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    public boolean check(String customCode, String device)
    {
        ArrayList<DataInforToMesBean> allConfigs=getDataSourceConfigDao.getList();
        for (DataInforToMesBean bean : allConfigs) {
            if (bean.getCustomCode().equals(customCode)&&(bean.getDevice().equals("ALL")||bean.getDevice().equals(device)))
            {
                return  true;
            }
        }
        return false;
    }
}
