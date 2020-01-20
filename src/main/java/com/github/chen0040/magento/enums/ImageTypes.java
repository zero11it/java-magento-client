package com.github.chen0040.magento.enums;

import java.util.HashMap;

public class ImageTypes {
	private static HashMap<String, ImageType> types;
	static {
		types.put("png", ImageType.Png);
		types.put("jpg", ImageType.Jpeg);
		types.put("jpeg", ImageType.Jpeg);
	}
	
	public static ImageType get(String key) {
		return types.get(key);
	}
}
