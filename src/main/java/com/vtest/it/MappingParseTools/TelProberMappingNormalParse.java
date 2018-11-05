package com.vtest.it.MappingParseTools;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.GetRandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author shawn_Sun;
 * @version 1.0;
 * @category IT;
 * @since 2017.7.2;
 */

@Service
public class TelProberMappingNormalParse {

	private GetRandomNumber getRandomNumber;
	@Autowired
	public void setGetRandomNumber(GetRandomNumber getRandomNumber) {
		this.getRandomNumber = getRandomNumber;
	}

	private  final String[] Negative_Coordinate_X={"ffff:-1","fffe:-2","fffd:-3","fffc:-4","fffb:-5","fffa:-6","fff9:-7","fff8:-8","fff7:-9","fff6:-10"};
	public void Get(File file,RawdataInitBean bean,String op,String cp)throws IOException, ParserConfigurationException, SAXException
	{
		LinkedHashMap<String,String> properties=new LinkedHashMap<>();
		HashMap<Integer,HashMap<Integer,Integer>> siteBinSum=new HashMap<>();
		HashMap<String,String> testDieMap=new HashMap<>();
		HashMap<String,String> skipAndMarkDieMap=new HashMap<>();
		HashMap<Integer,Integer> Bin_summary_Map =new HashMap<>();
		HashMap<String, String> Negative_Values=new HashMap<>();
		
		for (int i = 0; i < Negative_Coordinate_X.length; i++) {
			Negative_Values.put(Negative_Coordinate_X[i].split(":")[0], Negative_Coordinate_X[i].split(":")[1]);
		}
		Set<String> Negative_describe_set=Negative_Values.keySet();

		FileInputStream fios=new FileInputStream(file);
		byte[] bs = new byte[1000000];
		
		ArrayList<Integer> MapRow_Start_indexs=new ArrayList<Integer>();
		int Map_ROW_Start_index=70;
		
		String waferid=null;
		byte[] wafer_id_BT=new byte[25];
		String slot=null;
		byte[] Slot_number_BT=new byte[2];
		String Test_Start_Time=null;
		byte[] Test_Start_Time_BT=new byte[12];
		String Test_End_Time=null;
		byte[] Test_End_Time_BT=new byte[12];
		int Row_Sum=0;
		byte Row_Sum_BT = 0;

		fios.read(bs);
		fios.close();
			
		int Total=0;
		for (int i = 0; i < bs.length; i++) {
			if(i<25)
				wafer_id_BT[i]=bs[i];
			if (i>27&&i<30)
				Slot_number_BT[i-28]=bs[i];
			if (i>36&&i<49)
				Test_Start_Time_BT[i-37]=bs[i];
			if (i>48&&i<61)
				Test_End_Time_BT[i-49]=bs[i];
			if (i==61)
				Row_Sum_BT=bs[i];
				Total++;
			if (Integer.toHexString(bs[i]&0xFF).equals("80")) {
				if (bs[i+1]==0&&bs[i+2]==0) {
					break;
				}
			}	
		}
		while(Map_ROW_Start_index<Total)
		{
			MapRow_Start_indexs.add(Map_ROW_Start_index);
			Map_ROW_Start_index+=Integer.parseInt(Integer.toHexString(bs[Map_ROW_Start_index]&0xFF), 16)*2+5;			
		}
		Integer[] MapRow_Start_index_List=(Integer[]) MapRow_Start_indexs.toArray(new Integer[MapRow_Start_indexs.size()]);
		
		int passdies=0;
		int faildies=0;
		
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
		
		for (int i = 0; i < MapRow_Start_index_List.length; i++) {		
			int index=MapRow_Start_index_List[i];
			int dieSum=Integer.parseInt(Integer.toHexString(bs[index]&0xFF), 16);
			
			String First_X=Integer.toHexString(bs[index-3]&0xFF);
			String Second_X=Integer.toHexString(bs[index-4]&0xFF);
			Integer Coordinate_X;
			if (Negative_describe_set.contains(First_X+Second_X)) {
				Coordinate_X=Integer.parseInt(Negative_Values.get(First_X+Second_X));
			}else
			{
				Coordinate_X=Integer.parseInt(First_X+Second_X, 16);
			}
			
			
			String First_Y=Integer.toHexString(bs[index-1]&0xFF);
			String Second_Y=Integer.toHexString(bs[index-2]&0xFF);
			Integer Coordinate_Y;
			if (Negative_describe_set.contains(First_Y+Second_Y)) {
				Coordinate_Y=Integer.parseInt(Negative_Values.get(First_Y+Second_Y));
			}else
			{
				Coordinate_Y=Integer.parseInt(First_Y+Second_Y, 16);
			}
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
		
			int next_j=1;
			for(int j=1;j<=dieSum;j++)
			{
				if (maxX<Coordinate_X) {
					maxX=Coordinate_X;
				}
				String key=String.format("%4s", Coordinate_X)+String.format("%4s", Coordinate_Y);
				
				String dieType=Integer.toHexString(bs[index+next_j+1]&0xFF);		
				if(dieType.equals("e0")||dieType.equals("c0"))
					{
						// skip die
						skipAndMarkDieMap.put(key, String.format("%4s", "S")+String.format("%4s", "S")+String.format("%4s", "0"));
					}	
				else {
					String dieValue=String.valueOf(Integer.parseInt(Integer.toHexString(bs[index+next_j]&0xFF), 16));
					if (dieValue.equals("0")) {
						skipAndMarkDieMap.put(key, String.format("%4s", "M")+String.format("%4s", "M")+String.format("%4s", "0"));
					}else {
						if (!Bin_summary_Map.containsKey(Integer.valueOf(dieValue))) {
							Bin_summary_Map.put(Integer.valueOf(dieValue), 1);
						}else
						{
							Bin_summary_Map.put(Integer.valueOf(dieValue), Bin_summary_Map.get(Integer.valueOf(dieValue))+1);
						}
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
						if(dieType.equals("40")||dieType.equals("00")){
							   // pass die
							passdies++;
							testDieMap.put(key, String.format("%4s", dieValue)+String.format("%4s", dieValue)+String.format("%4s", "0"));
						}else {
							//others are normal test die
							faildies++;
							testDieMap.put(key, String.format("%4s", dieValue)+String.format("%4s", dieValue)+String.format("%4s", "0"));
					    }
					}					
				}
				Coordinate_X+=1;				
				next_j+=2;
			}
		}
		slot=new String(Slot_number_BT, "ASCII");
		waferid=new String(wafer_id_BT, "ASCII");	
		
        StringBuffer testStartTimeTemp = new StringBuffer(Test_Start_Time_BT.length * 2);  
        for (int i = 0; i < Test_Start_Time_BT.length; i++) {  
        	testStartTimeTemp.append((byte) ((Test_Start_Time_BT[i] & 0xf0) >>> 4));  
        	testStartTimeTemp.append((byte) (Test_Start_Time_BT[i] & 0x0f));  
        }   
        String testStartTime=testStartTimeTemp.toString();
        
        String year=testStartTime.substring(1,2)+testStartTime.substring(3,4);
        String Mouth=testStartTime.substring(5,6)+testStartTime.substring(7,8);
        String Day=testStartTime.substring(9,10)+testStartTime.substring(11,12);
        String Hour=testStartTime.substring(13,14)+testStartTime.substring(15,16);
        String Minute=testStartTime.substring(17,18)+testStartTime.substring(19,20);
        Test_Start_Time=year+Mouth+Day+Hour+Minute;
        
        StringBuffer testEndTimeTemp = new StringBuffer(Test_End_Time_BT.length * 2);  
        for (int i = 0; i < Test_End_Time_BT.length; i++) {  
        	testEndTimeTemp.append((byte) ((Test_End_Time_BT[i] & 0xf0) >>> 4));  
        	testEndTimeTemp.append((byte) (Test_End_Time_BT[i] & 0x0f));  
        }   
        String testEndTime=testEndTimeTemp.toString();
        String eyear=testEndTime.substring(1,2)+testEndTime.substring(3,4);
        String eMouth=testEndTime.substring(5,6)+testEndTime.substring(7,8);
        String eDay=testEndTime.substring(9,10)+testEndTime.substring(11,12);
        String eHour=testEndTime.substring(13,14)+testEndTime.substring(15,16);
        String eMinute=testEndTime.substring(17,18)+testEndTime.substring(19,20);
        Test_End_Time=eyear+eMouth+eDay+eHour+eMinute;
        
        Row_Sum=Integer.parseInt(Integer.toHexString(Row_Sum_BT&0xFF), 16); 
               
		
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
		Test_Start_Time="20"+Test_Start_Time+getRandomNumber.getRandomNumber(2);
		Test_End_Time="20"+Test_End_Time+getRandomNumber.getRandomNumber(2);

		properties.put("Wafer ID", waferid.trim());
		properties.put("Operator", op);
		properties.put("CP Process", cp);
		properties.put("Test Start Time", Test_Start_Time);
		properties.put("Test End Time", Test_End_Time);
		properties.put("Gross Die", String.valueOf(passdies+faildies));
		properties.put("Pass Die", String.valueOf(passdies));
		properties.put("Fail Die", String.valueOf(faildies));
		properties.put("Wafer Yield", String.format("%4.2f", (double)passdies*100/(passdies+faildies))+"%");
		properties.put("DataBase", "TEL");
		properties.put("TestTime", String.format("%.0f", ((float)Test_Time/60)));
		properties.put("Index X(mm)", "NA");
		properties.put("Index Y(mm)", "NA");
		properties.put("Map Cols", String.valueOf(maxX-MinX+1));
		properties.put("Map Rows", String.valueOf(Row_Sum));
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
