package com.vtest.it.vtestinterface;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * @author shawn.sun
 * @category IT
 * @version 1.0
 * @since 2018.3.15
 */
public interface Rawdata {
	abstract LinkedHashMap<String, String> getProperties();
	abstract HashMap<String, String> getTestDieMap();
	abstract HashMap<String, String> getMarkAndSkipDieMap();
	abstract TreeMap<Integer, Integer> getbinSummary();
}
