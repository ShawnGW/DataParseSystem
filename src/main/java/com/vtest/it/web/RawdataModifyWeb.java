package com.vtest.it.web;

import com.vtest.it.pojo.RawdataModifyBean;
import com.vtest.it.rawdataModify.RawDataModify;
import com.vtest.it.rawdataModify.RawDataModifyUpdate;
import com.vtest.it.tools.RawdataModifyInitFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;

@Controller
@RequestMapping("/RawdataModify")
public class RawdataModifyWeb {
    private RawdataModifyInitFile rawdataModifyInitFile;
    private RawDataModify rawDataModify;
    private RawDataModifyUpdate rawDataModifyUpdate;

    @Autowired
    public void setRawDataModify(RawDataModify rawDataModify) {
        this.rawDataModify = rawDataModify;
    }

    @Autowired
    public void setRawDataModifyUpdate(RawDataModifyUpdate rawDataModifyUpdate) {
        this.rawDataModifyUpdate = rawDataModifyUpdate;
    }

    @Autowired
    public void setRawdataModifyInitFile(RawdataModifyInitFile rawdataModifyInitFile) {
        this.rawdataModifyInitFile = rawdataModifyInitFile;
    }

    @RequestMapping("/modify")
    public ModelAndView modify(@RequestParam("modifyFile") MultipartFile file, @RequestParam("type") String type, ModelAndView modelAndView)
    {
        ArrayList<RawdataModifyBean> dealList=new ArrayList<>();
        try {
            File Directory=new File("/FileUpload");
            if (!Directory.exists())
            {
                Directory.mkdirs();
            }
            File backupFile=new File("/FileUpload/"+file.getOriginalFilename());
            file.transferTo(backupFile);
            File[] modifyDatalogs=rawdataModifyInitFile.initUploadFile(backupFile);
            if (type.equals("deapModify"))
            {
                dealList=rawDataModify.Transfer(modifyDatalogs);
            }else {
                dealList= rawDataModifyUpdate.Transfer(modifyDatalogs);
            }
            modelAndView.addObject("dealList",dealList);
            modelAndView.setViewName("RawdataAfterDeal");
            return modelAndView;
        }catch (Exception e)
        {
            modelAndView.setViewName("modifyFailed");
            return modelAndView;
        }
    }
    @RequestMapping("/upload")
    public String UploadFiles()
    {
        return "RawDataModifyUpload";
    }
}
