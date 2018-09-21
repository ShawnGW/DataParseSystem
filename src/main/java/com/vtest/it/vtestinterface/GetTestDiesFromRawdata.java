package com.vtest.it.vtestinterface;

import java.util.HashMap;

public interface GetTestDiesFromRawdata {
	abstract HashMap<String, String> getHardBinTestDie();
	abstract HashMap<String, String> getSoftBinTestDie();
	abstract String[][] getHardBinTestDiesDimensionalArray();
	abstract String[][] getSoftBinTestDiesDimensionalArray();
}
