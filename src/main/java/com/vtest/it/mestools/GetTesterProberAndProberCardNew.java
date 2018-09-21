package com.vtest.it.mestools;

import com.vtest.it.properties.MesProperties;
import com.vtest.it.vtestinterface.MesInterface;
import org.springframework.stereotype.Service;

@Service
public class GetTesterProberAndProberCardNew implements MesInterface {

	private String URL="?Action=GetWaferTestedMachines&ACode="+ MesProperties.VERIFICATION_CODE+"&ItemName=";
	private String waferid;

	public void setWaferid(String waferid) {
		this.waferid = waferid;
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return URL=URL+waferid;
	}
}
