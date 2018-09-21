package com.vtest.it.rawdataModify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RawDataFromStdf {
	private static final File config=new File("/Config/Rawdata_Form_Tester.ini");
	public static HashMap<String, ArrayList<String>> get()
	{
		HashMap<String, ArrayList<String>> shieldMap=new HashMap<>();
		try {
			FileReader reader=new FileReader(config);
			BufferedReader bReader=new BufferedReader(reader);
			String context;
			while((context=bReader.readLine())!=null)
			{
				String[] tokens=context.split(":");
				String customerCode=tokens[0];
				String[] devices=tokens[1].split(";");
				ArrayList<String> deviceArray=new ArrayList<>();
				for (String device : devices) {
					deviceArray.add(device);
				}
				shieldMap.put(customerCode, deviceArray);
			}
			bReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return shieldMap;
	}
}
