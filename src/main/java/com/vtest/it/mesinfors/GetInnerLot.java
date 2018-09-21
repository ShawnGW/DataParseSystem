package com.vtest.it.mesinfors;

import com.vtest.it.mestools.GetInnerLotFromMes;
import com.vtest.it.tools.OutPutSpecialChar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.TreeMap;

@Service
public class GetInnerLot {

	private OutPutSpecialChar outPutSpecialChar;
	private GetMesInformations getMesInformations;
	private GetInnerLotFromMes getInnerLotFromMes;

	@Autowired
	public void setGetInnerLotFromMes(GetInnerLotFromMes getInnerLotFromMes) {
		this.getInnerLotFromMes = getInnerLotFromMes;
	}
	@Autowired
	public void setOutPutSpecialChar(OutPutSpecialChar outPutSpecialChar) {
		this.outPutSpecialChar = outPutSpecialChar;
	}
	@Autowired
	public void setGetMesInformations(GetMesInformations getMesInformations) {
		this.getMesInformations = getMesInformations;
	}

	public  String get(String waferId) {
		String innerLot="NA";
		ArrayList<Integer> tokens=new ArrayList<>();
		ArrayList<String> contents=new ArrayList<>();
		getInnerLotFromMes.setWaferId(waferId);
		String infor=getMesInformations.getInfor(getInnerLotFromMes, GetMesInformations.TYPE_INNERLOT).get("INFOR");
		outPutSpecialChar.outPut(infor,tokens,contents);
		TreeMap<Integer, String> innerLotMap=new TreeMap<>();
		for (String content : contents) {
			Integer status=Integer.valueOf(content.substring(tokens.get(tokens.size()-2)).trim());
			if (status>0) {
				innerLot=content.substring(0, tokens.get(1));
				innerLotMap.put(status, innerLot);
			}
		}
		if (innerLotMap.keySet().size()>0) {
			return innerLotMap.get(innerLotMap.keySet().iterator().next());
		}
		return innerLot;
	}
}
