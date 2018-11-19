package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.probermapperdao.ProberDataDAO;
import com.vtest.it.pojo.binwaferinfors.waferIdInforBean;
import com.vtest.it.pojo.binwaferinfors.waferYieldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/getWaferInfor")
public class GetWaferInfor {
    private ProberDataDAO proberDataDAO;

    @Autowired
    public void setProberDataDAO(ProberDataDAO proberDataDAO) {
        this.proberDataDAO = proberDataDAO;
    }

    @RequestMapping("/getYields")
    @ResponseBody
    public String getYields(@RequestParam("customer")String customer,@RequestParam("device")String device,@RequestParam("lot")String lot,@RequestParam("cp")String cp,@RequestParam(value = "wafer",required = false)String wafer)
    {
        ArrayList<waferYieldBean> summary=proberDataDAO.getWaferBinSummary(customer,device,lot,cp,wafer);
        ArrayList<waferIdInforBean> paramBeanSummary=proberDataDAO.getOthersParam(customer,device,lot,cp,null);
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
}
