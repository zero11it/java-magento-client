package com.github.chen0040.magento.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class ApacheHttpClient implements HttpClient {
	private static final String DATA_ENCODING = "UTF-8";

	public String jsonPost(final String url, Map<String, String> parameters) throws IOException {
		try (CloseableHttpClient httpClient = configureClient().build()) {
			HttpPost request = new HttpPost(url);
			String body = JSON.toJSONString(parameters);
			StringEntity params = new StringEntity(body);
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			try (CloseableHttpResponse result = httpClient.execute(request)) {
				return result.getEntity() != null ? EntityUtils.toString(result.getEntity(), DATA_ENCODING) : "";
			}
		}
	}

	public String post(final String url, String body, Map<String, String> headers) throws IOException {
		try (CloseableHttpClient httpClient = configureClient().build()) {
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(body);

			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			request.setEntity(params);
			try (CloseableHttpResponse result = httpClient.execute(request)) {
				return result.getEntity() != null ? EntityUtils.toString(result.getEntity(), DATA_ENCODING) : "";
			}
		}
	}

	public String put(final String url, String body, Map<String, String> headers) throws IOException {
		try (CloseableHttpClient httpClient = configureClient().build()) {
			HttpPut request = new HttpPut(url);
			StringEntity params = new StringEntity(body);

			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			request.setEntity(params);
			try (CloseableHttpResponse result = httpClient.execute(request)) {
				return result.getEntity() != null ? EntityUtils.toString(result.getEntity(), DATA_ENCODING) : "";
			}
		}
	}

	public String delete(final String url, final Map<String, String> headers) throws IOException {
		try (CloseableHttpClient httpClient = configureClient().build()) {
			HttpDelete request = new HttpDelete(url);
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			try (CloseableHttpResponse result = httpClient.execute(request)) {
				return result.getEntity() != null ? EntityUtils.toString(result.getEntity(), DATA_ENCODING) : "";
			}
		}
	}

	public String get(final String url, final Map<String, String> headers) throws IOException {
		try (CloseableHttpClient httpClient = configureClient().build()) {
			HttpGet request = new HttpGet(url);
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			try (CloseableHttpResponse result = httpClient.execute(request)) {
				return result.getEntity() != null ? EntityUtils.toString(result.getEntity(), DATA_ENCODING) : "";
			}
		}
	}

	protected HttpClientBuilder configureClient() {
		int timeout = 60;
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000)
				.setConnectTimeout(timeout * 1000)
				.build();

		return HttpClients.custom().setDefaultRequestConfig(config);
	}
}
