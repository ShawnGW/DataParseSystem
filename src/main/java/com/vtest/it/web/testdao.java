package com.vtest.it.web;

import com.vtest.it.excelModel.SiteInforReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
@RequestMapping("/DAO")
public class testdao {
    private SiteInforReport siteInforReport;

    @Autowired
    public void setSiteInforReport(SiteInforReport siteInforReport) {
        this.siteInforReport = siteInforReport;
    }
    @RequestMapping("/report")
    @ResponseBody
    public String getInfor()
    {
        siteInforReport.write("AMC","GXLX_A","T2AW94.00","CP1",null,null);
        return "N";
    }
}
