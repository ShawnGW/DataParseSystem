package com.vtest.it.bumppingMergeParseTools;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class FscBumpMapping {
	public ArrayList<String> parse(File file)throws IOException, ParserConfigurationException, SAXException
	{
		ArrayList<String> failDieArray=new ArrayList<>();		
		FileReader in=new FileReader(file);
		BufferedReader bReader=new BufferedReader(in);
		String content;
		boolean flag=false;
		Integer coordinateY=0;
		while((content=bReader.readLine())!=null)
		{
			if (content.contains("MAPSTART")) {
				flag=true;
				continue;
			}
			if (flag) {
				for (int i = 0; i < content.length(); i++) {
					if (content.substring(i, i+1).equals("X")) {
						failDieArray.add(i+","+coordinateY+",99");
					}
				}
				coordinateY++;
			}
		}
		bReader.close();
		return failDieArray;
	}
}
