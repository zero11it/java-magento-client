package com.github.chen0040.magento.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

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
		
		return JSON.toJSONString(req, SerializerFeature.PrettyFormat);
	}
	
	/**
	 * Extracts an array of objects of the given type from a JSON object of the type:
	 * {
	 *    "key" : [...],
	 *    "another_key" : {...},
	 *    "yet_another_key" : 0,
	 *    ...
	 * }
	 * 
	 * as a Java List of objects of the given type.
	 * 
	 * @param json  JSON response payload to be parsed.
	 * @param key   Key corresponding to the array in the JSON object.
	 * @param clazz Class of the type of the objects in the list.
	 * @return
	 */
	public static <T> List<T> getArrayByKey(String json, String key, Class<T> clazz) {
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});

		return JSON.parseArray(resp.get(key), clazz);
	}
}
