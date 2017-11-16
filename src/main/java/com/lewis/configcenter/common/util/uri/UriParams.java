package com.lewis.configcenter.common.util.uri;

import com.lewis.configcenter.common.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

public class UriParams {
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private String charset;
	
	private boolean ignoreEmptyParam;
	
	private Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
	
	public UriParams() {
		this(DEFAULT_CHARSET, false);
	}
	
	public UriParams(String charset, boolean ignoreEmptyParam) {
		this.charset = charset;
        this.ignoreEmptyParam = ignoreEmptyParam;
	}
	
	public UriParams(String charset, boolean ignoreEmptyParam, String queryString) {
		this(charset, ignoreEmptyParam);
		
		if (StringUtils.isNotBlank(queryString)) {
			if (queryString.startsWith("?")) {
				queryString = queryString.substring(1);
			}
			
			String[] parts = queryString.split("&");
			for (String part : parts) {
				if (part.length() > 0) {
					int index = part.indexOf('=');
					if (index == -1) {
						add(part, "");
					} else {
						String name = UrlUtils.decodeUrl(part.substring(0, index), charset);
						if (index == part.length() - 1) {
							add(name, "");
						} else {
							add(name, UrlUtils.decodeUrl(part.substring(index + 1), charset));
						}
					}
				}
			}
		}
	}

	public UriParams add(String key, Object value, boolean extractValueCollection) {
		if (value != null && extractValueCollection) {
			if (value instanceof Collection) {
				for (Object obj : (Collection<?>) value) {
					add(key, obj, false);
				}
				return this;
			} else if (value.getClass().isArray()) {
				int size = Array.getLength(value);
                for (int i = 0; i < size; i++) {
					Object obj = Array.get(value, i);
					add(key, obj, false);
				}
                return this;
			}
		}
		
		List<String> values = map.get(key);
		if (values == null) {
			values = new ArrayList<String>();
			map.put(key, values);
		}
		
		String val = value != null ? value.toString() : "";
		if (StringUtils.isNotEmpty(val) || !ignoreEmptyParam) {
			values.add(val);
		}
		
		return this;
	}
	
	public UriParams add(String key, Object value) {
		return add(key, value, true);
	}
	
	public UriParams add(Map<?, ?> map, boolean extractValueCollection) {
		if (map != null) {
			for (Entry<?, ?> entry : map.entrySet()) {
				Object key = entry.getKey();
				if (key != null)
					add(key.toString(), entry.getValue(), extractValueCollection);
			}
		}
		return this;
	}
	
	public UriParams add(UriParams params) {
		if (params != null) {
			for (Entry<String, List<String>> entry : params.map.entrySet()) {
				add(entry.getKey(), entry.getValue());
			}
		}
		return this;
	}
	
	public UriParams add(Map<?, ?> map) {
		if (map != null) {
			for (Entry<?, ?> entry : map.entrySet()) {
				Object key = entry.getKey();
				if (key != null) {
					add(key.toString(), entry.getValue(), true);
				}
			}
		}
		return this;
	}
	
	public List<String> get(String key) {
		return Collections.unmodifiableList(map.get(key));
	}
	
	public String getSingle(String key, boolean safe) {
		List<String> values = map.get(key);
		if (values == null || values.isEmpty()) {
			return null;
		} else if (values.size() == 1 || safe) {
			return values.get(0);
		} else {
			throw new RuntimeException("Multiple values exist for key : " + key + ", values: " + values);
		}
	}
	
	public Set<String> keySet() {
		return map.keySet();
	}
	
	public boolean isEmpty() {
		if (this.map == null || this.map.isEmpty()) {
            return true;
        }
        for (List<String> values : this.map.values()) {
            if (values.size() > 0) {
                return false;
            }
        }
        return true;
	}
	
	public UriParams remove(String key) {
		List<String> values = map.get(key);
		if (values != null) {
			values.clear();
		}
		return this;
	}
	
	public UriParams removeAll(Collection<?> keys) {
		if (keys != null) {
			for (Object key : keys) {
				if (key != null) {
					remove(key.toString());
				}
			}
		}
		return this;
	}
	
	public String join() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, List<String>> entry : map.entrySet()) {
			for (String value : entry.getValue()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(UrlUtils.encodeUrl(entry.getKey(), charset)).append("=");
				sb.append(UrlUtils.encodeUrl(value, charset));
			}
		}
		return sb.toString();
	}
	
	public Map<String, String> getMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (String name : keySet()) {
			map.put(name, getSingle(name, true));
		}
		return map;
	}
}
