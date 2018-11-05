package com.vtest.it.MappingParseTools;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Service
public class TelOpusProberLotDaParse {
	public  HashMap<String, String>  get(File file) throws IOException{
		HashMap<String, String> resultMap=new HashMap<>();
		FileReader reader=new FileReader(file);
		BufferedReader bReader=new BufferedReader(reader);
		String Content;
		String OP="NA";
		String CP="CP";
		while((Content=bReader.readLine())!=null)
		{
			OP=Content.substring(109, 129).trim();
			CP=CP+Content.substring(157,189).trim().substring(3, 4);
		}
		resultMap.put("op", OP.startsWith("V")?OP:"V088");
		resultMap.put("cp", CP);
		bReader.close();
		return resultMap;	
	}
}
