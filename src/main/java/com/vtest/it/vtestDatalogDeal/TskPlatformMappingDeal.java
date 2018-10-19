package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.MappingParseTools.TskProberMappingParse;
import com.vtest.it.RawdataGenerate.InitMesConfigToRawdataProperties;
import com.vtest.it.dao.mesdao.GetMesConfig;
import com.vtest.it.dao.mesdao.GetSlotAndSequenceDAO;
import com.vtest.it.dao.systemdao.GetDataSourceConfigDao;
import com.vtest.it.mestools.SlotModify;
import com.vtest.it.pojo.DataSourceBean;
import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.pojo.SlotAndSequenceConfigBean;
import com.vtest.it.rawdatafterdeal.RawDataDeal;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.TimeCheck;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

public class TskPlatformMappingDeal implements Job{
    private GetDataSourceConfigDao dataSourceConfig;
    private TimeCheck timeCheck;
    private GetSlotAndSequenceDAO getSlotAndSequence;
    private SlotModify slotModify;
    private GetMesConfig getMesConfig;
    private TskProberMappingParse tskProberMappingParse;
    private InitMesConfigToRawdataProperties initMesConfigToRawdataProperties;
    private RawDataDeal rawDataDeal;

    @Autowired
    public void setRawDataDeal(RawDataDeal rawDataDeal) {
        this.rawDataDeal = rawDataDeal;
    }

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
    @Autowired
    public void setGetMesConfig(GetMesConfig getMesConfig) {
        this.getMesConfig = getMesConfig;
    }
    @Autowired
    public void setTskProberMappingParse(TskProberMappingParse tskProberMappingParse) {
        this.tskProberMappingParse = tskProberMappingParse;
    }
    @Autowired
    public void setInitMesConfigToRawdataProperties(InitMesConfigToRawdataProperties initMesConfigToRawdataProperties) {
        this.initMesConfigToRawdataProperties = initMesConfigToRawdataProperties;
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
                                RawdataInitBean rawdataInitBean=new RawdataInitBean();
                                tskProberMappingParse.Get(wafer,Integer.valueOf(slotAndSequenceConfigBean.getGpibBin()),rawdataInitBean);
                                String cpProcess=rawdataInitBean.getDataProperties().get("CP Process");
                                MesConfigBean mesConfigBean= getMesConfig.getBean(waferId,cpProcess);
                                initMesConfigToRawdataProperties.initMesConfig(rawdataInitBean,mesConfigBean);
                                rawDataDeal.Deal(rawdataInitBean);

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
