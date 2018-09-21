package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.mesinfors.GetInnerLot;
import com.vtest.it.mesinfors.GetMesInformations;
import com.vtest.it.mestools.GetLotConfigFromMes;
import com.vtest.it.tools.RawdataModifyInitFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/bumpMapping")
public class BumpMapping {
    private RawdataModifyInitFile rawdataModifyInitFile;
    private GetInnerLot getInnerLot;
    private GetMesInformations getMesInformations;
    private GetLotConfigFromMes getLotConfigFromMes;

    @Autowired
    public void setGetLotConfigFromMes(GetLotConfigFromMes getLotConfigFromMes) {
        this.getLotConfigFromMes = getLotConfigFromMes;
    }

    @Autowired
    public void setGetMesInformations(GetMesInformations getMesInformations) {
        this.getMesInformations = getMesInformations;
    }

    @Autowired
    public void setGetInnerLot(GetInnerLot getInnerLot) {
        this.getInnerLot = getInnerLot;
    }

    @Autowired
    public void setRawdataModifyInitFile(RawdataModifyInitFile rawdataModifyInitFile) {
        this.rawdataModifyInitFile = rawdataModifyInitFile;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public String uploadBumpMapping(@RequestParam("bumpMapping")MultipartFile bumpMapping)throws IOException
    {
        ArrayList<HashMap<String,String>> resultList=new ArrayList<>();
        try {
            File Directory=new File("/FileUpload/bumpMapping");
            if (!Directory.exists())
            {
                Directory.mkdirs();
            }
            File backupFile=new File(Directory.getPath()+"/"+bumpMapping.getOriginalFilename());
            bumpMapping.transferTo(backupFile);
            File[] modifyDatalogs=rawdataModifyInitFile.initUploadFile(backupFile);
            for (int i = 0; i < modifyDatalogs.length; i++) {
                try {
                    File mapping=modifyDatalogs[i];
                    String waferId=mapping.getName();
                    String innerLot=getInnerLot.get(waferId);
                    getLotConfigFromMes.setLot(innerLot);
                    HashMap<String, String> result=getMesInformations.getInfor(getLotConfigFromMes, GetMesInformations.TYPE_CONFIG);
                    resultList.add(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(resultList);
    }
    @RequestMapping("/init")
    public String startUpload()
    {
        return "BumMapping/BumpMappingUpload";
    }
}
