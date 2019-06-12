package com.vtest.it.web;

import com.vtest.it.dao.vtmesdao.VtMesTesterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/getPassBin")
public class GetPassBin {
    private VtMesTesterDAO vtMesTesterDAO;
    @Autowired
    public void setVtMesTesterDAO(VtMesTesterDAO vtMesTesterDAO) {
        this.vtMesTesterDAO = vtMesTesterDAO;
    }
    @ResponseBody
    @RequestMapping(value = "/passBin",produces ="text/html;charset=UTF-8")
    public String getPassBin(@RequestParam("waferId")String waferId)
    {
        return vtMesTesterDAO.getPassBin(waferId);
    }
}
