package com.vtest.it.mesinfors;

import com.vtest.it.mestools.StdfTouchDownInforIntoMes;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StdfTouchDownWrite {
	private StdfTouchDownInforIntoMes stdfTouchDownInforIntoMes;

	@Autowired
	public void setStdfTouchDownInforIntoMes(StdfTouchDownInforIntoMes stdfTouchDownInforIntoMes) {
		this.stdfTouchDownInforIntoMes = stdfTouchDownInforIntoMes;
	}

	public void write(HashMap<String, String> resultMap, RawdataInitBean bean) {
		String lotNum=resultMap.get("lot");
		String cp=resultMap.get("cpStep");
		String waferId=resultMap.get("waferId");		
		StringBuilder inforsBuilder=new StringBuilder();
		inforsBuilder.append("|FirstTestCnt:"+bean.getPrimaryTouchDownTimes());
		inforsBuilder.append("|FirstTestTime:"+bean.getPrimaryTouchDownDuringTime());
		inforsBuilder.append("|RetestCnt:"+bean.getReTestTouchDownTimes());
		inforsBuilder.append("|RetestTime:"+bean.getReTestTouchDownDuringTime());
		inforsBuilder.append("|SingleTestTime:"+bean.getSingleTouchDownDuringTime());
		inforsBuilder.append("|RetestYield:"+bean.getReTestRate());
		inforsBuilder.append("|TotalTestTime:"+bean.getTestDuringTime());
		stdfTouchDownInforIntoMes.write(lotNum, waferId, cp, inforsBuilder.toString());
	}
}
