package com.vtest.it.tools;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class getSlotFromMappingDat {
	public  String get(File file) throws IOException {
		FileInputStream fios=new FileInputStream(file);
		byte[] bs = new byte[1000];
		String slot=null;
		byte[] Slot_number_BT=new byte[2];
		fios.read(bs);
		fios.close();
		for (int i = 0; i < bs.length; i++)
		{
			if (i>27&&i<30)
			{
				Slot_number_BT[i-28]=bs[i];				
			}
			if (i>29) {
				break;
			}
		}
		slot=new String(Slot_number_BT, "ASCII");
		return slot;
	}
}
