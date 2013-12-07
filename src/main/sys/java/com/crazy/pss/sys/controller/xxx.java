package com.crazy.pss.sys.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

public class xxx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		get();
//		System.out.println(aa.name.toString());
//		Map<String, Serializable> aaa = new HashMap<String, Serializable>();
//		aaa.put("xxx", "xxx");
		
		System.out.println(String.valueOf("xxx"));
		System.out.println(new Long(1).toString());
		Integer[] ls = {1,2};
		System.out.println(ArrayUtils.toString(ls));
	}
	
	public enum aa{
		intdex, name
	}
	
	public static void get(){
		throw new RuntimeException();
	}

}
