package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.interfaces.HttpComponent;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.HttpMethod;
import com.github.mgiorda.oauth.OAuthConfig;
import com.github.mgiorda.oauth.OAuthSignature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xschen on 12/6/2017.
 */
public abstract class MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoHttpComponent.class);

	public abstract String token();
	public abstract String baseUri();
	
	public abstract boolean oauthEnabled();
	public abstract OAuthConfig oAuth();

	protected HttpComponent httpComponent;

	public MagentoHttpComponent(HttpComponent httpComponent) {
		this.httpComponent = httpComponent;
	}

	public HttpComponent getHttpComponent() {
		return httpComponent;
	}

	public void setHttpComponent(HttpComponent httpComponent) {
		this.httpComponent = httpComponent;
	}
	
	private Map<String, String> buildHeaders(HttpMethod method, String url) {
		Map<String, String> headers = new HashMap<>();
		
		if (oauthEnabled()) {
			OAuthSignature signature = oAuth().buildSignature(method, url).create();
			headers.put("Authorization", signature.getAsHeader());
		}
		else {
			if (!StringUtils.isEmpty(this.token())) {
				headers.put("Authorization", "Bearer " + this.token());
			}
		}
		
		headers.put("Content-Type", "application/json");
		
		return headers;
	}

	public String postSecure(String url, String body, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.POST, url);
		
		logger.info("POST-ing: {}", body);
		String resp = httpComponent.post(url, body, headers);
		logger.info("Got: {}", resp);
		
		return resp;
	}

	public String putSecure(String url, String body, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.PUT, url);
		
		logger.info("PUT-ting: {}", body);
		String resp = httpComponent.put(url, body, headers);
		logger.info("Got: {}", resp);
		
		return resp;
	}

	public String deleteSecure(String url, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.DELETE, url);
		
		logger.info("DELETE-ing @ " + url);
		String resp = httpComponent.delete(url, headers);
		logger.info("Got: {}", resp);
		
		return resp;
	}

	public String getSecure(String url, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.GET, url);

		logger.info("GET-ting @ " + url);
		String resp = httpComponent.get(url, headers);
		logger.info("Got: {}", resp);
		
		return resp;
	}

	public String escape(String text) {
		String result = text;
		
		try {
			result = URLEncoder.encode(text, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			logger.error("Failed to escape " + text, e);
		}
		
		return result;
	}

	protected boolean validateJSON(String json) {
		try {
			JSON.parse(json);
		}
		catch (JSONException exception) {
			return false;
		} 
		
		try {
			Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
			}.getType());

			if (data.containsKey("message")) {
				logger.error("query failed: {}", data.get("message"));
				logger.warn("trace: {}", data.get("trace"));
				return false;
			}
		}
		catch (JSONException exception) {
			return true;
		}
		return true;
	}
}
