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
public class HTSBumpMapping {
	public ArrayList<String> parse(File file)throws IOException, ParserConfigurationException, SAXException
	{
		ArrayList<String> failDieArray=new ArrayList<>();		
		FileReader in=new FileReader(file);
		BufferedReader bReader=new BufferedReader(in);
		String content;
		boolean flag=false;
		boolean start=true;
		int startX=1;
		while((content=bReader.readLine())!=null)
		{
			if (start) 
			{
				startX=Integer.valueOf(content.substring(3, 6).trim());
				start=false;
			}
			if(content.contains("++-"))
			{
				flag=true;
				continue;
			}
			if(flag)
			{
				if(!content.contains("|"))
				{
					flag=false;
					break;
				}
			}			
			if (flag) {
				int coordianteY=Integer.valueOf(content.substring(0, 3));
				int length=content.length();
				for (int i = 6; i < length; i=i+3) {
					if (i+3>length) {
						if (content.substring(i, i+1).trim().equals("F")) {
							failDieArray.add(((i)/3+startX-3)+","+(coordianteY-1)+",99");
						}
					}else {
						if (content.substring(i, i+3).trim().equals("F")) {
							failDieArray.add(((i)/3+startX-3)+","+(coordianteY-1)+",99");
						}
					}
				}
			}
		}
		bReader.close();
		return  failDieArray;
	}
}
