package com.java.utilities;

public class ServiceUtility {
	public static String generateId(String prefix) {
		long timestamp = System.currentTimeMillis();
		int randomNumber = (int) (Math.random() * 100000);
		String id = prefix + timestamp + "-" + randomNumber;
		return id;
	}
}
