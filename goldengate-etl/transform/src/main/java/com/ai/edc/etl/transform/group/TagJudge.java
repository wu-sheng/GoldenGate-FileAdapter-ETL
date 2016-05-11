package com.ai.edc.etl.transform.group;

public class TagJudge {
	public static boolean isY2N(String tagValue){
		return "Y->N".equals(tagValue);
	}
	
	public static boolean isN2Y(String tagValue){
		return "N->Y".equals(tagValue);
	}
}
