package com.vtest.it.rawdatafterdeal;

import com.alibaba.fastjson.JSON;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class GenerateWaferInforBean {
    public void generate(RawdataInitBean rawdataInitBean,BinWaferInforBean binWaferInforBean)
    {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
        LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
        binWaferInforBean.setCustomerCode(properties.get("Customer Code"));
        binWaferInforBean.setDevice(properties.get("Device Name"));
        binWaferInforBean.setLotId(properties.get("Lot ID"));
        binWaferInforBean.setCpStep(properties.get("CP Process"));
        binWaferInforBean.setWaferNo(properties.get("Wafer ID"));
        binWaferInforBean.setOprator(properties.get("Operator"));
        binWaferInforBean.setTesterProgram(properties.get("Tester Program"));
        binWaferInforBean.setTesterId(properties.get("Tester ID"));
        binWaferInforBean.setProberId(properties.get("Prober ID"));
        binWaferInforBean.setProberCardId(properties.get("Prober Card ID"));
        binWaferInforBean.setPidName(properties.get("Process SPEC"));
        binWaferInforBean.setPidVersion(properties.get("Process Revision"));
        try {
            binWaferInforBean.setStartTime(new Date(format.parse(properties.get("Test Start Time")).getTime()));
        } catch (ParseException e) {
            binWaferInforBean.setStartTime(new Date());
        }
        binWaferInforBean.setGrossDie(Integer.valueOf(properties.get("Gross Die")));
        binWaferInforBean.setPassDie(Integer.valueOf(properties.get("Pass Die")));
        binWaferInforBean.setFailDie(Integer.valueOf(properties.get("Fail Die")));
        binWaferInforBean.setYield(Double.valueOf(properties.get("Wafer Yield").substring(0,properties.get("Wafer Yield").length()-1)));
        try {
            binWaferInforBean.setEndTime(new Date(format.parse(properties.get("Test End Time")).getTime()));
        } catch (ParseException e) {
            binWaferInforBean.setEndTime(new Date());
        }
        binWaferInforBean.setCheckStatus(0);
        binWaferInforBean.setMapCols(Integer.valueOf(properties.get("Map Cols")));
        binWaferInforBean.setMapRows(Integer.valueOf(properties.get("Map Rows")));
        binWaferInforBean.setMinY(Integer.valueOf(properties.get("MinY")));
        binWaferInforBean.setMinX(Integer.valueOf(properties.get("MinX")));

        HashMap<String,String> othersparams=new HashMap<>();
        othersparams.put("Inner Lot",properties.get("Inner Lot"));
        othersparams.put("Inner MotherLot",properties.get("Inner MotherLot"));
        othersparams.put("Lot Start Time",properties.get("Lot Start Time"));
        othersparams.put("PE Owner",properties.get("PE Owner"));
        othersparams.put("Tester SoftWare Revision",properties.get("Tester SoftWare Revision"));
        othersparams.put("Tester Model",properties.get("Tester Model"));
        othersparams.put("Chinese Name",properties.get("Chinese Name"));
        othersparams.put("Chines Short Name",properties.get("Chines Short Name"));
        othersparams.put("Part Num ",properties.get("Part Num "));
        othersparams.put("Worker Order",properties.get("Worker Order"));
        othersparams.put("Lot Status",properties.get("Lot Status"));
        othersparams.put("Wip Stage",properties.get("Wip Stage"));
        othersparams.put("Wip Step",properties.get("Wip Step"));
        othersparams.put("PO Number",properties.get("PO Number"));
        othersparams.put("CTC PO Number",properties.get("CTC PO Number"));
        othersparams.put("Part DESC",properties.get("Part DESC"));
        othersparams.put("Map Or Ink ",properties.get("Map Or Ink "));
        othersparams.put("Is OTP",properties.get("Is OTP"));
        othersparams.put("Is COP",properties.get("Is COP"));
        othersparams.put("Fab Device",properties.get("Fab Device"));
        othersparams.put("Asy Device",properties.get("Asy Device"));
        othersparams.put("Assigned Testers",properties.get("Assigned Testers"));
        othersparams.put("Is Trimed",properties.get("Is Trimed"));
        othersparams.put("Load File",properties.get("Load File"));
        othersparams.put("Customer Test Spec",properties.get("Customer Test Spec"));
        othersparams.put("Map Reference",properties.get("Map Reference"));
        othersparams.put("Process Short Flow",properties.get("Process Short Flow"));
        othersparams.put("Wafer PCS",properties.get("Wafer PCS"));
        othersparams.put("customerNotch",properties.get("customerNotch"));
        othersparams.put("shipLot",properties.get("shipLot"));
        othersparams.put("Sites",properties.get("Sites"));
        othersparams.put("Site To Site",properties.get("Site To Site"));
        othersparams.put("Continue Fail",properties.get("Continue Fail"));
        othersparams.put("Prober Device",properties.get("Prober Device"));
        othersparams.put("ALL CP Programs",properties.get("ALL CP Programs"));
        othersparams.put("Hold Count",properties.get("Hold Count"));
        othersparams.put("MES Test Gross Die",properties.get("MES Test Gross Die"));
        othersparams.put("MES Stand Gross Die",properties.get("MES Stand Gross Die"));
        othersparams.put("Stop Yield",properties.get("Stop Yield"));
        othersparams.put("Major Fail",properties.get("Major Fail"));
        othersparams.put("Process Yield",properties.get("Process Yield"));
        othersparams.put("CP Yields",properties.get("CP Yields"));
        othersparams.put("Pass Bins",properties.get("Pass Bins"));
        othersparams.put("OS Bins",properties.get("OS Bins"));
        othersparams.put("Special Bins",properties.get("Special Bins"));
        othersparams.put("Theory Test Time",properties.get("Theory Test Time"));
        othersparams.put("Retest Rate",properties.get("Retest Rate"));
        othersparams.put("DataBase",properties.get("DataBase"));
        othersparams.put("TestTime",properties.get("TestTime"));
        othersparams.put("WaferID_read",properties.get("WaferID_read"));
        othersparams.put("Wafer_Sequence",properties.get("Wafer_Sequence"));
        othersparams.put("WF_Size",properties.get("WF_Size"));
        othersparams.put("Index X(mm)",properties.get("Index X(mm)"));
        othersparams.put("Index Y(mm)",properties.get("Index Y(mm)"));
        othersparams.put("GPIB_Bin",properties.get("GPIB_Bin"));
        othersparams.put("Notch",properties.get("Notch"));
        othersparams.put("Slot",properties.get("Slot"));
        othersparams.put("RightID",properties.get("RightID"));
        othersparams.put("MaxX",properties.get("MaxX"));
        othersparams.put("MaxY",properties.get("MaxY"));
        othersparams.put("TestDieMinX",properties.get("TestDieMinX"));
        othersparams.put("TestDieMinY",properties.get("TestDieMinY"));
        othersparams.put("TestDieMaxX",properties.get("TestDieMaxX"));
        othersparams.put("TestDieMaxY",properties.get("TestDieMaxY"));
        binWaferInforBean.setOthersParams(JSON.toJSONString(othersparams));
    }
}
