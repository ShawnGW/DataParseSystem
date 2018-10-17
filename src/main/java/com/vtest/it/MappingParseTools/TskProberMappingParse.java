package com.vtest.it.MappingParseTools;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.GetRandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 
 * @author shawn.sun 
 * @category IT
 * @since 2017.05.26
 * @serial 1.0
 */
@Service
public class TskProberMappingParse {
	public static void main(String[] args) throws  Exception{
		TskProberMappingParse tskProberMappingParse=new TskProberMappingParse();
		RawdataInitBean rawdataInitBean=new RawdataInitBean();
		tskProberMappingParse.Get(new File("E:\\C\\2\\001.WS983NG-1"),0,rawdataInitBean);
		HashMap<String,String> testDieMap=rawdataInitBean.getTestDieMap();

		Set<String> set=testDieMap.keySet();
		PrintWriter p=new PrintWriter(new FileWriter(new File("E:\\C\\2\\22.raw")));
		for (String key:set) {
			p.print(key+testDieMap.get(key)+"\r\n");
		}
		p.flush();
		p.close();
	}
	private GetRandomNumber getRandomNumber;
	@Autowired
	public void setGetRandomNumber(GetRandomNumber getRandomNumber) {
		this.getRandomNumber = getRandomNumber;
	}
	public  void Get(File file, int gpibBin, RawdataInitBean bean) throws IOException, NoSuchFieldException
	{
		LinkedHashMap<String,String> properties=bean.getDataProperties();
		HashMap<Integer,HashMap<Integer,Integer>> primarySiteBinSum=bean.getSiteBinSum();
		HashMap<String,String> testDieMap=bean.getTestDieMap();
		HashMap<String,String> skipAndMarkDieMap=bean.getSkipAndMarkDieMap();
		HashMap<Integer,Integer> Bin_summary_Map =new HashMap<>();

		int slot=0;
		try {
			slot=Integer.valueOf(file.getName().substring(0, 3));
		} catch (Exception e) {
			// TODO: handle exception
		}
		FileInputStream fileInputStream=new FileInputStream(file);
		byte[] bs=new byte[2000000];
		fileInputStream.read(bs);
		fileInputStream.close();
		String OP=null;
		byte[] OP_BT=new byte[20];
		String Device=null;
		byte[] Device_BT=new byte[16];
		int Wafer_Size=0;
		byte[] Wafer_Size_BT=new byte[2];
		double Index_X_Size=0;
		double Index_Y_Size=0;
		byte[] Index_X_Size_BT=new byte[4];
		byte[] Index_Y_Size_BT=new byte[4];
		int flat=0;
		byte[] Flat_BT=new byte[2];
		int Col=0;
		int Row=0;
		byte[] Col_BT=new byte[2];
		byte[] Row_BT=new byte[2];
		String Wafer_ID=null;
		byte[] Wafer_ID_BT=new byte[22];
		String Test_Start_Time=null;
		byte[] Test_Start_Time_BT=new byte[12];
		String Test_End_Time=null;
		byte[] Test_End_Time_BT=new byte[12];
		int Total_Fail_Die=0;
		byte[] Total_Fail_Die_BT=new byte[2];

		for (int i = 0; i < 2; i++) {
			Col_BT[i]=bs[i+52];
			Row_BT[i]=bs[i+54];
		}
		Col=Integer.parseInt(Integer.toHexString(Col_BT[0]&0xFF),16)*256+Integer.parseInt(Integer.toHexString(Col_BT[1]&0xFF), 16);
		Row=Integer.parseInt(Integer.toHexString(Row_BT[0]&0xFF),16)*256+Integer.parseInt(Integer.toHexString(Row_BT[1]&0xFF), 16);

		for (int i = 0; i < 216; i++) {
			if (i<20) {
				OP_BT[i]=bs[i];
			}
			if (i>19&&i<36) {
				Device_BT[i-20]=bs[i];
			}
			if (i>35&&i<38) {
				Wafer_Size_BT[i-36]=bs[i];
			}
			if (i>39&&i<44) {
				Index_X_Size_BT[i-40]=bs[i];
			}
			if (i>43&&i<48) {
				Index_Y_Size_BT[i-44]=bs[i];
			}
			if (i>47&&i<50) {
				Flat_BT[i-48]=bs[i];
			}
			if (i>59&&i<81) {
				Wafer_ID_BT[i-60]=bs[i];
			}
			if (i>147&&i<160) {
				Test_Start_Time_BT[i-148]=bs[i];
			}
			if (i>159&&i<172) {
				Test_End_Time_BT[i-160]=bs[i];
			}
			if (i>213&&i<216) {
				Total_Fail_Die_BT[i-214]=bs[i];
			}
		}

		OP=new String(OP_BT);
		if (!OP.contains("V")) {
			OP="V088";
		}
		Device=new String(Device_BT);
		int Size=Integer.parseInt(Integer.toHexString(Wafer_Size_BT[0]&0xFF),16)*256+Integer.parseInt(Integer.toHexString(Wafer_Size_BT[1]&0xFF), 16);
		if (Size>120)
			Wafer_Size=Size/25;
		else
			Wafer_Size=Size/10;

		for (int j = 0; j < Index_X_Size_BT.length; j++) {
			Index_X_Size+=Integer.parseInt(Integer.toHexString(Index_X_Size_BT[j]&0xFF),16)*(Math.pow(256, 3-j));
			Index_Y_Size+=Integer.parseInt(Integer.toHexString(Index_Y_Size_BT[j]&0xFF),16)*(Math.pow(256, 3-j));
		}
		Index_X_Size=Index_X_Size/100000;
		Index_Y_Size=Index_Y_Size/100000;

		flat=Integer.parseInt(Integer.toHexString(Flat_BT[0]&0xFF),16)*256+Integer.parseInt(Integer.toHexString(Flat_BT[1]&0xFF), 16);

		Wafer_ID=new String(Wafer_ID_BT);
		Test_Start_Time=new String(Test_Start_Time_BT);
		Test_End_Time=new String(Test_End_Time_BT);

		Total_Fail_Die=Integer.parseInt(Integer.toHexString(Total_Fail_Die_BT[0]&0xFF),16)*256+Integer.parseInt(Integer.toHexString(Total_Fail_Die_BT[1]&0xFF), 16);
		int i=235;
		int PassDie=0;
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
		boolean flag=false;
		boolean flag2=false;
		while(i<Col*Row*6+235)
		{
			int dieTestResult=(Integer.parseInt(Integer.toHexString(bs[i+1]&0xFF),16)&192)/64;
			int dieExcept=((Integer.parseInt(Integer.toHexString(bs[i+3]&0xFF),16))&2)/2;
			int measureFlag=(Integer.parseInt(Integer.toHexString(bs[i+6]&0xFF),16))&63;
			int CellY=Integer.parseInt(Integer.toHexString(bs[i+4]&0xFF),16)+(Integer.parseInt(Integer.toHexString(bs[i+3]&0xFF),16)&1)*256;
			int CellX=Integer.parseInt(Integer.toHexString(bs[i+2]&0xFF),16)+(Integer.parseInt(Integer.toHexString(bs[i+1]&0xFF),16)&1)*256;
			int CodeOfY=(Integer.parseInt(Integer.toHexString(bs[i+3]&0xFF),16)&4)/4;
			int CodeOfX=(Integer.parseInt(Integer.toHexString(bs[i+3]&0xFF),16)&8)/8;
			int prefcellY;
			int preCodeOfY;
			int prefcellX;
			int preCodeOfX;
			int Die_property=(Integer.parseInt(Integer.toHexString(bs[i+3]&0xFF),16)&192)/64;
			if (i==235) {
				prefcellY=CellY;
			}else {
				preCodeOfY=(Integer.parseInt(Integer.toHexString(bs[i-3]&0xFF),16)&4)/4;
				prefcellY=Integer.parseInt(Integer.toHexString(bs[i-2]&0xFF),16)+(Integer.parseInt(Integer.toHexString(bs[i-3]&0xFF),16)&1)*256;
				if (preCodeOfY==1)
					prefcellY=(-prefcellY);
			}

			if (i==235) {
				prefcellX=CellX;
			}else {
				preCodeOfX=(Integer.parseInt(Integer.toHexString(bs[i-3]&0xFF),16)&8)/8;
				prefcellX=Integer.parseInt(Integer.toHexString(bs[i-4]&0xFF),16)+(Integer.parseInt(Integer.toHexString(bs[i-5]&0xFF),16)&1)*256;
				if (preCodeOfX==1)
					prefcellX=(-prefcellX);
			}

			if (CodeOfX==1)
				CellX=(-CellX);
			if (CodeOfY==1)
				CellY=(-CellY);

			if (prefcellY==511&&CellY<511) {
				flag=true;
			}
			if (prefcellX==511&&CellX<511) {
				flag2=true;
			}
			if(prefcellY!=CellY)
			{
				flag2=false;
			}
			if (flag) {
				CellY+=512;
			}
			if (flag2) {
				CellX+=512;
			}

			/**  get m x,y limit **/
			if (MFlag) {
				MinX=CellX;
				MinY=CellY;
				maxX=CellX;
				maxY=CellY;
				MFlag=false;
			}
			if (MinX>CellX){
				MinX=CellX;
			}
			if (MinY>CellY){
				MinY=CellY;
			}
			if (maxX<CellX) {
				maxX=CellX;
			}
			if (maxY<CellY) {
				maxY=CellY;
			}
			/**  get m x,y limit **/
			if (dieExcept!=1){
				String key=String.format("%4s", CellX)+String.format("%4s", CellY);
				if (!testDieMap.containsKey(key)&&!skipAndMarkDieMap.containsKey(key)) {
					if (Die_property==0)
					{
						skipAndMarkDieMap.put(key, String.format("%4s", "S")+String.format("%4s", "S")+String.format("%4s", "0"));
					}
					else if (Die_property==2)
					{
						skipAndMarkDieMap.put(key, String.format("%4s", "M")+String.format("%4s", "M")+String.format("%4s", "0"));
					}
					else if (Die_property==1)
					{
						/** **/
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
						/** **/
						measureFlag=measureFlag+gpibBin;
						if (!Bin_summary_Map.containsKey(measureFlag)) {
							Bin_summary_Map.put(measureFlag, 1);
						}else
						{
							Bin_summary_Map.put(measureFlag, Bin_summary_Map.get(measureFlag)+1);
						}
						testDieMap.put(key, String.format("%4s", measureFlag)+String.format("%4s", measureFlag)+String.format("%4s", "0"));
						if (dieTestResult==1) {
							PassDie++;
						}
					}
				}
			}
			i+=6;
			if (i >Col*Row*6+235) {
				break;
			}
		}
		Device=Device.trim();
		Test_Start_Time=Test_Start_Time.trim();
		Test_End_Time=Test_End_Time.trim();


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
		Test_Start_Time="20"+Test_Start_Time+ /*getRandomNumber.getRandomNumber(2)*/"11";
		Test_End_Time="20"+Test_End_Time+/*getRandomNumber.getRandomNumber(2)*/"12";

		String cp=Device.substring(3,4);

		primarySiteBinSum.put(0,Bin_summary_Map);

		properties.put("Wafer ID", Wafer_ID.trim());
		properties.put("Operator", OP.trim());
		properties.put("CP Process", "CP"+cp);
		properties.put("Test Start Time", Test_Start_Time);
		properties.put("Test End Time", Test_End_Time);
		properties.put("Gross Die", String.valueOf((PassDie+Total_Fail_Die)));
		properties.put("Pass Die", String.valueOf(PassDie));
		properties.put("Fail Die", String.valueOf(Total_Fail_Die));
		properties.put("Wafer Yield", String.format("%4.2f", (double)PassDie*100/(PassDie+Total_Fail_Die))+"%");
		properties.put("DataBase", "TSK");
		properties.put("TestTime", String.format("%.0f", ((float)Test_Time/60)));
		properties.put("Index X(mm)", String.valueOf(Index_X_Size));
		properties.put("Index Y(mm)", String.valueOf(Index_Y_Size));
		properties.put("Map Cols", String.valueOf(Col));
		properties.put("Map Rows", String.valueOf(Row));
		properties.put("Notch", flat+"-Degree");
		properties.put("Retest Rate", "0");
		properties.put("WF_Size", String.valueOf(Wafer_Size));
		properties.put("Slot", String.valueOf(slot));
		properties.put("MinX", String.valueOf(MinX));
		properties.put("MinY", String.valueOf(MinY));
		properties.put("MaxX", String.valueOf(maxX));
		properties.put("MaxY", String.valueOf(maxY));
		properties.put("TestDieMinX", String.valueOf(testDieMinX));
		properties.put("TestDieMinY", String.valueOf(testDieMinY));
		properties.put("TestDieMaxX", String.valueOf(testDieMaxX));
		properties.put("TestDieMaxY", String.valueOf(testDieMaxY));
	}
}
