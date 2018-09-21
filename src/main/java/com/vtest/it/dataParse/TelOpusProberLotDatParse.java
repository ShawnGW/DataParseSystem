package com.vtest.it.dataParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class TelOpusProberLotDatParse {
	public static HashMap<String, String> Get(File file) throws IOException
	{
		HashMap<String, String> resultMap=new HashMap<>();
		FileInputStream fios=new FileInputStream(file);
		byte[] bs=new byte[20000];
		fios.read(bs);
		fios.close();
		String Lot=null;
		byte[] Lot_BT=new byte[30];
		String Operater=null;
		byte[] OP_BT=new byte[5];
		String CP=null;
		byte[] Device_BT=new byte[14];
		
		for (int i = 0; i < 152; i++) {
			if (i<30) {
				Lot_BT[i]=bs[i];
			}
			if (i<35&&i>29) {
				OP_BT[i-30]=bs[i];
			}
			if (i>137) {
				Device_BT[i-138]=bs[i];
			}
		}
		Lot=new String(Lot_BT).trim();
		Operater=new String(OP_BT).trim();
		CP=new String(Device_BT).trim().substring(3, 4);
		resultMap.put("lot", Lot);
		resultMap.put("op", Operater);
		resultMap.put("cp", "CP"+CP);
		return resultMap;				
	}
}
