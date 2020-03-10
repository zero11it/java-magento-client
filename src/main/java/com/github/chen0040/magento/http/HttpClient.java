package com.github.chen0040.magento.http;

import java.io.IOException;
import java.util.Map;

public interface HttpClient {
   String post(String url, String body, Map<String, String> headers) throws IOException;

   String put(String url, String body, Map<String, String> headers) throws IOException;

   String delete(String url, Map<String, String> headers) throws IOException;

   String get(String uri, Map<String, String> headers) throws IOException;

   String jsonPost(String uri, Map<String, String> data) throws IOException;
}
