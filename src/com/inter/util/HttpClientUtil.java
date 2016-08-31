package com.inter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	
	private static HttpClient httpClient = null;
	
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	private HttpClientUtil() {}
	
	private static HttpClient getHttpClient() {
		if (httpClient == null) {
			synchronized (HttpClientUtil.class) {
				if (httpClient == null) {
					HttpParams params = new BasicHttpParams();
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, /*2500*/3000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, /*2500*/3000);
					SchemeRegistry schemeRegistry = new SchemeRegistry();
					schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
					PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
					/* 每个Route的最大线程数 */
					cm.setDefaultMaxPerRoute(20000);
					/* 最大线程数 */
					cm.setMaxTotal(20);
					httpClient = new DefaultHttpClient(cm, params);
				}
			}
		}
		return httpClient;
	}
	
	public static String postData(String url, Map<String, String> params) {
		try {
			HttpClient client = getHttpClient();
			if (client == null) {
				return "";
			}
			HttpPost post = new HttpPost(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "utf-8");
			post.setEntity(formEntity);
			try {
				try {
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							String responseString = EntityUtils.toString(entity, "utf-8");
							return responseString;
						} else {
							logger.info("response entity is null");
						}
					}
				} catch (Exception e) {
					logger.error("htppClientUtil post error,url=" + url, e);
				}
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			logger.error("htppClientUtil post errors,url=" + url, e);
		}
		return "";
	}

}
