package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.MagentoException;
import com.github.chen0040.magento.http.HttpClient;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.HttpMethod;
import com.github.mgiorda.oauth.OAuthConfig;
import com.github.mgiorda.oauth.OAuthSignature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager {
	private static final Logger logger = LoggerFactory.getLogger(AbstractManager.class);

	public abstract String token();
	public abstract String baseUri();
	
	public abstract boolean oauthEnabled();
	public abstract OAuthConfig oAuth();

	protected HttpClient httpClient;

	public AbstractManager(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	private Map<String, String> buildHeaders(HttpMethod method, String uri) {
		Map<String, String> headers = new HashMap<>();
		
		if (oauthEnabled()) {
			OAuthSignature signature = oAuth().buildSignature(method, uri).create();
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

	protected String postSecure(String uri, String body, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.POST, uri);
		
		logger.debug("POST-ing @ {}:\n{}", uri, body);
		String resp;
		try {
			resp = httpClient.post(uri, body, headers);
		} catch (IOException e) {
			throw new MagentoException(e);
		}
		logger.debug("Got: {}", resp);
		
		return resp;
	}

	protected String putSecure(String uri, String body, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.PUT, uri);
		
		logger.debug("PUT-ting @ {}:\n{}", uri, body);
		String resp;
		try {
			resp = httpClient.put(uri, body, headers);
		} catch (IOException e) {
			throw new MagentoException(e);
		}
		logger.debug("Got: {}", resp);
		
		return resp;
	}

	protected String deleteSecure(String uri, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.DELETE, uri);
		
		logger.info("DELETE-ing @ " + uri);
		String resp;
		try {
			resp = httpClient.delete(uri, headers);
		} catch (IOException e) {
			throw new MagentoException(e);
		}
		logger.info("Got: {}", resp);
		
		return resp;
	}

	protected String getSecure(String uri, Logger logger) {
		Map<String, String> headers = buildHeaders(HttpMethod.GET, uri);

		logger.debug("GET-ting @ " + uri);
		String resp;
		try {
			resp = httpClient.get(uri, headers);
		} catch (IOException e) {
			throw new MagentoException(e);
		}
		logger.debug("Got: {}", resp);
		
		return resp;
	}

	protected static String escape(String text) {
		String result = text;
		
		try {
			result = URLEncoder.encode(text, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			logger.error("Failed to escape " + text, e);
		}
		
		return result;
	}

	protected static boolean validateJSON(String json) {
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
