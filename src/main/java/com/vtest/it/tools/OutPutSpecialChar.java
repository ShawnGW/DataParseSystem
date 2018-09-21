package com.vtest.it.tools;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OutPutSpecialChar {
	public void outPut(String infor,ArrayList<Integer> tokenArray,ArrayList<String> contentArray) {
		tokenArray.add(0);
		boolean lineFlag=true;
		boolean contentFlag=false;
		int sum=0,index=infor.indexOf("-");
		while(sum<infor.length())
		{			
			String content=dealCharacter(infor.substring(sum, sum+index));
			sum=sum+index;
			if (contentFlag) {
				if (content.contains("---")) {
					break;
				}else {
					 contentArray.add(content);
					 continue;
				}			
			}
			if (content.contains("---")) {
				if (lineFlag) {
					String[] lines=content.split(" ");
					for (int i = 0; i < lines.length; i++) {					
							tokenArray.add(lines[i].length()+tokenArray.get(i)+1);
					}
					lineFlag=false;
					contentFlag=true;
					continue;
				}
			}			
		}
	}
	private  String dealCharacter(String str)
	{
		String regex="[\u4e00-\u9fa5]{1}";
		Pattern pat = Pattern.compile(regex);
		Matcher matcher = pat.matcher(str);
		if (matcher.find()) {
			String content=matcher.group();
			str = str.replace(content, "A");
			str = dealCharacter(str);
		}
		return str;
	}
}
