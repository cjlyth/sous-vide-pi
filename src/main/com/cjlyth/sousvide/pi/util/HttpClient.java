package com.cjlyth.sousvide.pi.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;

/**
 * Http util class for web service call
 * @author hesparza
 * @version $Id: HttpUtils.java 6062 2014-12-17 19:21:02Z hesparza $
 *
 */
public class HttpClient {
	/**
	 * Performs POST call by sending a string payload, expects a string response
	 * 
	 * @param wsUrl
	 * @param methodName
	 * @param payload
	 * @return
	 * @throws IOException
	 */
	public String doHttpRequest(final String wsUrl, final String methodName, final String payload) throws IOException {
		final String METHOD_NAME = getClass().getName() + ".getStringByStringPOST()";
		String response = null;
		URL url = null;
		HttpURLConnection httpConnection = null;
		HttpsURLConnection httpsConnection = null;
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(wsUrl);
		urlBuilder.append("/");
		urlBuilder.append(methodName);
		//urlBuilder.append("/");
		try {
			url = new URL(urlBuilder.toString());
			if (url.toString().toUpperCase().contains("HTTPS://")) {
				httpsConnection = (HttpsURLConnection) url.openConnection();
				httpsConnection.setRequestMethod("GET");
				httpsConnection.setDoOutput(true);
				httpsConnection.setDoInput(true);
				httpsConnection.setRequestProperty("Content-Type", "application/text");
				httpsConnection.setRequestProperty("Accept", "application/text");
				httpsConnection.setConnectTimeout(5000);
//				if (payload != null) {
//					outputStreamWriter = new OutputStreamWriter(httpsConnection.getOutputStream());
//					outputStreamWriter.write(payload);
//					outputStreamWriter.flush();
//					outputStreamWriter.close();
//				}
				if (httpsConnection != null) {
					if (httpsConnection.getResponseCode() == 200) {
						response = IOUtils.toString(httpsConnection.getInputStream());
					} else {
						throw new IOException("GET=[" + url.toString() + "] failed. Response Code=[" + httpsConnection.getResponseCode() + "] Response Message=[" + httpsConnection.getResponseMessage() + "]");
					}
				}
			} else {
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestMethod("GET");
				httpConnection.setDoOutput(true);
				httpConnection.setDoInput(true);
				httpConnection.setRequestProperty("Content-Type", "application/text");
				httpConnection.setRequestProperty("Accept", "application/text");
				httpConnection.setConnectTimeout(5000);
//				if (payload != null) {
//					outputStreamWriter = new OutputStreamWriter(httpConnection.getOutputStream());
//					//outputStreamWriter.write(payload);
//					outputStreamWriter.flush();
//					outputStreamWriter.close();
//				}
				if (httpConnection.getResponseCode() == 200) {
					response = IOUtils.toString(httpConnection.getInputStream());
				} else {
				    throw new IOException("GET=[" + url.toString() + "] failed. Response Code=[" + httpConnection.getResponseCode() + "] Response Message=[" + httpConnection.getResponseMessage() + "]");
				}
			}
		} catch (Exception e) {
			throw new IOException(METHOD_NAME + " threw an Exception : " + e.getMessage(), e);
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (httpsConnection != null) {
				httpsConnection.disconnect();
			}
		}
		return response;
	}
}