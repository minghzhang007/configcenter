package com.lewis.configcenter.common.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

public class CachedHttpResponse {
	
	private String url;
	
	private int statusCode;
	
	private byte[] entityBytes;
	
	public CachedHttpResponse(String url, HttpResponse response) {
		try {
			this.url = url;
			this.statusCode = response.getStatusLine().getStatusCode();
			this.entityBytes = EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			//
		}
	}

	public String getUrl() {
		return url;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public boolean isStatusCodeOK() {
		return this.statusCode == HttpStatus.SC_OK;
	}

	public byte[] getEntityBytes() {
		return entityBytes;
	}

	public String getEntity(String charset) {
		try {
			return new String(entityBytes, charset);
		} catch (Exception e) {
			//
			return null;
		}
	}
}
