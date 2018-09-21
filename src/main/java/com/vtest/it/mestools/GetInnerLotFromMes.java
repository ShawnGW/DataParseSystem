package com.vtest.it.mestools;

import com.vtest.it.properties.MesProperties;
import com.vtest.it.vtestinterface.MesInterface;
import org.springframework.stereotype.Service;


/**
 * get inner lot
 * @author shawn.sun
 * @category IT
 * @version 2.0
 * @since 2018.3.15
 */
@Service
public class GetInnerLotFromMes implements MesInterface {
	private String waferId;
	public void setWaferId(String waferId) {
		this.waferId = waferId;
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		String URL="?ACode="+ MesProperties.VERIFICATION_CODE+"&Action=GetWaferAttributes&ItemName=";
		return URL+waferId;
	}

}
