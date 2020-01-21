package com.github.chen0040.magento.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class RESTUtils {
	/**
	 * Wraps the object 'object' to be serialized to JSON into a larger wrapper object with key 'key', as required by
	 * certain REST APIs.
	 * e.g.
	 * 		object --> {"attribute1" : "value1", ...}
	 * 
	 * becomes
	 * 		object --> {"key" : {"attribute1" : "value1", ...}}
	 * 
	 * @param key     Key of the wrapper JSON object.
	 * @param object  The object to be serialized.
	 * @return        The JSON string of the wrapper object.
	 */
	public static <T> String payloadWrapper(String key, T object) {
		Map<String, T> req = new HashMap<String, T>();
		
		req.put(key, object);
		
		return JSON.toJSONString(req);
	}
}
