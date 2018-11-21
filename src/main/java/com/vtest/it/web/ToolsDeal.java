package com.vtest.it.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Deal")
public class ToolsDeal {
    @RequestMapping("/dealDegreeTransform")
    public void  dealDegreeTransform(@RequestParam("coordinateX")String coordinateX, @RequestParam("coordinateY") String coordinateY, @RequestParam("MapChoose") String modifyType, @RequestParam("rawdata") MultipartFile file)
    {
        Integer modifyValueX;
        Integer modifyValueY;
        modifyValueX=coordinateX.startsWith("-")?-(Integer.valueOf(coordinateX.substring(1))):(coordinateX.startsWith("+")?Integer.valueOf(coordinateX.substring(1)):Integer.valueOf(coordinateX));
        modifyValueY=coordinateY.startsWith("-")?-(Integer.valueOf(coordinateY.substring(1))):(coordinateY.startsWith("+")?Integer.valueOf(coordinateY.substring(1)):Integer.valueOf(coordinateY));
        boolean flag=false;
        if (modifyType.equals("MarkDie"));
        {
            flag=true;
        }

    }
}
