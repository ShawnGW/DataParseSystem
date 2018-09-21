package com.vtest.it.rawdataModify;

import com.vtest.it.mestools.BinInforWriteToMes;
import com.vtest.it.pojo.RawdataModifyBean;
import com.vtest.it.rawdataParse.parseRawdata;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class RawDataModify  extends  RawdataModifyParent{

	private BinInforWriteToMes binInforWriteToMes;
	private ModifyFileCheck modifyFileCheck;

	@Autowired
	public void setModifyFileCheck(ModifyFileCheck modifyFileCheck) {
		this.modifyFileCheck = modifyFileCheck;
	}

	@Autowired
	public void setBinInforWriteToMes(BinInforWriteToMes binInforWriteToMes) {
		this.binInforWriteToMes = binInforWriteToMes;
	}

	public ArrayList<RawdataModifyBean> Transfer(File[] files)
	{
		super.initMap();
		ArrayList<RawdataModifyBean> dealList=new ArrayList<>();
		for (File csv : files) {
			String fileName=csv.getName();
			RawdataModifyBean rawdataModifyBean=new RawdataModifyBean();
			rawdataModifyBean.setFileName(fileName);
			try {
				if (!modifyFileCheck.check(csv))
				{
					throw new FileNotFoundException("there are error formart in your file,please check it again!");
				}
				String[] Tokens=fileName.split("_");
				int length=Tokens.length;
				String CustomerCode=Tokens[0].trim();
				StringBuffer Devicebuffer=new StringBuffer(Tokens[1]);
				for (int j = 2; j < Tokens.length-4; j++) {
					Devicebuffer.append("_"+Tokens[j]);
				}
				String Device=Devicebuffer.toString();
				String Lot=Tokens[length-4].trim();
				String Waferid=Tokens[length-3].trim();
				String CPProcess=Tokens[length-2].substring(0, 3).trim();
				File BackupDirectory=new File(DataBaseBackup.getPath()+"/"+CustomerCode+"/"+Device+"/"+Lot+"/"+CPProcess+"/"+Waferid+"/");
				if (!BackupDirectory.exists()) {
					BackupDirectory.mkdirs();
				}
				File BackupFile=new File(DataBaseBackup.getPath()+"/"+CustomerCode+"/"+Device+"/"+Lot+"/"+CPProcess+"/"+Waferid+"/"+fileName);
				if (BackupFile.exists()) {
					BackupFile.delete();
				}
				FileUtils.copyFile(csv, BackupFile);
				csv.delete();

				HashMap<String, ArrayList<String>> shieldMap=RawDataFromStdf.get();
				boolean specialCustomer=false;
				if (shieldMap.containsKey(CustomerCode)) {
					if (shieldMap.get(CustomerCode).contains("ALL")) {
						specialCustomer=true;
					}else if(shieldMap.get(CustomerCode).contains(Device)){
						specialCustomer=true;
					}
				}
				File ModifyRawdata=new File(RawdataProber.getPath()+"/"+CustomerCode+"/"+Device+"/"+Lot+"/"+CPProcess+"/"+Waferid+".raw");
				if (specialCustomer) {
					ModifyRawdata=new File(RawdataTester.getPath()+"/"+CustomerCode+"/"+Device+"/"+Lot+"/"+CPProcess+"/"+Waferid+".raw");
				}
				parseRawdata parseRawdata=null;
				try {
					parseRawdata=new parseRawdata(ModifyRawdata);
				}catch (Exception e)
				{
					throw new FileNotFoundException("no this serial rawdata ,please check your information");
				}
				LinkedHashMap<String, String> properties=parseRawdata.getProperties();


				String[] passBins=properties.get("Pass Bins").split(",");
				ArrayList<Integer> Pass_array=new ArrayList<>();
				for (String bin : passBins) {
					Pass_array.add(Integer.valueOf(bin));
				}
				ArrayList<Integer> osBins=new ArrayList<>();
				String[] osBin_array=properties.get("OS Bins").split(",");
				for (String os : osBin_array) {
					osBins.add(Integer.valueOf(os));
				}


				FileReader reader=new FileReader(BackupFile);
				BufferedReader bReader=new BufferedReader(reader);
				String content;
				boolean flag=false;
				HashMap<String, String> Map_infor_Modify=new HashMap<>();
				HashMap<String, String> map_marked=new HashMap<>();
				while((content=bReader.readLine())!=null)
				{
					if (content.toUpperCase().contains("X,Y")) {
						flag=true;
						continue;
					}
					if (flag) {
						String[] CoordinateInfor=content.split(",");
						String Coordinate_X=CoordinateInfor[0];
						String Coordinate_Y=CoordinateInfor[1];
						Integer BinValue=osBins.get(0);
						try {
							if (CoordinateInfor.length>2) {
								BinValue=Integer.valueOf(CoordinateInfor[2]);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						Integer softBinValue=BinValue;
						try {
							softBinValue=Integer.valueOf(CoordinateInfor[3]);
						} catch (Exception e) {
							// TODO: handle exception
						}
						Integer siteNumber=0;
						try {
							siteNumber=Integer.valueOf(CoordinateInfor[4]);
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (CoordinateInfor.length>2&&CoordinateInfor[2].toUpperCase().equals("M")) {
							map_marked.put(String.format("%4s", Coordinate_X)+String.format("%4s", Coordinate_Y), String.format("%4s", "M")+String.format("%4s", "M")+String.format("%4s", 0));
							continue;
						}
						Map_infor_Modify.put(String.format("%4s", Coordinate_X)+String.format("%4s", Coordinate_Y), String.format("%4s", BinValue)+String.format("%4s", softBinValue)+String.format("%4s", siteNumber));
					}
				}
				bReader.close();

				FileReader ModifyReafer=new FileReader(ModifyRawdata);
				BufferedReader modifyBufferreader=new BufferedReader(ModifyReafer);
				String ModContent;
				boolean Moflag=false;
				boolean markdieFlag=false;
				HashMap<String, String> Map_infor_Rawdata=new HashMap<>();
				HashMap<String, String> Map_infor_Rawdata_MarkDies=new HashMap<>();
				while((ModContent=modifyBufferreader.readLine())!=null)
				{
					if (ModContent.contains("RawData")) {
						Moflag=true;
						continue;
					}
					if (ModContent.contains("DataEnd")) {
						Moflag=false;
						continue;
					}
					if (Moflag) {
						Map_infor_Rawdata.put(ModContent.substring(0, 8), ModContent.substring(8, 20));
					}
					if (ModContent.equals("SkipAndMarkStart")) {
						markdieFlag=true;
						continue;
					}
					if (ModContent.equals("SkipAndMarkEnd")) {
						markdieFlag=false;
						continue;
					}
					if (markdieFlag) {
						Map_infor_Rawdata_MarkDies.put(ModContent.substring(0, 8), ModContent.substring(8, 20));
					}
				}
				modifyBufferreader.close();

				Set<String> testDieModifySet=Map_infor_Modify.keySet();
				for (String testDie : testDieModifySet) {
					Map_infor_Rawdata.put(testDie, Map_infor_Modify.get(testDie));
					if (Map_infor_Rawdata_MarkDies.containsKey(testDie)) {
						Map_infor_Rawdata_MarkDies.remove(testDie);
					}
				}
				Set<String> markDieModify=map_marked.keySet();
				for (String markDie : markDieModify) {
					Map_infor_Rawdata_MarkDies.put(markDie, map_marked.get(markDie));
					if (Map_infor_Rawdata.containsKey(markDie)) {
						Map_infor_Rawdata.remove(markDie);
					}
				}


				TreeMap<Integer, Integer> Bin_Summary=new TreeMap<>();
				Integer Pass_Die=0;
				Integer Fail_Die=0;

				Set<String> testDiesSet=Map_infor_Rawdata.keySet();
				for (String testDie : testDiesSet) {
					Integer SoftBin=Integer.valueOf(Map_infor_Rawdata.get(testDie).substring(4,8).trim());
					if (Bin_Summary.containsKey(SoftBin)) {
						Bin_Summary.put(SoftBin, Bin_Summary.get(SoftBin)+1);
					}else {
						Bin_Summary.put(SoftBin, 1);
					}
					if (Pass_array.contains(SoftBin)) {
						Pass_Die++;
					}else {
						Fail_Die++;
					}
				}
				properties.put("Pass Die", String.valueOf(Pass_Die));
				properties.put("Fail Die", String.valueOf(Fail_Die));
				properties.put("Gross Die", String.valueOf(Fail_Die+Pass_Die));
				properties.put("Wafer Yield", Double.valueOf((String.format("%.2f", (double)Pass_Die*100/(Pass_Die+Fail_Die))))+"%");

				File rawDataNew=new File("/server212/RawData/ProberRawdata/"+CustomerCode+"/"+Device+"/"+Lot+"/"+CPProcess+"/"+Waferid+".raw");
				if (!ModifyRawdata.getPath().contains("ProberRawdatabackup")) {
					rawDataNew=new File("/server212/RawData/TesterRawdata/"+CustomerCode+"/"+Device+"/"+Lot+"/"+CPProcess+"/"+Waferid+".raw");
				}
				if (!rawDataNew.getParentFile().exists()) {
					rawDataNew.getParentFile().mkdirs();
				}
				FileWriter out=new FileWriter(rawDataNew);
				PrintWriter printWriter=new PrintWriter(out);

				Set<String> propertiesSet=properties.keySet();
				for (String property : propertiesSet) {
					if (!property.equals("TestDieRow")&&!property.equals("TestDieCol")) {
						printWriter.print(modify(property)+":"+properties.get(property)+"\r\n");
					}
				}
				printWriter.print("RawData\r\n");
				Set<String> testDieSetP=Map_infor_Rawdata.keySet();
				for (String testDie : testDieSetP) {
					printWriter.print(testDie+Map_infor_Rawdata.get(testDie)+"\r\n");
				}
				printWriter.print("DataEnd\r\n");
				printWriter.print("SkipAndMarkStart\r\n");
				Set<String> marDieP=Map_infor_Rawdata_MarkDies.keySet();
				for (String markDie : marDieP) {
					printWriter.print(markDie+Map_infor_Rawdata_MarkDies.get(markDie)+"\r\n");
				}
				printWriter.print("SkipAndMarkEnd\r\n");
				printWriter.print("Bin Summary\r\n");
				TreeSet<Integer> Bin_F=new TreeSet<>(Bin_Summary.keySet());
				StringBuilder sBuilder=new StringBuilder();
				for (Integer key : Bin_F) {
					Integer Bin_sum=Bin_Summary.get(key);
					printWriter.print("Bin"+key+":");
					printWriter.print(Bin_sum+"\r\n");
					sBuilder.append("|Bin"+key+":"+Bin_sum);
				}
				binInforWriteToMes.Write(Lot, Waferid, sBuilder.toString(), CPProcess);
				printWriter.close();
				rawdataModifyBean.setStatus("Success");
				rawdataModifyBean.setReason("OK");
				dealList.add(rawdataModifyBean);
			} catch (Exception e) {
				// TODO: handle exception
				rawdataModifyBean.setStatus("Failed");
				rawdataModifyBean.setReason(e.getMessage());
				dealList.add(rawdataModifyBean);
			}
		}
		return dealList;
	}

}
