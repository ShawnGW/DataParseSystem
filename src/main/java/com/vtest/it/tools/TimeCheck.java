package com.vtest.it.tools;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Service
public class TimeCheck {
	public boolean check(File file,Integer seconds)
	{
		return ((new Date().getTime()-file.lastModified())/1000)>seconds?true:false;
	}
}
