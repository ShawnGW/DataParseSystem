package com.vtest.it.tools;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author shawn.sun
 * @category IT
 * @version 1.0
 * @since 2018.3.15
 */
@Service
public class GetRandomChar {
	public String getRandomChar(int length) {
		StringBuffer SB=new StringBuffer();
		for (int i = 0; i < length; i++) {
			int RandomNumber=(int) ((Math.random()*25)/1)+65;
			SB.append((char)RandomNumber);
		}
		return SB.toString();
	}
}
