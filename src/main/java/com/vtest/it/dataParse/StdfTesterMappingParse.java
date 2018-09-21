package com.vtest.it.dataParse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vtest.it.tools.GetRandomNumber;
import com.vtest.it.tools.HTS8689MarkToPass;
import com.vtest.it.tools.getSlotFromMappingDat;
import org.springframework.beans.factory.annotation.Autowired;

public class StdfTesterMappingParse {
	public String Test_End_Time;
	public int reTestDies=0;
	private GetRandomNumber getRandomNumber;
	private getSlotFromMappingDat getSlotFromMappingDat;
	private HTS8689MarkToPass hts8689MarkToPass;

	@Autowired
	public void setHts8689MarkToPass(HTS8689MarkToPass hts8689MarkToPass) {
		this.hts8689MarkToPass = hts8689MarkToPass;
	}

	@Autowired
	public void setGetSlotFromMappingDat(com.vtest.it.tools.getSlotFromMappingDat getSlotFromMappingDat) {
		this.getSlotFromMappingDat = getSlotFromMappingDat;
	}

	@Autowired
	public void setGetRandomNumber(GetRandomNumber getRandomNumber) {
		this.getRandomNumber = getRandomNumber;
	}

	public HashMap<String, String> get(ArrayList<File> mapping, HashMap<Integer, Integer> Bin_summary_Map, HashMap<String, String> DieMap, HashMap<String, String> skipAndMarkDieMap) throws IOException, ParseException
	{
		HashSet<Integer> passSet=new HashSet<>();
		HashMap<String, String> resultMap=new HashMap<>();
		HashMap<String, String> inforsResultMap=getWaferIdInfors(mapping.get(0));
		String waferId=inforsResultMap.get("waferId");
		String Test_Start_Time=inforsResultMap.get("testStartTime");
		String customerCode=inforsResultMap.get("customerCode");
		String device=inforsResultMap.get("device");
		String lot=inforsResultMap.get("lot");
		String cp=inforsResultMap.get("cp");
		String op=inforsResultMap.get("op");
		File slotDataSource=null;
		String slot="NA";
		if (slot.equals("NA")) {
			try {
				File[] tskFiles=new File("/server212/prod/prober/TSK/mapdown/"+lot).listFiles();
				String regex="[0-9]{3}\\."+waferId;
				Pattern pattern=Pattern.compile(regex);
				for (File file : tskFiles) {
					String fileName=file.getName();
					Matcher matcher=pattern.matcher(fileName);
					if (file.isFile()&&(matcher.find())) {
						slotDataSource=file;
					}
				}
				if (slotDataSource!=null) 
				{
					slot=String.valueOf(Integer.valueOf(slotDataSource.getName().substring(0,3)));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		if (slot.equals("NA")) {
			try {
				File[] tskFiles=new File("/server212/prod/prober/TEL/mapdown/"+lot).listFiles();
				for (File file : tskFiles) {
					String fileName=file.getName();
					if (file.isFile()&&(fileName.contains(waferId))) {
						if (fileName.contains("DAT")||fileName.contains("DA")) {
							slotDataSource=file;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (slotDataSource!=null) {
				if (slotDataSource.getName().contains("DAT")) {
					slot=getSlotFromMappingDat.get(slotDataSource);
				}else {
					slot=TelOpusProberMappingDaParse.GetResult(slotDataSource).get("slot");
				}
			}
		}
		File dataSource=new File("/server212/prod/BackupMap/"+customerCode+"/"+device+"/"+lot+"/"+cp);
		if (dataSource.exists()) {
			File[] datas=dataSource.listFiles();
			for (File file : datas) {
				String fileName=file.getName();
				if (file.isFile()&&(fileName.contains(waferId))) {
					if (fileName.contains("DAT")||fileName.contains("DA")) {
						slotDataSource=file;
					}
				}
			}
		}
		if (slot.equals("NA")) {
			if (slotDataSource!=null) {
				if (slotDataSource.getName().contains("DAT")) {
					slot=getSlotFromMappingDat.get(slotDataSource);
				}else {
					slot=TelOpusProberMappingDaParse.GetResult(slotDataSource).get("slot");
				}
			}
		}		
		if (slot.equals("NA")) {
			if (dataSource.exists()) {
				String regex="[0-9]{3}\\."+waferId+"_.*";
				Pattern pattern=Pattern.compile(regex);
				for (File file : dataSource.listFiles()) {
					String fileName=file.getName();
					Matcher matcher=pattern.matcher(fileName);
					if (file.isFile()&&(matcher.find())) {
						slotDataSource=file;
					}
				}
				if (slotDataSource!=null) 
				{
					slot=String.valueOf(Integer.valueOf(slotDataSource.getName().substring(0,3)));
				}
			}
		}
		if (slot.equals("NA"))
		{
			 dataSource=new File("/server212/prod/BackupMap/NA/NA/"+lot+"/"+cp);
			 if (dataSource.exists()) {
					File[] datas=dataSource.listFiles();
					for (File file : datas) {
						String fileName=file.getName();
						if (file.isFile()&&(fileName.contains(waferId))&&fileName.contains("DA")) {
								slotDataSource=file;
						}
					}
					if (slotDataSource!=null) 
					{
						slot=TelOpusProberMappingDaParse.GetResult(slotDataSource).get("slot");
					}
				}
		}
		if (slot.equals("NA"))
		{
			 dataSource=new File("/server212/SeriousEorrMapping/"+lot);
			 if (dataSource.exists()) {
					File[] datas=dataSource.listFiles();
					for (File file : datas) {
						String fileName=file.getName();
						if (file.isFile()&&(fileName.contains(waferId))) {
								slotDataSource=file;
						}
					}
					if (slotDataSource!=null) 
					{
						if (slotDataSource.getName().endsWith("DAT")) {
							slot=getSlotFromMappingDat.get(slotDataSource);
						}else {
							slot=String.valueOf(Integer.valueOf(slotDataSource.getName().substring(0,3)));
						}
					}
				}
		}
		
		boolean reTestFlag=true;
		for (File file : mapping) {
			HashMap<String, String> tempDieMap=new HashMap<>();
			if (reTestFlag) {
				ArrayList<String> dieInforList=parse(file);
				for (String dieInfor : dieInforList) {
					String[] infordetal=dieInfor.split(":");
					String coordinateX=infordetal[4];
					String coordinateY=infordetal[5];
					String passFlag=infordetal[1];
					String siteNumber=infordetal[0];
					String hardBin=infordetal[2];
					String softBin=infordetal[3];
					String key=String.format("%4s", coordinateX)+String.format("%4s", coordinateY);		
					if (tempDieMap.containsKey(key)) {
						if (passFlag.equals("true")) {
							passSet.add(Integer.valueOf(softBin));
							reTestDies++;
						}
					}else {
						if (passFlag.equals("true")) {
							passSet.add(Integer.valueOf(softBin));
						}
					}
					tempDieMap.put(key, String.format("%4s", hardBin)+String.format("%4s", softBin)+String.format("%4s", siteNumber));				
				}
				reTestFlag=false;
			}else {
				ArrayList<String> dieInforList=parse(file);
				for (String dieInfor : dieInforList) {
					String[] infordetal=dieInfor.split(":");
					String coordinateX=infordetal[4];
					String coordinateY=infordetal[5];
					String passFlag=infordetal[1];
					String siteNumber=infordetal[0];
					String hardBin=infordetal[2];
					String softBin=infordetal[3];
					String key=String.format("%4s", coordinateX)+String.format("%4s", coordinateY);
					if (passFlag.equals("true")) {
						passSet.add(Integer.valueOf(softBin));
						reTestDies++;
					}
					tempDieMap.put(key, String.format("%4s", hardBin)+String.format("%4s", softBin)+String.format("%4s", siteNumber));				
				}
			}
			Set<String> tempMapSet=tempDieMap.keySet();
			for (String die : tempMapSet) {
				DieMap.put(die, tempDieMap.get(die));
			}
		}
		if (customerCode.equals("HTS")&&(device.equals("HS8689S1A2.CP")||device.equals("HS8916CMS1A1.CP")||device.equals("HS8916CMS1A3.CP"))) {
			hts8689MarkToPass.modifyMap(DieMap);
		}		
		int passDies=0;
		int failDies=0;
		
		int testDieMinX=0;
		int testDieMinY=0;
		int testDieMaxX=0;
		int testDieMaxY=0;
		boolean testDieFlag=true;	
		Set<String> testDieMap=DieMap.keySet();
		for (String testDie : testDieMap) {
			int CellX=Integer.valueOf(testDie.substring(0, 4).trim());
			int CellY=Integer.valueOf(testDie.substring(4).trim());
			Integer softBinValue=Integer.valueOf(DieMap.get(testDie).substring(4,8).trim());
			if (Bin_summary_Map.containsKey(softBinValue)) {
				Bin_summary_Map.put(softBinValue, Bin_summary_Map.get(softBinValue)+1);
			}else {
				Bin_summary_Map.put(softBinValue, 1);
			}
			if (testDieFlag) {
				 testDieMinX=CellX;
				 testDieMinY=CellY;
				 testDieMaxX=CellX;
				 testDieMaxY=CellY;
				 testDieFlag=false;
			}
			if (testDieMinX>CellX){
				testDieMinX=CellX;
			}
			if (testDieMinY>CellY){
				testDieMinY=CellY;
			}	
			if (testDieMaxX<CellX) {
				testDieMaxX=CellX;
			}
			if (testDieMaxY<CellY) {
				testDieMaxY=CellY;
			}
		}
		Set<Integer> binSumSet=Bin_summary_Map.keySet();
		for (Integer bin : binSumSet) {
			if (passSet.contains(bin)) {
				passDies+=Bin_summary_Map.get(bin);
			}else {
				failDies+=Bin_summary_Map.get(bin);
			}
		}
		Integer Year=Integer.valueOf(Test_Start_Time.substring(0, 2));
		Integer Mouth=Integer.valueOf(Test_Start_Time.substring(2,4));
		Integer Day=Integer.valueOf(Test_Start_Time.substring(4,6));
		Integer Hour=Integer.valueOf(Test_Start_Time.substring(6, 8));
		Integer Second=Integer.valueOf(Test_Start_Time.substring(8,10));

		Integer Year2=Integer.valueOf(Test_End_Time.substring(0, 2));
		Integer Mouth2=Integer.valueOf(Test_End_Time.substring(2,4));
		Integer Day2=Integer.valueOf(Test_End_Time.substring(4,6));
		Integer Hour2=Integer.valueOf(Test_End_Time.substring(6, 8));
		Integer Second2=Integer.valueOf(Test_End_Time.substring(8,10));
		Integer Test_Time=-((Year-Year2)*365*24*3600+(Mouth-Mouth2)*30*24*3600+(Day-Day2)*24*3600+(Hour-Hour2)*3600+(Second-Second2)*60);
		Test_Start_Time="20"+Test_Start_Time+getRandomNumber.getRandomNumber(2);
		Test_End_Time="20"+Test_End_Time+getRandomNumber.getRandomNumber(2);

		
		int cols=testDieMaxX-testDieMinX+1;
		int Rows=testDieMaxY-testDieMinY+1;
		
		resultMap.put("Wafer ID", waferId.trim());
		resultMap.put("Operator", op);
		resultMap.put("CP Process", cp);
		resultMap.put("Test Start Time", Test_Start_Time);
		resultMap.put("Test End Time", Test_End_Time);
		resultMap.put("Gross Die", String.valueOf(passDies+failDies));
		resultMap.put("Pass Die", String.valueOf(passDies));
		resultMap.put("Fail Die",String.valueOf(failDies));
		resultMap.put("Wafer Yield", String.format("%4.2f", (double)passDies*100/(passDies+failDies))+"%");
		resultMap.put("DataBase", "STDF");
		resultMap.put("TestTime", String.format("%.0f", ((float)Test_Time/60)));
		resultMap.put("Index X(mm)", "NA");
		resultMap.put("Index Y(mm)", "NA");
		resultMap.put("Map Cols", String.valueOf(cols));
		resultMap.put("Map Rows", String.valueOf(Rows));
		resultMap.put("Notch", "NA");
		resultMap.put("Retest Rate", String.format("%4.2f", (double)reTestDies*100/(passDies+failDies))+"%");
		resultMap.put("WF_Size", "NA");
		resultMap.put("Slot", String.valueOf(slot));
		resultMap.put("MinX", String.valueOf(testDieMinX));
		resultMap.put("MinY", String.valueOf(testDieMinY));
		resultMap.put("MaxX", String.valueOf(testDieMaxX));
		resultMap.put("MaxY", String.valueOf(testDieMaxY));
		resultMap.put("TestDieMinX", String.valueOf(testDieMinX));
		resultMap.put("TestDieMinY", String.valueOf(testDieMinY));
		resultMap.put("TestDieMaxX", String.valueOf(testDieMaxX));
		resultMap.put("TestDieMaxY", String.valueOf(testDieMaxY));
		return resultMap;
		
	}
	private HashMap<String, String> getWaferIdInfors(File file)
	{	
		HashMap<String, String> inforsResultMap=new HashMap<>();
		Integer TesterID=6;
		boolean flag=true;
		String[] nameTokens=file.getName().split("_");
		Integer Length=nameTokens.length;
		for (int j2 = 0; j2 < nameTokens.length; j2++) {
			if (nameTokens[j2].contains("TT")&&nameTokens[j2].length()==6) {
				if (flag) {
					TesterID=j2;
					flag=false;
				}
			}
		}
		String WaferID=nameTokens[TesterID-1];
		String Test_Start_Time=nameTokens[Length-2]+nameTokens[Length-1].substring(0,6);
		StringBuffer Device_Buffer=new StringBuffer(nameTokens[3]);
		for (int j2 = 4; j2< TesterID-2; j2++) {
			Device_Buffer.append("_");
			Device_Buffer.append(nameTokens[j2]);
		}
		String device=Device_Buffer.toString();
		String customerCode=nameTokens[2];
		String lot=nameTokens[TesterID-2];	
		String cp=nameTokens[Length-5];
		String Operater=nameTokens[Length-3];
		inforsResultMap.put("customerCode", customerCode);
		inforsResultMap.put("device", device);
		inforsResultMap.put("lot", lot);
		inforsResultMap.put("cp", cp);
		inforsResultMap.put("waferId", WaferID);
		inforsResultMap.put("op", Operater);
		inforsResultMap.put("testStartTime",Test_Start_Time.substring(2));
		return inforsResultMap;
	}
	public ArrayList<String> parse(File stdfText) throws IOException, ParseException
	{
		ArrayList<String> dieInforList=new ArrayList<>();
		FileReader reader=new FileReader(stdfText);
		BufferedReader bufferedReader=new BufferedReader(reader);
		String content;
		boolean dieStartFlag=false;
		Test_End_Time=new SimpleDateFormat("yyMMddHHmmss").format(stdfText.lastModified());
		StringBuilder SB=new StringBuilder();
		while((content=bufferedReader.readLine())!=null)
		{
			if (content.contains("PRR")) {
				SB=new StringBuilder();
				dieStartFlag=true;
				continue;
			}
			if(dieStartFlag)
			{				
				if (content.contains("SITE_NUM")) {
					SB.append(content.split(":")[1].trim()+":");
					continue;
				}
				if (content.contains("PART_FLG")) {
					if (content.split(":")[1].trim().equals("0x0")) {
						SB.append("true:");
					}else {
						SB.append("false:");
					}
					continue;
				}
				if (content.contains("HARD_BIN")) {
					SB.append(content.split(":")[1].trim()+":");
					continue;
				}
				if (content.contains("SOFT_BIN:")) {
					SB.append(content.split(":")[1].trim()+":");
					continue;
				}
				if (content.contains("X_COORD:")) {
					SB.append(content.split(":")[1].trim()+":");
					continue;
				}
				if (content.contains("Y_COORD:")) {
					SB.append(content.split(":")[1].trim());	
					dieStartFlag=false;
					String[] infors=SB.toString().split(":");
					boolean removeDie=false;
					for (int i = 2; i < infors.length; i++) {
						if (Math.abs(Integer.valueOf(infors[i]))>9999) {
							removeDie=true;
						}
					}					
					if (!removeDie) {
						dieInforList.add(SB.toString());
					}
				}
			}
			if (content.contains("FINISH_T:")) {
				String Test_End_Time=content.split("\\(")[1];
				Test_End_Time=new SimpleDateFormat("yyMMddHHmmss").format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH).parse(Test_End_Time));
				break;
			}
		}
		bufferedReader.close();
		return dieInforList;
	}
}
