package com.vtest.it.rawdatafterdeal;

import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.marktopassmodel.MarkToPassConfigBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class MarkToPassBySpecialModel extends AbstractRawDataAfterDeal {
    private GetModelFromConfig getModelFromConfig;
    private GetDataSourceConfigDao getDataSourceConfigDao;

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    @Autowired
    public void setGetModelFromConfig(GetModelFromConfig getModelFromConfig) {
        this.getModelFromConfig = getModelFromConfig;
    }

    @Override
    public void deal(RawdataInitBean rawdataInitBean) {
        ArrayList<MarkToPassConfigBean> config = getDataSourceConfigDao.getMarkToPassConfigs();
        LinkedHashMap<String, String> properties = rawdataInitBean.getProperties();
        String customCode = properties.get("Customer Code");
        String device = properties.get("Device Name");
        String lot = properties.get("Lot ID");
        String waferId = properties.get("Wafer ID");
        String cpStep = properties.get("CP Process");
        String platform = properties.get("DataBase");
        String modelId = getPath(customCode, device, lot, cpStep, waferId, platform, config, 0);
        String path = getDataSourceConfigDao.getModel(modelId).getModelPath();
        if (null!=path)
        {
            Set<String> model = getModelFromConfig.getTestDieModel(new File(path));
            HashMap<String, String> testDieMap = rawdataInitBean.getTestDieMap();
            HashMap<String, String> skipAndMarkDieMap = rawdataInitBean.getSkipAndMarkDieMap();
            for (String coordinate : model) {
                if (!testDieMap.containsKey(coordinate)) {
                    testDieMap.put(coordinate, String.format("%4s", "1") + String.format("%4s", "1") + String.format("%4s", "0"));
                }
                if (skipAndMarkDieMap.containsKey(coordinate)) {
                    skipAndMarkDieMap.remove(coordinate);
                }
            }
        }
    }

    public String getPath(String customer, String device, String lot, String cpStep, String waferId, String platform, ArrayList<MarkToPassConfigBean> config, int level) {
        String modelId = null;
        ArrayList<MarkToPassConfigBean> screenList = new ArrayList<>();
        for (MarkToPassConfigBean bean : config) {
            if (level == 0) {
                if (bean.getCustomerCode().equals(customer)) {
                    screenList.add(bean);
                }
            } else if (level == 1) {
                if (bean.getDevice().equals(device)) {
                    screenList.add(bean);
                }
            } else if (level == 2) {
                if (bean.getLot().equals(lot)) {
                    screenList.add(bean);
                }
            } else if (level == 3) {
                if (bean.getCp().equals(cpStep)) {
                    screenList.add(bean);
                }
            } else if (level == 4) {
                if (bean.getWaferId().equals(waferId)) {
                    screenList.add(bean);
                }
            } else if (level == 5) {
                if (bean.getPlatform().equals(platform)) {
                    return bean.getModelId();
                }
            }
        }
        if (screenList.size() == 0) {
            return modelId;
        } else if (screenList.size() == 1) {
            return screenList.get(0).getModelId();
        } else {
            return getPath(customer, device, lot, cpStep, waferId, platform, config, level + 1);
        }

    }
}
