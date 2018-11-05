package com.vtest.it.MappingParseTools;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.GetRandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class TelProberMappingSmallDieParse {

	private GetRandomNumber getRandomNumber;
	@Autowired
	public void setGetRandomNumber(GetRandomNumber getRandomNumber) {
		this.getRandomNumber = getRandomNumber;
	}
	public  void Get(File file, HashMap<String, String> waferIdDaResultMap, RawdataInitBean bean,String cp,String op) throws IOException
	{
		LinkedHashMap<String,String> properties=new LinkedHashMap<>();
		HashMap<Integer,HashMap<Integer,Integer>> siteBinSum=new HashMap<>();
		HashMap<String,String> testDieMap=new HashMap<>();
		HashMap<String,String> skipAndMarkDieMap=new HashMap<>();
		HashMap<Integer,Integer> Bin_summary_Map =new HashMap<>();
		FileInputStream fins=new FileInputStream(file);
		byte[] bs=new byte[1000000];
		fins.read(bs);
		fins.close();
		Integer Rows=Integer.parseInt(Integer.toHexString(bs[0]&0xFF), 16);
			
		Integer Map_ROW_Start_index=2;
		ArrayList<Integer> MapRow_Start_indexs=new ArrayList<Integer>();
		
		int Total=0;
		for (int i = 0; i < bs.length; i++) {			
			if (Integer.toHexString(bs[i]&0xFF).equals("80")) {
				if (bs[i+1]==0&&bs[i+2]==0&&bs[i+3]==0&&bs[i+4]==0&&bs[i+5]==0&&bs[i+6]==0) {
					break;
				}
			}	
			Total=i-3;
		}
		while(Map_ROW_Start_index<Total)
		{
			String FirstIndex=Integer.toHexString(bs[Map_ROW_Start_index+5]&0xFF);
			String SecondIndex=Integer.toHexString(bs[Map_ROW_Start_index+4]&0xFF);
			if (SecondIndex.length()==1) {
				SecondIndex="0"+SecondIndex;
			}
			String Sum_Hex=FirstIndex+SecondIndex;
			Integer Sum=Integer.parseInt(Sum_Hex, 16);
			MapRow_Start_indexs.add(Map_ROW_Start_index);
			Map_ROW_Start_index+=Sum*5+6;			
		}
		int cols=0;
		int MinX=0;
		int MinY=0;
		int maxX=0;
		int maxY=0;
		int testDieMinX=0;
		int testDieMinY=0;
		int testDieMaxX=0;
		int testDieMaxY=0;
		boolean MFlag=true;
		boolean testDieFlag=true;	
		boolean  Max_Col_Flag=true;
		
		for (Integer index : MapRow_Start_indexs) {
			String Coordinate_X_First=Integer.toHexString(bs[index+1]&0xFF);
			String Coordinate_X_Second=Integer.toHexString(bs[index]&0xFF);
			if (Coordinate_X_Second.length()==1) {
				Coordinate_X_Second="0"+Coordinate_X_Second;
			}
			String Coordinate_Y_First=Integer.toHexString(bs[index+3]&0xFF);
			String Coordinate_Y_Second=Integer.toHexString(bs[index+2]&0xFF);
			if (Coordinate_Y_Second.length()==1) {
				Coordinate_Y_Second="0"+Coordinate_Y_Second;
			}
			String Coordinate_Y_Str=Coordinate_Y_First+Coordinate_Y_Second;
			Integer Coordinate_Y=Integer.parseInt(Coordinate_Y_Str, 16);
			
			String Coordinate_X_Str=Coordinate_X_First+Coordinate_X_Second;
			Integer Coordinate_X=Integer.parseInt(Coordinate_X_Str, 16);
			
			if (MFlag) {
				MinX=Coordinate_X;
				MinY=Coordinate_Y;
				maxX=Coordinate_X;
				maxY=Coordinate_Y;
				MFlag=false;
			}
			if (MinX>Coordinate_X){
				MinX=Coordinate_X;
			}
			if (MinY>Coordinate_Y){
				MinY=Coordinate_Y;
			}	
			if (maxY<Coordinate_Y) {
				maxY=Coordinate_Y;
			}
			
			String FirstIndex=Integer.toHexString(bs[index+5]&0xFF);
			String SecondIndex=Integer.toHexString(bs[index+4]&0xFF);
			if (SecondIndex.length()==1) {
				SecondIndex="0"+SecondIndex;
			}
			String Sum_Hex=FirstIndex+SecondIndex;
			Integer sum=Integer.parseInt(Sum_Hex, 16);
			
			if (Max_Col_Flag) {
				cols=sum;
				Max_Col_Flag=false;
			}
			if (sum>cols) {
				cols=sum;
			}
			Integer DieStartIndex=index+6;
			for (int i = 0; i < sum; i++) {
				String Value=Integer.toHexString(bs[DieStartIndex]&0xFF);	
				String key=String.format("%4s", Coordinate_X)+String.format("%4s", Coordinate_Y);
				if (Value.equals("0")) {
					skipAndMarkDieMap.put(key, String.format("%4s", "M")+String.format("%4s", "M")+String.format("%4s", "0"));
				}else {
					if (testDieFlag) {
						 testDieMinX=Coordinate_X;
						 testDieMinY=Coordinate_Y;
						 testDieMaxX=Coordinate_X;
						 testDieMaxY=Coordinate_Y;
						 testDieFlag=false;
					}
					if (testDieMinX>Coordinate_X){
						testDieMinX=Coordinate_X;
					}
					if (testDieMinY>Coordinate_Y){
						testDieMinY=Coordinate_Y;
					}	
					if (testDieMaxX<Coordinate_X) {
						testDieMaxX=Coordinate_X;
					}
					if (testDieMaxY<Coordinate_Y) {
						testDieMaxY=Coordinate_Y;
					}
					Integer NumberValue=Integer.parseInt(Value, 16);	
					if (Bin_summary_Map.containsKey(NumberValue)) {
						Bin_summary_Map.put(NumberValue, Bin_summary_Map.get(NumberValue)+1);
					}else {
						Bin_summary_Map.put(NumberValue, 1);
					}
					testDieMap.put(key, String.format("%4s", NumberValue)+String.format("%4s", NumberValue)+String.format("%4s", "0"));
				}
				DieStartIndex+=5;
				Coordinate_X++;
				if (maxX<Coordinate_X) {
					maxX=Coordinate_X;
				}
				
			}	
		}
		String Test_Start_Time=waferIdDaResultMap.get("testStartTime");
		String Test_End_Time=waferIdDaResultMap.get("testEndTime");
		String waferid=waferIdDaResultMap.get("waferid");
		String slot=waferIdDaResultMap.get("slot");
		
		
		Integer Year1=Integer.valueOf(Test_Start_Time.substring(0, 2));
		Integer Mouth1=Integer.valueOf(Test_Start_Time.substring(2,4));
		Integer Day1=Integer.valueOf(Test_Start_Time.substring(4,6));
		Integer Hour1=Integer.valueOf(Test_Start_Time.substring(6, 8));
		Integer Second1=Integer.valueOf(Test_Start_Time.substring(8,10));

		Integer Year2=Integer.valueOf(Test_End_Time.substring(0, 2));
		Integer Mouth2=Integer.valueOf(Test_End_Time.substring(2,4));
		Integer Day2=Integer.valueOf(Test_End_Time.substring(4,6));
		Integer Hour2=Integer.valueOf(Test_End_Time.substring(6, 8));
		Integer Second2=Integer.valueOf(Test_End_Time.substring(8,10));
		Integer Test_Time=-((Year1-Year2)*365*24*3600+(Mouth1-Mouth2)*30*24*3600+(Day1-Day2)*24*3600+(Hour1-Hour2)*3600+(Second1-Second2)*60);
		Test_Start_Time="20"+Test_Start_Time+ getRandomNumber.getRandomNumber(2);
		Test_End_Time="20"+Test_End_Time+getRandomNumber.getRandomNumber(2);


		properties.put("Wafer ID", waferid.trim());
		properties.put("Operator", op);
		properties.put("CP Process", cp);
		properties.put("Test Start Time", Test_Start_Time);
		properties.put("Test End Time", Test_End_Time);
		properties.put("Gross Die", "NA");
		properties.put("Pass Die", "NA");
		properties.put("Fail Die","NA");
		properties.put("Wafer Yield", "NA");
		properties.put("DataBase", "TEL");
		properties.put("TestTime", String.format("%.0f", ((float)Test_Time/60)));
		properties.put("Index X(mm)", "NA");
		properties.put("Index Y(mm)", "NA");
		properties.put("Map Cols", String.valueOf(cols));
		properties.put("Map Rows", String.valueOf(Rows));
		properties.put("Notch", "NA");
		properties.put("Retest Rate", "0");
		properties.put("WF_Size", "NA");
		properties.put("Slot", String.valueOf(slot));
		properties.put("MinX", String.valueOf(MinX));
		properties.put("MinY", String.valueOf(MinY));
		properties.put("MaxX", String.valueOf(maxX));
		properties.put("MaxY", String.valueOf(maxY));
		properties.put("TestDieMinX", String.valueOf(testDieMinX));
		properties.put("TestDieMinY", String.valueOf(testDieMinY));
		properties.put("TestDieMaxX", String.valueOf(testDieMaxX));
		properties.put("TestDieMaxY", String.valueOf(testDieMaxY));
		siteBinSum.put(0,Bin_summary_Map);

		bean.setDataProperties(properties);
		bean.setSiteBinSum(siteBinSum);
		bean.setTestDieMap(testDieMap);
		bean.setSkipAndMarkDieMap(skipAndMarkDieMap);
	}
}	
