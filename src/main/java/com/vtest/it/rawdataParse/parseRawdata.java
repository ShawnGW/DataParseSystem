package com.vtest.it.rawdataParse;

import com.vtest.it.parseRawdataInterface.GetBinSummaryFromRawdata;
import com.vtest.it.parseRawdataInterface.GetPropertiesFromRawdata;
import com.vtest.it.parseRawdataInterface.GetTestDiesFromRawdata;
import com.vtest.it.parseRawdataInterface.getMarkAndSkipDiesFromRawdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

public class parseRawdata implements GetPropertiesFromRawdata,GetBinSummaryFromRawdata,getMarkAndSkipDiesFromRawdata,GetTestDiesFromRawdata{

	private File rawdataSource;
	private LinkedHashMap<String, String> properties=new LinkedHashMap<>();
	private HashMap<String, String> hardBinTestDieMap=new HashMap<>();
	private HashMap<String, String> softBinTestDieMap=new HashMap<>();
	private String[][] hardBinTestDiesDimensionalArray=new String[800][800];
	private String[][] softBinTestDiesDimensionalArray=new String[800][800];
	private String[][] markAndSkipDiesDimensionalArray=new String[800][800];
	private HashMap<String, String> markAndSkipMap=new HashMap<>();
	private TreeMap<Integer, Integer> binSummary=new TreeMap<>();
	
	public parseRawdata(File rawdataSource) {
		// TODO Auto-generated constructor stub
		this.rawdataSource=rawdataSource;
		parse();
		ModifyProperties();
	}
	private void parse()
	{
		boolean testDieStartFlag=false;
		boolean markAndSkipFlag=false;
		boolean startFlag=true;
		boolean binSummaryFlag=false;
		try {
			FileReader reader=new FileReader(rawdataSource);
			BufferedReader bReader=new BufferedReader(reader);
			String content;
			while((content=bReader.readLine())!=null)
			{
				if (content.contains("Bin Summary")) {
					binSummaryFlag=true;
					continue;
				}
				if (content.contains("SkipAndMarkStart")) {
					markAndSkipFlag=true;
					continue;
				}
				if (content.contains("SkipAndMarkEnd")) {
					markAndSkipFlag=false;
					continue;
				}
				if (content.contains("DataEnd")) {
					testDieStartFlag=false;
					continue;
				}
				if (content.contains("RawData")) {
					startFlag=false;
					testDieStartFlag=true;
					continue;
				}
				if (startFlag) {
					Integer indexOfPoint=content.indexOf(":");
					String key=content.substring(0, indexOfPoint);
					String value=content.substring(indexOfPoint+1);
					if (key.contains("MinX")) {
						key=key.replace("MinX", "left");
					}
					if (key.contains("MinY")) {
						key=key.replace("MinY", "up");
					}
					if (key.contains("MaxX")) {
						key=key.replace("MaxX", "right");
					}
					if (key.contains("MaxY")) {
						key=key.replace("MaxY", "down");
					}
					properties.put(key, value);
				}
				if (testDieStartFlag) {
					Integer rowCoordinate=	Integer.valueOf(content.substring(4, 8).trim());	
					Integer colCoordinate=	Integer.valueOf(content.substring(0, 4).trim());
					String hardbin=content.substring(8,12).trim();
					String softbin=content.substring(12,16).trim();
					hardBinTestDieMap.put(rowCoordinate+":"+colCoordinate, hardbin);
					softBinTestDieMap.put(rowCoordinate+":"+colCoordinate, softbin);
				}
				if (markAndSkipFlag) {
					Integer rowCoordinate=	Integer.valueOf(content.substring(4, 8).trim());	
					Integer colCoordinate=	Integer.valueOf(content.substring(0, 4).trim());
					String symbol=content.substring(8,12).trim();
					markAndSkipMap.put(rowCoordinate+":"+colCoordinate, symbol);
					
				}
				if (binSummaryFlag) {
					if (content.contains("Bin")) {
						String[] infor=content.split(":");
						Integer bin=Integer.valueOf(infor[0].substring(3).trim());
						Integer value=Integer.valueOf(infor[1]);
						binSummary.put(bin, value);
					}
				}
			}
			bReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void ModifyProperties()
	{
		Integer TestDieRow=Integer.valueOf(properties.get("TestDiedown"))-Integer.valueOf(properties.get("TestDieup"))+1;
		Integer TestDieCol=Integer.valueOf(properties.get("TestDieright"))-Integer.valueOf(properties.get("TestDieleft"))+1;
		softBinTestDiesDimensionalArray=new String[TestDieRow][TestDieCol];
		hardBinTestDiesDimensionalArray=new String[TestDieRow][TestDieCol];
		properties.put("TestDieRow", String.valueOf(TestDieRow));
		properties.put("TestDieCol", String.valueOf(TestDieCol));
	}
	@Override
	public LinkedHashMap<String, String> getProperties() {
		// TODO Auto-generated method stub
		return properties;
	}

	@Override
	public HashMap<String, String> getHardBinTestDie() {
		// TODO Auto-generated method stub
		return hardBinTestDieMap;
	}

	@Override
	public HashMap<String, String> getSoftBinTestDie() {
		// TODO Auto-generated method stub
		return softBinTestDieMap;
	}

	@Override
	public String[][] getHardBinTestDiesDimensionalArray() {
		// TODO Auto-generated method stub
		Integer up=Integer.valueOf(properties.get("TestDieup"));
		Integer left=Integer.valueOf(properties.get("TestDieleft"));
		Set<String> coordinateSet=hardBinTestDieMap.keySet();
		for (String coordinate : coordinateSet) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=hardBinTestDieMap.get(coordinate);
			hardBinTestDiesDimensionalArray[x][y]=value;
		}
		return hardBinTestDiesDimensionalArray;
	}

	@Override
	public String[][] getSoftBinTestDiesDimensionalArray() {
		// TODO Auto-generated method stub
		Integer up=Integer.valueOf(properties.get("TestDieup"));
		Integer left=Integer.valueOf(properties.get("TestDieleft"));
		Set<String> coordinateSet=softBinTestDieMap.keySet();
		for (String coordinate : coordinateSet) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=softBinTestDieMap.get(coordinate);
			softBinTestDiesDimensionalArray[x][y]=value;
		}
		return softBinTestDiesDimensionalArray;
	}

	@Override
	public HashMap<String, String> getMarkAndSkipDies() {
		// TODO Auto-generated method stub
		return markAndSkipMap;
	}

	@Override
	public String[][] getMarkAndSkipDiesDimensionalArray() {
		// TODO Auto-generated method stub
		Integer up=Integer.valueOf(properties.get("up"));
		Integer left=Integer.valueOf(properties.get("left"));
		Set<String> coordinateSet=markAndSkipMap.keySet();
		for (String coordinate : coordinateSet) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=markAndSkipMap.get(coordinate);
			markAndSkipDiesDimensionalArray[x][y]=value;
		}
		return markAndSkipDiesDimensionalArray;
	}

	@Override
	public TreeMap<Integer, Integer> getBinSummary() {
		// TODO Auto-generated method stub
		return binSummary;
	}
	public String[][] getAllDiesDimensionalArray()
	{
		Integer up=Integer.valueOf(properties.get("up"));
		Integer left=Integer.valueOf(properties.get("left"));
		Integer row=Integer.valueOf(properties.get("Map Rows"));
		Integer col=Integer.valueOf(properties.get("Map Cols"));
		
		String[][] allDiesArray=new String[row][col];
		Set<String> testDieCoordiante=hardBinTestDieMap.keySet();
		for (String coordinate : testDieCoordiante) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=hardBinTestDieMap.get(coordinate);
			allDiesArray[x][y]=value;
		}
		Set<String> markAndSkipCoordiante=markAndSkipMap.keySet();
		for (String coordinate : markAndSkipCoordiante) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=markAndSkipMap.get(coordinate);
			allDiesArray[x][y]=value;
		}
		return allDiesArray;
	}
	public String[][] getAllDiesDimensionalArraySoftBin()
	{
		Integer up=Integer.valueOf(properties.get("up"));
		Integer left=Integer.valueOf(properties.get("left"));
		Integer row=Integer.valueOf(properties.get("Map Rows"));
		Integer col=Integer.valueOf(properties.get("Map Cols"));
		
		String[][] allDiesArray=new String[row][col];
		Set<String> testDieCoordiante=softBinTestDieMap.keySet();
		for (String coordinate : testDieCoordiante) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=softBinTestDieMap.get(coordinate);
			allDiesArray[x][y]=value;
		}
		Set<String> markAndSkipCoordiante=markAndSkipMap.keySet();
		for (String coordinate : markAndSkipCoordiante) {
			String[] coordinateTokens=coordinate.split(":");
			Integer x=Integer.valueOf(coordinateTokens[0])-up;
			Integer y=Integer.valueOf(coordinateTokens[1])-left;
			String value=markAndSkipMap.get(coordinate);
			allDiesArray[x][y]=value;
		}
		return allDiesArray;
	}
}
