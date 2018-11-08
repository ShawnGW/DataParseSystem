package com.vtest.it.mesinfors;

import com.vtest.it.mestools.WaferidInforIntoMes;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

@Service
public class WaferIdBinSummaryWrite {
	private WaferidInforIntoMes waferidInforIntoMes;

	@Autowired
	public void setWaferidInforIntoMes(WaferidInforIntoMes waferidInforIntoMes) {
		this.waferidInforIntoMes = waferidInforIntoMes;
	}

	public void write(HashMap<String, String> resultMap, RawdataInitBean rawdataInitBean)
	{
		String lotNum=resultMap.get("lot");
		String cp=resultMap.get("cpStep");
		String waferId=resultMap.get("waferId");

		HashMap<Integer,HashMap<Integer,Integer>> siteBinSmmary=rawdataInitBean.getSiteBinSum();
		TreeMap<Integer,Integer> binSummary=new TreeMap<>();
		Set<Integer> siteSet=siteBinSmmary.keySet();
		for (Integer site: siteSet) {
			HashMap<Integer,Integer> binMap=siteBinSmmary.get(site);
			Set<Integer> binSet=binMap.keySet();
			for (Integer bin: binSet) {
				if (binSummary.containsKey(bin))
				{
					binSummary.put(bin,binSummary.get(bin)+binMap.get(bin));
				}
				else
				{
					binSummary.put(bin,binMap.get(bin));
				}
			}
		}
		StringBuffer SB=new StringBuffer();
		Set<Integer> set=binSummary.keySet();
		for (Integer bin : set) {
			SB.append("|Bin"+bin+":"+binSummary.get(bin));
		}
		String summary=SB.toString();
		waferidInforIntoMes.write(lotNum, waferId, cp, summary);	
	}
}
