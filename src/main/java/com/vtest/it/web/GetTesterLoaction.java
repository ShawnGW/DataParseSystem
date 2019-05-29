package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.vtmesdao.VtMesTesterDAO;
import com.vtest.it.pojo.testerLocationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/getTesterLocation")
public class GetTesterLoaction {
    private VtMesTesterDAO vtMesTesterDAO;
    @Autowired
    public void setVtMesTesterDAO(VtMesTesterDAO vtMesTesterDAO) {
        this.vtMesTesterDAO = vtMesTesterDAO;
    }
    @ResponseBody
    @RequestMapping(value = "/location",produces ="text/html;charset=UTF-8")
    public String getStatus()
    {
        ArrayList<testerLocationBean> testerLocation=vtMesTesterDAO.getTesterLocation();
        return JSON.toJSONString(testerLocation);
    }
}
