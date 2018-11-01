package com.vtest.it.mestools;

import com.vtest.it.dao.vtptmtmapperdao.MesPropertiesDAO;
import com.vtest.it.pojo.MesProperties;
import com.vtest.it.properties.GetStreamFromMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * bin summary into MES
 * @author shawn.sun
 * @category IT
 * @version 2.0
 * @since 2018.3.15
 */
@Service
public class WaferidInforIntoMes {
	private MesPropertiesDAO mesPropertiesDAO;
	private GetStreamFromMes getStreamFromMes;

	@Autowired
	public void setMesPropertiesDAO(MesPropertiesDAO mesPropertiesDAO) {
		this.mesPropertiesDAO = mesPropertiesDAO;
	}

	@Autowired
	public void setGetStreamFromMes(GetStreamFromMes getStreamFromMes) {
		this.getStreamFromMes = getStreamFromMes;
	}

	public void write(String lotNumber, String waferID, String CP, String BinSummary)
	{
		try {
			MesProperties properties=mesPropertiesDAO.getProperties();
			String URL="?Action=UploadBinSummaryPerWafer&ACode=";
			int RandomNumber=(int) ((Math.random()*100000000)/1);
			URL=URL+properties.getAcode()+"&ItemName=WaferLot:"+lotNumber+"|WaferID:"+waferID+"|CP:"+CP.trim()+BinSummary+"&rand="+RandomNumber;
			getStreamFromMes.getStream(URL);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
