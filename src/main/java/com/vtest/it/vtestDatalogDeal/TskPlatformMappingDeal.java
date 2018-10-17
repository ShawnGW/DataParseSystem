package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.dao.systemdao.GetDataSourceConfigDao;
import com.vtest.it.dao.mesdao.GetMesConfig;
import com.vtest.it.dao.mesdao.GetSlotAndSequenceDAO;
import com.vtest.it.mestools.SlotModify;
import com.vtest.it.pojo.DataSourceBean;
import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.pojo.SlotAndSequenceConfigBean;
import com.vtest.it.tools.TimeCheck;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

public class TskPlatformMappingDeal{
    private GetDataSourceConfigDao dataSourceConfig;
    private TimeCheck timeCheck;
    private GetSlotAndSequenceDAO getSlotAndSequence;
    private SlotModify slotModify;
    private GetMesConfig getMesConfig;
    @Autowired
    public void setSlotModify(SlotModify slotModify) {
        this.slotModify = slotModify;
    }

    @Autowired
    public void setGetSlotAndSequence(GetSlotAndSequenceDAO getSlotAndSequence) {
        this.getSlotAndSequence = getSlotAndSequence;
    }
    @Autowired
    public void setTimeCheck(TimeCheck timeCheck) {
        this.timeCheck = timeCheck;
    }

    @Autowired
    public void setDataSourceConfig(GetDataSourceConfigDao dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }
    public void  startDeal()
    {
        DataSourceBean dataSourceConfigBean=dataSourceConfig.getConfig("TSK");
        File dataSource=new File(dataSourceConfigBean.getSourcePath());
        File[] files=dataSource.listFiles();
        for (File lot: files) {
            try {
                if (lot.isDirectory())
                {
                    if (timeCheck.check(lot,60))
                    {
                        try {
                            FileUtils.copyDirectory(lot,new File(dataSourceConfigBean.getBackupSourcePath()));
                            String lotNum=lot.getName();
                            File[] wafers=lot.listFiles();
                            for (File wafer :wafers) {
                                String waferId=wafer.getName().substring(4);
                                SlotAndSequenceConfigBean slotAndSequenceConfigBean=getSlotAndSequence.getConfig(lotNum);
                                if (slotAndSequenceConfigBean.getReadType().equals("SLOT"));
                                {
                                    waferId=slotModify.modify(slotAndSequenceConfigBean.getSequence(),waferId);
                                }

                                MesConfigBean mesConfigBean= getMesConfig.getBean(waferId,"cp");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    try {
                        FileUtils.forceDelete(lot);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
             e.printStackTrace();
            }
        }
    }
}
