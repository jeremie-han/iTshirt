package com.escape.common;

import java.util.List;
import java.util.Map;

public class CommonUtil {
	
	public CommonUtil(){
		
	}
	
	public static boolean valid(String str){
		if(str == null || str.isEmpty() || str == "undefined"){
			return false;
		}
		return true;
	}
	
	public static <T>  boolean valid(List<T> list){
		if(list == null || list.size() == 0){
			return false;
		}
		return true;
	}
	
	public static <K, V> boolean valid(Map<K,V> map){
		if(map == null || map.size() == 0){
			return false;
		}
		return true;
	}
	
	public static String ckNullStr(String value) {
	    return value == null ? "" : value;
	}
	
	public static <T> T ckNullStr(T value, T defaultValue) {
	    return value == null ? defaultValue : value;
	}

}
