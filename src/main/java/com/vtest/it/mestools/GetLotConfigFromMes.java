package com.vtest.it.mestools;

import com.vtest.it.properties.MesProperties;
import com.vtest.it.vtestinterface.MesInterface;
import org.springframework.stereotype.Service;

/**
 * get lot config
 * @author shawn.sun
 * @category IT
 * @version 2.0
 * @since 2018.3.15
 */

@Service
public class GetLotConfigFromMes implements MesInterface {
	private String lot;

	public void setLot(String lot) {
		this.lot = lot;
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		String URL="?ACode="+ MesProperties.VERIFICATION_CODE+"&Action=GetLotAttributes&ItemName=";
		return URL=URL+lot;
	}
}
