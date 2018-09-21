package com.vtest.it.mestools;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SlotModify {
	public  String modify(HashMap<String, String> customerLotConfig,String waferid)
	{
		String Type=customerLotConfig.get("readType");
		if (Type.toUpperCase().equals("SLOT")){			
			String regex1="^[0-9A-Z]{1,}-[0-9A-Z]{1,}-[0-9]{1,2}$";
			String regex2="^[0-9A-Z]{1,}-[0-9A-Z]{1,}-[A-Z]{1}-[0-9]{1,}$";
			String regex3="^[0-9A-Z]{1,}[\\.]{1}[0-9A-Z]{1,}-[0-9]{1,}$";
			String regex4="^[0-9A-Z]{1,}-[0-9]{1,2}$";
			
			Pattern pattern1=Pattern.compile(regex1);
			Pattern pattern2=Pattern.compile(regex2);
			Pattern pattern3=Pattern.compile(regex3);
			Pattern pattern4=Pattern.compile(regex4);
			
			Matcher matcher1=pattern1.matcher(waferid);
			Matcher matcher2=pattern2.matcher(waferid);
			Matcher matcher3=pattern3.matcher(waferid);
			Matcher matcher4=pattern4.matcher(waferid);			
			
			String sequnce=customerLotConfig.get("sequence");
			
			String partLot=null;
			String value=null;
			if (matcher1.find()) {
				String[] tokens=waferid.split("-");
				partLot=tokens[0]+tokens[1].substring(0, 4);
				value=modifyValue(sequnce, tokens[2]);
			}else if (matcher2.find()) {
				String[] tokens=waferid.split("-");
				partLot=tokens[0]+tokens[1];
				value=modifyValue(sequnce, tokens[3]);
			}else if(matcher3.find()){
				String[] tokens=waferid.split("-");
				partLot=tokens[0].split("\\.")[0];
				value=modifyValue(sequnce, tokens[1]);
			}else if(matcher4.find()){
				String[] tokens=waferid.split("-");
				partLot=tokens[0];
				if (partLot.length()==7) {
					String specialRegex="[0-9]{7}";
					Pattern specialPattern=Pattern.compile(specialRegex);
					Matcher specialMatcher=specialPattern.matcher(partLot);
					if (specialMatcher.find()) {
						partLot=partLot.substring(0, 6)+"0";
					}
				}
				value=modifyValue(sequnce, tokens[1]);
			}
			
			if ((partLot+"-"+value).equals("null-null")) {
				return  waferid;
			}
			waferid=partLot+"-"+value;
			return waferid;
		}else{
			return waferid;
		} 		
		
	}
	public  String modifyTel(HashMap<String, String> customerLotConfig,String waferid) {
		String Type=customerLotConfig.get("readType");
		if (Type.toUpperCase().equals("SLOT")){			
			String regex1="^[0-9A-Z]{1,}-[0-9A-Z]{1,}-[0-9]{2}$";
			String regex2="^[0-9A-Z]{1,}-[0-9A-Z]{1,}-[A-Z]{1}-[0-9]{1,}$";
			String regex3="^[0-9A-Z]{1,}-[0-9]{2}$";
			
			Pattern pattern1=Pattern.compile(regex1);
			Pattern pattern2=Pattern.compile(regex2);
			Pattern pattern3=Pattern.compile(regex3);
			
			Matcher matcher1=pattern1.matcher(waferid);
			Matcher matcher2=pattern2.matcher(waferid);
			Matcher matcher3=pattern3.matcher(waferid);			
			
			String sequnce=customerLotConfig.get("sequence");
			
			String partLot=null;
			String value=null;
			if (matcher1.find()) {
				String[] tokens=waferid.split("-");
				partLot=tokens[0]+tokens[1].substring(0, 4);
				value=modifyValue(sequnce, tokens[2]);
			}else if (matcher2.find()) {
				String[] tokens=waferid.split("-");
				partLot=tokens[0]+tokens[1];
				value=modifyValue(sequnce, tokens[3]);
			}else if(matcher3.find()){
				String[] tokens=waferid.split("-");
				partLot=tokens[0];
				value=modifyValue(sequnce, tokens[1]);
			}
			
			if ((partLot+"-"+value).equals("null-null")) {
				return  waferid;
			}
			waferid=partLot+"-"+value;
			return waferid;
		}else{
			return waferid;
		} 
	}
	private  String modifyValue(String sequnce,String value)
	{
		
		if (sequnce.equals("1-25")) {
			if (value.length()==1) {
				value="0"+value;
			}
			return value;
		}else {
			Integer valueInt=Integer.valueOf(value);
			valueInt=26-valueInt;
			value=String.valueOf(valueInt);
			if (value.length()==1) {
				value="0"+value;
			}
			return value;
		}
		
	}
}
