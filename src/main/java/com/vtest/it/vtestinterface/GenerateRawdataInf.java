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
public interface GenerateRawdataInf {
	abstract void writeProperties(LinkedHashMap<String, String> properties);
	abstract void writeTestDie(HashMap<String, String> testDieMap);
	abstract void writeMarkAndSkipDie(HashMap<String, String> markAndSkipDieMap);
	abstract void writeBinSummary(TreeMap<Integer, Integer> binSmmary);
}
