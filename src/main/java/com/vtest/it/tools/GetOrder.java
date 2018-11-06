package com.vtest.it.tools;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class GetOrder {
	public ArrayList<File> Order(File[] Filelist)
	{
		ArrayList<File> files=new ArrayList<>();
		ArrayList<Integer> File_Number=new ArrayList<>();
		HashMap<Long, File> Number_File_Map=new HashMap<>();
		for (int i = 0; i < Filelist.length; i++) {
			String[] NameTokens=Filelist[i].getName().split("_");
			Integer Length=NameTokens.length;
			String RP=NameTokens[Length-4].substring(2, 3);
			String Test_Start_Time_R= (NameTokens[Length-2]+NameTokens[Length-1].substring(0, 6));
			Long Year=Long.valueOf(Test_Start_Time_R.substring(0, 4));
			Long Mouth=Long.valueOf(Test_Start_Time_R.substring(4,6));
			Long Day=Long.valueOf(Test_Start_Time_R.substring(6,8));
			Long Hour=Long.valueOf(Test_Start_Time_R.substring(8, 10));
			Long Minute=Long.valueOf(Test_Start_Time_R.substring(10,12));
			Long Second=Long.valueOf(Test_Start_Time_R.substring(12,14));
			Long Time= Year*365*24*60*60+Mouth*30*24*60*60+Day*24*60*60+Hour*60*60+Minute*60+Second;
			Long Fianl_time=Long.valueOf(RP+Time.toString());
			Number_File_Map.put(Fianl_time, Filelist[i]);
		}
		Long[] NUmbers=Number_File_Map.keySet().toArray(new Long[File_Number.size()]);
		sort(NUmbers);		
		for (int i = 0; i < NUmbers.length; i++) {
			files.add(Number_File_Map.get(NUmbers[i]));
		}
		return files;
	}
	private void sort(Long data[]) {
        for (int i = 0; i < data.length -1; i++) {  
            for (int j = 0; j < data.length - i - 1; j++) {  
                if (data[j] > data[j + 1]) {  
                	Long temp = data[j];  
                    data[j] = data[j + 1];  
                    data[j + 1] = temp;  
                }  
  
            }  
        }  
    }
}
