package com.vtest.it.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetStreamFromMes {
	public static BufferedReader getStream(String URL) throws IOException
	{
		URL url=new URL(MesProperties.SERVICE_URL+URL);
		HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("HOST", MesProperties.SERVICE_HOST);
		urlConnection.setReadTimeout(10000);
		urlConnection.setConnectTimeout(10000);
		initUrlConnecttion(urlConnection, 0);
		InputStream inputStream=urlConnection.getInputStream();
		InputStreamReader IsReader=new InputStreamReader(inputStream,"utf8");
		BufferedReader bufferedReader=new BufferedReader(IsReader);	
		return bufferedReader;
	}
	private static void initUrlConnecttion(HttpURLConnection urlConnection,int times)
	{
		try {
			urlConnection.connect();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			times++;
			if (times<3) {
				initUrlConnecttion(urlConnection, times);
			}
		}
	}
}
