package com.vtest.it.tools;

public class RemoveBracket {
	public static String remove(String fileName) {
		if (fileName.contains("(")) {
			fileName=fileName.replaceAll("\\(", "");
		}
		if (fileName.contains(")")) {
			fileName=fileName.replaceAll("\\)", "");
		}
		return fileName;
	}
}
