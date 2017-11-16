package com.lewis.configcenter.common.util.uri;

import java.util.Map;

public class UriBuilder {
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private String uri;
	
	private UriParams params;
	
	public UriBuilder(String uri) {
        this(uri, DEFAULT_CHARSET, false);
    }

    public UriBuilder(String uri, String charset) {
        this(uri, charset, false);
    }
	
	public UriBuilder(String uri, String charset, boolean ignoreEmptyParam) {
		int index = uri.indexOf("?");
		if (index == -1) {
			this.uri = uri;
			this.params = new UriParams(charset, ignoreEmptyParam);
		} else {
			this.uri = uri.substring(0, index);
			if (index != uri.length() - 1) {
				this.params = new UriParams(charset, ignoreEmptyParam, uri.substring(index + 1));
			} else {
				this.params = new UriParams(charset, ignoreEmptyParam);
			}
		}
	}
	
	public UriBuilder add(String key, Object value) {
		this.params.add(key, value);
		return this;
	}

	public UriBuilder add(UriParams params) {
		this.params.add(params);
		return this;
	}
	
	public UriBuilder add(Map<String, ?> map) {
		this.params.add(map);
		return this;
	}
	
	public String toUri() {
		StringBuilder sb = new StringBuilder(uri);
		if (!this.params.isEmpty()) {
			sb.append("?").append(this.params.join());
		}
		return sb.toString();
	}
}
