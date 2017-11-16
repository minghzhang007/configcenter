package com.lewis.configcenter.common.util.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.poi.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 提供Http服务
 * @author hzlimaozhi
 *
 */
public class HttpUtils {
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static CloseableHttpClient httpClient;
	
	static {
		try {
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
				.build();
			
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			
			httpClient = HttpClients.custom()
				.setConnectionManager(connManager).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CachedHttpResponse post(String url, List<NameValuePair> parameters) throws Exception {
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(parameters, DEFAULT_CHARSET));
			HttpResponse response = httpClient.execute(post);
			return new CachedHttpResponse(url, response);
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}
	
	public static CachedHttpResponse post(String url, String entity) throws Exception {
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			post.setEntity(new StringEntity(entity, DEFAULT_CHARSET));
			HttpResponse response = httpClient.execute(post);
			return new CachedHttpResponse(url, response);
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}
	
	public static String getRequestUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		if (StringUtils.isNotBlank(query)) {
			uri += "?" + query;
		}
		return uri;
	}
	
	public static String getRequestUrlWithoutPage(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		if (StringUtils.isNotBlank(query)) {
			query = query.replaceAll("&page=\\d+", "").replaceAll("page=\\d+", "");
			if (StringUtils.isNotBlank(query)) {
				uri += "?" + query;
			}
		}
		return uri;
	}
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return StringUtils.endsWith(request.getRequestURI(), ".json") ||
			StringUtils.containsIgnoreCase(request.getContentType(), "json") ||
			StringUtils.containsIgnoreCase(request.getHeader("X-Requested-With"), "XMLHttpRequest");
	}
	
	public static void sendResponse(HttpServletResponse response, String mimeType, String charset, String responseBody) throws IOException {
		setNoCache(response);
		PrintWriter writer = null;
		try {
			ContentType contentType = ContentType.create(mimeType, charset);
			response.setContentType(contentType.toString());
			writer = response.getWriter();
			writer.print(responseBody);
			writer.flush();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	
	public static void setNoCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0);
    }
	
	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}
	
	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}
	
	public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.contains(",")) {
            String[] ips = StringUtils.split(ip, ", ");
            ip = ips[0].trim();
        }

        return ip;
    }
	
	public static String getRemoteIP(HttpServletRequest request) {
		String uip = request.getHeader("X-From-IP");
		if (StringUtils.isBlank(uip)) {
			uip = request.getHeader("X-Real-IP");
		}

		if (StringUtils.isBlank(uip)) {
			uip = request.getHeader("X-Forwarded-For");
			if (uip != null) {
				String[] ips = uip.split(",");
				if (ips.length > 1)
					uip = ips[0];
			}
		}

		if (StringUtils.isBlank(uip)) {
			uip = request.getRemoteAddr();
		}

		return StringUtils.trimToEmpty(uip);
	}
}
