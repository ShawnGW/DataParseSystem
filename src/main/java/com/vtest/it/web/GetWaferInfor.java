package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.probermapperdao.ProberDataDAO;
import com.vtest.it.pojo.binwaferinfors.GetWaferInforBean;
import com.vtest.it.pojo.binwaferinfors.WaferIdBean;
import com.vtest.it.pojo.binwaferinfors.waferIdInforBean;
import com.vtest.it.pojo.binwaferinfors.waferYieldBean;
import com.vtest.it.pojo.mvcDieBean;
import com.vtest.it.pojo.vtdbInfors.CustomerAndDevicesBean;
import com.vtest.it.pojo.vtdbInfors.LotAndCpsBean;
import com.vtest.it.pojo.vtdbInfors.LotSummaryWaferBean;
import com.vtest.it.rawdataParse.ParseRawdataNew;
import com.vtest.it.rawdatacheck.CheckIfInforToMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/getWaferInfor")
public class GetWaferInfor {
    private CheckIfInforToMes checkIfInforToMes;
    private ParseRawdataNew parseRawdataNew;
    private ProberDataDAO proberDataDAO;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");

    @Autowired
    public void setCheckIfInforToMes(CheckIfInforToMes checkIfInforToMes) {
        this.checkIfInforToMes = checkIfInforToMes;
    }

    @Autowired
    public void setParseRawdataNew(ParseRawdataNew parseRawdataNew) {
        this.parseRawdataNew = parseRawdataNew;
    }
    @Autowired
    public void setProberDataDAO(ProberDataDAO proberDataDAO) {
        this.proberDataDAO = proberDataDAO;
    }

    @ResponseBody
    @RequestMapping("/getBinSummary")
    public String getBinSummary(@RequestParam("customer")String customer,@RequestParam("device")String device,@RequestParam("lot")String lot,@RequestParam("cp")String cp)
    {
        ArrayList<waferYieldBean> summary=proberDataDAO.getWaferBinSummary(customer,device,lot,cp,null);
        ArrayList<LotSummaryWaferBean> lotSummary=proberDataDAO.getwaferInfor(customer,device,lot,cp);
        TreeSet<Integer> binSet=new TreeSet<>();
        HashMap<String,TreeMap<Integer,Integer>> lotBinMap=new HashMap<>();
        for (waferYieldBean bean :summary) {
            binSet.add(bean.getSoftBinNo());
            if (lotBinMap.containsKey(bean.getWaferNo()))
            {
                TreeMap<Integer,Integer> binMap=lotBinMap.get(bean.getWaferNo());
                if (binMap.containsKey(bean.getSoftBinNo()))
                {
                    binMap.put(bean.getSoftBinNo(),binMap.get(bean.getSoftBinNo())+bean.getBinCount());
                }else
                {
                    binMap.put(bean.getSoftBinNo(),bean.getBinCount());
                }
            }else {
                TreeMap<Integer,Integer> binMap=new TreeMap<>();
                binMap.put(bean.getSoftBinNo(),bean.getBinCount());
                lotBinMap.put(bean.getWaferNo(),binMap);
            }
        }
        TreeSet<String> waferIdSet=new TreeSet<>(lotBinMap.keySet());
        for (String waferId : waferIdSet) {
            TreeMap<Integer,Integer> binSummary=lotBinMap.get(waferId);
            for (Integer bin : binSet) {
                if (!binSummary.containsKey(bin))
                {
                    binSummary.put(bin,0);
                }
            }
            for (LotSummaryWaferBean bean : lotSummary) {
                if (bean.getWaferNo().equals(waferId))
                {
                    ArrayList<Integer> yyy=new ArrayList<Integer>(binSummary.values());
                    bean.setBinSummary(yyy);
                }
            }
        }
        LotSummaryWaferBean binDefine=new LotSummaryWaferBean();
        binDefine.setWaferNo("bin_define");
        binDefine.setBinSummary(new ArrayList<Integer>(binSet));
        lotSummary.add(0,binDefine);
        return  JSON.toJSONString(lotSummary);
    }
    @ResponseBody
    @RequestMapping("/checkDataSourceType")
    public String checkDataTypeSource(@RequestParam("customer")String customer,@RequestParam("device")String device)
    {
        return  JSON.toJSONString(checkIfInforToMes.check(customer,device));
    }
    @RequestMapping("/getSuperPosition")
    @ResponseBody
    public String getSuperPosition(@RequestParam("customer")String customer,@RequestParam("device")String device,@RequestParam("lot")String lot,@RequestParam("cp")String cp)
    {
        ArrayList<superPostionBean> result=new ArrayList<>();
        ArrayList<Integer> passBins=new ArrayList<>();
        File[] rawdatas=new File("/server212/RawData/ProberRawdata/"+customer+"/"+device+"/"+lot+"/"+cp).listFiles();
        String passInfor="1";
        try {
            BufferedReader reader=new BufferedReader(new FileReader(rawdatas[0]));
            String content;
            while((content=reader.readLine())!=null)
            {
                    if (content.contains("Pass Bins"))
                    {
                        passInfor=content.split(":")[1];
                        break;
                    }
            }
            reader.close();
        } catch (IOException e) {
            passBins.add(1);
        }
        String[] passBinStr=(passInfor).split(",");
        for (int i = 0; i < passBinStr.length; i++) {
            passBins.add(Integer.valueOf(passBinStr[i]));
        }
        HashMap<String,Integer> resultMap=new HashMap<>();
        for (File rawdata : rawdatas) {
            ArrayList<mvcDieBean> binInfors= parseRawdataNew.getMap(rawdata);
            for (mvcDieBean bean : binInfors) {
                if (!passBins.contains(bean.getS()))
                {
                    if (resultMap.containsKey(bean.getX()+":"+bean.getY()))
                    {
                        resultMap.put(bean.getX()+":"+bean.getY(),resultMap.get(bean.getX()+":"+bean.getY())+1);
                    }else
                    {
                        resultMap.put(bean.getX()+":"+bean.getY(),1);
                    }
                }else if (!resultMap.containsKey(bean.getX()+":"+bean.getY()))
                {
                    resultMap.put(bean.getX()+":"+bean.getY(),0);
                }
            }
        }
        Set<String> coordinateSet=resultMap.keySet();
        for (String coodinate : coordinateSet) {
            superPostionBean bean=new superPostionBean();
            bean.setX(Integer.valueOf(coodinate.substring(0,coodinate.indexOf(":"))));
            bean.setY(Integer.valueOf(coodinate.substring(coodinate.indexOf(":")+1)));
            bean.setV(resultMap.get(coodinate));
            result.add(bean);
        }
        return  JSON.toJSONString(result);
    }
    @RequestMapping("/getCustomerAndDevices")
    @ResponseBody
    public String getCustomerAndDevice()
    {
        HashMap<String,ArrayList<String>> result=new HashMap<>();
        ArrayList<CustomerAndDevicesBean> list=proberDataDAO.getCustomerAndDevices();
        for (CustomerAndDevicesBean bean: list) {
            if (result.containsKey(bean.getCustomerCode()))
            {
                ArrayList<String> devices=result.get(bean.getCustomerCode());
                devices.add(bean.getDevice());
                result.put(bean.getCustomerCode(),devices);
            }else
            {
                ArrayList<String> devices=new ArrayList<>();
                devices.add(bean.getDevice());
                result.put(bean.getCustomerCode(),devices);
            }
        }
        ArrayList<customerAndDevice> customerAndDevicesArray=new ArrayList<>();
        Set<String> customerSet=result.keySet();
        for (String customer : customerSet) {
            customerAndDevice customerAndDevice=new customerAndDevice();
            customerAndDevice.setCustomer(customer);
            customerAndDevice.setDevice(result.get(customer));
            customerAndDevicesArray.add(customerAndDevice);
        }
        return JSON.toJSONString(customerAndDevicesArray);
    }
    @RequestMapping("/getLotAndCps")
    @ResponseBody
    public String getLotAndCps(@RequestParam("customer")String customer,@RequestParam("device")String device)
    {
        HashMap<String,ArrayList<String>> result=new HashMap<>();
        ArrayList<LotAndCpsBean> list=proberDataDAO.getLotAndCp(customer,device);
        for (LotAndCpsBean bean: list) {
            if (result.containsKey(bean.getLot()))
            {
                ArrayList<String> cps=result.get(bean.getLot());
                cps.add(bean.getCp());
                result.put(bean.getLot(),cps);
            }else
            {
                ArrayList<String> cps=new ArrayList<>();
                cps.add(bean.getCp());
                result.put(bean.getLot(),cps);
            }
        }
        ArrayList<lotAndCp> lotAndCpssArray=new ArrayList<>();
        Set<String> lotSet=result.keySet();
        for (String lot : lotSet) {
            lotAndCp lotAndCp=new lotAndCp();
            lotAndCp.setLot(lot);
            lotAndCp.setCp(result.get(lot));
            lotAndCpssArray.add(lotAndCp);
        }
        return JSON.toJSONString(lotAndCpssArray);
    }
    @RequestMapping("/getWaferIds")
    @ResponseBody
    public String getWaferIDs(@RequestParam("customer")String customer,@RequestParam("device")String device,@RequestParam("lot")String lot,@RequestParam("cp")String cp)
    {
        ArrayList<WaferIdBean> waferIds=proberDataDAO.getWaferIds(customer,device,lot,cp);
        ArrayList<String> result=new ArrayList<>();
        for (WaferIdBean bean : waferIds) {
            result.add(bean.getWaferNo());
        }
        return JSON.toJSONString(result);
    }
    @RequestMapping("/getQureyInfors")
    @ResponseBody
    public String getqureyWaferInfors(@RequestParam("customer")String customer,@RequestParam(value = "device",required = false)String device,@RequestParam(value = "lot",required = false)String lot,@RequestParam(value = "cp",required = false)String cp,@RequestParam(value = "wafer",required = false) String waferId)
    {
        ArrayList<GetWaferInforBean> result=proberDataDAO.getQureyInfors(customer,device,lot,cp,waferId);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/getYields")
    @ResponseBody
    public String getYields(@RequestParam("customer")String customer,@RequestParam("device")String device,@RequestParam("lot")String lot,@RequestParam("cp")String cp,@RequestParam(value = "wafer",required = false)String wafer)
    {
        ArrayList<waferYieldBean> summary=proberDataDAO.getWaferBinSummary(customer,device,lot,cp,wafer);
        ArrayList<waferIdInforBean> paramBeanSummary=proberDataDAO.getOthersParam(customer,device,lot,cp,wafer);
        HashMap<Integer,String> lotMap=new HashMap<>();
        HashMap<String,String> waferLimitYieldMap=new HashMap<>();
        HashMap<String,String> lotLimitYieldMap=new HashMap<>();
        for (waferIdInforBean bean : paramBeanSummary) {
            String id=(String)JSON.parseObject(bean.getParams()).get("RightID");
            String limits=(String)JSON.parseObject(bean.getParams()).get("Process Yield");
            String waferLimit=limits.split("\\|")[0];
            String lotLimit=limits.split("\\|")[1];
            String waferId=bean.getWaferId();
            lotMap.put(Integer.valueOf(id),waferId);
            waferLimitYieldMap.put(waferId,waferLimit.equals("NA")?"0.00":waferLimit.substring(0,waferLimit.length()-1));
            lotLimitYieldMap.put(waferId,lotLimit.equals("NA")?"0.00":lotLimit.substring(0,lotLimit.length()-1));
        }
        TreeSet<Integer> binSet=new TreeSet<>();
        HashMap<String,Integer> passDieMap=new HashMap<>();
        HashMap<String,HashMap<Integer,Integer>> lotBinMap=new HashMap<>();
        HashMap<String,Integer> waferIdGrossDieMap=new HashMap<>();
        for (waferYieldBean bean :summary) {
            binSet.add(bean.getSoftBinNo());
            if (waferIdGrossDieMap.containsKey(bean.getWaferNo()))
            {
                waferIdGrossDieMap.put(bean.getWaferNo(),bean.getBinCount()+waferIdGrossDieMap.get(bean.getWaferNo()));
            }else {
                waferIdGrossDieMap.put(bean.getWaferNo(),bean.getBinCount());
            }
            if (bean.getPassFail())
            {
                if (passDieMap.containsKey(bean.getWaferNo()))
                {
                    passDieMap.put(bean.getWaferNo(),passDieMap.get(bean.getWaferNo())+bean.getBinCount());
                }else
                {
                    passDieMap.put(bean.getWaferNo(),bean.getBinCount());
                }
            }
            if (lotBinMap.containsKey(bean.getWaferNo()))
            {
                HashMap<Integer,Integer> binMap=lotBinMap.get(bean.getWaferNo());
                if (binMap.containsKey(bean.getSoftBinNo()))
                {
                    binMap.put(bean.getSoftBinNo(),binMap.get(bean.getSoftBinNo())+bean.getBinCount());
                }else
                {
                    binMap.put(bean.getSoftBinNo(),bean.getBinCount());
                }
            }else {
                HashMap<Integer,Integer> binMap=new HashMap<>();
                binMap.put(bean.getSoftBinNo(),bean.getBinCount());
                lotBinMap.put(bean.getWaferNo(),binMap);
            }
        }
        //wafer order by id
        Infor waferIdsOrder=new Infor();
        waferIdsOrder.setName("wafer");
        Infor passYieldInfor=new Infor();
        passYieldInfor.setName("yield");
        Infor waferLimit=new Infor();
        waferLimit.setName("wafer yield limit");
        ArrayList<Object> waferLimitYieldArray=new ArrayList<>();

        Infor lotLimit=new Infor();
        lotLimit.setName("lot yield limit");
        ArrayList<Object> lotLimitYieldArray=new ArrayList<>();

        ArrayList<Object> waferIdArray=new ArrayList<>();
        ArrayList<Object> waferIdPassYieldArray=new ArrayList<>();
        TreeSet<Integer>  waferOrderSet=new TreeSet<>(lotMap.keySet());
        ArrayList<Infor> binYieldInforArray=new ArrayList<>();
        for (Integer bin: binSet) {
            Infor infor=new Infor();
            infor.setName("Bin"+bin);
            ArrayList<Object> yieldArray=new ArrayList<>();
            infor.setValues(yieldArray);
            binYieldInforArray.add(infor);
        }
        for (Integer order : waferOrderSet) {
            String waferId=lotMap.get(order);
            waferLimitYieldArray.add(waferLimitYieldMap.get(waferId));
            lotLimitYieldArray.add(lotLimitYieldMap.get(waferId));

            waferIdArray.add(waferId);
            HashMap<Integer,Integer> binSummaryMap=lotBinMap.get(waferId);
            for (Infor infor:binYieldInforArray) {
                Integer bin=Integer.valueOf(infor.getName().substring(3));
                if (binSummaryMap.containsKey(bin))
                {
                    infor.getValues().add(String.format("%.6f",((double)binSummaryMap.get(bin))*100/waferIdGrossDieMap.get(waferId) ));
                }else
                {
                    infor.getValues().add(String.format("%.6f", 0.00 ));
                }
            }
            waferIdPassYieldArray.add(String.format("%.6f",(((double)passDieMap.get(waferId))*100/waferIdGrossDieMap.get(waferId))));
        }
        waferLimit.setValues(waferLimitYieldArray);
        lotLimit.setValues(lotLimitYieldArray);
        waferIdsOrder.setValues(waferIdArray);
        passYieldInfor.setValues(waferIdPassYieldArray);
        binYieldInforArray.add(waferIdsOrder);
        binYieldInforArray.add(0,passYieldInfor);
        binYieldInforArray.add(waferLimit);
        binYieldInforArray.add(lotLimit);
        //wafer  pass yield by id
        return JSON.toJSONString(binYieldInforArray);
    }
    class Infor{
        private String name;
        private ArrayList<Object> values;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Object> getValues() {
            return values;
        }

        public void setValues(ArrayList<Object> values) {
            this.values = values;
        }
    }
    class customerAndDevice{
        private String customer;
        private ArrayList<String> device;

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public ArrayList<String> getDevice() {
            return device;
        }

        public void setDevice(ArrayList<String> device) {
            this.device = device;
        }
    }
    class lotAndCp{
        private String lot;
        private ArrayList<String> cp;

        public String getLot() {
            return lot;
        }

        public void setLot(String lot) {
            this.lot = lot;
        }

        public ArrayList<String> getCp() {
            return cp;
        }

        public void setCp(ArrayList<String> cp) {
            this.cp = cp;
        }
    }
    class superPostionBean
    {
        private Integer x;
        private Integer y;
        private Integer v;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }
    }
}
