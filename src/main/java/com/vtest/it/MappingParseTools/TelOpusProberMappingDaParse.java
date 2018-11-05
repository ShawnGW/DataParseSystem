package com.vtest.it.MappingParseTools;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Service
public class TelOpusProberMappingDaParse {
	public  HashMap<String, String> GetResult(File file)throws IOException {
		HashMap<String, String> resultMap=new HashMap<>();
		FileReader reader=new FileReader(file);
		BufferedReader bufferedReader=new BufferedReader(reader);
		String content;		
		String testStartTime="NA";
		String testEndTime="NA";
		String waferID="NA";
		String slot="NA";
		while((content=bufferedReader.readLine())!=null)
		{
			 testStartTime=content.substring(33,45);
			 testEndTime=content.substring(45,57);
			 waferID=content.substring(57, 90).trim();
			 slot=content.substring(93, 95);
		}
		bufferedReader.close();
		resultMap.put("testStartTime", testStartTime);
		resultMap.put("testEndTime", testEndTime);
		resultMap.put("waferid", waferID);
		resultMap.put("slot", slot);
		return resultMap;
	}
}
