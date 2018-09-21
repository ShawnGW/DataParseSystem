package com.vtest.it.tools;

import org.springframework.stereotype.Service;

@Service
public class GetRandomNumber {
	public String getRandomNumber(int length) {
		StringBuffer SB=new StringBuffer();
		for (int i = 0; i < length; i++) {
			int RandomNumber=(int) ((Math.random()*6)/1);
			SB.append(RandomNumber);
		}
		return SB.toString();
	}
}
