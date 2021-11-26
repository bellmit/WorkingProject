package com.smart.homewifi.mesh.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {
	public static String decode(String source){
		String result = null;
		byte[] b = Base64.getDecoder().decode(source);
		try {
			result = new String(b, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
