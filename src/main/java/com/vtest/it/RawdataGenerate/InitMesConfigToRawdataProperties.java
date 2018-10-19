package com.vtest.it.RawdataGenerate;

import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class InitMesConfigToRawdataProperties {
    public void initMesConfig(RawdataInitBean rawdataInitBean, MesConfigBean mesConfigBean)
    {
        LinkedHashMap<String,String> properties=new LinkedHashMap<>();
        LinkedHashMap<String,String> dataProperties=rawdataInitBean.getDataProperties();

        properties.put("Lot ID",mesConfigBean.getLotId());
        properties.put("Inner Lot",mesConfigBean.getInnerLot());
        properties.put("Inner MotherLot",mesConfigBean.getInnerMontherlot());
        properties.put("Customer Code",mesConfigBean.getCustomerCode());
        properties.put("Device Name",mesConfigBean.getDeviceName());
        properties.put("Wafer ID",mesConfigBean.getWaferId());
        properties.put("Lot Start Time",mesConfigBean.getLotStarttime());
        properties.put("PE Owner",mesConfigBean.getPeOwner());
        properties.put("Tester SoftWare Revision",mesConfigBean.getTesterSoftWareRevision());
        properties.put("Tester Model",mesConfigBean.getTesterModel());
        properties.put("Chinese Name",mesConfigBean.getChineseName());
        properties.put("Chines Short Name",mesConfigBean.getChineseShortname());
        properties.put("Part Num ",mesConfigBean.getPartNum());
        properties.put("Process SPEC",mesConfigBean.getProcessSpec());
        properties.put("Process Revision",mesConfigBean.getProcessRevision());
        properties.put("Worker Order",mesConfigBean.getWorkerOrder());
        properties.put("Lot Status",mesConfigBean.getLotStatus());
        properties.put("Wip Stage",mesConfigBean.getWipStage());
        properties.put("Wip Step",mesConfigBean.getWipStep());
        properties.put("PO Number",mesConfigBean.getPoNumber());
        properties.put("Part DESC",mesConfigBean.getPartDesc());
        properties.put("Map Or Ink ",mesConfigBean.getMapOrInk());
        properties.put("Is OTP",mesConfigBean.getIsOtp());
        properties.put("Is COP",mesConfigBean.getIsCop());
        properties.put("Fab Device",mesConfigBean.getFabDevice());
        properties.put("Asy Device",mesConfigBean.getAsyDevice());
        properties.put("Assigned Testers",mesConfigBean.getAssignedTesters());
        properties.put("Is Trimed",mesConfigBean.getIsTrimed());
        properties.put("Load File",mesConfigBean.getLoadFile());
        properties.put("Customer Test Spec",mesConfigBean.getCustomerTesterSpec());
        properties.put("Map Reference",mesConfigBean.getMapReference());
        properties.put("Process Short Flow",mesConfigBean.getProcessShortFlow());
        properties.put("Wafer PCS",mesConfigBean.getWaferPcs());
        properties.put("customerNotch",mesConfigBean.getCustomerNotch());
        properties.put("shipLot",mesConfigBean.getShipLot());
        properties.put("Sites",mesConfigBean.getSites());
        properties.put("Site To Site",mesConfigBean.getSiteToSite());
        properties.put("Continue Fail",mesConfigBean.getContinueFail());
        properties.put("Prober Device",mesConfigBean.getProberDevice());
        properties.put("Operator",mesConfigBean.getOperator());
        properties.put("Tester Program",mesConfigBean.getTesterProgram());
        properties.put("ALL CP Programs",mesConfigBean.getAllCpPrograms());
        properties.put("Tester ID",mesConfigBean.getTesterId());
        properties.put("Prober ID",mesConfigBean.getProberId());
        properties.put("Prober Card ID",mesConfigBean.getProberCardId());
        properties.put("CP Process",mesConfigBean.getCpProcess());
        properties.put("Hold Count",mesConfigBean.getHoldCount());
        properties.put("Test Start Time ",mesConfigBean.getTestStartTime());
        properties.put("Test End Time",mesConfigBean.getTestEndTime());
        properties.put("MES Test Gross Die",mesConfigBean.getTestGrossDie());
        properties.put("MES Stand Gross Die",mesConfigBean.getStandGrossDie());
        properties.put("Gross Die",mesConfigBean.getGrossDie());
        properties.put("Pass Die",mesConfigBean.getPassDie());
        properties.put("Fail Die",mesConfigBean.getFailDie());
        properties.put("Wafer Yield",mesConfigBean.getWaferYield());
        properties.put("Stop Yield",mesConfigBean.getStopYield());
        properties.put("Major Fail",mesConfigBean.getMajorFail());
        properties.put("Process Yield",mesConfigBean.getProcessYield());
        properties.put("CP Yields",mesConfigBean.getCpYield());
        properties.put("Pass Bins",mesConfigBean.getPassBins());
        properties.put("OS Bins",mesConfigBean.getOsBins());
        properties.put("Special Bins",mesConfigBean.getSpecialBins());
        properties.put("Theory Test Time",mesConfigBean.getTheoryTestTime());
        properties.put("Retest Rate",mesConfigBean.getRetestRate());
        properties.put("DataBase",mesConfigBean.getDataBase());
        properties.put("TestTime",mesConfigBean.getTestTime());
        properties.put("WaferID_read",mesConfigBean.getWaferIdRead());
        properties.put("Wafer_Sequence",mesConfigBean.getWaferSequence());
        properties.put("WF_Size",mesConfigBean.getWfSize());
        properties.put("Index X(mm)",mesConfigBean.getIndexX());
        properties.put("Index Y(mm)",mesConfigBean.getIndexY());
        properties.put("Map Cols",mesConfigBean.getMapCols());
        properties.put("Map Rows",mesConfigBean.getMapRows());
        properties.put("GPIB_Bin",mesConfigBean.getGpibBin());
        properties.put("Notch",mesConfigBean.getNotch());
        properties.put("Slot",mesConfigBean.getSlot());
        properties.put("RightID",mesConfigBean.getRightId());
        properties.put("MinX",mesConfigBean.getMinx());
        properties.put("MinY",mesConfigBean.getMiny());
        properties.put("MaxX",mesConfigBean.getMaxx());
        properties.put("MaxY",mesConfigBean.getMaxy());
        properties.put("TestDieMinX",mesConfigBean.getTestDieMinx());
        properties.put("TestDieMinY",mesConfigBean.getTestDieMiny());
        properties.put("TestDieMaxX",mesConfigBean.getTestDieMaxx());
        properties.put("TestDieMaxY",mesConfigBean.getTestDieMaxy());

        properties.put("Operator", dataProperties.get("Operator"));
        properties.put("CP Process", dataProperties.get("CP Process"));
        properties.put("Test Start Time", dataProperties.get("Test Start Time"));
        properties.put("Test End Time", dataProperties.get("Test End Time"));
        properties.put("Gross Die", dataProperties.get("Gross Die"));
        properties.put("Pass Die",dataProperties.get("Pass Die"));
        properties.put("Fail Die", dataProperties.get("Fail Die"));
        properties.put("Wafer Yield", dataProperties.get("Wafer Yield"));
        properties.put("DataBase",dataProperties.get("DataBase"));
        properties.put("TestTime", dataProperties.get("TestTime"));
        if (!dataProperties.get("Index X(mm)").equals("NA"))
        {
            properties.put("Index X(mm)",dataProperties.get("Index X(mm)"));
        }
        if (!dataProperties.get("Index Y(mm)").equals("NA"))
        {
            properties.put("Index Y(mm)", dataProperties.get("Index Y(mm)"));
        }
        properties.put("Map Cols", dataProperties.get("Map Cols"));
        properties.put("Map Rows", dataProperties.get("Map Rows"));
        properties.put("Notch", dataProperties.get("Notch"));
        properties.put("Retest Rate", dataProperties.get("Retest Rate"));
        if (!dataProperties.get("WF_Size").equals("NA"))
        {
            properties.put("WF_Size", dataProperties.get("WF_Size"));
        }
        properties.put("MinX", dataProperties.get("MinX"));
        properties.put("MinY", dataProperties.get("MinY"));
        properties.put("MaxX", dataProperties.get("MaxX"));
        properties.put("MaxY", dataProperties.get("MaxY"));
        properties.put("TestDieMinX", dataProperties.get("TestDieMinX"));
        properties.put("TestDieMinY", dataProperties.get("TestDieMinY"));
        properties.put("TestDieMaxX", dataProperties.get("TestDieMaxX"));
        properties.put("TestDieMaxY", dataProperties.get("TestDieMaxY"));

        rawdataInitBean.setProperties(properties);
    }
}
