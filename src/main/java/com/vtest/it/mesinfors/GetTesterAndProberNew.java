package com.vtest.it.mesinfors;

import com.vtest.it.mestools.GetTesterProberAndProberCardNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GetTesterAndProberNew {
	private GetMesInformations getMesInformations;
	private GetTesterProberAndProberCardNew getTesterProberAndProberCardNew;

	@Autowired
	public void setGetMesInformations(GetMesInformations getMesInformations) {
		this.getMesInformations = getMesInformations;
	}

	@Autowired
	public void setGetTesterProberAndProberCardNew(GetTesterProberAndProberCardNew getTesterProberAndProberCardNew) {
		this.getTesterProberAndProberCardNew = getTesterProberAndProberCardNew;
	}

	public HashMap<String, String> Get(String waferid, String cp)
	{
		HashMap<String, String> resultMap=new HashMap<>();
		resultMap.put("Tester ID", "NA");
		resultMap.put("Prober ID", "NA");
		resultMap.put("Prober Card ID", "NA");
		getTesterProberAndProberCardNew.setWaferid(waferid);
		HashMap<String,String> result= getMesInformations.getInfor(getTesterProberAndProberCardNew, GetMesInformations.TYPE_TESTERANDPROBER_NEW);
		String[] everyCpConfig=result.get("INFOR").split("\\|");
		for (String config : everyCpConfig) {
			if (config.subSequence(0, 3).equals(cp)) {
				String[] testerAndProbers=config.split("@")[1].split("\\/");
				resultMap.put("Tester ID", testerAndProbers[0]);
				resultMap.put("Prober ID", testerAndProbers[1]);
				resultMap.put("Prober Card ID", testerAndProbers[2]);
			}
		}
		return resultMap;
	}
}
