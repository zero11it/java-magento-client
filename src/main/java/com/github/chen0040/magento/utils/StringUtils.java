package com.github.chen0040.magento.utils;

import java.nio.charset.StandardCharsets;

/**
 * Created by xschen on 12/6/2017.
 */
public class StringUtils {
	public static String stripQuotation(String s) {
		if (s.startsWith("\"")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith("\"")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public static boolean isEmpty(String text) {
		return text == null || text.equals("");
	}

	public static String cleanup(String text) {
		String[] parts = text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
		StringBuilder sb = new StringBuilder();
		
		for (String p : parts) {
			sb.append(p);
		}

		return sb.toString();
	}
	
	public static boolean isAlphanumeric(String text) {
		for (char ch : text.toCharArray()) {
			if (!Character.isLetterOrDigit(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static String toUTF8(String text) {
		byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static String stripWhitespace(String text) {
		return text.replaceAll("\\s+", "");
	}
}
