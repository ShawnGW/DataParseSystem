package com.vtest.it.tools;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class HTS8689MarkToPass {
	public  void modifyMap(HashMap<String, String> Final_Bin_Map)
	{
		String[][] Map=new String[800][800];
		Set<String> keyset=Final_Bin_Map.keySet();
		Integer MaxX=0;
		Integer MaxY=0;
		for (String key : keyset) {
			Integer coordinateY=Integer.valueOf(key.substring(0,4).trim());
			Integer coordinateX=Integer.valueOf(key.substring(4,8).trim());
			if(coordinateY>MaxY)
			{
				MaxY=coordinateY;
			}
			if(coordinateX>MaxX)
			{
				MaxX=coordinateX;
			}
			Map[coordinateX][coordinateY]="R";
		}
		for (int i = 0; i < MaxX+1; i++) {
			for (int j = 0; j < MaxY+1; j++) {
				if (Map[i][j]==null&&dieCheck(i, j, Map, MaxX)) {
					Map[i][j]="1";
				}
			}
		}
		for (int i = 0; i < MaxX+1; i++) {
			for (int j = 0; j < MaxY+1; j++) {
				String key=String.format("%4s", j)+String.format("%4s", i);
				if (Map[i][j]!=null) {
					if (!Final_Bin_Map.containsKey(key)) {
						Final_Bin_Map.put(key, String.format("%4s", 1)+String.format("%4s", 1)+String.format("%4s", "0"));
					}
				}
			}
		}
	}
	private Boolean dieCheck(Integer coordinateX,Integer coordinateY,String[][] map,Integer row)
	{
		boolean upCheck=false;
		boolean downCheck=false;
		for (int i = coordinateX-row; i < coordinateX+row; i++) {
			if (i<coordinateX) {
				try {
					if (map[i][coordinateY]!=null) {
						downCheck=true;
						i=coordinateX;
					};
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else  {
				try {
					if (map[i][coordinateY]!=null) {
						upCheck=true;
						break;
					};
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		if (downCheck&&upCheck) {
			return true;
		}else {
			return false;
		}
	}
}
