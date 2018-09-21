package com.vtest.it.mestools;

import com.vtest.it.properties.MesProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BinInforWriteToMes {
	public void Write(String lotNumber,String waferID,String BinSummary,String CP) throws IOException
	{
		InputStream inputStream;
		URL url=null;
		HttpURLConnection urlConnection=null;
		int RandomNumber=(int) ((Math.random()*100000000)/1);
		String serverURL=MesProperties.SERVICE_URL+"?Action=UploadBinSummaryPerWafer&ACode="+MesProperties.VERIFICATION_CODE+"&ItemName=WaferLot:"+lotNumber+"|WaferID:"+waferID+"|CP:"+CP.trim()+BinSummary+"&rand="+RandomNumber;
		url=new URL(serverURL);
		urlConnection=(HttpURLConnection)url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("HOST", MesProperties.SERVICE_HOST);
		urlConnection.setReadTimeout(10000);
		urlConnection.setConnectTimeout(10000);
		urlConnection.connect();
		inputStream=urlConnection.getInputStream();
		byte[] bs=new byte[1024];
		int length=0;
		while((length=inputStream.read(bs))!=-1)
		{
			System.out.println(new String(bs, 0, length));
		}
		urlConnection.disconnect();
	}
}
