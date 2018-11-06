package com.vtest.it.MappingParseTools;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.GetRandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StdfTesterMappingParse {
	private GetRandomNumber getRandomNumber;
	@Autowired
	public void setGetRandomNumber(GetRandomNumber getRandomNumber) {
		this.getRandomNumber = getRandomNumber;
	}
	class TimeAndTimes{
		private  long testTime=0;
		private Integer touchDownTime=0;

		public long getTestTime() {
			return testTime;
		}

		public void setTestTime(long testTime) {
			this.testTime = testTime;
		}

		public Integer getTouchDownTime() {
			return touchDownTime;
		}

		public void setTouchDownTime(Integer touchDownTime) {
			this.touchDownTime = touchDownTime;
		}
	}
	public void get(ArrayList<File> mapping, RawdataInitBean bean) throws IOException, ParseException
	{
		HashSet<Integer> passSet=new HashSet<>();

		LinkedHashMap<String,String> properties=new LinkedHashMap<>();
		HashMap<Integer,HashMap<Integer,Integer>> siteBinSum=new HashMap<>();
		HashMap<Integer,HashMap<Integer,Integer>> primarySiteBinSum=new HashMap<>();
		HashMap<Integer,HashMap<Integer,Integer>> retestSiteBinSum=new HashMap<>();
		HashMap<String,String> DieMap=new HashMap<>();
		HashMap<String,String> skipAndMarkDieMap=new HashMap<>();
		HashMap<Integer,Integer> Bin_summary_Map =new HashMap<>();

		String Test_End_Time=new SimpleDateFormat("yyMMddHHmmss").format(mapping.get(mapping.size()-1).lastModified());
		HashMap<String, String> inforsResultMap=getWaferIdInfors(mapping.get(0));
		String waferId=inforsResultMap.get("waferId");
		String Test_Start_Time=inforsResultMap.get("testStartTime");
		String customerCode=inforsResultMap.get("customerCode");
		String device=inforsResultMap.get("device");
		String lot=inforsResultMap.get("lot");
		String cp=inforsResultMap.get("cp");
		String op=inforsResultMap.get("op");
		int reTestDies=0;
		StringBuilder touchDownBuilder=new StringBuilder();
		HashSet<String> touchDownPassRecord=new HashSet<>(); 
		HashMap<String, String> tempDieMap=new HashMap<>();
		TimeAndTimes timeAndTimes=new TimeAndTimes();
		timeAndTimes.setTestTime(0);
		timeAndTimes.setTouchDownTime(0);
		for (File file : mapping) {
				ArrayList<String> dieInforList=parse(file,touchDownBuilder,touchDownPassRecord,timeAndTimes);
				for (String dieInfor : dieInforList) {
					String[] infordetal=dieInfor.split(":");
					String coordinateX=infordetal[4];
					String coordinateY=infordetal[5];
					String passFlag=infordetal[1];
					String siteNumber=infordetal[0];
					String hardBin=infordetal[2];
					String softBin=infordetal[3];
					String key=String.format("%4s", coordinateX)+String.format("%4s", coordinateY);
					Integer siteNum=Integer.valueOf(siteNumber);
					Integer softBinValue=Integer.valueOf(softBin);
					if (tempDieMap.containsKey(key)) {
						if (retestSiteBinSum.containsKey(siteNum))
						{
							HashMap<Integer,Integer> binMap=retestSiteBinSum.get(siteNum);
							if (binMap.containsKey(softBinValue))
							{
								binMap.put(softBinValue,binMap.get(softBinValue)+1);
							}else
							{
								binMap.put(softBinValue,1);
							}
						}else
						{
							HashMap<Integer,Integer> binMap=new HashMap<>();
							binMap.put(softBinValue,1);
							retestSiteBinSum.put(siteNum,binMap);
						}
						if (passFlag.equals("true")) {
							passSet.add(Integer.valueOf(softBin));
							reTestDies++;
						}
					}else {
						if (primarySiteBinSum.containsKey(siteNum))
						{
							HashMap<Integer,Integer> binMap=primarySiteBinSum.get(siteNum);
							if (binMap.containsKey(softBinValue))
							{
								binMap.put(softBinValue,binMap.get(softBinValue)+1);
							}else
							{
								binMap.put(softBinValue,1);
							}
						}else
						{
							HashMap<Integer,Integer> binMap=new HashMap<>();
							binMap.put(softBinValue,1);
							primarySiteBinSum.put(siteNum,binMap);
						}
						if (passFlag.equals("true")) {
							passSet.add(Integer.valueOf(softBin));
						}
					}
					tempDieMap.put(key, String.format("%4s", hardBin)+String.format("%4s", softBin)+String.format("%4s", siteNumber));				
				}
				Set<String> tempMapSet=tempDieMap.keySet();
				for (String die : tempMapSet) {
					DieMap.put(die, tempDieMap.get(die));
				}
		}
		HashMap<String, Integer> primaryTouchDownMap=new HashMap<>();
		HashMap<String, Integer> reTestTouchDownMap=new HashMap<>();
		
		HashSet<String> primaryTouchDownSet=new HashSet<>();
		HashSet<String> reTestTouchDownSet=new HashSet<>();
		String[] touchDownInfors=touchDownBuilder.toString().split(";");
		for (String touchDownInfor : touchDownInfors) {
			String touchDownTimes=touchDownInfor.substring(0,touchDownInfor.indexOf(":"));
			touchDownInfor=touchDownInfor.substring(touchDownInfor.indexOf(":")+1);
			String coordinate=touchDownInfor.substring(0, touchDownInfor.lastIndexOf(":"));
			Integer touchDownTime=Integer.valueOf(touchDownInfor.substring(touchDownInfor.lastIndexOf(":")+1));
			if (!primaryTouchDownSet.contains(coordinate)) {
				primaryTouchDownSet.add(coordinate);
				primaryTouchDownMap.put(touchDownTimes, touchDownTime);
			}else {
				reTestTouchDownSet.add(coordinate);	
				reTestTouchDownMap.put(touchDownTimes, touchDownTime);
			}
			
		}
		int passDies=0;
		int failDies=0;
		
		int testDieMinX=0;
		int testDieMinY=0;
		int testDieMaxX=0;
		int testDieMaxY=0;
		boolean testDieFlag=true;	
		Set<String> testDieSet=DieMap.keySet();
		for (String testDie : testDieSet) {
			int CellX=Integer.valueOf(testDie.substring(0, 4).trim());
			int CellY=Integer.valueOf(testDie.substring(4).trim());
			Integer softBinValue=Integer.valueOf(DieMap.get(testDie).substring(4,8).trim());
			Integer siteNum=Integer.valueOf(DieMap.get(testDie).substring(8).trim());
			if (siteBinSum.containsKey(siteNum))
			{
				HashMap<Integer,Integer> binMap=siteBinSum.get(siteNum);
				if (binMap.containsKey(softBinValue))
				{
					binMap.put(softBinValue,binMap.get(softBinValue)+1);
				}else
				{
					binMap.put(softBinValue,1);
				}
			}else
			{
				HashMap<Integer,Integer> binMap=new HashMap<>();
				binMap.put(softBinValue,1);
				siteBinSum.put(siteNum,binMap);
			}
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
		Test_Start_Time="20"+Test_Start_Time+ getRandomNumber.getRandomNumber(2);
		Test_End_Time="20"+Test_End_Time+getRandomNumber.getRandomNumber(2);

		
		int cols=testDieMaxX-testDieMinX+1;
		int Rows=testDieMaxY-testDieMinY+1;
		
		properties.put("Wafer ID", waferId.trim());
		properties.put("Operator", op);
		properties.put("CP Process", cp);
		properties.put("Test Start Time", Test_Start_Time);
		properties.put("Test End Time", Test_End_Time);
		properties.put("Gross Die", String.valueOf(passDies+failDies));
		properties.put("Pass Die", String.valueOf(passDies));
		properties.put("Fail Die",String.valueOf(failDies));
		properties.put("Wafer Yield", String.format("%4.2f", (double)passDies*100/(passDies+failDies))+"%");
		properties.put("DataBase", "STDF");
		properties.put("TestTime", String.format("%.0f", ((float)timeAndTimes.testTime/1000/60)));
		properties.put("Index X(mm)", "NA");
		properties.put("Index Y(mm)", "NA");
		properties.put("Map Cols", String.valueOf(cols));
		properties.put("Map Rows", String.valueOf(Rows));
		properties.put("Notch", "NA");
		properties.put("Retest Rate", String.format("%4.2f", (double)reTestDies*100/(passDies+failDies))+"%");
		properties.put("WF_Size", "NA");
		properties.put("Slot", "NA");
		properties.put("MinX", String.valueOf(testDieMinX));
		properties.put("MinY", String.valueOf(testDieMinY));
		properties.put("MaxX", String.valueOf(testDieMaxX));
		properties.put("MaxY", String.valueOf(testDieMaxY));
		properties.put("TestDieMinX", String.valueOf(testDieMinX));
		properties.put("TestDieMinY", String.valueOf(testDieMinY));
		properties.put("TestDieMaxX", String.valueOf(testDieMaxX));
		properties.put("TestDieMaxY", String.valueOf(testDieMaxY));

		bean.setDataProperties(properties);
		bean.setSiteBinSum(siteBinSum);
		bean.setTestDieMap(DieMap);
		bean.setSkipAndMarkDieMap(skipAndMarkDieMap);
		bean.setPrimarySiteBinSum(primarySiteBinSum);
		bean.setRetestSiteBinSum(retestSiteBinSum);

		long primaryTouchDownDuringTime=0;
		long reTestTouchDownDuringTime=0;
		long passTouchDownTotoalTimes=0;
		Integer passTouchDownNumber=0;
		for (String timeOrder : touchDownPassRecord) {
			if (primaryTouchDownMap.containsKey(timeOrder)) {
				passTouchDownTotoalTimes+=primaryTouchDownMap.get(timeOrder);
				passTouchDownNumber++;
			}
		}
		
		Set<String> primaryKeySet=primaryTouchDownMap.keySet();
		for (String primaryKey : primaryKeySet) {
			primaryTouchDownDuringTime+=primaryTouchDownMap.get(primaryKey);
		}
		Set<String> reTestKeySet=reTestTouchDownMap.keySet();
		for (String retestKey : reTestKeySet) {
			reTestTouchDownDuringTime+=reTestTouchDownMap.get(retestKey);
		}

		bean.setPrimaryTouchDownTimes(primaryKeySet.size());
		bean.setPrimaryTouchDownDuringTime(primaryTouchDownDuringTime);
		bean.setPrimaryRate((double)(passDies-reTestDies)*100/(passDies+failDies));
		bean.setReTestRate((double)reTestDies*100/(passDies+failDies));
		bean.setReTestTouchDownTimes(reTestKeySet.size());
		bean.setReTestTouchDownDuringTime(reTestTouchDownDuringTime);
		bean.setSingleTouchDownDuringTime(passTouchDownTotoalTimes/passTouchDownNumber);
		bean.setTestDuringTime(timeAndTimes.testTime);
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
	private ArrayList<String> parse(File stdfText,StringBuilder touchDownBuilder,HashSet<String> touchDownPassRecord,TimeAndTimes timeAndTimes) throws IOException, ParseException
	{
		long testStartTime= 0;
		long testFinnishTime = 0;
		ArrayList<String> dieInforList=new ArrayList<>();
		FileReader reader=new FileReader(stdfText);
		BufferedReader bufferedReader=new BufferedReader(reader);
		String content;
		boolean dieStartFlag=false;
		StringBuilder SB=new StringBuilder();
		boolean touchDownFlag=false;
		boolean startRecordflag=false;
		while((content=bufferedReader.readLine())!=null)
		{
			if (content.contains("START_T:")) {
				String Test_Start_Time=content.split("\\(")[1];
				testStartTime=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH).parse(Test_Start_Time.substring(0, Test_Start_Time.length()-1)).getTime();

			}
			if (startRecordflag&&content.contains("PIR Record")) {
				startRecordflag=false;
				timeAndTimes.touchDownTime++;
			}
			if (content.contains("PIR Record")) {
				touchDownFlag=true;
				continue;
			}
			if (touchDownFlag&&content.contains("PRR Record")) {
				startRecordflag=true;
			}
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
						touchDownPassRecord.add(String.valueOf(timeAndTimes.touchDownTime));
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
					touchDownBuilder.append(timeAndTimes.touchDownTime+":"+content.split(":")[1].trim()+":");
					continue;
				}
				if (content.contains("Y_COORD:")) {
					SB.append(content.split(":")[1].trim());	
					touchDownBuilder.append(content.split(":")[1].trim()+":");					
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
				if (content.contains("TEST_T")) {
					String touchDownTestTime=content.split(":")[1].trim();
					touchDownBuilder.append(touchDownTestTime+";");
					dieStartFlag=false;
				}
			}
			if (content.contains("FINISH_T:")) {
				String Test_End_Time=content.split("\\(")[1];				
				testFinnishTime=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH).parse(Test_End_Time.substring(0, Test_End_Time.length()-1)).getTime();
				Test_End_Time=new SimpleDateFormat("yyMMddHHmmss").format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH).parse(Test_End_Time));
				break;
			}
		}
		timeAndTimes.testTime+=(testFinnishTime-testStartTime);
		bufferedReader.close();
		return dieInforList;
	}
}
